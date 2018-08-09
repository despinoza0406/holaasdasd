package hubble.backend.business.services.models.distValues.events;

import hubble.backend.business.services.models.distValues.DistValues;

public class DistributionEventsUnit extends DistValues {

    private String date;
    private String monitorType;
    private String message;

    public DistributionEventsUnit(int value, String status, String date, String monitorType, String message){
        super(value,status);
        this.date = date;
        this.monitorType = monitorType;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(String monitorType) {
        this.monitorType = monitorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
