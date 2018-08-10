package hubble.backend.tasksrunner.tasks;

import org.quartz.JobDetail;
import org.quartz.Trigger;

public interface Task {

    public Trigger getTrigger();

    public JobDetail getJobDetail();

    public int getIntervalSeconds();

    public void setIntervalSeconds(int seconds);

    public void setTrigger(Trigger trigger);

    public void setJobDetail(JobDetail jobDetail);

    public void setCronExpression(String cronExpression);

    public String getCronExpression();

    public String getIndentityName();

    public void setIndentityName(String name);

    public String getIndentityGroupName();

    public void setIndentityGroupName(String groupName);

}
