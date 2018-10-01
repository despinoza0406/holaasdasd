package hubble.backend.providers.parsers.implementations.ppm;

import hubble.backend.core.enums.Results;
import hubble.backend.providers.configurations.PpmConfiguration;
import hubble.backend.providers.configurations.factories.TaskRunnerExecutionFactory;
import hubble.backend.providers.configurations.mappers.ppm.PpmMapperConfiguration;
import hubble.backend.providers.models.ppm.PpmProgramIssueProviderModel;
import hubble.backend.providers.parsers.interfaces.ppm.PpmDataParser;
import hubble.backend.providers.transports.interfaces.PpmTransport;
import hubble.backend.storage.models.WorkItemStorage;
import hubble.backend.storage.repositories.TaskRunnerRepository;
import hubble.backend.storage.repositories.WorkItemRepository;
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
public class PpmDataParserImpl implements PpmDataParser {

    @Autowired
    PpmConfiguration configuration;
    @Autowired
    PpmTransport ppmTransport;
    @Autowired
    PpmMapperConfiguration mapper;
    @Autowired
    WorkItemRepository workItemRepository;
    @Autowired
    private TaskRunnerExecutionFactory executionFactory;
    @Autowired
    private TaskRunnerRepository taskRunnerRepository;

    private final Logger logger = LoggerFactory.getLogger(PpmDataParserImpl.class);

    @Override
    public void run() {
        ppmTransport.setError("");
        ppmTransport.setResult(Results.RESULTS.SUCCESS);
        try {
            if (configuration.taskEnabled()) {
                String encodedAuthString = "";
                try {
                    encodedAuthString = ppmTransport.encodeToBase64(
                            ppmTransport.getEnvironment().getUser(),
                            ppmTransport.getEnvironment().getPassword());
                } catch (NullPointerException e) {
                    logger.error("Error en environment de ppm. Por favor revisar los valores suministrados");
                    ppmTransport.setResult(Results.RESULTS.FAILURE);
                    ppmTransport.setError("Error en environment de ppm. Por favor revisar los valores suministrados");
                }

                List<String> requestTypeIds = ppmTransport.getConfiguredRequestTypes(encodedAuthString);
                List<JSONObject> requestsToBeParsed = new ArrayList<JSONObject>();
                List<JSONObject> detailedRequests = new ArrayList<JSONObject>();
                try {
                    for (String id : requestTypeIds) {
                        requestsToBeParsed.addAll(ppmTransport.getRequests(encodedAuthString, id));
                    }

                    for (JSONObject request : requestsToBeParsed) {
                        JSONObject reqDetails = ppmTransport.getRequestDetails(encodedAuthString, request.getString("id"));
                        if (reqDetails != null) {
                            detailedRequests.add(reqDetails);
                        }
                    }
                } catch (NullPointerException ex) {
                    logger.error(ex.getMessage());
                    ppmTransport.setResult(Results.RESULTS.FAILURE);
                }
                if (ppmTransport.getResult().equals(Results.RESULTS.SUCCESS) && detailedRequests.isEmpty()) {
                    ppmTransport.setResult(Results.RESULTS.NO_DATA);
                }

                for (JSONObject detailedRequest : detailedRequests) {
                    WorkItemStorage workItem = this.convert(this.parse(detailedRequest));
                    workItemRepository.save(workItem);
                }

            }
        }catch (Exception e){
            logger.error(e.getMessage());
            ppmTransport.setError("Algo paso");
            ppmTransport.setResult(Results.RESULTS.FAILURE);
        }finally {
            for (String hubbleAppName : configuration.getApplicationValueToIdMap().keySet()) {
                taskRunnerRepository.save(executionFactory.createExecution("ppm", hubbleAppName, ppmTransport.getResult(), ppmTransport.getError()));
            }
        }
    }

    @Override
    public PpmProgramIssueProviderModel parse(JSONObject data) {
        if (data == null) {
            return null;
        }

        JSONArray ppmProgramIssueFields = data.getJSONArray("field");

        PpmProgramIssueProviderModel ppmProgramIssue = this.mapToPpmProgramIssue(ppmProgramIssueFields);

        return ppmProgramIssue;
    }

    @Override
    public WorkItemStorage convert(PpmProgramIssueProviderModel ppmProgramIssueProviderModel) {
        return mapper.mapToWorkItemStorage(ppmProgramIssueProviderModel);
    }

    public PpmProgramIssueProviderModel mapToPpmProgramIssue(JSONArray ppmIssue) {
        PpmProgramIssueProviderModel model = new PpmProgramIssueProviderModel();

        model.setId(Integer.valueOf(getValue(ppmIssue, "REQ.REQUEST_ID")));
        model.setContactEmail(getValue(ppmIssue, "REQ.CONTACT_EMAIL"));
        model.setLastUpdateDate(getValue(ppmIssue, "REQ.ENTITY_LAST_UPDATE_DATE"));
        model.setContactName(getValue(ppmIssue, "REQ.CONTACT_NAME"));
        model.setCreatedDate(getValue(ppmIssue, "REQ.CREATION_DATE"));
        model.setStatusCode(getValue(ppmIssue, "REQ.STATUS_CODE"));
        model.setWorkflowName(getValue(ppmIssue, "REQ.WORKFLOW_NAME"));
        model.setDescription(getValue(ppmIssue, "REQ.DESCRIPTION"));
        model.setProposedSolution(getValue(ppmIssue, "REQD.P_PROPOSED_SOL"));
        model.setDetailedDescription(getValue(ppmIssue, "REQD.P_DETAILED_DESC"));
        model.setAssignee(getValue(ppmIssue, "REQ.ASSIGNED_TO_NAME"));
        model.setDepartment(getValue(ppmIssue, "REQ.DEPARTMENT_NAME"));
        model.setCreatedBy(getValue(ppmIssue, "REQ.CREATED_BY"));
        model.setStatusName(getValue(ppmIssue, "REQ.STATUS_NAME"));
        model.setCompanyName(getValue(ppmIssue, "REQ.COMPANY_NAME"));
        model.setPriority(getValue(ppmIssue, "REQ.PRIORITY_NAME"));
        model.setBusinessApplication(getValue(ppmIssue, configuration.getApplicationFieldName()));
        model.setRequestType(getValue(ppmIssue, "REQ.REQUEST_TYPE_NAME"));
        model.setApplicationId(resolveApplicationIdFromConfiguration(model.getBusinessApplication()));
        model.setPercentComplete(Integer.valueOf(getValue(ppmIssue, "REQ.PERCENT_COMPLETE")));
        model.setDueDate(getValue(ppmIssue, "REQD.P_DUE_DATE"));
        model.setProviderName(configuration.getProviderName());
        model.setProviderOrigin(configuration.getProviderOrigin());

        return model;
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
                + applicationName);
        return null;
    }



}
