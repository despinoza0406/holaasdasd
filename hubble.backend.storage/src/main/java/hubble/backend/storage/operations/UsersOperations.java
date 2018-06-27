package hubble.backend.storage.operations;

import hubble.backend.storage.models.UserStorage;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public interface UsersOperations {

    boolean emailExists(String email);

    Optional<UserStorage> findByEmail(String email);
    
    Optional<UserStorage> findByAccessToken(UUID token);
}
