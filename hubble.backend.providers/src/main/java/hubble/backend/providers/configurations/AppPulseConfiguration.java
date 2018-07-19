package hubble.backend.providers.configurations;

import java.util.HashMap;

public interface AppPulseConfiguration {

    public  boolean taskEnabled();
    public HashMap<String,String> getApplicationValueToIdMap();

}
