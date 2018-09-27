package hubble.backend.providers.configurations;

import java.util.HashMap;

public interface SiteScopeConfiguration {
    public String getProviderName();
    public String getApplicationFieldName();
    public HashMap<String,String> getApplicationValueToIdMap();
    public boolean taskEnabled();
}
