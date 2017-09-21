package hubble.backend.providers.parsers.implementations.alm;

import hubble.backend.providers.configurations.AlmConfiguration;
import hubble.backend.providers.configurations.mappers.alm.AlmMapperConfiguration;
import hubble.backend.providers.models.alm.AlmDefectProviderModel;
import hubble.backend.providers.parsers.interfaces.alm.AlmDataParser;
import hubble.backend.providers.transports.interfaces.AlmTransport;
import hubble.backend.storage.models.IssueStorage;
import hubble.backend.storage.repositories.IssueRepository;
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
public class AlmDataParserImpl implements AlmDataParser {

    @Autowired
    private AlmConfiguration configuration;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private AlmTransport almTransport;
    @Autowired
    private AlmMapperConfiguration mapperConfiguration;

    private final Logger logger = LoggerFactory.getLogger(AlmDataParserImpl.class);

    @Override
    public AlmDefectProviderModel parse(JSONObject data) {
        if (data == null) {
            return null;
        }

        JSONArray issueFields = data.getJSONArray("Fields");

        AlmDefectProviderModel almIssue = this.mapToAlmModel(issueFields);

        return almIssue;
    }

    public List<JSONObject> parseList(JSONObject data) {
        JSONArray jsonArray = data.getJSONArray("entities");
        List<JSONObject> dataList = new ArrayList<JSONObject>();

        for (int x = 0; x < jsonArray.length(); x++) {
            dataList.add(jsonArray.getJSONObject(x));
        }

        return dataList;
    }

    public AlmDefectProviderModel mapToAlmModel(JSONArray almIssue) {
        AlmDefectProviderModel model = new AlmDefectProviderModel();

        model.setAssignee(getValue(almIssue, "owner"));
        model.setClosedDate(getValue(almIssue, "closing-date"));
        model.setCorrectedOnRelease(getValue(almIssue, "target-rel"));
        model.setDescription(getValue(almIssue, "description"));
        model.setDetectedOnRelease(getValue(almIssue, "detected-in-rel"));
        model.setId(Integer.valueOf(getValue(almIssue, "id")));
        model.setModifiedDate(getValue(almIssue, "last-modified"));
        model.setPriority(getValue(almIssue, "priority"));
        model.setProject(getValue(almIssue, "project"));
        model.setRegisteredDate(getValue(almIssue, "creation-time"));
        model.setReproducible(getValue(almIssue, "reproducible"));
        model.setSeverity(getValue(almIssue, "severity"));
        model.setStatus(getValue(almIssue, configuration.getStatusFieldName()));
        model.setTitle(getValue(almIssue, "name"));
        model.setDetectedBy(getValue(almIssue, "detected-by"));
        model.setBusinessApplication(getValue(almIssue, configuration.getApplicationFieldName()));
        model.setApplicationId(resolveApplicationIdFromConfiguration(model.getBusinessApplication()));
        model.setTransaction(getValue(almIssue, configuration.getTransactionFieldName()));
        model.setProviderName(configuration.getProviderName());
        model.setProviderOrigin(configuration.getProviderOrigin());

        return model;
    }

    @Override
    public IssueStorage convert(AlmDefectProviderModel almProviderModel) {
        return mapperConfiguration.maptoIssueStorage(almProviderModel);
    }

    @Override
    public void run() {
        almTransport.login();
        Map<String, String> cookies = almTransport.getSessionCookies();
        IssueStorage issue;
        JSONObject allDefects = almTransport.getAllDefects(cookies);
        List<JSONObject> defects = this.parseList(allDefects);
        for (JSONObject defect : defects) {
            issue = this.convert(this.parse(defect));
            if (!issueRepository.exist(issue)) {
                issueRepository.save(issue);
            }
        }
        almTransport.logout();
    }

    private String getValue(JSONArray issueFields, String fieldName) {
        JSONObject values = new JSONObject();
        JSONArray valueArray = new JSONArray();
        String valueToReturn;
        for (int x = 0; x < issueFields.length(); x++) {
            if (fieldName.equals(issueFields.getJSONObject(x).getString("Name"))) {
                valueArray = issueFields.getJSONObject(x).getJSONArray("values");
                if (valueArray.length() > 0 && valueArray.getJSONObject(0).has("value")) {
                    values = valueArray.getJSONObject(0);
                    valueToReturn = values.getString("value");
                    return valueToReturn;
                } else {
                    return "";
                }

            }
        }
        return "";
    }

    private String resolveApplicationIdFromConfiguration(String applicationName) {
        String[] applicationsIdMap = configuration.getApplicationValueToIdMap().split(",");
        for (int x = 0; x < applicationsIdMap.length; x++) {
            if (applicationName.equals(applicationsIdMap[x].split(":")[0])) {
                return applicationsIdMap[x].split(":")[1];
            }
        }
        logger.debug("Alm field for applications and ids map not correctly"
                + " configured in properties file for specified app name: "
                + applicationName);
        return null;
    }

}
