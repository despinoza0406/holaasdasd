package hubble.backend.api.models;

import hubble.backend.core.enums.KPITypes;
import java.util.Set;

/**
 * 
 * @author Guelmy
 */

public class BusinessApplicationLigth {

    String id;
    String applicationId;
    String applicationName;
    private String description;
    private boolean enabledTaskRunner;
    boolean active;
    Set<KPITypes> enabledKPIs;
    private Set<String> kpisForMenu;

    
    public BusinessApplicationLigth(String id, String applicationId, String applicationName, String description, boolean enabledTaskRunner, boolean active, Set<KPITypes> enabledKPIs,Set<String> kpisForMenu) {
        this.id = id;
        this.applicationId = applicationId;
        this.applicationName = applicationName;
        this.description = description;
        this.enabledTaskRunner = enabledTaskRunner;
        this.active = active;
        this.enabledKPIs = enabledKPIs;
        this.kpisForMenu = kpisForMenu;
    }
    
    

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabledTaskRunner() {
        return enabledTaskRunner;
    }

    public void setEnabledTaskRunner(boolean enabledTaskRunner) {
        this.enabledTaskRunner = enabledTaskRunner;
    }


    public Set<String> getKpisForMenu() {
        return kpisForMenu;
    }

    public void setKpisForMenu(Set<String> kpisForMenu) {
        this.kpisForMenu = kpisForMenu;
    }
}
