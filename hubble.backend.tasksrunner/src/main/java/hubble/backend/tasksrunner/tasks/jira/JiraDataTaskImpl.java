package hubble.backend.tasksrunner.tasks.jira;

import hubble.backend.tasksrunner.jobs.ParserJob;
import hubble.backend.tasksrunner.jobs.jira.JiraDataParserJob;
import hubble.backend.tasksrunner.tasks.ParserTask;
import static org.quartz.JobBuilder.newJob;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;

public class JiraDataTaskImpl implements ParserTask {

    private String identityName;
    private String identityGroupName;
    private ParserJob jiraJob;
    private JobDetail job;
    private Trigger trigger;
    private int interval;
    private String cronExpression;
    
    public JiraDataTaskImpl() { 
    }
    
    public JiraDataTaskImpl(ParserJob jiraJob) {
        this.jiraJob = jiraJob;
    }
    
    @Override
    public ParserJob getParserJob() {
        return this.jiraJob;
    }

    @Override
    public void setParserJob(ParserJob parserJob) {
        this.jiraJob = parserJob;
    }

    @Override
    public Trigger getTrigger() {
        this.trigger = newTrigger()
                .withIdentity(this.identityName, this.identityGroupName)
                .startNow()
                .withSchedule(CronScheduleBuilder
                        .cronSchedule(cronExpression))
                .build();
        return this.trigger;
    }

    @Override
    public JobDetail getJobDetail() {
        this.job = newJob(JiraDataParserJob.class)
                .withIdentity(this.identityName, this.identityGroupName)
                .build();
        return this.job;
    }

    @Override
    public int getIntervalSeconds() {
        return this.interval;
    }

    @Override
    public void setIntervalSeconds(int seconds) {
        this.interval = seconds;
    }

    @Override
    public void setCronExpression(String cronExpression) { this.cronExpression = cronExpression; }

    @Override
    public String getCronExpression() { return cronExpression; }

    @Override
    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    @Override
    public void setJobDetail(JobDetail jobDetail) {
        this.job = jobDetail;
    }

    @Override
    public String getIndentityName() {
        return this.identityName;
    }

    @Override
    public void setIndentityName(String name) {
        this.identityName = name;
    }

    @Override
    public String getIndentityGroupName() {
        return this.identityGroupName;
    }

    @Override
    public void setIndentityGroupName(String groupName) {
        this.identityGroupName = groupName;
    }   
}