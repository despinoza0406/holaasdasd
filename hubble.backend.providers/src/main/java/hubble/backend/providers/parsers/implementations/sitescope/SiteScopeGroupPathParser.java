package hubble.backend.providers.parsers.implementations.sitescope;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SiteScopeGroupPathParser {

    public String generateJsonPathArgumentFromJson(JSONObject jsonObject, String valueSearched,String path) {
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
                        path += generateJsonPathArgumentFromJson(child, valueSearched, "");
                    }
                }
            }
        }
        return path;
    }

}
