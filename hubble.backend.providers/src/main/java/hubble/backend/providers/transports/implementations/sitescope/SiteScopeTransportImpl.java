package hubble.backend.providers.transports.implementations.sitescope;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hubble.backend.providers.configurations.SiteScopeConfiguration;
import hubble.backend.providers.configurations.environments.SiteScopeProviderEnvironment;
import hubble.backend.providers.parsers.implementations.sitescope.SiteScopePathParser;
import hubble.backend.providers.transports.interfaces.SiteScopeTransport;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class SiteScopeTransportImpl implements SiteScopeTransport {

    @Autowired
    SiteScopeProviderEnvironment environment;

    @Autowired
    SiteScopeConfiguration configuration;

    @Autowired
    SiteScopePathParser siteScopePathParser;

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
            HashMap<String,String> applicationsIdMap = configuration.getApplicationValueToIdMap();
            Set<String> keys = applicationsIdMap.keySet();
            List<String> applicationNames = new ArrayList<>();
            for (String key : keys) {
                applicationNames.add(applicationsIdMap.get(key));
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

    public List<JSONObject> getMonitorSnapshots(List<String> paths){
        String fullPathsToGroups = StringUtils.join(paths,";");
        String path = String.format("/SiteScope/api/monitors/snapshots");
        String requestsUri = buildUri(path);
        HttpResponse<JsonNode> data = null;
        JSONObject jsonObject = null;
        List<JSONObject> dataList = new ArrayList<JSONObject>();
        boolean hasMultipleGroups = true;

        try {
            data = Unirest.get(requestsUri)
                    .queryString("fullPathsToMonitors",fullPathsToGroups)
                    .basicAuth(environment.getUser(),environment.getPassword())
                    .asJson();
        } catch (UnirestException e) {
            logger.error(e.getMessage());
            return null;
        }

        try {
            for(String groupPath: paths ) {
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
        HttpResponse<JsonNode> data = null;
        JSONObject jsonObject = null;

        data = this.getFullConfig();

        jsonObject = data.getBody().getObject().getJSONObject("snapshot_groupSnapshotChildren");
        for (String group : groups) {
            pathsToGroups.add (siteScopePathParser.generateJsonGroupPathArgumentFromJson(jsonObject, group ,""));
        }

        return pathsToGroups;
    }

    public List<String> getMonitorPaths(String group){
        List<String> pathsToMonitors;
        HttpResponse<JsonNode> data = null;
        JSONObject jsonObject = null;

        data = this.getFullConfig();

        jsonObject = data.getBody().getObject().getJSONObject("snapshot_groupSnapshotChildren");

        pathsToMonitors = siteScopePathParser.generateJsonMonitorPathArgumentFromJson(jsonObject,group,new ArrayList<String>(),"");

        return pathsToMonitors;
    }

    private HttpResponse<JsonNode> getFullConfig(){
        String path = String.format("/SiteScope/api/admin/config/snapshot");
        String requestsUri = buildUri(path);
        HttpResponse<JsonNode> data = null;

        try {
            data = Unirest.get(requestsUri)
                    .basicAuth(environment.getUser(),environment.getPassword())
                    .asJson();
            return data;
        } catch (UnirestException e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    private String buildUri(String path) {
        String uri = String.format("http://%s:%s%s",
                environment.getHost(),
                environment.getPort(),
                path);

        return uri;
    }
}
