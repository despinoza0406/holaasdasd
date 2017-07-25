package hubble.backend.tasksrunner.jobs;

import hubble.backend.providers.parsers.interfaces.AppPulseActiveParser;
import hubble.backend.providers.parsers.interfaces.Parser;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ConfigurableApplicationContext;

public class AppPulseParserJob implements ParserJob {

    private Parser appPulseActiveParser;
    private static final Logger logger = Logger.getLogger(AppPulseParserJob.class.getName());

    public AppPulseParserJob() {
        //This constructor is used by Quartz. DON'T DELETE. CANT SET DEFAULT CONSTRUCTOR.
    }

    public AppPulseParserJob(Parser parser) {
        appPulseActiveParser = parser;
    }

    @Override
    public void execute(JobExecutionContext jobContext) throws JobExecutionException {

        SchedulerContext schedulerContext = null;
        try {
            schedulerContext = (SchedulerContext) jobContext.getScheduler().getContext();
        } catch (SchedulerException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        ConfigurableApplicationContext taskRunneAppContext = (ConfigurableApplicationContext) schedulerContext.get("context");
        appPulseActiveParser = taskRunneAppContext.getBean(AppPulseActiveParser.class);

        try {
            appPulseActiveParser.run();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Parser getParser() {
        return appPulseActiveParser;
    }

    @Override
    public void setParser(Parser parser) {
        appPulseActiveParser = parser;
    }
}
