package hubble.backend.tasksrunner.jobs.bsm;

import hubble.backend.core.utils.DateHelper;
import hubble.backend.providers.parsers.interfaces.Parser;
import hubble.backend.providers.parsers.interfaces.bsm.BsmDataParser;
import hubble.backend.storage.repositories.ProvidersRepository;
import hubble.backend.tasksrunner.jobs.ParserJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

public class BsmDataParserJob implements ParserJob {

    private Parser bsmParser;
    private static final Logger logger = Logger.getLogger(BsmDataParserJob.class.getName());

    @Autowired
    ProvidersRepository providersRepository;

    public BsmDataParserJob() {
        //This constructor is used by Quartz. DON'T DELETE. CANT SET DEFAULT CONSTRUCTOR.
    }

    public BsmDataParserJob(Parser parser) {
        bsmParser = parser;
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
        bsmParser = taskRunneAppContext.getBean(BsmDataParser.class);

        try {
            bsmParser.run();
            DateHelper.lastExecutionDate = DateHelper.getDateNow();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Parser getParser() {
        return bsmParser;
    }

    @Override
    public void setParser(Parser parser) {
        bsmParser = parser;
    }
}
