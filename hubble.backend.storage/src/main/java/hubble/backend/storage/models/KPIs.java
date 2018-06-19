package hubble.backend.storage.models;

import java.util.Set;

/**
 * KPIs de una aplicación Hubble.
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class KPIs {

    private Set<KPITypes> enabledKPIs;
    private Tasks tasks;
    private Defects defects;
    private Availavility availability;
    private Performance performance;
    private Events events;

    public KPIs() {
    }

    public KPIs(Tasks tasks, Defects defects, Availavility availability, Performance performance, Events events) {
        this.tasks = tasks;
        this.defects = defects;
        this.availability = availability;
        this.performance = performance;
        this.events = events;
    }

    public Set<KPITypes> getEnabledKPIs() {
        return enabledKPIs;
    }

    public void setEnabledKPIs(Set<KPITypes> enabledKPIs) {
        this.enabledKPIs = enabledKPIs;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    public Defects getDefects() {
        return defects;
    }

    public void setDefects(Defects defects) {
        this.defects = defects;
    }

    public Availavility getAvailability() {
        return availability;
    }

    public void setAvailability(Availavility availability) {
        this.availability = availability;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
        this.events = events;
    }

}
