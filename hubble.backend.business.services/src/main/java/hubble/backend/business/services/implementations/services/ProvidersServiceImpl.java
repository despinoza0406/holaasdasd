package hubble.backend.business.services.implementations.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import hubble.backend.business.services.interfaces.services.ProvidersService;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.ProviderStorage;
import hubble.backend.storage.repositories.ProvidersRepository;
import java.util.List;
import static java.util.stream.Collectors.toList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Guelmy Díaz <guelmy.diaz.blanco@fit.com.ar>
 */
@Service
public class ProvidersServiceImpl implements ProvidersService {

    private final ProvidersRepository providersRepository;

    @Autowired
    RescheduleServiceImpl rescheduleService;

    public ProvidersServiceImpl(ProvidersRepository providersRepository) {
        this.providersRepository = providersRepository;
    }


    @Transactional
    @Override
    public void editProviderFromJson(JsonNode jsonNode) throws Exception{
        ProviderStorage providerStorage = null;

        try {

            JsonNode nodeId = jsonNode.get("id");

            if (nodeId != null) {
                providerStorage = providersRepository.findOne(jsonNode.get("id").asText());
            } else {
                throw new Exception("No existe el campo ID dentro del JSON");
            }

            if (providerStorage != null) {
                providerStorage = providerStorage.fromJson(jsonNode);
                providersRepository.save(providerStorage);
                rescheduleService.reschedule(providerStorage.getId());

            } else {
                throw new RuntimeException("No existe un provider con el ID: " + jsonNode.get("id").asText());
            }

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public List<ProviderStorage> findAll(boolean includeInactives) {
        return providersRepository.findAll().stream().filter((p) -> includeInactives || p.isEnabled()).collect(toList());
    }

    @Override
    public ProviderStorage findById(String id) {
      return providersRepository.findOne(id);
    }

    
    @Override
    public void enabledDisabled(String id, boolean enabled) {

        try {
            ProviderStorage provider = providersRepository.findOne(id);
            if (provider == null) {
                throw new RuntimeException("No existe un provider con el ID: " + id);
            } else {
                provider.setEnabled(enabled);
                providersRepository.save(provider);
            }

        } catch (Throwable t) {
            throw new RuntimeException("Ocurrió un error mientras se habilitaba/deshabilitaba el provider. Causa: " + t.getMessage());
        }

    }
    
     @Override
    public void enabledDisabledTaskRunner(String id, boolean enabled) {

        try {
            ProviderStorage provider = providersRepository.findOne(id);
            if (provider == null) {
                throw new RuntimeException("No existe un provider con el ID: " + id);
            } else {
                provider.getTaskRunner().setEnabled(enabled);
                providersRepository.save(provider);
            }

        } catch (Throwable t) {
            throw new RuntimeException("Ocurrió un error mientras se habilitaba/deshabilitaba el provider. Causa: " + t.getMessage());
        }

    }
}
