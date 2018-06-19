package hubble.backend.storage.models;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

/**
 * Disponibilidad de BSM o AppPulse.
 */
@Document
public class AvailabilityStorage {

    private String id;
    private String applicationName;
    private String applicationId;
    private String transactionName;
    private String transactionId;
    private String locationName;
    private String locationId;
    private int responseTime;
    private Date timeStamp;
    private String serverName;
    private String scriptName;
    private String performanceStatus;
    private String availabilityStatus;
    private int availabilityFailIfAbove;
    private int numberOfErrors;
    private List<ErrorStorage> errors;
    private String providerOrigin;

    public AvailabilityStorage() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public String getPerformanceStatus() {
        return performanceStatus;
    }

    public void setPerformanceStatus(String performanceStatus) {
        this.performanceStatus = performanceStatus;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public int getAvailabilityFailIfAbove() {
        return availabilityFailIfAbove;
    }

    public void setAvailabilityFailIfAbove(int availabilityFailIfAbove) {
        this.availabilityFailIfAbove = availabilityFailIfAbove;
    }

    public int getNumberOfErrors() {
        return numberOfErrors;
    }

    public void setNumberOfErrors(int numberOfErrors) {
        this.numberOfErrors = numberOfErrors;
    }

    public List<ErrorStorage> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorStorage> errors) {
        this.errors = errors;
    }

    public String getProviderOrigin() {
        return providerOrigin;
    }

    public void setProviderOrigin(String providerOrigin) {
        this.providerOrigin = providerOrigin;
    }
}
