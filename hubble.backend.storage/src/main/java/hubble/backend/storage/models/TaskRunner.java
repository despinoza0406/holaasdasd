package hubble.backend.storage.models;

import com.fasterxml.jackson.databind.JsonNode;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Configuración de un provider para Task Runner.
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
@Document
public class TaskRunner {
//
//    @Id
//    private String id;
    
    private boolean enabled;
    private Schedule schedule;

    public TaskRunner() {
    }

    public TaskRunner(boolean enabled, Schedule schedule) {
        this.enabled = enabled;
        this.schedule = schedule;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    
    public TaskRunner fromJson(JsonNode jsonNode) {

        this.enabled = jsonNode.get("enabled").asBoolean();
        
        JsonNode scheduleJson  = jsonNode.get("schedule");
        
        Schedule scheduleFromJson = this.getSchedule().fromJson(scheduleJson);
        
        this.setSchedule(scheduleFromJson);
        
        return this;
    }

}

