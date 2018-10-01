package hubble.backend.core.enums;

import static java.util.Arrays.asList;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa los KPIs existentes en la aplicación.
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public enum KPITypes {
    TASKS, DEFECTS, AVAILABILITY, PERFORMANCE, EVENTS;
    
    public static Set<KPITypes> all() {
        return new HashSet<>(asList(values()));
    }
}
