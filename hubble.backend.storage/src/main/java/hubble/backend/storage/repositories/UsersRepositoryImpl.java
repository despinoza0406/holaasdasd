package hubble.backend.storage.repositories;

import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.operations.UsersOperations;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
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
        return Optional.ofNullable(mongo.findOne(query(where("email").is(email)), UserStorage.class));
    }


}
