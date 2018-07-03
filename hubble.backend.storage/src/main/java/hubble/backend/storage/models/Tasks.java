package hubble.backend.storage.models;

/**
 * KPI de tareas.
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class Tasks implements KPI {

    private boolean enabled;
    private Threashold dayThreashold;
    private Threashold weekThreashold;
    private Threashold monthThreashold;
    private ApplicationInProvider ppm;

    public Tasks() {
    }

    public Tasks(boolean enabled, Threashold dayThreashold, Threashold weekThreashold, Threashold monthThreashold,
        ApplicationInProvider ppm) {
        this.enabled = enabled;
        this.dayThreashold = dayThreashold;
        this.weekThreashold = weekThreashold;
        this.monthThreashold = monthThreashold;
        this.ppm = ppm;
    }

    @Override
    public boolean getEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean value) {
        enabled = true;
    }

    public Threashold getDayThreashold() {
        return dayThreashold;
    }

    public void setDayThreashold(Threashold dayThreashold) {
        this.dayThreashold = dayThreashold;
    }

    public Threashold getWeekThreashold() {
        return weekThreashold;
    }

    public void setWeekThreashold(Threashold weekThreashold) {
        this.weekThreashold = weekThreashold;
    }

    public Threashold getMonthThreashold() {
        return monthThreashold;
    }

    public void setMonthThreashold(Threashold monthThreashold) {
        this.monthThreashold = monthThreashold;
    }

    public ApplicationInProvider getPpm() {
        return ppm;
    }

    public void setPpm(ApplicationInProvider ppm) {
        this.ppm = ppm;
    }

}
