package hubble.backend.tasksrunner.jobs.jira;

import hubble.backend.providers.parsers.interfaces.Parser;
import hubble.backend.providers.parsers.interfaces.jira.JiraApplicationParser;
import hubble.backend.tasksrunner.jobs.ParserJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ConfigurableApplicationContext;

public class JiraApplicationParserJob implements ParserJob {
    
    private Parser jiraParser;
    private static final Logger logger = Logger.getLogger(JiraApplicationParserJob.class.getName());
    
    public JiraApplicationParserJob() {
        //This constructor is used by Quartz
    }
    
    public JiraApplicationParserJob(Parser parser) {
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
        } catch (SchedulerException e) {
            logger.log(Level.SEVERE, null, e);
        }
        
        ConfigurableApplicationContext taskRunnerAppContext = (ConfigurableApplicationContext) schedulerContext.get("context");
        this.jiraParser = taskRunnerAppContext.getBean(JiraApplicationParser.class);
        
        try {
            jiraParser.run();
        } catch(Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
    }    
}