package hubble.backend.storage.repositories;

import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.operations.EventOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface EventRepository extends MongoRepository<EventStorage,String>, EventOperations {
}