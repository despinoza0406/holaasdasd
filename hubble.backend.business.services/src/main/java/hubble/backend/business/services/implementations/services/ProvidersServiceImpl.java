package hubble.backend.business.services.implementations.services;

import com.fasterxml.jackson.databind.JsonNode;
import hubble.backend.business.services.interfaces.services.ProvidersService;
import hubble.backend.storage.models.ProviderStorage;
import hubble.backend.storage.repositories.ProvidersRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Guelmy Díaz <guelmy.diaz.blanco@fit.com.ar>
 */
@Service
public class ProvidersServiceImpl implements ProvidersService {

    private final ProvidersRepository providersRepository;

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
            } else {
                throw new RuntimeException("No existe un provider con el ID: " + jsonNode.get("id").asText());
            }

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public List<ProviderStorage> findAll() {
        return providersRepository.findAll();
    }

    @Override
    public ProviderStorage findById(String id) {
      return providersRepository.findOne(id);
    }

}