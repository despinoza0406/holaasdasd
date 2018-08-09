package hubble.backend.tasksrunner.application;

import hubble.backend.providers.parsers.interfaces.alm.AlmDataParser;
import hubble.backend.providers.parsers.interfaces.bsm.BsmDataParser;
import hubble.backend.providers.parsers.interfaces.jira.JiraDataParser;
import hubble.backend.providers.parsers.interfaces.ppm.PpmDataParser;
import hubble.backend.providers.parsers.interfaces.sitescope.SiteScopeDataParser;
import hubble.backend.storage.repositories.ProvidersRepository;
import hubble.backend.tasksrunner.application.scheduler.SchedulerMediator;
import hubble.backend.tasksrunner.configurations.TasksRunnerConfiguration;
import hubble.backend.tasksrunner.jobs.ParserJob;
import hubble.backend.tasksrunner.jobs.alm.AlmDataParserJob;
import hubble.backend.tasksrunner.jobs.bsm.BsmDataParserJob;
import hubble.backend.tasksrunner.jobs.jira.JiraDataParserJob;
import hubble.backend.tasksrunner.jobs.ppm.PpmDataParserJob;
import hubble.backend.tasksrunner.jobs.sitescope.SiteScopeDataParserJob;
import hubble.backend.tasksrunner.tasks.ParserTask;
import hubble.backend.tasksrunner.tasks.alm.AlmDataTaskImpl;
import hubble.backend.tasksrunner.tasks.bsm.BsmDataTaskImpl;
import hubble.backend.tasksrunner.tasks.jira.JiraDataTaskImpl;
import hubble.backend.tasksrunner.tasks.ppm.PpmDataTaskImpl;
import hubble.backend.tasksrunner.tasks.sitescope.SiteScopeDataTaskImpl;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;

@Profile("test")
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class TasksRunnerApplication {

    @Autowired
    SchedulerMediator scheduler;
    @Autowired
    ProvidersRepository providersRepository;
    ConfigurableApplicationContext context;

    public void run(ConfigurableApplicationContext context) throws SchedulerException, Exception {

        scheduler.startContext(context);
/*
        //AppPulse
        /*
        AppPulseActiveDataParser appPulseparser = context.getBean(AppPulseActiveDataParser.class);
        ParserJob appPulseJob = new AppPulseDataParserJob(appPulseparser);
        ParserTask appPulseDataTask = new AppPulseDataTaskImpl(appPulseJob);
        appPulseDataTask.setIndentityGroupName("AppPulse Active Provider Job");
        appPulseDataTask.setIndentityName(providersRepository.appPulse().getId());

        */
        //BSM

        BsmDataParser bsmParser = context.getBean(BsmDataParser.class);
        ParserJob bsmJob = new BsmDataParserJob(bsmParser);
        ParserTask bsmTask = new BsmDataTaskImpl(bsmJob);
        bsmTask.setIndentityGroupName("BSM");
        bsmTask.setIndentityName(providersRepository.bsm().getId());
        bsmTask.setCronExpression(providersRepository.bsm().getTaskRunner().getSchedule().cronExpression());

        scheduler.addTask(bsmTask);

        //Alm
        AlmDataParser almParser = context.getBean(AlmDataParser.class);
        ParserJob almJob = new AlmDataParserJob(almParser);
        ParserTask almDataTask = new AlmDataTaskImpl(almJob);
        almDataTask.setIndentityGroupName("Alm Provider Job");
        almDataTask.setIndentityName(providersRepository.alm().getId());
        almDataTask.setCronExpression(providersRepository.alm().getTaskRunner().getSchedule().cronExpression());

        scheduler.addTask(almDataTask);

        //Ppm
        PpmDataParser ppmParser = context.getBean(PpmDataParser.class);
        ParserJob ppmJob = new PpmDataParserJob(ppmParser);
        ParserTask ppmDataTask = new PpmDataTaskImpl(ppmJob);
        ppmDataTask.setIndentityGroupName("Ppm Provider Job");
        ppmDataTask.setIndentityName(providersRepository.ppm().getId());
        ppmDataTask.setCronExpression(providersRepository.ppm().getTaskRunner().getSchedule().cronExpression());
        
        scheduler.addTask(ppmDataTask);

        //Jira
        JiraDataParser jiraParser = context.getBean(JiraDataParser.class);
        ParserJob jiraJob = new JiraDataParserJob(jiraParser);
        ParserTask jiraDataTask = new JiraDataTaskImpl(jiraJob);
        jiraDataTask.setIndentityGroupName("Jira Provider Job");
        jiraDataTask.setIndentityName(providersRepository.jira().getId());
        jiraDataTask.setCronExpression(providersRepository.jira().getTaskRunner().getSchedule().cronExpression());

        scheduler.addTask(jiraDataTask);

        //SiteScope
        SiteScopeDataParser siteScopeDataParser = context.getBean(SiteScopeDataParser.class);
        ParserJob siteScopeJob = new SiteScopeDataParserJob(siteScopeDataParser);
        ParserTask siteScopeDataTask = new SiteScopeDataTaskImpl(siteScopeJob);
        siteScopeDataTask.setIndentityGroupName("SiteScope Provider Job");
        siteScopeDataTask.setIndentityName(providersRepository.siteScope().getId());
        siteScopeDataTask.setCronExpression(providersRepository.siteScope().getTaskRunner().getSchedule().cronExpression());

        scheduler.addTask(siteScopeDataTask);


        scheduler.start();
    }

    public static void main(String[] args) throws Exception {

        //TODO: profile should be set as parameter.
        ConfigurableApplicationContext context = new SpringApplicationBuilder()
                .profiles("test")
                .sources(TasksRunnerConfiguration.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);

        TasksRunnerApplication app = context.getBean(TasksRunnerApplication.class);
        app.run(context);
    }
}
