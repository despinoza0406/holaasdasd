package hubble.backend.providers.parsers.implementations.jira;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hubble.backend.core.utils.EncoderHelper;
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
    private IssueRepository issueRepository;

    private final Logger logger = LoggerFactory.getLogger(JiraDataParserImpl.class);

    @Override
    public JiraIssuesProviderModel parse(JSONObject data) {
        if (data == null) {
            return null;
        }

        JiraIssuesProviderModel jiraModel = this.extract(data);

        return jiraModel;
    }

    @Override
    public void run() {
        String jiraUser = jiraTransport.getEnvironment().getUser();
        String jiraPassword = jiraTransport.getEnvironment().getPassword();
        String encodedAuthString = EncoderHelper.encodeToBase64(jiraUser, jiraPassword);
        JiraIssuesProviderModel jiraModel;
        ArrayList<JiraIssueModel> issues;
        IssueStorage issueStorage;

        jiraTransport.setEncodedCredentials(encodedAuthString);
        String[] projectsKey = jiraTransport.getConfiguration().getProjectKey().split(",");
        for(String project : projectsKey) {
            JSONObject response = jiraTransport.getAllIssuesByProject(project);
            jiraModel = this.parse(response);
            issues = jiraModel.getIssues();

            for (JiraIssueModel issue : issues) {
                issueStorage = jiraMapperConfiguration.mapToIssueStorage(issue);
                issueRepository.save(issueStorage);
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
        JiraIssuesProviderModel jiraDataModel;

        try {
            jiraDataModel = objMapper.readValue(dataStream, JiraIssuesProviderModel.class);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }

        return jiraDataModel;
    }
}
