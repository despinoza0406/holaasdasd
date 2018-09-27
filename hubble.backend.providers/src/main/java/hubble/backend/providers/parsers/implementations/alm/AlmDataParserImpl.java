package hubble.backend.providers.parsers.implementations.alm;

import hubble.backend.core.enums.Results;
import hubble.backend.providers.configurations.AlmConfiguration;
import hubble.backend.providers.configurations.factories.TaskRunnerExecutionFactory;
import hubble.backend.providers.configurations.mappers.alm.AlmMapperConfiguration;
import hubble.backend.providers.models.alm.AlmDefectProviderModel;
import hubble.backend.providers.parsers.interfaces.alm.AlmDataParser;
import hubble.backend.providers.transports.interfaces.AlmTransport;
import hubble.backend.storage.models.IssueStorage;
import hubble.backend.storage.repositories.IssueRepository;

import java.util.*;

import static org.apache.commons.lang.StringUtils.EMPTY;

import hubble.backend.storage.repositories.TaskRunnerRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
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
    @Autowired
    private TaskRunnerExecutionFactory executionFactory;
    @Autowired
    private TaskRunnerRepository taskRunnerRepository;

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

    @Override
    public List<JSONObject> parseList(JSONObject data) {
        JSONArray jsonArray = data.getJSONArray("entities");
        List<JSONObject> dataList = new ArrayList<>();

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
        model.setDescription(Jsoup.parse(getValue(almIssue, "description")).text());
        model.setDetectedOnRelease(getValue(almIssue, "detected-in-rel"));
        model.setId(Integer.valueOf(getValue(almIssue, "id")));
        model.setModifiedDate(getValue(almIssue, "last-modified"));
        model.setPriority(getValue(almIssue, "priority"));
        model.setProject(getValue(almIssue, "project"));
        model.setRegisteredDate(getValue(almIssue, "creation-time"));
        model.setReproducible(getValue(almIssue, "reproducible"));
        model.setSeverity(getValue(almIssue, "severity"));
        model.setTitle(getValue(almIssue, "name"));
        model.setDetectedBy(getValue(almIssue, "detected-by"));
        try {
            model.setStatus(getValue(almIssue, configuration.getStatusFieldName()));
            model.setBusinessApplication(getValue(almIssue, configuration.getApplicationFieldName()));
            model.setApplicationId(resolveApplicationIdFromConfiguration(model.getBusinessApplication()));
            model.setTransaction(getValue(almIssue, configuration.getTransactionFieldName()));
            model.setProviderName(configuration.getProviderName());
            model.setProviderOrigin(configuration.getProviderOrigin());
        }catch (NullPointerException e){
            almTransport.setResult(Results.RESULTS.FAILURE);
            almTransport.setError("Error en la configuracion de alm. Por favor revisar los valores suministrados");
            logger.error("Error en la configuracion de alm. Por favor revisar los valores suministrados");
        }
        return model;
    }

    @Override
    public IssueStorage convert(AlmDefectProviderModel almProviderModel) {
        return mapperConfiguration.maptoIssueStorage(almProviderModel);
    }

    @Override
    public void run() {
        List<IssueStorage> issueStorageList = new ArrayList<>();
        try{
            if(configuration.taskEnabled()) {

                try {
                    almTransport.login();
                } catch (NullPointerException e) {
                    almTransport.setResult(Results.RESULTS.FAILURE);
                    almTransport.setError("Error en el login a alm");
                    logger.error("Error en environment de alm. Por favor revisar los valores suministrados");
                }
                Map<String, String> cookies = almTransport.getSessionCookies();
                int startInd = 1;
                int cantDefects;
                do {
                    JSONObject allDefects = null;
                    try {
                        allDefects = almTransport.getOpenDefects(cookies, startInd);
                    } catch (NullPointerException e) {
                        almTransport.setError("Error de conexion");
                        almTransport.setResult(Results.RESULTS.FAILURE);
                        logger.error("Error de conexion a alm");
                        break;
                    }
                    cantDefects = (allDefects.getInt("TotalResults"));
                    List<JSONObject> defects = this.parseList(allDefects);
                    for (JSONObject defect : defects) {
                        IssueStorage issue = this.convert(this.parse(defect));
                        issueStorageList.add(issue);
                    }
                    issueRepository.save(issueStorageList);
                    startInd += defects.size();
                } while (cantDefects >= startInd);
                almTransport.logout();
            }
        }catch (Exception e){
            logger.error("Algo paso");
            almTransport.setError("Algo paso");
            almTransport.setResult(Results.RESULTS.FAILURE);
        }finally {
            for (String hubbleAppName : configuration.getApplicationValueToIdMap().keySet()) {
                taskRunnerRepository.save(executionFactory.createExecution("alm", hubbleAppName, almTransport.getResult(), almTransport.getError()));
            }
        }
    }

    private String getValue(JSONArray issueFields, String fieldName) {
        String valueToReturn;
        for (int x = 0; x < issueFields.length(); x++) {
            if (!fieldName.equals(issueFields.getJSONObject(x).getString("Name"))) {
                continue;
            }
            JSONArray valueArray = issueFields.getJSONObject(x).getJSONArray("values");
            if (valueArray.length() <= 0 || !valueArray.getJSONObject(0).has("value")) {
                return EMPTY;
            }

            JSONObject values = valueArray.getJSONObject(0);
            valueToReturn = values.getString("value");
            return valueToReturn;
        }

        logger.debug("ALM: Name field is empty.");
        return EMPTY;
    }

    private String resolveApplicationIdFromConfiguration(String applicationName) {

        HashMap<String,String> applicationsIdMap = configuration.getApplicationValueToIdMap();
        Set<String> keySet = applicationsIdMap.keySet();
        for (String key : keySet) {
            if (applicationName.equals(applicationsIdMap.get(key))) {
                return key;
            }
        }

        logger.debug("ALM: field for applications and ids map not correctly configured in properties file");
        return null;
    }

}
