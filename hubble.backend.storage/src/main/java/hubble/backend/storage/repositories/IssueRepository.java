package hubble.backend.storage.repositories;

import hubble.backend.storage.models.IssueStorage;
import hubble.backend.storage.operations.IssueOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface IssueRepository extends MongoRepository<IssueStorage,String>, IssueOperations {
}
