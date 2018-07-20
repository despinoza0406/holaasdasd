package hubble.backend.tasksrunner.jobs.sitescope;

import hubble.backend.providers.parsers.interfaces.Parser;
import hubble.backend.providers.parsers.interfaces.sitescope.SiteScopeDataParser;
import hubble.backend.storage.repositories.ProvidersRepository;
import hubble.backend.tasksrunner.jobs.ParserJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class SiteScopeDataParserJob implements ParserJob {

    private Parser parser;

    private final Logger logger = LoggerFactory.getLogger(SiteScopeDataParserJob.class);

    @Autowired
    ProvidersRepository providersRepository;

    public SiteScopeDataParserJob() {
        //This constructor is used by Quartz. DON'T DELETE. CANT SET DEFAULT CONSTRUCTOR.
    }

    public SiteScopeDataParserJob(Parser parser) {
        this.parser = parser;
    }

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
        //Esto deberia funcionar, pero no se de donde sacar el nuevo schedule/intervalo
        Trigger newTrigger = newTrigger().withIdentity(jec.getTrigger().getKey().getName(),jec.getTrigger().getKey().getGroup())
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(60 * 60 * 24) //Por ahora es asi para mantener los valores con los que se viene trabajando
                        .repeatForever()
                )
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
        parser = taskRunnerAppContext.getBean(SiteScopeDataParser.class);

        try {
            parser.run();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
