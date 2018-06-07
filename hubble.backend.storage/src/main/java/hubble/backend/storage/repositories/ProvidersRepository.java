package hubble.backend.storage.repositories;

import hubble.backend.storage.models.ProviderStorage;
import hubble.backend.storage.operations.ProvidersOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
@Component
public interface ProvidersRepository extends MongoRepository<ProviderStorage, String>, ProvidersOperations {

}
