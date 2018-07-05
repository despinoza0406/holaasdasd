package hubble.backend.business.services.interfaces.services;

import com.fasterxml.jackson.databind.JsonNode;
import hubble.backend.storage.models.ProviderStorage;
import java.util.List;

/**
 *
 * @author Guelmy <guelmy.diaz.blanco@fit.com.ar>
 */
public interface ProvidersService {

    void editProviderFromJson(JsonNode jsonNode) throws Exception;
    
    List<ProviderStorage> findAll();
    
    ProviderStorage findById(String id);
    
    void enabledDisabled(String id, boolean enabled);
    
    void enabledDisabledTaskRunner(String id, boolean enabled);
    
    
}
