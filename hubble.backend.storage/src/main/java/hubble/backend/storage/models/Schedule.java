package hubble.backend.storage.models;

import com.fasterxml.jackson.databind.JsonNode;
import hubble.backend.core.enums.MonitoringFields;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class Schedule {

    private Frecuency frecuency;
    private Days days;
    private HourRange hours;

    public Schedule() {
    }

    public Frecuency getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(Frecuency frecuency) {
        this.frecuency = frecuency;
    }

    public Days getDays() {
        return days;
    }

    public void setDays(Days days) {
        this.days = days;
    }

    public HourRange getHours() {
        return hours;
    }

    public void setHours(HourRange hours) {
        this.hours = hours;
    }
    
    public Schedule(Days days, HourRange hours, Frecuency frecuency) {
        this.frecuency = frecuency;
        this.days = days;
        this.hours = hours;
    }

    public String cronExpression() {
        return String.format("0 0 %s %s * *", hours.hours(frecuency), days.days());
    }
    
      public Schedule fromJson(JsonNode jsonNode) {

        this.frecuency = Frecuency.valueOf(jsonNode.get("frecuency").asText());
        this.days = Days.valueOf(jsonNode.get("days").asText());
        this.hours = HourRange.valueOf(jsonNode.get("hours").asText());
        
        return this;
    }


}
