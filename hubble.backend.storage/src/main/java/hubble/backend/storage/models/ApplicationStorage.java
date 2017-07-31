package hubble.backend.storage.models;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ApplicationStorage {

    private String id;
    private String applicationId;
    private String applicationName;
    private boolean active;
    private String timeZoneId;
    private int okThreshold;
    private int criticalThreshold;
    private int outlierThreshold;
    private int availabilityThreshold;
    private List<TransactionStorage> transactions;
    private List<LocationStorage> locations;
    private String providerOrigin;
    private int applicationConfigurationVersion;

    public ApplicationStorage() {
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

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public int getOkThreshold() {
        return okThreshold;
    }

    public void setOkThreshold(int okThreshold) {
        this.okThreshold = okThreshold;
    }

    public int getCriticalThreshold() {
        return criticalThreshold;
    }

    public void setCriticalThreshold(int criticalThreshold) {
        this.criticalThreshold = criticalThreshold;
    }

    public int getOutlierThreshold() {
        return outlierThreshold;
    }

    public void setOutlierThreshold(int outlierThreshold) {
        this.outlierThreshold = outlierThreshold;
    }

    public int getAvailabilityThreshold() {
        return availabilityThreshold;
    }

    public void setAvailabilityThreshold(int availabilityThreshold) {
        this.availabilityThreshold = availabilityThreshold;
    }

    public List<TransactionStorage> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionStorage> transactions) {
        this.transactions = transactions;
    }

    public List<LocationStorage> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationStorage> locations) {
        this.locations = locations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProviderOrigin() {
        return providerOrigin;
    }

    public void setProviderOrigin(String providerOrigin) {
        this.providerOrigin = providerOrigin;
    }

    public int getApplicationConfigurationVersion() {
        return applicationConfigurationVersion;
    }

    public void setApplicationConfigurationVersion(int applicationConfigurationVersion) {
        this.applicationConfigurationVersion = applicationConfigurationVersion;
    }
}
