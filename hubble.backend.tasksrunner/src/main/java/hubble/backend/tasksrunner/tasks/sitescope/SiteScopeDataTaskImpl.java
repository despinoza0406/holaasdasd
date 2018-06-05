package hubble.backend.tasksrunner.tasks.sitescope;

import hubble.backend.tasksrunner.jobs.ParserJob;
import hubble.backend.tasksrunner.tasks.ParserTask;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class SiteScopeDataTaskImpl implements ParserTask {
    String identityName;
    String identityGroupName;
    ParserJob siteScopeJob;
    JobDetail job;
    Trigger trigger;
    int interval = 0;

    public SiteScopeDataTaskImpl() {

    }

    public SiteScopeDataTaskImpl(ParserJob providerJob) {
        siteScopeJob = providerJob;
    }

    @Override
    public Trigger getTrigger() {
        this.trigger = newTrigger()
                .withIdentity(this.identityName, this.identityGroupName)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(this.interval)
                        .repeatForever()
                )
                .build();
        return this.trigger;
    }

    @Override
    public JobDetail getJobDetail() {
        this.job = newJob(siteScopeJob.getClass())
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
    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    @Override
    public void setJobDetail(JobDetail jobDetail) {
        this.job = jobDetail;
    }

    @Override
    public ParserJob getParserJob() {
        return this.siteScopeJob;
    }

    @Override
    public void setParserJob(ParserJob parserJob) {
        this.siteScopeJob = parserJob;
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
