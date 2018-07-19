package hubble.backend.providers.configurations;

import java.util.HashMap;

public interface JiraConfiguration {
    
    public String[] getProjectKeys() throws NullPointerException;
    public String getApplicationFieldName();
    public boolean taskEnabled();
    public HashMap<String,String> getValuesToIdMap();
}
