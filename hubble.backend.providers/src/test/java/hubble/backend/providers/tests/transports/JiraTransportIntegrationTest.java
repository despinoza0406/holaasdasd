package hubble.backend.providers.tests.transports;

import hubble.backend.core.utils.EncoderHelper;
import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.transports.interfaces.JiraTransport;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
@Ignore
public class JiraTransportIntegrationTest {

    @Autowired
    private JiraTransport jiraTransport;

    @Test
    public void jira_transport_should_be_instantiated() {
        assertNotNull(jiraTransport);
    }

    @Test
    public void jira_environment_retrieve_correct_configuration() {
        assertEquals("8888", jiraTransport.getEnvironment().getPort());
    }

    
    @Test
    public void jira_should_retrieve_issues_from_project() {
        //Assign

        String encodedAuthSuccessString = EncoderHelper.encodeToBase64(
                jiraTransport.getEnvironment().getUser(),
                jiraTransport.getEnvironment().getPassword());
        JSONObject dataRetrieved;
        jiraTransport.setEncodedCredentials(encodedAuthSuccessString);
        jiraTransport.logout();

        //Act
        dataRetrieved = jiraTransport.getAllIssuesByProject(
                jiraTransport.getConfiguration().getProjectKey());

        //Assert
        assertTrue(dataRetrieved.has("issues"));

    }

    @Test
    public void jira_should_return_null_from_incorrect_request() {

        //Assign
        String encodedAuthFailString = EncoderHelper.encodeToBase64(
                jiraTransport.getEnvironment().getUser(),
                "wrongPassword");
        JSONObject dataRetrieved;

        String encodedAuthSuccessString = EncoderHelper.encodeToBase64(
                jiraTransport.getEnvironment().getUser(),
                jiraTransport.getEnvironment().getPassword());

        jiraTransport.setEncodedCredentials(encodedAuthSuccessString);
        jiraTransport.logout();

        jiraTransport.setEncodedCredentials(encodedAuthFailString);

        //Act
        dataRetrieved = jiraTransport.getAllIssuesByProject(
                jiraTransport.getConfiguration().getProjectKey());

        //Assert
        assertNull(dataRetrieved);

    }
}
