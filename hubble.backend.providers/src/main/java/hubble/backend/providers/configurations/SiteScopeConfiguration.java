package hubble.backend.providers.configurations;

import java.util.HashMap;

public interface SiteScopeConfiguration {
    public String getApplicationFieldName();
    public HashMap<String,String> getApplicationValueToIdMap();
}
