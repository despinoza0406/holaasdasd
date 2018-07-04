package hubble.backend.business.services.interfaces.services;

import hubble.backend.storage.models.AuthToken;
import hubble.backend.business.services.models.Roles;
import hubble.backend.storage.models.UserStorage;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public interface UsersService {

    UserStorage create(String email, String name, char[] password, Set<Roles> roles, Set<String> applications);
    
    void edit(String id, String email, String name, char[] password, Set<Roles> roles, Set<String> applications);

    AuthToken authenticate(String email, char[] password);

    AuthToken refreshToken(String email, UUID token);

}
