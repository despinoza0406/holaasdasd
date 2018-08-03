package hubble.backend.business.services.models;

public class DistributionValues implements DistValues{
    private int distValue;

    public DistributionValues() {
    }

    public DistributionValues(int distValue){
        this.distValue = distValue;
    }

    public int getDistValue() {
        return distValue;
    }

    public void setDistValue(int distValue) {
        this.distValue = distValue;
    }
}
