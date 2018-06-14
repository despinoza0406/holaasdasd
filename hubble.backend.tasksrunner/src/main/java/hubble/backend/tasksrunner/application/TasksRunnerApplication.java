package hubble.backend.tasksrunner.application;

import hubble.backend.providers.parsers.interfaces.alm.AlmApplicationParser;
import hubble.backend.providers.parsers.interfaces.alm.AlmDataParser;
import hubble.backend.providers.parsers.interfaces.bsm.BsmApplicationParser;
import hubble.backend.providers.parsers.interfaces.bsm.BsmDataParser;
import hubble.backend.providers.parsers.interfaces.jira.JiraApplicationParser;
import hubble.backend.providers.parsers.interfaces.jira.JiraDataParser;
import hubble.backend.providers.parsers.interfaces.ppm.PpmApplicationParser;
import hubble.backend.providers.parsers.interfaces.ppm.PpmDataParser;
import hubble.backend.providers.parsers.interfaces.sitescope.SiteScopeApplicationParser;
import hubble.backend.providers.parsers.interfaces.sitescope.SiteScopeDataParser;
import hubble.backend.tasksrunner.application.scheduler.SchedulerMediator;
import hubble.backend.tasksrunner.configurations.TasksRunnerConfiguration;
import hubble.backend.tasksrunner.jobs.ParserJob;
import hubble.backend.tasksrunner.jobs.alm.AlmApplicationParserJob;
import hubble.backend.tasksrunner.jobs.alm.AlmDataParserJob;
import hubble.backend.tasksrunner.jobs.bsm.BsmApplicationParserJob;
import hubble.backend.tasksrunner.jobs.bsm.BsmDataParserJob;
import hubble.backend.tasksrunner.jobs.jira.JiraApplicationParserJob;
import hubble.backend.tasksrunner.jobs.jira.JiraDataParserJob;
import hubble.backend.tasksrunner.jobs.ppm.PpmApplicationParserJob;
import hubble.backend.tasksrunner.jobs.ppm.PpmDataParserJob;
import hubble.backend.tasksrunner.jobs.sitescope.SiteScopeApplicationParserJob;
import hubble.backend.tasksrunner.jobs.sitescope.SiteScopeDataParserJob;
import hubble.backend.tasksrunner.tasks.ParserTask;
import hubble.backend.tasksrunner.tasks.Task;
import hubble.backend.tasksrunner.tasks.alm.AlmApplicationTaskImpl;
import hubble.backend.tasksrunner.tasks.alm.AlmDataTaskImpl;
import hubble.backend.tasksrunner.tasks.bsm.BsmApplicationTaskImpl;
import hubble.backend.tasksrunner.tasks.bsm.BsmDataTaskImpl;
import hubble.backend.tasksrunner.tasks.jira.JiraApplicationTaskImpl;
import hubble.backend.tasksrunner.tasks.jira.JiraDataTaskImpl;
import hubble.backend.tasksrunner.tasks.ppm.PpmApplicationTaskImpl;
import hubble.backend.tasksrunner.tasks.ppm.PpmDataTaskImpl;
import hubble.backend.tasksrunner.tasks.sitescope.SiteScopeApplicationTaskImpl;
import hubble.backend.tasksrunner.tasks.sitescope.SiteScopeDataTaskImpl;
import org.quartz.SchedulerException;
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

    SchedulerMediator scheduler;
    ConfigurableApplicationContext context;

    public void run(ConfigurableApplicationContext context) throws SchedulerException, Exception {

        scheduler = new SchedulerMediator(context);
/*
        //AppPulse
        /*
        AppPulseActiveDataParser appPulseparser = context.getBean(AppPulseActiveDataParser.class);
        ParserJob appPulseJob = new AppPulseDataParserJob(appPulseparser);
        ParserTask appPulseDataTask = new AppPulseDataTaskImpl(appPulseJob);
        appPulseDataTask.setIndentityGroupName("AppPulse Active Provider Job");
        appPulseDataTask.setIndentityName("AppPulse Data");
        appPulseDataTask.setIntervalSeconds(40);

        AppPulseActiveApplicationsParser appPulseApplicationParser = context.getBean(AppPulseActiveApplicationsParser.class);
        ParserJob appPulseAppJob = new AppPulseApplicationParserJob(appPulseApplicationParser);
        ParserTask appPulseApplicationTask = new AppPulseApplicationTaskImpl(appPulseAppJob);
        appPulseApplicationTask.setIndentityGroupName("AppPulse Active Provider Job");
        appPulseApplicationTask.setIndentityName("AppPulse Applications");
        appPulseApplicationTask.setIntervalSeconds(100);
        scheduler.addTask(appPulseApplicationTask);
        scheduler.addTask(appPulseDataTask);
        */
        //BSM

        BsmDataParser bsmParser = context.getBean(BsmDataParser.class);
        ParserJob bsmJob = new BsmDataParserJob(bsmParser);
        ParserTask bsmTask = new BsmDataTaskImpl(bsmJob);
        bsmTask.setIndentityGroupName("BSM");
        bsmTask.setIndentityName("BSM Data Transacciones");
        bsmTask.setIntervalSeconds(60 * 60);

        BsmApplicationParser bsmApplicationParser = context.getBean(BsmApplicationParser.class);
        ParserJob bsmApplicationJob = new BsmApplicationParserJob(bsmApplicationParser);
        Task bsmApplicationTask = new BsmApplicationTaskImpl(bsmApplicationJob);
        bsmApplicationTask.setIndentityGroupName("BSM");
        bsmApplicationTask.setIndentityName("BSM Applicaciones");
        bsmApplicationTask.setIntervalSeconds(60 * 60);
        scheduler.addTask(bsmApplicationTask);
        scheduler.addTask(bsmTask);
        
        //Alm
        AlmDataParser almParser = context.getBean(AlmDataParser.class);
        ParserJob almJob = new AlmDataParserJob(almParser);
        ParserTask almDataTask = new AlmDataTaskImpl(almJob);
        almDataTask.setIndentityGroupName("Alm Provider Job");
        almDataTask.setIndentityName("Alm Data");
        almDataTask.setIntervalSeconds(60); //1 día
        
        AlmApplicationParser almApplicationParser = context.getBean(AlmApplicationParser.class);
        ParserJob almApplicationJob = new AlmApplicationParserJob(almApplicationParser);
        ParserTask almApplicationTask = new AlmApplicationTaskImpl(almApplicationJob);
        almApplicationTask.setIndentityGroupName("Alm Provider Job");
        almApplicationTask.setIndentityName("Alm Applications");
        almApplicationTask.setIntervalSeconds(60 * 60 * 24);

        scheduler.addTask(almDataTask);
        scheduler.addTask(almApplicationTask);
        
        //Ppm
        PpmDataParser ppmParser = context.getBean(PpmDataParser.class);
        ParserJob ppmJob = new PpmDataParserJob(ppmParser);
        ParserTask ppmDataTask = new PpmDataTaskImpl(ppmJob);
        ppmDataTask.setIndentityGroupName("Ppm Provider Job");
        ppmDataTask.setIndentityName("Ppm Data");
        ppmDataTask.setIntervalSeconds(60 * 60 * 24);
        
        PpmApplicationParser ppmApplicationParser = context.getBean(PpmApplicationParser.class);
        ParserJob ppmApplicationJob = new PpmApplicationParserJob(ppmApplicationParser);
        ParserTask ppmApplicationTask = new PpmApplicationTaskImpl(ppmApplicationJob);
        ppmApplicationTask.setIndentityGroupName("Ppm Provider Job");
        ppmApplicationTask.setIndentityName("Ppm Applications");
        ppmApplicationTask.setIntervalSeconds(60 * 60 * 24); //1 día
        
        scheduler.addTask(ppmDataTask);
        scheduler.addTask(ppmApplicationTask);
        
        //Jira
        JiraDataParser jiraParser = context.getBean(JiraDataParser.class);
        ParserJob jiraJob = new JiraDataParserJob(jiraParser);
        ParserTask jiraDataTask = new JiraDataTaskImpl(jiraJob);
        jiraDataTask.setIndentityGroupName("Jira Provider Job");
        jiraDataTask.setIndentityName("Jira Data");
        jiraDataTask.setIntervalSeconds(60);
        
        JiraApplicationParser jiraAppParser = context.getBean(JiraApplicationParser.class);
        ParserJob jiraAppJob = new JiraApplicationParserJob(jiraAppParser);
        ParserTask jiraAppTask = new JiraApplicationTaskImpl(jiraAppJob);
        jiraAppTask.setIndentityGroupName("Jira Provider Job");
        jiraAppTask.setIndentityName("Jira Applications");
        jiraAppTask.setIntervalSeconds(60 * 60);
        scheduler.addTask(jiraDataTask);
        scheduler.addTask(jiraAppTask);

        //SiteScope
        SiteScopeDataParser siteScopeDataParser = context.getBean(SiteScopeDataParser.class);
        ParserJob siteScopeJob = new SiteScopeDataParserJob(siteScopeDataParser);
        ParserTask siteScopeDataTask = new SiteScopeDataTaskImpl(siteScopeJob);
        siteScopeDataTask.setIndentityGroupName("SiteScope Provider Job");
        siteScopeDataTask.setIndentityName("SiteScope Data");
        siteScopeDataTask.setIntervalSeconds(60 * 5);

        SiteScopeApplicationParser siteScopeApplicationParser = context.getBean(SiteScopeApplicationParser.class);
        ParserJob siteScopeAppJob = new SiteScopeApplicationParserJob(siteScopeApplicationParser);
        ParserTask siteScopeAppTask = new SiteScopeApplicationTaskImpl(siteScopeAppJob);
        siteScopeAppTask.setIndentityGroupName("SiteScope Provider Job");
        siteScopeAppTask.setIndentityName("SiteScope Applications");
        siteScopeAppTask.setIntervalSeconds(100);

        scheduler.addTask(siteScopeDataTask);
        scheduler.addTask(siteScopeAppTask);

        scheduler.showMenu();
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
