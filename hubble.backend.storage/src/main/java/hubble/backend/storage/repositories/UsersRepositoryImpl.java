package hubble.backend.storage.repositories;

import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.operations.UsersOperations;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class UsersRepositoryImpl implements UsersOperations {

    @Autowired
    private MongoOperations mongo;

    @Override
    public boolean emailExists(String email) {
        return findByEmail(email).isPresent();
    }

    @Override
    public Optional<UserStorage> findByEmail(String email) {
        return findByProperty("email", email);
    }

    @Override
    public Optional<UserStorage> findByAccessToken(UUID token) {
        return findByProperty("token.token", token);
    }

    public <T> Optional<UserStorage> findByProperty(String name, T value) {
        return Optional.ofNullable(mongo.findOne(query(where(name).is(value)), UserStorage.class));
    }

}
