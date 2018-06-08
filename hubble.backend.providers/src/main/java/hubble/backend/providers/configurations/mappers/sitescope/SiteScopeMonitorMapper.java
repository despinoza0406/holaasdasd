package hubble.backend.providers.configurations.mappers.sitescope;


import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class SiteScopeMonitorMapper {

    public JSONObject mapToSimpleMonitor(JSONObject monitorSnapshot){
        JSONObject simpleMonitor = new JSONObject();
        JSONObject runtime = monitorSnapshot.getJSONObject("runtime_snapshot");
        JSONObject config = monitorSnapshot.getJSONObject("configuration_snapshot");

        simpleMonitor.put("summary",runtime.getString("summary"));
        simpleMonitor.put("name",config.getString("name"));

        return simpleMonitor;
    }

}
