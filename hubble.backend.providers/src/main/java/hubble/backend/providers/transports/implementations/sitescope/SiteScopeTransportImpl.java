package hubble.backend.providers.transports.implementations.sitescope;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hubble.backend.providers.configurations.SiteScopeConfiguration;
import hubble.backend.providers.configurations.environments.SiteScopeProviderEnvironment;
import hubble.backend.providers.parsers.implementations.sitescope.SiteScopeGroupPathParser;
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

    @Autowired
    SiteScopeGroupPathParser siteScopeGroupPathParser;

    private final Logger logger = LoggerFactory.getLogger(SiteScopeTransportImpl.class);


    @Override
    public SiteScopeProviderEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public SiteScopeConfiguration getConfiguration() {
        return this.configuration;
    }

    public List<String> getApplicationNames(){
            String[] applicationsIdMap = configuration.getApplicationValueToIdMap().split(",");
            String[] applicationName = null;
            List<String> applicationNames = new ArrayList<>();
            for (int x = 0; x < applicationsIdMap.length; x++) {
                applicationName = applicationsIdMap[x].split(":");
                applicationNames.add(applicationName[1]);
            }
            return applicationNames;

    }

    public List<JSONObject> getGroupsSnapshots(List<String> groupPaths){
        String fullPathsToGroups = StringUtils.join(groupPaths,";");
        String path = String.format("/SiteScope/api/monitors/groups/snapshots");
        String requestsUri = buildUri(path);
        HttpResponse<JsonNode> data = null;
        JSONObject jsonObject = null;
        List<JSONObject> dataList = new ArrayList<JSONObject>();
        boolean hasMultipleGroups = true;

        try {
            data = Unirest.get(requestsUri)
                    .queryString("fullPathsToGroups",fullPathsToGroups)
                    .basicAuth(environment.getUser(),environment.getPassword())
                    .asJson();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
            return null;
        }

        try {
            for(String groupPath: groupPaths ) {
                jsonObject = data.getBody().getObject().getJSONObject(groupPath);
                if(!jsonObject.has("error_code"))
                dataList.add(jsonObject);
            }
        } catch (Exception e) {
            logger.warn("There was a problem", e);
        }

        return dataList;

    }

    public List<String> getPathsToGroups(List<String> groups){
        List<String> pathsToGroups = new ArrayList<>();
        String path = String.format("/SiteScope/api/admin/config/snapshot");
        String requestsUri = buildUri(path);
        HttpResponse<JsonNode> data = null;
        JSONObject jsonObject = null;

        try {
            data = Unirest.get(requestsUri)
                    .basicAuth(environment.getUser(),environment.getPassword())
                    .asJson();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
            return null;
        }

        jsonObject = data.getBody().getObject().getJSONObject("snapshot_groupSnapshotChildren");
        for (String group : groups) {
            pathsToGroups.add (siteScopeGroupPathParser.generateJsonPathArgumentFromJson(jsonObject, group ,""));
        }


        return pathsToGroups;
    }


    private String buildUri(String path) {
        String uri = String.format("http://%s:%s%s",
                environment.getHost(),
                environment.getPort(),
                path);

        return uri;
    }
}
