package hubble.backend.api.models;

public class DistValues {
    private int distValue;

    public DistValues() {
    }

    public DistValues(int distValue){
        this.distValue = distValue;
    }

    public int getDistValue() {
        return distValue;
    }

    public void setDistValue(int distValue) {
        this.distValue = distValue;
    }
}
