package hubble.backend.providers.parsers.implementations.ppm;

import hubble.backend.providers.configurations.PpmConfiguration;
import hubble.backend.providers.configurations.mappers.ppm.PpmMapperConfiguration;
import hubble.backend.providers.models.ppm.PpmApplicationProviderModel;
import hubble.backend.providers.parsers.interfaces.ppm.PpmApplicationParser;
import hubble.backend.providers.transports.interfaces.PpmTransport;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PpmApplicationParserImpl implements PpmApplicationParser {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private PpmTransport ppmTransport;
    @Autowired
    private PpmConfiguration configuration;
    @Autowired
    private PpmMapperConfiguration mapperConfiguration;

    private final Logger logger = LoggerFactory.getLogger(PpmApplicationParserImpl.class);

    @Override
    public PpmApplicationProviderModel parse(JSONObject data) {
        if (data == null) {
            return null;
        }

        JSONArray ppmProgramIssueFields = data.getJSONArray("field");

        PpmApplicationProviderModel ppmProgramIssue = this.mapToPpmApplicationModel(ppmProgramIssueFields);

        return ppmProgramIssue;
    }

    @Override
    public ApplicationStorage convert(PpmApplicationProviderModel ppmApplication) {
        return mapperConfiguration.mapToApplicationStorage(ppmApplication);
    }

    @Override
    public void run() {
        String encodedAuthString = ppmTransport.encodeToBase64(
                ppmTransport.getEnvironment().getUser(),
                ppmTransport.getEnvironment().getPassword());
        ApplicationStorage application;
        List<JSONObject> requestsToBeParsed = new ArrayList();
        List<JSONObject> detailedRequests = new ArrayList();
        List<String> requestTypeIds = ppmTransport.getConfiguredRequestTypes(encodedAuthString);

        for (String id : requestTypeIds) {
            requestsToBeParsed.addAll(ppmTransport.getRequests(encodedAuthString, id));
        }

        for (JSONObject request : requestsToBeParsed) {
            detailedRequests.add(ppmTransport.getRequestDetails(encodedAuthString, request.getString("id")));
        }

        for (JSONObject detailedRequest : detailedRequests) {
            application = this.convert(this.parse(detailedRequest));
            if (!applicationRepository.exist(application)) {
                applicationRepository.save(application);
            }
        }
    }

    private PpmApplicationProviderModel mapToPpmApplicationModel(JSONArray ppmRequest) {
        PpmApplicationProviderModel application = new PpmApplicationProviderModel();
        application.setName(getValue(ppmRequest, configuration.getApplicationFieldName()));
        application.setApplicationId(resolveApplicationIdFromConfiguration(application.getName()));
        return application;
    }

    private String getValue(JSONArray issueFields, String fieldName) {
        String valueToReturn;
        String keyName = "";
        for (int x = 0; x < issueFields.length(); x++) {
            if (fieldName.equals(issueFields.getJSONObject(x).getString("token"))) {
                keyName = issueFields.getJSONObject(x).keys().next().toString();
                valueToReturn = issueFields.getJSONObject(x).getString(keyName);
                return valueToReturn;
            }
        }
        return "";
    }

    private String resolveApplicationIdFromConfiguration(String applicationName) {
        HashMap<String,String> applicationsIdMap = configuration.getApplicationValueToIdMap();
        Set<String> keySet = applicationsIdMap.keySet();
        for (String key : keySet) {
            if (applicationName.equals(applicationsIdMap.get(key))) {
                return key;
            }
        }
        logger.warn("Ppm field for applications and ids map not correctly"
                + " configured in properties file for specified app name: "
                + applicationName +" from fieldName: "+configuration.getApplicationFieldName());
        return null;
    }
}
