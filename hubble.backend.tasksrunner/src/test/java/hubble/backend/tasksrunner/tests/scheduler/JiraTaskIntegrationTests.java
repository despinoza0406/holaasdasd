package hubble.backend.tasksrunner.tests.scheduler;

import hubble.backend.providers.parsers.interfaces.jira.JiraDataParser;
import hubble.backend.tasksrunner.application.scheduler.SchedulerMediator;
import hubble.backend.tasksrunner.jobs.ParserJob;
import hubble.backend.tasksrunner.jobs.jira.JiraDataParserJob;
import hubble.backend.tasksrunner.tasks.Task;
import hubble.backend.tasksrunner.tasks.jira.JiraDataTaskImpl;
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
public class JiraTaskIntegrationTests {

    @Autowired
    private ApplicationContext appContext;

    public JiraTaskIntegrationTests() {
    }

    @Test
    public void SchedulerMediator_should_schedule_jira_job() throws SchedulerException, Exception {

        //Assign
        JiraDataParser jiraParser = appContext.getBean(JiraDataParser.class);
        SchedulerMediator schedule = new SchedulerMediator((ConfigurableApplicationContext) appContext);

        ParserJob jiraDataJob = new JiraDataParserJob(jiraParser);
        Task jiraDataTask = new JiraDataTaskImpl(jiraDataJob);
        jiraDataTask.setIndentityGroupName("Alm Provider Job");
        jiraDataTask.setIndentityName("Alm Data");
        jiraDataTask.setIntervalSeconds(1);
        schedule.addTask(jiraDataTask);

        //Act
        schedule.start();
        Thread.sleep(4000);

        //Assert
        schedule.shutdown();
    }


}
