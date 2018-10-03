package hubble.backend.core.enums;

import hubble.backend.core.KPICapabilities;

import static java.util.Arrays.asList;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa los KPIs existentes en la aplicación.
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public enum KPITypes implements KPICapabilities {
    TASKS {
        public String getKPIMeditionType() {
            return "Días de desvío";
        }
        public String getKPIForMenu() { return "Tareas"; }
    }, DEFECTS {
        public String getKPIMeditionType() {
            return "Severidad";
        }
        public String getKPIForMenu() { return "Incidencias"; }
    }, AVAILABILITY {
        public String getKPIMeditionType() {
            return "Porcentaje de disponibilidad";
        }
        public String getKPIForMenu() { return "Disponibilidad"; }
    }, PERFORMANCE {
        public String getKPIMeditionType() {
            return "Milisegundos";
        }
        public String getKPIForMenu() { return "Performance"; }
    }, EVENTS {
        public String getKPIMeditionType() {
            return "Severidad";
        }
        public String getKPIForMenu() { return "Eventos"; }
    };
    
    public static Set<KPITypes> all() {
        return new HashSet<>(asList(values()));
    }
}
