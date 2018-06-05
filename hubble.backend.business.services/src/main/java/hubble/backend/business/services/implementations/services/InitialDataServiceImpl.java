package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.interfaces.services.InitialDataService;
import hubble.backend.business.services.models.Roles;
import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.repositories.UsersRepository;
import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
@Service
public class InitialDataServiceImpl implements InitialDataService {

    private static final String ADMIN_EMAIL = "admin@tsoftlatam.com";
    private final UsersRepository users;

    @Autowired
    public InitialDataServiceImpl(UsersRepository users) {
        this.users = users;
    }

    @Transactional
    @Override
    public void createData() {
        createAdminUser();
    }

    private void createAdminUser() {
        if (!exists(adminSample())) {
            users.save(admin());
        }
    }

    private boolean exists(UserStorage adminSample) {
        return users.findOne(Example.of(adminSample)) != null;
    }

    private UserStorage adminSample() {
        UserStorage sample = new UserStorage();
        sample.setEmail(ADMIN_EMAIL);
        return sample;
    }

    private UserStorage admin() {
        return new UserStorage(
            ADMIN_EMAIL,
            "Administrator",
            "administrator".toCharArray(),
            new HashSet<>(asList(Roles.ADMINISTRATOR.name())),
            Collections.EMPTY_SET
        );
    }

}
