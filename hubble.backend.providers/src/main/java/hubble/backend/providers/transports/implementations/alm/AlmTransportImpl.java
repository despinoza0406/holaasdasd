package hubble.backend.providers.transports.implementations.alm;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hubble.backend.providers.configurations.AlmConfiguration;
import hubble.backend.providers.configurations.environments.AlmProviderEnvironment;
import hubble.backend.providers.transports.interfaces.AlmTransport;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AlmTransportImpl implements AlmTransport {

    @Autowired
    private AlmProviderEnvironment environment;

    @Autowired
    private AlmConfiguration configuration;

    private final Logger logger = LoggerFactory.getLogger(AlmTransportImpl.class);

    public AlmTransportImpl(AlmProviderEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public AlmProviderEnvironment getEnvironment() {
        return environment;
    }

    @Override
    public void login() {
        String path = "/authentication-point/authenticate";
        String Url = buildUri(path);

        try {
            Unirest.get(Url).basicAuth(environment.getUserName(),
                    environment.getPassword()).asString();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void logout() {
        String path = "/authentication-point/logout";
        String Url = buildUri(path);
        try {
            Unirest.get(Url).asString();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public boolean isAuthenticated() {
        String path = "/rest/is-authenticated";
        String isAuthenticatedUrl = buildUri(path);
        int status = 0;

        try {
            status = Unirest.get(isAuthenticatedUrl).asString().getStatus();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
        }
        return status == 200;
    }

    @Override
    public JSONObject getAllDefects(Map<String, String> sessionCookies) {
        String path = "/rest/domains/" + environment.getDomain()
                + "/projects/" + environment.getProject() + "/defects";
        String defectsUri = buildUri(path);
        HttpResponse<JsonNode> data = null;

        try {
            data = Unirest.get(defectsUri)
                    .header("QCSession", sessionCookies.get("QCSession"))
                    .header("XSRF-TOKEN", sessionCookies.get("XSRF-TOKEN"))
                    .header("ALM_USER", sessionCookies.get("ALM_USER"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .asJson();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
        }

        if (data == null) {
            return null;
        }

        if (data.getBody() == null) {
            return null;
        }

        return data.getBody().getObject();
    }

    @Override
    public Map<String, String> getSessionCookies() {
        String pathSession = "/rest/site-session";
        String sessionUri = buildUri(pathSession);
        HttpResponse<JsonNode> data = null;
        Map<String, String> cookiesMap = new HashMap<>();

        try {
            data = Unirest.post(sessionUri).asJson();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
            return null;
        }

        List<String> cookies = data.getHeaders().get("Set-Cookie");

        if (cookies == null) {
            return null;
        }

        for (String item : cookies) {
            cookiesMap.put(item.split("=")[0], item.split("=")[1].split(";")[0]);
        }

        return cookiesMap;
    }

    @Override
    public JSONObject getOpenDefects(Map<String, String> sessionCookies) {
        String query = "";
        String statusValuesToReturn = this.parseCsvConfigurations(
                configuration.getStatusOpenValues(),
                " or ");
        try {
            query = URLEncoder.encode(
                    String.format("{%s[%s]}",
                            configuration.getStatusFieldName(),
                            statusValuesToReturn),
                    "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        String path = "/rest/domains/" + environment.getDomain()
                + "/projects/" + environment.getProject() + "/defects?query="
                + query;
        String defectsUri = buildUri(path);
        HttpResponse<JsonNode> data = null;

        try {
            data = Unirest.get(defectsUri)
                    .header("QCSession", sessionCookies.get("QCSession"))
                    .header("XSRF-TOKEN", sessionCookies.get("XSRF-TOKEN"))
                    .header("ALM_USER", sessionCookies.get("ALM_USER"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .asJson();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
        }
        return data.getBody().getObject();
    }

    private String buildUri(String path) {
        String uri = String.format("http://%s:%s/qcbin%s",
                environment.getHost(),
                environment.getPort(),
                path);

        return uri;
    }

    private String parseCsvConfigurations(String values, String newSeparator) {
        StringBuilder valueToReturn = new StringBuilder();
        String[] statusesList = values.split(",");
        for (int x = 0; x < statusesList.length; x++) {
            valueToReturn.append(statusesList[x]);
            if (x != (statusesList.length - 1)) {
                valueToReturn.append(newSeparator);
            }
        }
        return valueToReturn.toString();
    }

}
