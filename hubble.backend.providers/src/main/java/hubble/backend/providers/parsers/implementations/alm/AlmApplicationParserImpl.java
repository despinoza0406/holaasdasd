package hubble.backend.providers.parsers.implementations.alm;

import hubble.backend.providers.configurations.AlmConfiguration;
import hubble.backend.providers.configurations.mappers.alm.AlmMapperConfiguration;
import hubble.backend.providers.models.alm.AlmApplicationProviderModel;
import hubble.backend.providers.parsers.interfaces.alm.AlmApplicationParser;
import hubble.backend.providers.transports.interfaces.AlmTransport;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlmApplicationParserImpl implements AlmApplicationParser {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private AlmTransport almTransport;
    @Autowired
    private AlmConfiguration configuration;
    @Autowired
    private AlmMapperConfiguration mapperConfiguration;

    private final Logger logger = LoggerFactory.getLogger(AlmApplicationParserImpl.class);

    @Override
    public AlmApplicationProviderModel parse(JSONObject data) {
        if (data == null) {
            return null;
        }

        JSONArray issueFields = data.getJSONArray("Fields");
        AlmApplicationProviderModel almApplication = this.mapToAlmModel(issueFields);

        return almApplication;
    }

    @Override
    public void run() {
        almTransport.login();
        Map<String, String> cookies = almTransport.getSessionCookies();
        JSONObject allDefects = almTransport.getOpenDefects(cookies,1);
        List<JSONObject> defects = this.parseList(allDefects);
        for (JSONObject defect : defects) {
            ApplicationStorage application = this.convert(this.parse(defect));
            if (!applicationRepository.exist(application)) {
                applicationRepository.save(application);
            }
        }
        almTransport.logout();
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
    public ApplicationStorage convert(AlmApplicationProviderModel application) {
        return mapperConfiguration.mapToApplicationStorage(application);
    }

    private AlmApplicationProviderModel mapToAlmModel(JSONArray almIssue) {
        AlmApplicationProviderModel application = new AlmApplicationProviderModel();
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

        logger.debug("ALM: no name field found");
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
