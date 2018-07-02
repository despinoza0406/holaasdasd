package hubble.backend.providers.configurations;

import java.util.HashMap;
import java.util.Set;

public interface PpmConfiguration {

    public String getApplicationFieldName();

    public String getTransactionFieldName();

    public String getProviderOrigin();

    public String getProviderName();

    public String getRequestTypeIds();

    public HashMap<String,String> getApplicationValueToIdMap();
}
