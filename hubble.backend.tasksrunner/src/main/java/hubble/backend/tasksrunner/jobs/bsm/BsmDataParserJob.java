package hubble.backend.tasksrunner.jobs.bsm;

import hubble.backend.core.utils.DateHelper;
import hubble.backend.providers.parsers.interfaces.Parser;
import hubble.backend.providers.parsers.interfaces.bsm.BsmDataParser;
import hubble.backend.storage.repositories.ProvidersRepository;
import hubble.backend.tasksrunner.jobs.ParserJob;


import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class BsmDataParserJob implements ParserJob {

    private Parser bsmParser;
    private final Logger logger = LoggerFactory.getLogger(BsmDataParserJob.class);

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
        //Esto deberia funcionar, pero no se de donde sacar el nuevo schedule/intervalo
        /*Trigger newTrigger = newTrigger().withIdentity(jobContext.getTrigger().getKey().getName(),jobContext.getTrigger().getKey().getGroup())
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(60 * 60 * 24) //Por ahora es asi para mantener los valores con los que se viene trabajando
                        .repeatForever()
                )
                .build();
        Trigger oldTrigger = jobContext.getTrigger();


        try {
            Scheduler scheduler = jobContext.getScheduler();
            scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
        }catch (SchedulerException ex){
            logger.warn("Couldn't reschedule job");
        }
        */
        SchedulerContext schedulerContext = null;
        try {
            schedulerContext = (SchedulerContext) jobContext.getScheduler().getContext();
        } catch (SchedulerException ex) {
            logger.error(ex.getMessage());

        }

        ConfigurableApplicationContext taskRunneAppContext = (ConfigurableApplicationContext) schedulerContext.get("context");
        bsmParser = taskRunneAppContext.getBean(BsmDataParser.class);

        try {
            bsmParser.run();
            DateHelper.lastExecutionDate = DateHelper.getDateNow();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
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
