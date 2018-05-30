package hubble.backend.providers.transports.implementations.sitescope;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hubble.backend.providers.configurations.SiteScopeConfiguration;
import hubble.backend.providers.configurations.environments.SiteScopeProviderEnvironment;
import hubble.backend.providers.transports.interfaces.SiteScopeTransport;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class SiteScopeTransportImpl implements SiteScopeTransport {

    @Autowired
    SiteScopeProviderEnvironment environment;

    @Autowired
    SiteScopeConfiguration configuration;

    private final Logger logger = LoggerFactory.getLogger(SiteScopeTransportImpl.class);


    @Override
    public SiteScopeProviderEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public SiteScopeConfiguration getConfiguration() {
        return this.configuration;
    }

    public List<JSONObject> getGroupsSnapshots(List<String> groupPaths){
        String fullPathsToGroups = StringUtils.join(groupPaths,"%3B");
        String path = String.format("/api/monitors/groups/snapshots");
        String requestsUri = buildUri(path);
        HttpResponse<JsonNode> data = null;
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;
        List<JSONObject> dataList = new ArrayList<JSONObject>();
        boolean hasMultipleGroups = true;

        try {
            data = Unirest.get(requestsUri)
                    .header("accept","application/json")
                    .queryString("fullPathsToGroups",fullPathsToGroups)
                    .basicAuth(environment.getUser(),environment.getPassword())
                    .asJson();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
            return null;
        }

        /*try {
            for(String groupPath: groupPaths ) {
                jsonArray = data.getBody().getObject().getJSONObject(groupPath).getJSONArray("group");
            }
        } catch (Exception e) {
            hasMultipleGroups = false;
            try {
                jsonObject = data.getBody().getObject().getJSONObject(groupPaths.get(0)).getJSONObject("group");
            } catch (Exception ex) {
                logger.warn("There are no groups with said names");
                return null;
            }
        }

        if (hasMultipleGroups) {
            for (int x = 0; x < jsonArray.length(); x++) {
                JSONObject object = jsonArray.getJSONObject(x);
                if(!object.has("error_code")) {
                    dataList.add(object);
                }
            }
        } else {
            if(!jsonObject.has("error_code")) {
                dataList.add(jsonObject);
            }
        }
        */
        return dataList;

    }



    private String buildUri(String path) {
        String uri = String.format("http://%s:%s%s",
                environment.getHost(),
                environment.getPort(),
                path);

        return uri;
    }
}
