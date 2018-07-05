package hubble.backend.business.services.interfaces.services;

import hubble.backend.storage.models.ApplicationStorage;

import java.util.List;

public interface ApplicationService {

    public List<ApplicationStorage> getAll();
    
     void enabledDisabled(String id, boolean enabled);

}
