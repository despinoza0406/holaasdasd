package hubble.backend.providers.transports.interfaces;

import hubble.backend.core.enums.Results;
import hubble.backend.providers.configurations.SiteScopeConfiguration;
import hubble.backend.providers.configurations.environments.SiteScopeProviderEnvironment;
import org.json.JSONObject;

import java.util.List;

public interface SiteScopeTransport {

    SiteScopeProviderEnvironment getEnvironment();

    SiteScopeConfiguration getConfiguration();

    List<JSONObject> getGroupsSnapshots(List<String> paths);

    List<JSONObject> getMonitorSnapshots(List<String> paths);

    List<String> getPathsToGroups(List<String> groups);

    List<String> getApplicationNames();

    List<String> getMonitorPaths(String group);

    String getError();

    Results.RESULTS getResult();




}
