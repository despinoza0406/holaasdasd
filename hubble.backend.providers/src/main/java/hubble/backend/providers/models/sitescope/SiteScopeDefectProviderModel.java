package hubble.backend.providers.models.sitescope;

public class SiteScopeDefectProviderModel {  //No es un defect, pero no se que es ni que posee
    private int id;
    private String registeredDate;
    private String modifiedDate;
    private String closedDate;
    private String title;
    private String priority;
    private String severity;
    private String project;
    private String status;
    private String assignee;
    private String detectedOnRelease;
    private String correctedOnRelease;
    private String reproducible;
    private String description;
    private String detectedBy;
    private String businessApplication;
    private String applicationId;
    private String transaction;
    private String providerName;
    private String providerOrigin;

    public SiteScopeDefectProviderModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(String closedDate) {
        this.closedDate = closedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
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

    public String getReproducible() {
        return reproducible;
    }

    public void setReproducible(String reproducible) {
        this.reproducible = reproducible;
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

    public String getBusinessApplication() {
        return businessApplication;
    }

    public void setBusinessApplication(String businessApplication) {
        this.businessApplication = businessApplication;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderOrigin() {
        return providerOrigin;
    }

    public void setProviderOrigin(String providerOrigin) {
        this.providerOrigin = providerOrigin;
    }
}
