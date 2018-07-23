package hubble.backend.api.models;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class NewApplication {

    private String applicationId;
    private String name;
    private String description;

    public NewApplication() {
    }

    public NewApplication(String applicationId, String name, String description) {
        this.applicationId = applicationId;
        this.name = name;
        this.description = description;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

}
