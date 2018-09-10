package hubble.backend.business.services.models.distValues.issues;

import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.core.enums.DateTypes;

public class DistributionIssuesGroup extends DistValues {
    private DateTypes TipoFecha;
    private String fecha;

    public DistributionIssuesGroup(double valor, String status, String fecha, DateTypes tipoFecha){
        super((float) valor,status);
        this.fecha = fecha;
        this.TipoFecha = tipoFecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public DateTypes getTipoFecha() {
        return TipoFecha;
    }

    public void setTipoFecha(DateTypes tipoFecha) {
        TipoFecha = tipoFecha;
    }
}
