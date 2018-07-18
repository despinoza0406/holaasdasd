package hubble.backend.business.services.interfaces.services;

import com.fasterxml.jackson.databind.JsonNode;
import hubble.backend.storage.models.ApplicationStorage;

import java.util.List;

public interface ApplicationService {

    List<ApplicationStorage> getAll();

    void enabledDisabled(String id, boolean enabled);

    void enabledDisabledTaskRunner(String id, boolean enabled);

    ApplicationStorage create(String applicationId, String name, String description);

    ApplicationStorage findById(String id);
    
    ApplicationStorage editApplicationFromJson(JsonNode jsonNode);

}
