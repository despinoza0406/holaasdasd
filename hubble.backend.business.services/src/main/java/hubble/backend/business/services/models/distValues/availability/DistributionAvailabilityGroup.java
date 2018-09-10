package hubble.backend.business.services.models.distValues.availability;

import hubble.backend.core.enums.DateTypes;

public class DistributionAvailabilityGroup extends DistributionAvailabilityUnit {

    public DistributionAvailabilityGroup(int value, String status, String transaction, String date, DateTypes tipo){
        super(value,status,transaction,date,tipo);
    }

}
