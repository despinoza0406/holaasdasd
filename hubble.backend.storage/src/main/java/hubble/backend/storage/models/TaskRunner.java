package hubble.backend.storage.models;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Configuración de un provider para Task Runner.
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
@Document
public class TaskRunner {

    private boolean enabled;
    private String cronExpression;

    public TaskRunner() {
    }

    public TaskRunner(boolean enabled, String cronExpression) {
        this.enabled = enabled;
        this.cronExpression = cronExpression;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

}
