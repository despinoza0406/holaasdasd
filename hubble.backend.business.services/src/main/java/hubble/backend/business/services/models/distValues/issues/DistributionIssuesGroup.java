package hubble.backend.business.services.models.distValues.issues;

import hubble.backend.business.services.models.distValues.DistValues;

public class DistributionIssuesGroup extends DistValues {
    private String fecha;

    public DistributionIssuesGroup(double valor, String status, String fecha){
        super((float) valor,status);
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
