package hubble.backend.storage.repositories;

import hubble.backend.storage.models.EventStorage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface EventRepository extends MongoRepository<EventStorage,String>, EventOperations {
}