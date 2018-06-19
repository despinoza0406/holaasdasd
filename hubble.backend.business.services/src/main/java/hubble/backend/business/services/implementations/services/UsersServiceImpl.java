package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.interfaces.services.UsersService;
import hubble.backend.business.services.models.Roles;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.AuthToken;
import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.UsersRepository;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import static java.util.stream.Collectors.toSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository users;
    private final ApplicationRepository applications;

    public UsersServiceImpl(UsersRepository users, ApplicationRepository applications) {
        this.users = users;
        this.applications = applications;
    }

    @Transactional
    @Override
    public UserStorage create(String email, String name, char[] password, Set<Roles> roles, Set<String> applications) {
        if (users.emailExists(email)) {
            throw new RuntimeException(String.format("Email %s is already used", email));
        }
        UserStorage user = new UserStorage(
            email,
            name,
            password,
            roles.stream().map(Roles::name).collect(toSet()),
            applications.stream().map(this::findApplication).collect(toSet())
        );
        return users.save(user);
    }

    private ApplicationStorage findApplication(String id) {
        return applications.findApplicationById(id);
    }

    @Transactional
    @Override
    public AuthToken authenticate(String email, char[] password) {
        UserStorage user = user(email, () -> "Usuario o contraseña inválidos.");
        AuthToken token = user.authenticate(password);
        users.save(user);
        return token;
    }

    @Transactional
    @Override
    public AuthToken refreshToken(String email, UUID token) {
        UserStorage user = user(email, () -> "No existe el usuario.");
        AuthToken newToken = user.refreshToken(token);
        users.save(user);
        return newToken;
    }

    private UserStorage user(String email, Supplier<String> error) {
        return users
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException(error.get()));
    }

}
