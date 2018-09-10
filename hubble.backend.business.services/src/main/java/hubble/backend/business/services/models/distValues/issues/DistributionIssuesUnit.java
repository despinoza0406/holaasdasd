package hubble.backend.business.services.models.distValues.issues;

import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.core.enums.DateTypes;

public class DistributionIssuesUnit extends DistValues {
    private String description;
    private DateTypes TipoFecha;
    private String fecha;

    public DistributionIssuesUnit(float valor, String status, String description, String fechaAlta,DateTypes tipo){
        super(valor,status);
        this.description = description;
        this.fecha = fechaAlta;
        this.TipoFecha = tipo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fechaAlta) {
        this.fecha = fechaAlta;
    }

    public DateTypes getTipoFecha() {
        return TipoFecha;
    }

    public void setTipoFecha(DateTypes tipoFecha) {
        TipoFecha = tipoFecha;
    }
}
