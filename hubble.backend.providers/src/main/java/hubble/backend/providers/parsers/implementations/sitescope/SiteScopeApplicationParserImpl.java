package hubble.backend.providers.parsers.implementations.sitescope;

import hubble.backend.providers.configurations.SiteScopeConfiguration;
import hubble.backend.providers.configurations.mappers.sitescope.SiteScopeMapperConfiguration;
import hubble.backend.providers.models.sitescope.SiteScopeApplicationProviderModel;
import hubble.backend.providers.parsers.interfaces.sitescope.SiteScopeApplicationParser;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SiteScopeApplicationParserImpl implements SiteScopeApplicationParser {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private SiteScopeTransport siteScopeTransport;
    @Autowired
    private SiteScopeConfiguration configuration;
    @Autowired
    private SiteScopeMapperConfiguration mapperConfiguration;

    private final Logger logger = LoggerFactory.getLogger(SiteScopeApplicationParserImpl.class);

    @Override
    public SiteScopeApplicationProviderModel parse(JSONObject data) {
        if (data == null) {
            return null;
        }

        JSONArray issueFields = data.getJSONArray("Fields");
        SiteScopeApplicationProviderModel almApplication = this.mapToSiteScopeModel(issueFields);

        return almApplication;
    }

    @Override
    public void run() {
        siteScopeTransport.login();
        Map<String, String> cookies = siteScopeTransport.getSessionCookies();
        JSONObject allDefects = siteScopeTransport.getAllDefects(cookies);
        List<JSONObject> defects = this.parseList(allDefects);
        for (JSONObject defect : defects) {
            ApplicationStorage application = this.convert(this.parse(defect));
            if (!applicationRepository.exist(application)) {
                applicationRepository.save(application);
            }
        }
        siteScopeTransport.logout();
    }

    @Override
    public List<JSONObject> parseList(JSONObject data) {
        JSONArray jsonArray = data.getJSONArray("entities");
        List<JSONObject> dataList = new ArrayList<>();

        for (int x = 0; x < jsonArray.length(); x++) {
            dataList.add(jsonArray.getJSONObject(x));
        }

        return dataList;
    }

    @Override
    public ApplicationStorage convert(SiteScopeApplicationProviderModel application) {
        return mapperConfiguration.mapToApplicationStorage(application);
    }

    private SiteScopeApplicationProviderModel mapToSiteScopeModel(JSONArray almIssue) {
        SiteScopeApplicationProviderModel application = new SiteScopeApplicationProviderModel();
        application.setName(getValue(almIssue, configuration.getApplicationFieldName()));
        application.setApplicationId(resolveApplicationIdFromConfiguration(application.getName()));
        return application;
    }

    private String getValue(JSONArray issueFields, String fieldName) {
        String valueToReturn;
        for (int x = 0; x < issueFields.length(); x++) {

            if (!fieldName.equals(issueFields.getJSONObject(x).getString("Name"))) {
                continue;
            }

            JSONArray valueArray = issueFields.getJSONObject(x).getJSONArray("values");
            if (valueArray.length() <= 0 || !valueArray.getJSONObject(0).has("value")) {
                return null;
            }

            JSONObject values = valueArray.getJSONObject(0);
            valueToReturn = values.getString("value");
            return valueToReturn;
        }

        logger.debug("SITESCOPE: no name field found");
        return "";
    }

    public String resolveApplicationIdFromConfiguration(String applicationName) {
        String[] applicationsIdMap = configuration.getApplicationValueToIdMap().split(",");
        for (String applicationsIdMap1 : applicationsIdMap) {
            if (applicationName.equals(applicationsIdMap1.split(":")[0])) {
                return applicationsIdMap1.split(":")[1];
            }
        }
        logger.debug("ALM: field for applications and ids map not correctly configured in properties file");
        return null;
    }



}
