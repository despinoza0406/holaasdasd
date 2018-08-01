package hubble.backend.storage.configurations;

import com.mongodb.*;
import org.bson.Document;
import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Filters.*;
import static java.util.Arrays.asList;

import com.mongodb.client.model.changestream.ChangeStreamDocument;
import hubble.backend.storage.configurations.environment.StorageEnvironment;
import hubble.backend.storage.listeners.CascadeSaveMongoEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

//TODO: Agregar credenciales para Mongo.
@Configuration
@ComponentScan(basePackages = {"hubble.backend.storage"})
@EnableMongoRepositories(basePackages = "hubble.backend.storage")
public class StorageComponentConfiguration {

    @Autowired
    StorageEnvironment storageConf;

    @Bean
    public MongoFactoryBean mongo() {
        MongoFactoryBean mongo = new MongoFactoryBean();
        String host = storageConf.getHost();
        mongo.setHost(host);
        return mongo;
    }



    @Bean
    public MongoOperations mongoTemplate(Mongo mongo) {
        String dbName = storageConf.getDbname();
        return new MongoTemplate(mongo, dbName);
    }
    
    @Bean
    public CascadeSaveMongoEventListener cascadingMongoEventListener() {
        return new CascadeSaveMongoEventListener();
    }

}
