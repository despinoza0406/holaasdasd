package hubble.backend.providers.transports.implementations.apppulse;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hubble.backend.providers.configurations.environments.AppPulseProviderEnvironment;
import hubble.backend.providers.transports.interfaces.AppPulseActiveTransport;
import static org.apache.commons.lang.StringUtils.EMPTY;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppPulseActiveTransportImpl implements AppPulseActiveTransport {

    @Autowired
    private AppPulseProviderEnvironment environment;

    private String clientId;
    private String clientSecret;
    private String tokenValue = EMPTY;
    private String lastRetrievedSequenceId = "0";
    private boolean hasMoreData = false;

    private final Logger logger = LoggerFactory.getLogger(AppPulseActiveTransportImpl.class);

    @Override
    public String getToken() {
        JSONObject authenticationJson = new JSONObject("{ 'clientSecret': '" + environment.getSecret() + "', 'clientId': '" + environment.getClient() + "' }");

        HttpResponse<JsonNode> jsonResponse = null;

        try {
            jsonResponse = Unirest.post(environment.getUrl() + "oauth/token")
                    .header("Content-Type", "application/json")
                    .body(authenticationJson)
                    .asJson();
        } catch (UnirestException ex) {
            logger.error(ex.getMessage());
        }

        if (jsonResponse == null || jsonResponse.getBody() == null) {
            logger.warn("No se pudo traer data de AppPulse");
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
            appPulseActiveHttpResponse = Unirest.get(environment.getUrl() + "getData")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + this.getTokenValue())
                    .queryString("lastRetrievedSequenceId", this.lastRetrievedSequenceId)
                    .asJson();
        } catch (UnirestException ex) {
            //TODO: Log here.
            ex.printStackTrace(System.out);
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
            appPulseActiveHttpResponse = Unirest.get(environment.getUrl() + "getConfiguration")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + this.getTokenValue())
                    .asJson();
        } catch (UnirestException ex) {
            //TODO: Log here.
            ex.printStackTrace(System.out);

        }

        if (appPulseActiveHttpResponse == null || appPulseActiveHttpResponse.getBody() == null) {
            return null;
        }

        JSONObject appPulseActiveJson = appPulseActiveHttpResponse.getBody().getObject();

        return appPulseActiveJson;
    }
}
