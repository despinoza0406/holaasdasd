package hubble.backend.business.services.models.measures;

public class IssuesQuantity extends Quantity {

    private Integer criticalThreshold;

    public Integer getCriticalThreshold() {
        return criticalThreshold;
    }

    public void setCriticalThreshold(Integer criticalThreshold) {
        this.criticalThreshold = criticalThreshold;
    }
}
