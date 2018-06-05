package hubble.backend.tasksrunner.jobs.alm;

import hubble.backend.core.utils.DateHelper;
import hubble.backend.providers.parsers.interfaces.Parser;
import hubble.backend.providers.parsers.interfaces.alm.AlmDataParser;
import hubble.backend.tasksrunner.jobs.ParserJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ConfigurableApplicationContext;

public class AlmDataParserJob implements ParserJob{

    private Parser almParser;
    private static final Logger logger = Logger.getLogger(AlmDataParserJob.class.getName());

    public AlmDataParserJob() {
        //This constructor is used by Quartz. DON'T DELETE. CANT SET DEFAULT CONSTRUCTOR.
    }
  
    public AlmDataParserJob(Parser almParser) {
        this.almParser = almParser;
    }
    
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        
        SchedulerContext schedulerContext = null;
        try {
            schedulerContext = (SchedulerContext) jec.getScheduler().getContext();
        } catch (SchedulerException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        ConfigurableApplicationContext taskRunnerAppContext = (ConfigurableApplicationContext) schedulerContext.get("context");
        almParser = taskRunnerAppContext.getBean(AlmDataParser.class);

        try {
            almParser.run();
            DateHelper.lastExecutionDate = DateHelper.getDateNow();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Parser getParser() {
        return this.almParser;
    }

    @Override
    public void setParser(Parser parser) {
        this.almParser = parser;
    }
}
