package hubble.backend.storage.models;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Los defectos en ALM o Jira.
 */
@Document
public class IssueStorage {
    
    private String id;
    private int externalId;
    private Date registeredDate;
    private Date modifiedDate;
    private Date closedDate;
    private String providerOrigin;
    private String providerName;
    private String title;
    private String businessApplication;
    private String businessApplicationId;
    private String transaction;
    private int transactionId;
    private int priority;
    private int severity;
    private String status;
    private String assignee;
    private String detectedBy;
    private String detectedOnRelease;
    private String correctedOnRelease;
    private boolean reproducible;
    private String externalUri;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getExternalId() {
        return externalId;
    }

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public String getProviderOrigin() {
        return providerOrigin;
    }

    public void setProviderOrigin(String providerOrigin) {
        this.providerOrigin = providerOrigin;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBusinessApplication() {
        return businessApplication;
    }

    public void setBusinessApplication(String businessApplication) {
        this.businessApplication = businessApplication;
    }

    public String getBusinessApplicationId() {
        return businessApplicationId;
    }

    public void setBusinessApplicationId(String businessApplicationId) {
        this.businessApplicationId = businessApplicationId;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDetectedOnRelease() {
        return detectedOnRelease;
    }

    public void setDetectedOnRelease(String detectedOnRelease) {
        this.detectedOnRelease = detectedOnRelease;
    }

    public String getCorrectedOnRelease() {
        return correctedOnRelease;
    }

    public void setCorrectedOnRelease(String correctedOnRelease) {
        this.correctedOnRelease = correctedOnRelease;
    }

    public boolean isReproducible() {
        return reproducible;
    }

    public void setReproducible(boolean reproducible) {
        this.reproducible = reproducible;
    }

    public String getExternalUri() {
        return externalUri;
    }

    public void setExternalUri(String externalUri) {
        this.externalUri = externalUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetectedBy() {
        return detectedBy;
    }

    public void setDetectedBy(String detectedBy) {
        this.detectedBy = detectedBy;
    }
}
