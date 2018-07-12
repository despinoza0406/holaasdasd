package hubble.backend.tasksrunner.jobs.apppulse;

import hubble.backend.core.utils.DateHelper;
import hubble.backend.providers.parsers.interfaces.Parser;
import hubble.backend.providers.parsers.interfaces.apppulse.AppPulseActiveDataParser;
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

public class AppPulseDataParserJob implements ParserJob {

    private Parser appPulseActiveParser;
    private static final Logger logger = Logger.getLogger(AppPulseDataParserJob.class.getName());

    @Autowired
    ProvidersRepository providersRepository;
    public AppPulseDataParserJob() {
        //This constructor is used by Quartz. DON'T DELETE. CANT SET DEFAULT CONSTRUCTOR.
    }

    public AppPulseDataParserJob(Parser parser) {
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
        appPulseActiveParser = taskRunneAppContext.getBean(AppPulseActiveDataParser.class);

        try {
            if(providersRepository.alm().isEnabled() && providersRepository.alm().getTaskRunner().isEnabled()) {
                appPulseActiveParser.run();
                DateHelper.lastExecutionDate = DateHelper.getDateNow();
            }
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
