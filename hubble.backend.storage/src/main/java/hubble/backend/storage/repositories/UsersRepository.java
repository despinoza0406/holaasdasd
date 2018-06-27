package hubble.backend.storage.repositories;

import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.operations.UsersOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
@Component
public interface UsersRepository extends MongoRepository<UserStorage, String>, UsersOperations {

    
    
}
