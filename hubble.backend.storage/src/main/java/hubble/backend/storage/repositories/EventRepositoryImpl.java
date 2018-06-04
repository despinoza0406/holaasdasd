package hubble.backend.storage.repositories;

import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.operations.EventOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class EventRepositoryImpl implements EventOperations {

    @Autowired
    MongoOperations mongo;

    @Override
    public boolean exist(EventStorage issue) {
        Criteria isSameObjectId= Criteria.where("_id").is(issue.getId()); //SiteScope no viene con ID
        Criteria isSameProviderName = Criteria.where("providerName").is(issue.getProviderName());

        List<EventStorage> issues = mongo.find(Query.query(isSameProviderName.andOperator(isSameObjectId)),
                                                EventStorage.class);

        return !issues.isEmpty();
    }
}
