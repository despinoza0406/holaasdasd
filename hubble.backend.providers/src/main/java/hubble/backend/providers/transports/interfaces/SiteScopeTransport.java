package hubble.backend.providers.transports.interfaces;

import hubble.backend.providers.configurations.SiteScopeConfiguration;
import hubble.backend.providers.configurations.environments.SiteScopeProviderEnvironment;
import org.json.JSONObject;

import java.util.List;

public interface SiteScopeTransport {

    public SiteScopeProviderEnvironment getEnvironment();
    public SiteScopeConfiguration getConfiguration();
    public List<JSONObject> getGroupsSnapshots(List<String> paths);


}
