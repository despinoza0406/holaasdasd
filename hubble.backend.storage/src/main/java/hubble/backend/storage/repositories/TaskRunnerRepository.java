package hubble.backend.storage.repositories;

import hubble.backend.storage.models.TaskRunnerExecution;
import hubble.backend.storage.operations.TaskRunnerOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface TaskRunnerRepository extends MongoRepository<TaskRunnerExecution,String>, TaskRunnerOperations {
}
