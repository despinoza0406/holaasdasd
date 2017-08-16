package hubble.backend.providers.transports.implementations.apppulse;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hubble.backend.providers.configurations.environments.ProviderEnvironment;
import hubble.backend.providers.transports.interfaces.AppPulseActiveTransport;
import static org.apache.commons.lang.StringUtils.EMPTY;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppPulseActiveTransportImpl implements AppPulseActiveTransport {

    @Autowired
    private ProviderEnvironment environment;

    private String APP_PULSE_ACTIVE_URL;
    private String clientId;
    private String clientSecret;
    private String tokenValue = EMPTY;
    private String lastRetrievedSequenceId = "0";
    private boolean hasMoreData = false;

    public AppPulseActiveTransportImpl(ProviderEnvironment environment) {
        this.environment = environment;
        this.APP_PULSE_ACTIVE_URL = environment.getUrl();
    }

    @Override
    public String getToken() {
        JSONObject authenticationJson = new JSONObject("{ 'clientSecret': '" + environment.getSecret() + "', 'clientId': '" + environment.getClient() + "' }");

        HttpResponse<JsonNode> jsonResponse = null;

        try {
            jsonResponse = Unirest.post(APP_PULSE_ACTIVE_URL + "oauth/token")
                    .header("Content-Type", "application/json")
                    .body(authenticationJson)
                    .asJson();
        } catch (UnirestException ex) {
            //TODO: Log here
        }

        if (jsonResponse == null || jsonResponse.getBody() == null) {
            return EMPTY;
        }

        JSONObject response = jsonResponse.getBody().getObject();

        if (!response.has("token")) {
            return this.tokenValue = EMPTY;
        }

        this.tokenValue = response.get("token").toString();

        return this.tokenValue;

    }

    @Override
    public JSONObject getData() {

        if (getTokenValue().equals(EMPTY)) {
            return null;
        }

        HttpResponse<JsonNode> appPulseActiveHttpResponse = null;
        try {
            appPulseActiveHttpResponse = Unirest.get(APP_PULSE_ACTIVE_URL + "getData")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + this.getTokenValue())
                    .queryString("lastRetrievedSequenceId", this.lastRetrievedSequenceId)
                    .asJson();
        } catch (UnirestException ex) {
            //TODO: Log here.
        }

        if (appPulseActiveHttpResponse == null || appPulseActiveHttpResponse.getBody() == null) {
            return null;
        }

        JSONObject appPulseActiveJson = appPulseActiveHttpResponse.getBody().getObject();

        if (appPulseActiveJson.has("hasMoreDataToFetch")) {
            this.hasMoreData = appPulseActiveJson.getBoolean("hasMoreDataToFetch");
        }

        if (appPulseActiveJson.has("lastRetrievedSequenceId")) {
            this.lastRetrievedSequenceId = appPulseActiveJson.getString("lastRetrievedSequenceId");
        }

        return appPulseActiveJson;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setTokenValue(String token) {
        this.tokenValue = token;
    }

    public String getTokenValue() {
        return this.tokenValue;
    }

    @Override
    public boolean hasMoreData() {
        return hasMoreData;
    }

    @Override
    public void setLastRetrievedSequenceId(String lastRetrievedSequenceId) {
        this.lastRetrievedSequenceId = lastRetrievedSequenceId;
    }

    @Override
    public JSONObject getApplications() {

        if (getTokenValue().equals(EMPTY)) {
            return null;
        }

        HttpResponse<JsonNode> appPulseActiveHttpResponse = null;
        try {
            appPulseActiveHttpResponse = Unirest.get(APP_PULSE_ACTIVE_URL + "getConfiguration")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + this.getTokenValue())
                    .asJson();
        } catch (UnirestException ex) {
            //TODO: Log here.
        }

        if (appPulseActiveHttpResponse == null || appPulseActiveHttpResponse.getBody() == null) {
            return null;
        }

        JSONObject appPulseActiveJson = appPulseActiveHttpResponse.getBody().getObject();

        return appPulseActiveJson;
    }
}
