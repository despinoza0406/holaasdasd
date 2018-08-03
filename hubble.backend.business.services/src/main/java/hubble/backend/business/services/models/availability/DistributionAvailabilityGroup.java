package hubble.backend.business.services.models.availability;

public class DistributionAvailabilityGroup extends DistributionAvailabilityUnit {

    public DistributionAvailabilityGroup(int value, String status, String transaction, String date){
        this.setValor(value);
        this.setStatus(status);
        this.setTransaction(transaction);
        this.setFecha(date);
    }

}
