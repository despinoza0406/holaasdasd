package hubble.backend.api.models;

import hubble.backend.storage.models.KPITypes;
import java.util.Set;

/**
 * 
 * @author Guelmy
 */

public class BusinessApplicationLigth {

    String id;
    String applicationId;
    String applicationName;
    boolean active;
    Set<KPITypes> enabledKPIs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<KPITypes> getEnabledKPIs() {
        return enabledKPIs;
    }

    public void setEnabledKPIs(Set<KPITypes> enabledKPIs) {
        this.enabledKPIs = enabledKPIs;
    }
    
    
}
