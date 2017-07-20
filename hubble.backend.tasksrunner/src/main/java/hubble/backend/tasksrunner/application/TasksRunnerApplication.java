package hubble.backend.tasksrunner.application;

import hubble.backend.tasksrunner.application.scheduler.SchedulerMediator;
import hubble.backend.tasksrunner.configurations.TasksRunnerConfiguration;
import hubble.backend.tasksrunner.implementations.AppPulseTaskImpl;
import hubble.backend.tasksrunner.interfaces.Task;
import org.quartz.SchedulerException;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;

@Profile("test")
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class TasksRunnerApplication {

    SchedulerMediator scheduler;
    ConfigurableApplicationContext context;

    public void run(ConfigurableApplicationContext context) throws SchedulerException, Exception {

        scheduler = new SchedulerMediator(context);

        Task appPulseTask = new AppPulseTaskImpl();
        scheduler.addTask(appPulseTask);
        scheduler.showMenu();
    }

    public static void main(String[] args) throws Exception {

        //TODO: profile should be set as parameter.
        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .profiles("test")
                .sources(TasksRunnerConfiguration.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);

        TasksRunnerApplication app = context.getBean(TasksRunnerApplication.class);
        app.run(context);
    }
}
