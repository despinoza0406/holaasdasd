package hubble.backend.business.services.interfaces.services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import hubble.backend.storage.models.AuthToken;
import hubble.backend.business.services.models.Roles;
import hubble.backend.storage.models.UserStorage;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public interface UsersService {

    UserStorage create(String email, String name, char[] password, Set<Roles> roles, Set<String> applications);
    
    void edit(String id, String email, String name, Set<Roles> roles, Set<String> applications, Optional<String> password);

    AuthToken authenticate(String email, char[] password);

    AuthToken refreshToken(String email, UUID token);
    
    void enabledDisabled(String id, boolean enabled);
    
    ArrayNode allUsers(boolean includeInactives);
    
    

}
