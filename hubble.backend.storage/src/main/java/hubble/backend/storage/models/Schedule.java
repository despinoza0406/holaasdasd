package hubble.backend.storage.models;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class Schedule {

    private final Frecuency frecuency;
    private final Days days;
    private final HourRange hours;

    public Schedule(Days days, HourRange hours, Frecuency frecuency) {
        this.frecuency = frecuency;
        this.days = days;
        this.hours = hours;
    }

    public String cronExpression() {
        return String.format("0 0 %s %s * *", hours.hours(frecuency), days.days());
    }

}
