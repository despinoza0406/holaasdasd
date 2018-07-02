package hubble.backend.providers.transports.implementations.alm;

import hubble.backend.core.utils.DateHelper;
import org.json.JSONArray;
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
import java.util.Date;
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
        String url = buildUri(path);

        try {
            Unirest.get(url).basicAuth(environment.getUserName(),
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
    public JSONObject getOpenDefects(Map<String, String> sessionCookies,int startInd) {
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
                    .queryString("page-size","2000")
                    .queryString("start-index",startInd)
                    .queryString("query", "{status[Nuevo Or Abierto Or Reabierto]}")
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
    public JSONObject getClosedTodayDefects(Map<String, String> sessionCookies, int startInd) {
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
                    .queryString("page-size","2000")
                    .queryString("start-index",startInd)
                    .queryString("query", "{status[Cerrado Or Rechazado Or Corregido]}")
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

        JSONObject jsonObject = data.getBody().getObject();
        JSONArray entities = jsonObject.getJSONArray("entities");

        for (int i = 0; i < entities.length(); i++) {
            JSONArray fields = entities.getJSONObject(i).getJSONArray("Fields");
            for (int j = 0; j < fields.length(); j++) {
                JSONObject field = fields.getJSONObject(j);
                if (field.get("Name").equals("last-modified")){
                    String modifiedDateString = field.getJSONArray("values").getJSONObject(0).getString("value");
                    Date modifiedDate = DateHelper.parseDateTime(modifiedDateString);
                    Date today = DateHelper.getDateNow();
                    if (!DateHelper.areSameDate(modifiedDate, today)){
                        entities.remove(i);
                    }
                    break;
                }
            }
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
