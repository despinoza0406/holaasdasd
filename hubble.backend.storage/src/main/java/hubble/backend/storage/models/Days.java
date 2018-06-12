package hubble.backend.storage.models;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public enum Days {
    MONDAY_TO_FRIDAY("MON-FRI"), EVERY_DAY("*");
    
    private final String days;

    private Days(String days) {
        this.days = days;
    }
    
    public String days() {
        return days;
    }
}
