package hubble.backend.providers.configurations;

import java.util.HashMap;

public interface BSMConfiguration {
    public String getApplicationFieldName();
    public  boolean taskEnabled();
    public String getProviderName();
    public HashMap<String,String> getApplicationValueToIdMap();
}
