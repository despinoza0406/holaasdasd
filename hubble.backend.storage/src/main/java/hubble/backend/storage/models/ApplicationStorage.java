package hubble.backend.storage.models;

import java.util.List;
import java.util.Set;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document
public class ApplicationStorage {

    private String id;
    private String applicationId;
    private String applicationName;
    private boolean active;
    private int applicationConfigurationVersion;
    private KPIs kpis;

    public ApplicationStorage() {
    }

    public ApplicationStorage(String applicationId, String applicationName, boolean active, KPIs kpis) {
        this.id = applicationId;
        this.applicationId = applicationId;
        this.applicationName = applicationName;
        this.active = active;
        this.kpis = kpis;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getApplicationConfigurationVersion() {
        return applicationConfigurationVersion;
    }

    public void setApplicationConfigurationVersion(int applicationConfigurationVersion) {
        this.applicationConfigurationVersion = applicationConfigurationVersion;
    }

    public KPIs getKpis() {
        return kpis;
    }

    public void setKpis(KPIs kpis) {
        this.kpis = kpis;
    }

}
