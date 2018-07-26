package hubble.backend.storage.repositories;

import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.operations.ApplicationOperations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.data.mongodb.core.query.Query;
import static org.springframework.data.mongodb.core.query.Query.query;

//TODO: Reemplazar por el storage real - será necesario usar la API getConfigurations y crear
//las colecciones en Mongo
public class ApplicationRepositoryImpl implements ApplicationOperations {

    @Autowired
    MongoOperations mongo;

    @Override
    public List<ApplicationStorage> findAllApplications() {
        return getApplications();
    }

    @Override
    public ApplicationStorage findApplicationById(String applicationId) {
        Criteria isSameApplicationId = Criteria.where("applicationId").is(applicationId);

        List<ApplicationStorage> applications = mongo
                .find(Query
                        .query(isSameApplicationId),
                        ApplicationStorage.class);

        return !applications.isEmpty() ? applications.get(0) : null;
    }

    @Override
    public ApplicationStorage findApplicationByTransactionId(String transactionId) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.match(Criteria.where("transactions.transactionId").is(transactionId)));
        TypedAggregation<ApplicationStorage> applicationAggregation = Aggregation.newAggregation(ApplicationStorage.class, aggregationOperations);
        List<ApplicationStorage> applicationStorageList = mongo.aggregate(applicationAggregation, ApplicationStorage.class, ApplicationStorage.class).getMappedResults();

        if (applicationStorageList.isEmpty()) {
            return null;
        }

        return applicationStorageList.get(0);
    }

    @Override
    public boolean exist(ApplicationStorage application) {

        Criteria isSameApplicationId = Criteria.where("applicationId").is(application.getApplicationId());

        List<ApplicationStorage> applications = mongo
                .find(Query
                        .query(isSameApplicationId),
                        ApplicationStorage.class);

        return !applications.isEmpty();

    }

    private List<ApplicationStorage> getApplications() {
        List<ApplicationStorage> applicationStorageList = mongo.findAll(ApplicationStorage.class);

        return applicationStorageList;
    }

     @Override
     public boolean existAppId(String applicationId){
         return Optional.ofNullable(mongo.findOne(query(where("applicationId").is(applicationId)), ApplicationStorage.class)).isPresent();
     }
     
     @Override
     public boolean existAppName(String name){
         return Optional.ofNullable(mongo.findOne(query(where("applicationName").is(name)), ApplicationStorage.class)).isPresent();
     }

    
}
