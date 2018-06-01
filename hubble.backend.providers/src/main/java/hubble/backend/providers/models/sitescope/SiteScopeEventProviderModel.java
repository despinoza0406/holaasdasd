package hubble.backend.providers.models.sitescope;

public class SiteScopeEventProviderModel {
    private int id;
    private String updatedDate;
    private String closedDate;
    private String title;
    private String status;
    private String reproducible;
    private String description;
    private String detectedBy;
    private String businessApplication;
    private String applicationId;
    private String transaction;
    private String providerName;
    private String providerOrigin;

    public SiteScopeEventProviderModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
