package hubble.backend.business.services.models.distValues.events;

import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.core.enums.DateTypes;

public class DistributionEventsGroup extends DistValues {
    private DateTypes TipoFecha;
    private String fecha;
    private String monitor;

    public DistributionEventsGroup(int valor, String status, String fecha, String monitor, DateTypes tipoFecha){
        super(valor,status);
        this.fecha = fecha;
        this.monitor = monitor;
        this.TipoFecha = tipoFecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }


    public DateTypes getTipoFecha() {
        return TipoFecha;
    }

    public void setTipoFecha(DateTypes tipoFecha) {
        TipoFecha = tipoFecha;
    }
}
