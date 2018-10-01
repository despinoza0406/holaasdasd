package hubble.backend.providers.parsers.implementations.jira;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hubble.backend.core.enums.Results;
import hubble.backend.core.utils.EncoderHelper;
import hubble.backend.providers.configurations.JiraConfiguration;
import hubble.backend.providers.configurations.factories.TaskRunnerExecutionFactory;
import hubble.backend.providers.configurations.mappers.jira.JiraMapperConfiguration;
import hubble.backend.providers.models.jira.JiraIssueModel;
import hubble.backend.providers.models.jira.JiraIssuesProviderModel;
import hubble.backend.providers.parsers.interfaces.jira.JiraDataParser;
import hubble.backend.providers.transports.interfaces.JiraTransport;
import hubble.backend.storage.models.IssueStorage;
import hubble.backend.storage.repositories.IssueRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import hubble.backend.storage.repositories.TaskRunnerRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JiraDataParserImpl implements JiraDataParser {

    @Autowired
    private JiraTransport jiraTransport;
    @Autowired
    private JiraMapperConfiguration jiraMapperConfiguration;
    @Autowired
    private JiraConfiguration configuration;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private TaskRunnerExecutionFactory executionFactory;
    @Autowired
    private TaskRunnerRepository taskRunnerRepository;

    private final Logger logger = LoggerFactory.getLogger(JiraDataParserImpl.class);

    @Override
    public JiraIssuesProviderModel parse(JSONObject data) {
        if (data == null) {
            logger.warn("No se trajo data de jira");
            return null;
        }

        JiraIssuesProviderModel jiraModel = this.extract(data);

        return jiraModel;
    }

    @Override
    public void run() {

        try {
            jiraTransport.setError("");
            jiraTransport.setResult(Results.RESULTS.SUCCESS);
            if (configuration.taskEnabled()) {
                String jiraUser = "";
                String jiraPassword = "";

                try {
                    jiraUser = jiraTransport.getEnvironment().getUser();
                    jiraPassword = jiraTransport.getEnvironment().getPassword();

                } catch (NullPointerException e) {
                    logger.error("Error en environment de jira. Por favor revisar los valores suministrados");
                    jiraTransport.setResult(Results.RESULTS.FAILURE);
                    jiraTransport.setError("Error en environment de jira. Por favor revisar los valores suministrados");
                }

                String encodedAuthString = EncoderHelper.encodeToBase64(jiraUser, jiraPassword);
                JiraIssuesProviderModel jiraModel;
                ArrayList<JiraIssueModel> issues;
                IssueStorage issueStorage;

                jiraTransport.setEncodedCredentials(encodedAuthString);
                String[] projectsKey = null;

                try {
                    projectsKey = jiraTransport.getConfiguration().getProjectKeys();
                } catch (NullPointerException e) {
                    logger.error("Error en la configuracion de jira. Por favor revisar los valores suministrados");
                    jiraTransport.setResult(Results.RESULTS.FAILURE);
                    jiraTransport.setError("Error en la configuracion de jira. Por favor revisar los valores suministrados");
                }
                if (projectsKey != null) {
                    for (String project : projectsKey) {
                        JSONObject response;
                        int startAt = 0;
                        int totalIssues = 0;
                        int maxResults = 0;
                        try {
                            response = jiraTransport.getIssuesByProject(project, startAt);
                            totalIssues = response.getInt("total");
                            maxResults = response.getInt("maxResults");
                        } catch (NullPointerException e) {
                            logger.error("No se obtuvo respuesta del proyecto " + project);
                            jiraTransport.setResult(Results.RESULTS.FAILURE);
                            jiraTransport.setError("No se obtuvo respuesta del proyecto " + project);
                            break;
                        }
                        do {
                            response = jiraTransport.getIssuesByProject(project, startAt);
                            jiraModel = this.parse(response);
                            try {
                                issues = jiraModel.getIssues();
                            } catch (Exception e) {
                                logger.error("No se pudieron obtener los issues de jira del proyecto " + project);
                                jiraTransport.setResult(Results.RESULTS.FAILURE);
                                jiraTransport.setError("No se pudieron obtener los issues de jira del proyecto " + project);
                                break;
                            }
                            for (JiraIssueModel issue : issues) {
                                issueStorage = jiraMapperConfiguration.mapToIssueStorage(issue);
                                issueRepository.save(issueStorage);
                            }
                            startAt = startAt + maxResults;
                        }
                        while (startAt < totalIssues);

                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            jiraTransport.setError("Algo paso");
            jiraTransport.setResult(Results.RESULTS.FAILURE);
        }finally {
            for (String hubbleAppName : configuration.getValuesToIdMap().keySet()) {
                taskRunnerRepository.save(executionFactory.createExecution("jira", hubbleAppName, jiraTransport.getResult(), jiraTransport.getError()));
            }
        }
    }

    public JiraIssuesProviderModel extract(JSONObject data) {
        if (data == null) {
            return null;
        }

        byte[] dataBytes = data.toString().getBytes();
        InputStream dataStream = new ByteArrayInputStream(dataBytes);

        ObjectMapper objMapper = new ObjectMapper();
        objMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
        JiraIssuesProviderModel jiraDataModel;

        try {
            jiraDataModel = objMapper.readValue(dataStream, JiraIssuesProviderModel.class); //Falla aca y solo con crm
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }

        return jiraDataModel;
    }
}
