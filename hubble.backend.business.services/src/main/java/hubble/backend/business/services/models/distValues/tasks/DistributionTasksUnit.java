package hubble.backend.business.services.models.distValues.tasks;

import hubble.backend.business.services.models.distValues.DistValues;

public class DistributionTasksUnit extends DistValues {
    private String name;
    private String taskStatus;
    private String endDate;

    public DistributionTasksUnit(int valor, String status, String name, String taskStatus, String endDate){
        super(valor,status);
        this.name = name;
        this.taskStatus = taskStatus;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
