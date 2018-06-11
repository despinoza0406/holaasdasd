package hubble.backend.providers.parsers.implementations.sitescope;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SiteScopePathParser {

    public String generateJsonGroupPathArgumentFromJson(JSONObject jsonObject, String valueSearched,String path) {
        List<JSONObject> jsonObjects = new ArrayList<>();
        Set<String> keySet= jsonObject.keySet();
        for(String key : keySet){
            JSONObject aux = jsonObject.getJSONObject(key);
            jsonObjects.add(aux);
        }
        for (JSONObject object : jsonObjects){
            String name = object.getString("entitySnapshot_name");
            if(name.equals(valueSearched)){
                path += valueSearched;
                return path;
            }else{
                if(object.has("snapshot_groupSnapshotChildren")) {
                    JSONObject child = object.getJSONObject("snapshot_groupSnapshotChildren");
                    if(child.length() > 0) {
                        path += object.get("entitySnapshot_name") + "_sis_path_delimiter_";
                        path += generateJsonGroupPathArgumentFromJson(child, valueSearched, "");
                    }
                }
            }
        }
        return path;
    }

    public List<String> generateJsonMonitorPathArgumentFromJson(JSONObject jsonObject, String valueSearched,List<String> paths,String path) {
        String groupPath = this.generateJsonGroupPathArgumentFromJson(jsonObject,valueSearched,path) + "_sis_path_delimiter_";
        JSONObject monitors = this.getGroupMonitors(jsonObject,valueSearched);
        return getMonitorPaths(groupPath,monitors);
    }


    private JSONObject getGroupMonitors(JSONObject jsonObject,String valueSearched){
        List<JSONObject> jsonObjects = new ArrayList<>();
        Set<String> keySet= jsonObject.keySet();
        for(String key : keySet){
            JSONObject aux = jsonObject.getJSONObject(key);
            jsonObjects.add(aux);
        }
        for (JSONObject object : jsonObjects){
            String name = object.getString("entitySnapshot_name");
            if(name.equals(valueSearched)){
                return object.getJSONObject("snapshot_monitorSnapshotChildren");
            }else{
                if(object.has("snapshot_groupSnapshotChildren")) {
                    JSONObject child = object.getJSONObject("snapshot_groupSnapshotChildren");
                    if(child.length() > 0) {

                        return getGroupMonitors(child, valueSearched);
                    }
                }
            }
        }
        return null;

    }
    private List<String> getMonitorPaths(String path,JSONObject monitors){
        List<JSONObject> jsonObjects = new ArrayList<>();
        Set<String> keySet= monitors.keySet();
        List<String> pathsToMonitors = new ArrayList<>();
        for(String key : keySet){
            JSONObject aux = monitors.getJSONObject(key);
            jsonObjects.add(aux);
        }
        for(JSONObject monitor : jsonObjects){
            String name = monitor.getString("entitySnapshot_name");
            String pathToMonitor = path + name;
            pathsToMonitors.add(pathToMonitor);
        }
        return pathsToMonitors;
    }

}
