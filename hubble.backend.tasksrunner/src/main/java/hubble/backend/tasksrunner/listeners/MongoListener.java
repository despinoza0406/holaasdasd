package hubble.backend.tasksrunner.listeners;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.FullDocument;
import hubble.backend.storage.configurations.environment.StorageEnvironment;
import hubble.backend.tasksrunner.application.scheduler.SchedulerMediator;
import org.bson.Document;
import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import static java.util.Arrays.asList;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import static com.mongodb.client.model.Filters.and;

@Profile("test")
@Component
public class MongoListener extends Thread {

    @Autowired
    StorageEnvironment storageConf;
    @Autowired
    SchedulerMediator scheduler;
    public void run(){
        MongoCollection<Document> providerCollection =
                new MongoClient(storageConf.getHost())
                        .getDatabase(storageConf.getDbname())
                        .getCollection("providerStorage");

        ChangeStreamIterable<Document> changes = providerCollection.watch(asList(
                Aggregates.match( and(Filters
                        .in("operationType"
                                ,asList("insert","replace"))

                )))).fullDocument(FullDocument.UPDATE_LOOKUP);

        changes.forEach(new Block<ChangeStreamDocument<Document>>() {
            @Override
            public void apply(ChangeStreamDocument<Document> t) {
                scheduler.reschedule((String) t.getFullDocument().get("_id"));
            }
        });


    }
}

