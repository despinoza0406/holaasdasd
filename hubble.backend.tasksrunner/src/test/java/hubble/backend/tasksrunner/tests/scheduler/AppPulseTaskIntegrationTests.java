package hubble.backend.tasksrunner.tests.scheduler;

import hubble.backend.providers.parsers.interfaces.apppulse.*;
import hubble.backend.tasksrunner.application.scheduler.SchedulerMediator;
import hubble.backend.tasksrunner.jobs.ParserJob;
import hubble.backend.tasksrunner.jobs.apppulse.AppPulseDataParserJob;
import hubble.backend.tasksrunner.tasks.Task;
import hubble.backend.tasksrunner.tasks.apppulse.AppPulseDataTaskImpl;
import hubble.backend.tasksrunner.tests.configurations.TasksRunnerTestConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TasksRunnerTestConfiguration.class)
public class AppPulseTaskIntegrationTests {

    @Autowired
    private ApplicationContext appContext;

    public AppPulseTaskIntegrationTests() {
    }

    @Test
    public void SchedulerMediator_should_schedule_apppulse_job() throws SchedulerException, Exception {

        //Assign
        AppPulseActiveDataParser appPulseParser = appContext.getBean(AppPulseActiveDataParser.class);
        SchedulerMediator schedule = new SchedulerMediator((ConfigurableApplicationContext) appContext);

        ParserJob appPulseJob = new AppPulseDataParserJob(appPulseParser);
        Task appPulseTask = new AppPulseDataTaskImpl(appPulseJob);
        appPulseTask.setIndentityGroupName("AppPulse Active Provider Job");
        appPulseTask.setIndentityName("AppPulse");
        appPulseTask.setIntervalSeconds(1);
        schedule.addTask(appPulseTask);
        //Act
        schedule.start();
        Thread.sleep(4000);

        //Assert
        schedule.shutdown();
    }

}
