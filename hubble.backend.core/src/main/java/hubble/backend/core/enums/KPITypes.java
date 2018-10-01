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
    }, DEFECTS {
        public String getKPIMeditionType() {
            return "Severidad";
        }
    }, AVAILABILITY {
        public String getKPIMeditionType() {
            return "Porcentaje de disponibilidad";
        }
    }, PERFORMANCE {
        public String getKPIMeditionType() {
            return "Milisegundos";
        }
    }, EVENTS {
        public String getKPIMeditionType() {
            return "Severidad";
        }
    };
    
    public static Set<KPITypes> all() {
        return new HashSet<>(asList(values()));
    }
}
