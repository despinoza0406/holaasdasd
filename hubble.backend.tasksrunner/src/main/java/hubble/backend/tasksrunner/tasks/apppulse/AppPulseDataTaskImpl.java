package hubble.backend.tasksrunner.tasks.apppulse;

import hubble.backend.tasksrunner.jobs.ParserJob;
import hubble.backend.tasksrunner.tasks.ParserTask;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;

public class AppPulseDataTaskImpl implements ParserTask {

    String identityName;
    String identityGroupName;
    ParserJob appPulseJob;
    JobDetail job;
    Trigger trigger;
    int interval = 0;
    String cronExpression;

    public AppPulseDataTaskImpl() {

    }

    public AppPulseDataTaskImpl(ParserJob providerJob) {
        appPulseJob = providerJob;
    }

    @Override
    public Trigger getTrigger() {
        this.trigger = newTrigger()
                .withIdentity(this.identityName, this.identityGroupName)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(this.interval)
                        .repeatForever())
                .build();
        return this.trigger;
    }

    @Override
    public JobDetail getJobDetail() {
        this.job = newJob(appPulseJob.getClass())
                .withIdentity(this.identityName, this.identityGroupName)
                .build();
        return this.job;
    }

    @Override
    public int getIntervalSeconds() {
        return interval;
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
    public ParserJob getParserJob() {
        return this.appPulseJob;
    }

    @Override
    public void setParserJob(ParserJob parserJob) {
        this.appPulseJob = parserJob;
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
