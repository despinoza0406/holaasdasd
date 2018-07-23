package hubble.backend.tasksrunner.jobs.alm;

import hubble.backend.core.utils.DateHelper;
import hubble.backend.providers.parsers.interfaces.Parser;
import hubble.backend.providers.parsers.interfaces.alm.AlmDataParser;
import hubble.backend.storage.repositories.ProvidersRepository;
import hubble.backend.tasksrunner.jobs.ParserJob;


import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class AlmDataParserJob implements ParserJob{

    private Parser almParser;
    private final Logger logger = LoggerFactory.getLogger(AlmDataParserJob.class);

    @Autowired
    ProvidersRepository providersRepository;

    public AlmDataParserJob() {
        //This constructor is used by Quartz. DON'T DELETE. CANT SET DEFAULT CONSTRUCTOR.
    }
  
    public AlmDataParserJob(Parser almParser) {
        this.almParser = almParser;
    }
    
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        //Esto deberia funcionar, pero no se de donde sacar el nuevo schedule/intervalo
        Trigger newTrigger = newTrigger().withIdentity(jec.getTrigger().getKey().getName(),jec.getTrigger().getKey().getGroup())
                .startNow()
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("* * 0 * * ?"))
                .build();
        Trigger oldTrigger = jec.getTrigger();


        try {
            Scheduler scheduler = jec.getScheduler();
            scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
        }catch (SchedulerException ex){
            logger.warn("Couldn't reschedule job");
        }
        SchedulerContext schedulerContext = null;
        try {
            schedulerContext = (SchedulerContext) jec.getScheduler().getContext();
        } catch (SchedulerException ex) {
            logger.error(ex.getMessage());

        }

        ConfigurableApplicationContext taskRunnerAppContext = (ConfigurableApplicationContext) schedulerContext.get("context");
        almParser = taskRunnerAppContext.getBean(AlmDataParser.class);

        try {

                almParser.run();
                DateHelper.lastExecutionDate = DateHelper.getDateNow();

        } catch (Exception ex) {
            logger.warn(ex.getMessage());
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
