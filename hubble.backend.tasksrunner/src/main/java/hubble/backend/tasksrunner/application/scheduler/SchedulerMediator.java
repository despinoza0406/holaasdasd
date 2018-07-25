package hubble.backend.tasksrunner.application.scheduler;

import hubble.backend.tasksrunner.application.scheduler.menu.Menu;
import hubble.backend.tasksrunner.application.scheduler.menu.MenuImpl;
import hubble.backend.tasksrunner.tasks.Task;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.commons.lang.StringUtils.EMPTY;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.ConfigurableApplicationContext;

public class SchedulerMediator implements SchedulerUserCommands, SchedulerTasksActions {

    Menu menu;
    public Scheduler scheduler;
    ConfigurableApplicationContext context;

    public SchedulerMediator(ConfigurableApplicationContext context, Scheduler scheduler, Menu menu) {
        this.context = context;
        this.scheduler = scheduler;
        this.menu = menu;
    }

    public SchedulerMediator(ConfigurableApplicationContext context) throws SchedulerException, Exception {
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
            for(String name: scheduler.getJobGroupNames()){
                for(JobKey key: scheduler.getJobKeys(GroupMatcher.jobGroupEquals(name))){
                    scheduler.triggerJob(key);
                }
            }
        } catch (SchedulerException ex) {
            Logger.getLogger(SchedulerMediator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void pause() {
        try {
            this.scheduler.standby();
        } catch (SchedulerException ex) {
            Logger.getLogger(SchedulerMediator.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SchedulerMediator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void shutdown() {
        try {
            this.scheduler.shutdown();
        } catch (SchedulerException ex) {
            Logger.getLogger(SchedulerMediator.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SchedulerMediator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void showMenu() {
        menu.execute();
    }
}
