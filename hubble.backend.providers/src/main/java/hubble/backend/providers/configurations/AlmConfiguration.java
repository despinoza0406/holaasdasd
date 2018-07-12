package hubble.backend.providers.configurations;

import java.util.HashMap;
import java.util.Set;

public interface AlmConfiguration {
    public String getApplicationFieldName();
    public String getStatusFieldName();
    public String getTransactionFieldName();
    public String getProviderOrigin();
    public String getProviderName();
    public boolean taskEnabled();
    public Set<String> getStatusOpenValues();
    public HashMap<String,String> getApplicationValueToIdMap();

}
