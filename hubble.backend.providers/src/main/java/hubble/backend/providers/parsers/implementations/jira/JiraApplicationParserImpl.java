package hubble.backend.providers.parsers.implementations.jira;

import hubble.backend.core.utils.EncoderHelper;
import hubble.backend.providers.configurations.mappers.jira.JiraMapperConfiguration;
import hubble.backend.providers.models.jira.JiraApplicationProviderModel;
import hubble.backend.providers.parsers.interfaces.jira.JiraApplicationParser;
import hubble.backend.providers.transports.interfaces.JiraTransport;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JiraApplicationParserImpl implements JiraApplicationParser {

    @Autowired
    private JiraTransport jiraTransport;
    @Autowired
    private JiraMapperConfiguration jiraMapperConfiguration;
    @Autowired
    private ApplicationRepository applicationRepository;

    private final Logger logger = LoggerFactory.getLogger(JiraApplicationParserImpl.class);

    @Override
    public JiraApplicationProviderModel parse(JSONObject data) {
        if (data == null) {
            return null;
        }

        JiraApplicationProviderModel jiraApplicationModel;
        jiraApplicationModel = jiraMapperConfiguration.mapToApplicationModel(data);

        return jiraApplicationModel;
    }

    @Override
    public void run() {
        String jiraUser = jiraTransport.getEnvironment().getUser();
        String jiraPassword = jiraTransport.getEnvironment().getPassword();
        String encodedAuthString = EncoderHelper.encodeToBase64(jiraUser, jiraPassword);
        String[] projects = jiraTransport.getConfiguration().getProjectKey().split(",");

        JiraApplicationProviderModel jiraApplicationModel;
        ApplicationStorage applicationStorage;

        jiraTransport.setEncodedCredentials(encodedAuthString);

        for(String project : projects) {
            JSONObject response = jiraTransport.getAllIssuesByProject(project);
            jiraApplicationModel = this.parse(response);
            applicationStorage = jiraMapperConfiguration.mapToApplicationStorage(jiraApplicationModel);

            if (!applicationRepository.exist(applicationStorage)) {
                applicationRepository.save(applicationStorage);
            }
        }
    }
}
