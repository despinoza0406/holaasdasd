package hubble.backend.tasksrunner.jobs.sitescope;

import hubble.backend.providers.parsers.interfaces.Parser;
import hubble.backend.providers.parsers.interfaces.sitescope.SiteScopeApplicationParser;
import hubble.backend.tasksrunner.jobs.ParserJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

public class SiteScopeApplicationParserJob implements ParserJob {

    private Parser parser;

    private final Logger logger = LoggerFactory.getLogger(SiteScopeApplicationParserJob.class);

    public SiteScopeApplicationParserJob(){
        //This constructor is used by Quartz. DON'T DELETE. CANT SET DEFAULT CONSTRUCTOR.
    }

    public SiteScopeApplicationParserJob(Parser parser){ this.parser = parser; }

    @Override
    public Parser getParser() {
        return this.parser;
    }

    @Override
    public void setParser(Parser parser) {
        this.parser = parser;
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
        parser = taskRunnerAppContext.getBean(SiteScopeApplicationParser.class);

        try {
            parser.run();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
