package hubble.backend.providers.configurations;

import java.util.HashMap;

public interface JiraConfiguration {
    
    public String getProjectKey();
    public String getApplicationFieldName();
    public HashMap<String,String> getValuesToIdMap();
}
