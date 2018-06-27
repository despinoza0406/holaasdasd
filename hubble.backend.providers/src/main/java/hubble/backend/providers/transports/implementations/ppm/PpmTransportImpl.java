package hubble.backend.providers.transports.implementations.ppm;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.providers.configurations.PpmConfiguration;
import hubble.backend.providers.configurations.environments.PpmProviderEnvironment;
import hubble.backend.providers.transports.interfaces.PpmTransport;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PpmTransportImpl implements PpmTransport {

    @Autowired
    PpmProviderEnvironment environment;

    @Autowired
    PpmConfiguration configuration;

    private final Logger logger = LoggerFactory.getLogger(PpmTransportImpl.class);

    @Override
    public PpmProviderEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public String encodeToBase64(String userName, String password) {
        String formatToEncode = String.format("%s:%s", userName, password);
        byte[] encodedBytes = Base64.getEncoder().encode(formatToEncode.getBytes());
        return new String(encodedBytes);
    }

    @Override
    public String decodeFromBase64(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString.getBytes());
        return new String(decodedBytes);
    }

    @Override
    public List<JSONObject> getRequests(String encodedAuthString, String requestTypeId) {
        String path = String.format("/itg/rest/dm/requestTypes/%s/requests?alt=application/json", requestTypeId);
        String requestsUri = buildUri(path);
        HttpResponse<JsonNode> data = null;
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;
        List<JSONObject> dataList = new ArrayList<JSONObject>();
        Boolean hasMultipleRequests = true;

        try {
            data = Unirest.get(requestsUri)
                    .header("Authorization", "Basic " + encodedAuthString)
                    .asJson();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
            return null;
        }

        try {
            jsonArray = data.getBody().getObject().getJSONObject("ns2:requests").getJSONArray("request");
        } catch (Exception e) {
            hasMultipleRequests = false;
            try {
                jsonObject = data.getBody().getObject().getJSONObject("ns2:requests").getJSONObject("request");
            } catch (Exception ex) {
                logger.warn("There are no requests availables for this request type id: " + requestTypeId);
                return null;
            }
        }

        if (hasMultipleRequests) {
            for (int x = 0; x < jsonArray.length(); x++) {
                dataList.add(jsonArray.getJSONObject(x));
            }
        } else {
            dataList.add(jsonObject);
        }

        return dataList;
    }

    @Override
    public List<JSONObject> getAllRequestTypes(String encodedAuthString) {
        String path = "/itg/rest/dm/requestTypes?alt=application/json";
        String requestsUri = buildUri(path);
        HttpResponse<JsonNode> data = null;
        JSONArray jsonArray = null;
        List<JSONObject> dataList = new ArrayList<JSONObject>();

        try {
            data = Unirest.get(requestsUri)
                    .header("Authorization", "Basic " + encodedAuthString)
                    .asJson();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
            return null;
        }
        jsonArray = data.getBody().getObject().getJSONObject("ns2:requestTypes").getJSONArray("requestType");

        for (int x = 0; x < jsonArray.length(); x++) {
            dataList.add(jsonArray.getJSONObject(x));
        }

        return dataList;
    }

    @Override
    public List<JSONObject> getRequestTypes(String encodedAuthString, List<String> requestTypeIds) {
        String path = "/itg/rest/dm/requestTypes?alt=application/json";
        String requestsUri = buildUri(path);
        HttpResponse<JsonNode> data = null;
        JSONArray jsonArray = null;
        List<JSONObject> dataList = new ArrayList<JSONObject>();

        try {
            data = Unirest.get(requestsUri)
                    .header("Authorization", "Basic " + encodedAuthString)
                    .asJson();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
            return null;
        }
        jsonArray = data.getBody().getObject().getJSONObject("ns2:requestTypes").getJSONArray("requestType");

        for (int x = 0; x < jsonArray.length(); x++) {
            for (int y = 0; y < requestTypeIds.size(); y++) {
                if (jsonArray.getJSONObject(x).getString("id").equals(requestTypeIds.get(y))) {
                    dataList.add(jsonArray.getJSONObject(x));
                }
            }
        }

        return dataList;
    }

    @Override
    public JSONObject getRequestDetails(String encodedAuthString, String requestId) {
        String path = String.format("/itg/rest/dm/requests/%s?alt=application/json", requestId);
        String requestsUri = buildUri(path);
        HttpResponse<JsonNode> data = null;

        try {
            data = Unirest.get(requestsUri)
                    .header("Authorization", "Basic " + encodedAuthString)
                    .asJson();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
            return null;
        }

        JSONObject fields = data.getBody().getObject().getJSONObject("ns2:request").getJSONObject("fields");
        JSONArray fieldsArray = fields.getJSONArray("field");
        for (int i = 0; i < fieldsArray.length(); i++){
            JSONObject field = fieldsArray.getJSONObject(i);
            if (field.get("token").equals("REQ.STATUS_CODE")) {
                if (field.getString("stringValue").equals("IN_PROGRESS")) {
                    return fields;
                }
            }

            if (field.get("token").equals("REQ.LAST_UPDATE_DATE")) {
                Date lastUpdate = DateHelper.parseDateTime(field.getString("dateValue"));
                Date today = DateHelper.getDateNow();
                if (DateHelper.areSameDate(lastUpdate, today)) {
                    return fields;
                }
            }
        }
        return null;
    }

    @Override
    public List<String> getConfiguredRequestTypes(String encodedAuthString) {
        String requestTypeIds = configuration.getRequestTypeIds();
        List<String> requestTypeIdsToBeRetrieved = new ArrayList();
        List<JSONObject> rawRequestTypes;
        if (requestTypeIds.equals("*")) {
            rawRequestTypes = getAllRequestTypes(encodedAuthString);
            for (int x = 0; x < rawRequestTypes.size(); x++) {
                requestTypeIdsToBeRetrieved.add(rawRequestTypes.get(x).getString("id"));
            }
        } else {
            String[] parsedIds = requestTypeIds.split(",");
            for (String id : parsedIds) {
                requestTypeIdsToBeRetrieved.add(id);
            }
        }
        return requestTypeIdsToBeRetrieved;
    }

    private String buildUri(String path) {
        String uri = String.format("http://%s:%s%s",
                environment.getHost(),
                environment.getPort(),
                path);

        return uri;
    }
}
