package hubble.backend.providers.configurations.mappers.sitescope;

import hubble.backend.storage.models.Monitor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class SiteScopeMonitorMapper {

    public Monitor mapToSimpleMonitor(JSONObject monitorSnapshot){
        Monitor simpleMonitor = new Monitor();
        JSONObject runtime = monitorSnapshot.getJSONObject("runtime_snapshot");
        JSONObject config = monitorSnapshot.getJSONObject("configuration_snapshot");

        simpleMonitor.setSummary(runtime.getString("summary"));
        simpleMonitor.setName(config.getString("name"));

        return simpleMonitor;
    }

}
