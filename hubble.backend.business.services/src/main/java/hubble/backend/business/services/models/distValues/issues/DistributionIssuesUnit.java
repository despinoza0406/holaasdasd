package hubble.backend.business.services.models.distValues.issues;

import hubble.backend.business.services.models.distValues.DistValues;

public class DistributionIssuesUnit extends DistValues {
    private String description;
    private String fechaAlta;

    public DistributionIssuesUnit(float valor, String status, String description, String fechaAlta){
        super(valor,status);
        this.description = description;
        this.fechaAlta = fechaAlta;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
}
