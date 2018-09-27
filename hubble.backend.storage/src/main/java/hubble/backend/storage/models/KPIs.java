package hubble.backend.storage.models;

import java.util.HashSet;
import java.util.Set;

import hubble.backend.core.enums.KPITypes;
import org.springframework.data.annotation.Transient;

/**
 * KPIs de una aplicación Hubble.
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class KPIs {

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

    @Transient
    public Set<KPITypes> getEnabledKPIs() {

        Set<KPITypes> enabledKPIs = new HashSet<>();
        
        if (this.tasks.isEnabled()) {
            enabledKPIs.add(KPITypes.TASKS);
        }

        if (this.defects.isEnabled()) {
            enabledKPIs.add(KPITypes.DEFECTS);
        }

        if (this.availability.isEnabled()) {
            enabledKPIs.add(KPITypes.AVAILABILITY);
        }

        if (this.performance.isEnabled()) {
            enabledKPIs.add(KPITypes.PERFORMANCE);
        }

         if (this.events.isEnabled()) {
            enabledKPIs.add(KPITypes.EVENTS);
        }

        return enabledKPIs;
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
