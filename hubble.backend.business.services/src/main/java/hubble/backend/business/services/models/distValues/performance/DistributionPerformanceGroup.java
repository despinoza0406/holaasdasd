package hubble.backend.business.services.models.distValues.performance;

import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.core.enums.DateTypes;

public class DistributionPerformanceGroup extends DistributionPerformanceUnit {

    public DistributionPerformanceGroup(int valor, String status, String transaction, String fecha, DateTypes tipo){
        super(valor,status,transaction,fecha,tipo);
    }
}
