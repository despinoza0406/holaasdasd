package hubble.backend.api;

import hubble.backend.business.services.interfaces.services.InitialDataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ServletComponentScan
public class    HubbleBackendAPIApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(HubbleBackendAPIApplication.class, args);
        ctx.getBean(InitialDataService.class).createData();
    }
}
