package hubble.backend.tasksrunner.tests.scheduler;


import hubble.backend.providers.parsers.interfaces.sitescope.SiteScopeDataParser;
import hubble.backend.tasksrunner.application.scheduler.SchedulerMediator;
import hubble.backend.tasksrunner.jobs.ParserJob;
import hubble.backend.tasksrunner.jobs.sitescope.SiteScopeDataParserJob;
import hubble.backend.tasksrunner.tasks.Task;
import hubble.backend.tasksrunner.tasks.sitescope.SiteScopeApplicationTaskImpl;
import hubble.backend.tasksrunner.tasks.sitescope.SiteScopeDataTaskImpl;
import hubble.backend.tasksrunner.tests.configurations.TasksRunnerTestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TasksRunnerTestConfiguration.class)
public class SiteScopeTaskIntegrationTests {

    @Autowired
    private ApplicationContext appContext;

    public SiteScopeTaskIntegrationTests() {
    }

    @Test
    public void SchedulerMediator_should_schedule_sitescope_job() throws SchedulerException, Exception {

        //Assign
        SiteScopeDataParser siteScopeParser = appContext.getBean(SiteScopeDataParser.class);
        SchedulerMediator schedule = new SchedulerMediator((ConfigurableApplicationContext) appContext);

        ParserJob siteScopeDataJob = new SiteScopeDataParserJob(siteScopeParser);
        Task siteScopeDataTask = new SiteScopeDataTaskImpl(siteScopeDataJob);
        siteScopeDataTask.setIndentityGroupName("SiteScope Provider Job");
        siteScopeDataTask.setIndentityName("SiteScope Data");
        siteScopeDataTask.setIntervalSeconds(1);
        schedule.addTask(siteScopeDataTask);
        //Act
        schedule.start();
        Thread.sleep(4000);

        //Assert
        schedule.shutdown();
    }


}
