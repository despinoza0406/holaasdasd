package hubble.backend.business.services.models.distValues.performance;

import hubble.backend.business.services.models.distValues.DistValues;

public class DistributionPerformanceGroup extends DistributionPerformanceUnit {

    public DistributionPerformanceGroup(int valor, String status, String transaction, String fecha){
        super(valor,status,transaction,fecha);
    }
}
