package hubble.backend.tasksrunner.tests.scheduler;

import hubble.backend.providers.parsers.interfaces.bsm.BsmApplicationParser;
import hubble.backend.providers.parsers.interfaces.bsm.BsmDataParser;
import hubble.backend.tasksrunner.application.scheduler.SchedulerMediator;
import hubble.backend.tasksrunner.jobs.ParserJob;
import hubble.backend.tasksrunner.jobs.bsm.BsmApplicationParserJob;
import hubble.backend.tasksrunner.jobs.bsm.BsmDataParserJob;
import hubble.backend.tasksrunner.tasks.Task;
import hubble.backend.tasksrunner.tasks.bsm.BsmDataTaskImpl;
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
public class BsmTaskIntegrationTests {

    @Autowired
    private ApplicationContext appContext;

    public BsmTaskIntegrationTests() {
    }

    @Test
    public void SchedulerMediator_should_schedule_bsm_job() throws SchedulerException, Exception {

        //Assign
        BsmDataParser bsmParser = appContext.getBean(BsmDataParser.class);
        SchedulerMediator schedule = new SchedulerMediator((ConfigurableApplicationContext) appContext);

        ParserJob bsmJob = new BsmDataParserJob(bsmParser);
        Task bsmTask = new BsmDataTaskImpl(bsmJob);
        bsmTask.setIndentityGroupName("BSM Job");
        bsmTask.setIndentityName("BSM");
        bsmTask.setIntervalSeconds(1);
        schedule.addTask(bsmTask);
        //Act
        schedule.start();
        Thread.sleep(10000);

        //Assert
        schedule.shutdown();
    }


}
