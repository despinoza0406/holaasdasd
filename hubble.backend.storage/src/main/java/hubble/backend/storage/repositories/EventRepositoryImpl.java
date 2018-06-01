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
        //Criteria isSameIssueExternalId = Criteria.where("externalId").is(issue.getExternalId()); SiteScope no viene con ID
        Criteria isSameProviderName = Criteria.where("providerName").is(issue.getProviderName());

        List<EventStorage> issues = mongo.find(Query.query(isSameProviderName),
                                                EventStorage.class);

        return !issues.isEmpty();
    }
}
