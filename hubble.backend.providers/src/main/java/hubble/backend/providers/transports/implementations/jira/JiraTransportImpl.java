package hubble.backend.providers.transports.implementations.jira;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hubble.backend.core.enums.Results;
import hubble.backend.providers.configurations.JiraConfiguration;
import hubble.backend.providers.configurations.environments.JiraProviderEnvironment;
import hubble.backend.providers.transports.interfaces.JiraTransport;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JiraTransportImpl implements JiraTransport {

    @Autowired
    JiraProviderEnvironment environment;

    @Autowired
    JiraConfiguration configuration;

    private String url;
    protected String encodedCredentials;
    private Results.RESULTS result = Results.RESULTS.SUCCESS;
    private String error = null;

    private final Logger logger = LoggerFactory.getLogger(JiraTransportImpl.class);

    @Override
    public JSONObject getData() {
        String requestsUri = buildUri(this.url);
        HttpResponse<JsonNode> response;
        JSONObject data;
        String encodedAuthString = this.getEncodedCredentials();

        try {
            response = Unirest.get(requestsUri)
                    .header("Accept", "application/json")
                    .header("Authorization", "Basic " + encodedAuthString)
                    .asJson();

        } catch (UnirestException e) {
            logger.error(e.getMessage());
            result = Results.RESULTS.FAILURE;
            error = e.getMessage();
            return null;
        }

        data = response.getBody().getObject();

        if (data == null) {
            logger.warn("No se pudieron traer datos de jira");
            result = Results.RESULTS.NO_DATA;
            error = "No se tiene data";
            return null;
        }
        return data;
    }

    @Override
    public JiraProviderEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public JiraConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override
    public JSONObject getIssuesByProject(String projectKey, int startAt) {
        this.url = String.format("/rest/api/2/search?jql=project=%s&startAt=%d&maxResults=1000", projectKey, startAt);

        return this.getData();
    }

    private String buildUri(String path) {
        String uri = String.format("http://%s:%s%s",
                environment.getHost(),
                environment.getPort(),
                path);
        return uri;
    }

    @Override
    public void logout() {

        String requestsUri = buildUri(String.format("/rest/auth/1/session"));

        try {
            Unirest.delete(requestsUri)
                    .header("Accept", "application/json")
                    .header("Authorization", "Basic " + getEncodedCredentials())
                    .asJson();

        } catch (UnirestException e) {
            logger.error(e.getMessage());
            result = Results.RESULTS.FAILURE;
            error = e.getMessage();
        }
    }

    @Override
    public String getEncodedCredentials() {
        return this.encodedCredentials;
    }

    @Override
    public void setEncodedCredentials(String encodedCredentials) {
        this.encodedCredentials = encodedCredentials;
    }

    public Results.RESULTS getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    public void setResult(Results.RESULTS result) {
        this.result = result;
    }

    public void setError(String error){
        this.error = error;
    }
}
