package hubble.backend.tasksrunner.application.scheduler;

import hubble.backend.storage.models.ProviderStorage;
import hubble.backend.storage.repositories.ProvidersRepository;
import hubble.backend.tasksrunner.application.scheduler.menu.Menu;
import hubble.backend.tasksrunner.application.scheduler.menu.MenuImpl;
import hubble.backend.tasksrunner.tasks.Task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SchedulerMediator implements SchedulerUserCommands, SchedulerTasksActions {

    Menu menu;
    public Scheduler scheduler;
    ConfigurableApplicationContext context;
    @Autowired
    ProvidersRepository providersRepository;

    private final Logger logger = LoggerFactory.getLogger(SchedulerMediator.class);

    public SchedulerMediator(ConfigurableApplicationContext context, Scheduler scheduler, Menu menu) {
        this.context = context;
        this.scheduler = scheduler;
        this.menu = menu;
    }

    public SchedulerMediator(){

    }

    public SchedulerMediator(ConfigurableApplicationContext context) throws SchedulerException, Exception {
        this.context = context;

        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getContext().put("context", this.context);

        this.menu = new MenuImpl(this);
    }

    public void startContext(ConfigurableApplicationContext context) throws SchedulerException, Exception{
        this.context = context;

        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getContext().put("context", this.context);

        this.menu = new MenuImpl(this);
    }

    @Override
    public ConfigurableApplicationContext getContext() {
        return this.context;
    }

    @Override
    public void setContext(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Override
    public void start() {
        try {
            this.scheduler.start();
            /*
            for(String name: scheduler.getJobGroupNames()){
                for(JobKey key: scheduler.getJobKeys(GroupMatcher.jobGroupEquals(name))){
                    scheduler.triggerJob(key);
                }
            }*/
        } catch (SchedulerException ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void pause() {
        try {
            this.scheduler.standby();
        } catch (SchedulerException ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void print() {
        try {

            for (String groupName : scheduler.getJobGroupNames()) {

                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();

                    //get job's trigger
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();

                    System.out.println("[jobName] : " + jobName + " [groupName] : "
                            + jobGroup + " - [nextExecution]" + nextFireTime);
                }
            }
        } catch (SchedulerException ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void shutdown() {
        try {
            this.scheduler.shutdown();
        } catch (SchedulerException ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void addTask(Task taskRunner) throws Exception {

        if ((taskRunner.getIndentityGroupName() == null || taskRunner.getIndentityGroupName().equals(EMPTY))
                || (taskRunner.getIndentityGroupName() == null || taskRunner.getIndentityGroupName().equals(EMPTY))) {
            int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
            throw new Exception("Identity and Group Name are empty. SchedulerMediator.addTask. Line Number:" + lineNumber);
        }

        try {
            this.scheduler.scheduleJob(taskRunner.getJobDetail(), taskRunner.getTrigger());
        } catch (SchedulerException ex) {
           logger.error(ex.getMessage());
        }
    }

    @Override
    public void showMenu() {
        menu.execute();
    }

    public void execute(String provider){
        ProviderStorage storage = null;

        try {
            for (String groupName : scheduler.getJobGroupNames()) {

                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                    String jobName = jobKey.getName();

                    if(jobName.equalsIgnoreCase(provider)) {
                        scheduler.triggerJob(jobKey);
                    }
                }

            }
        }catch (SchedulerException e){
            logger.error(e.getMessage());
        }
    }

    public void reschedule(String provider) {

        ProviderStorage storage = null;

        try {
            for (String groupName : scheduler.getJobGroupNames()) {

                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                    String jobName = jobKey.getName();

                    if(jobName.equalsIgnoreCase(provider)) {
                        scheduler.pauseJob(jobKey);

                        Method[] methods = providersRepository.getClass().getMethods();
                        List<Method> methodList = Arrays.asList(methods);


                        methodList = methodList.stream().filter(method -> method.getName().equalsIgnoreCase(jobName)).collect(Collectors.toList());
                        try {
                            storage = (ProviderStorage) methodList.get(0).invoke(providersRepository);
                        } catch (IllegalArgumentException e) {
                            logger.error(e.getMessage());
                        } catch (IllegalAccessException e) {
                            logger.error(e.getMessage());
                        } catch (InvocationTargetException e) {
                            logger.error(e.getMessage());
                        }

                        List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                        Trigger newTrigger = null;
                        try {
                            newTrigger = newTrigger().withIdentity(jobKey.getName(), jobKey.getGroup())
                                    .withSchedule(CronScheduleBuilder
                                            .cronSchedule(storage.getTaskRunner().getSchedule().cronExpression()))
                                    .build();

                        } catch (NullPointerException e) {
                            logger.error(e.getMessage());
                        }
                        //get job's trigger
                        Trigger oldTrigger = triggers.get(0);
                        try {
                            scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
                        }catch (ObjectAlreadyExistsException e){
                            logger.error(e.getMessage());
                        }
                        System.out.println("[jobName] : " + jobName + " [groupName] : "
                                + jobKey.getGroup() + " - [nextExecution]" + newTrigger.getNextFireTime());
                        scheduler.resumeJob(jobKey);
                    }
                }

            }
        }catch (SchedulerException e){
            logger.error(e.getMessage());
        }
    }
}
