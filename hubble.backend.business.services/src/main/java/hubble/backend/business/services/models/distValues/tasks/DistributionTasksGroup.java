package hubble.backend.business.services.models.distValues.tasks;

import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.core.enums.DateTypes;

public class DistributionTasksGroup extends DistValues {
    private DateTypes TipoFecha;
    private String fecha;

    public DistributionTasksGroup(int valor, String status, String fecha,DateTypes tipoFecha)
    {
        super(valor,status);
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
