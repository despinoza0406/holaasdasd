package hubble.backend.storage.models;

/**
 * KPI de defectos.
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class Defects implements KPI {

    private boolean enabled;
    private Threashold dayThreashold;
    private Threashold weekThreashold;
    private Threashold monthThreashold;
    private ApplicationInProvider alm;
    private ApplicationInProvider jira;

    public Defects() {
    }

    public Defects(boolean enabled, Threashold dayThreashold, Threashold weekThreashold, Threashold monthThreashold,
        ApplicationInProvider alm, ApplicationInProvider jira) {
        this.enabled = enabled;
        this.dayThreashold = dayThreashold;
        this.weekThreashold = weekThreashold;
        this.monthThreashold = monthThreashold;
        this.alm = alm;
        this.jira = jira;
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

    public ApplicationInProvider getAlm() {
        return alm;
    }

    public void setAlm(ApplicationInProvider alm) {
        this.alm = alm;
    }

    public ApplicationInProvider getJira() {
        return jira;
    }

    public void setJira(ApplicationInProvider jira) {
        this.jira = jira;
    }

}
