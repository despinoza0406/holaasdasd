package hubble.backend.tasksrunner.jobs.jira;

import hubble.backend.core.utils.DateHelper;
import hubble.backend.providers.parsers.interfaces.Parser;
import hubble.backend.providers.parsers.interfaces.jira.JiraDataParser;
import hubble.backend.storage.repositories.ProvidersRepository;
import hubble.backend.tasksrunner.jobs.ParserJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class JiraDataParserJob implements ParserJob {
    
    private Parser jiraParser;
    private final Logger logger = LoggerFactory.getLogger(JiraDataParserJob.class);
    @Autowired
    ProvidersRepository providersRepository;

    public JiraDataParserJob() {
        //This constructor is used by Quartz
    }
    
    public JiraDataParserJob(Parser parser) {
        this.jiraParser = parser;
    }
    
    @Override
    public Parser getParser() {
        return this.jiraParser;
    }

    @Override
    public void setParser(Parser parser) {
        this.jiraParser = parser;
    }

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        SchedulerContext schedulerContext = null;
        try {
            schedulerContext = (SchedulerContext) jec.getScheduler().getContext();
        } catch (SchedulerException ex) {
            logger.error(ex.getMessage());

        }
        
        ConfigurableApplicationContext taskRunnerAppContext = (ConfigurableApplicationContext) schedulerContext.get("context");
        this.jiraParser = taskRunnerAppContext.getBean(JiraDataParser.class);
        
        try {

            jiraParser.run();
            DateHelper.lastExecutionDate = DateHelper.getDateNow();

        } catch(Exception e) {
            logger.error(e.getMessage());
        }
    }    
}