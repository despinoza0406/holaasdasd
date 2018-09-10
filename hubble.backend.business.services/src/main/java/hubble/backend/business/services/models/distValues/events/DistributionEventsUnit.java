package hubble.backend.business.services.models.distValues.events;

import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.core.enums.DateTypes;

public class DistributionEventsUnit extends DistValues {
    private DateTypes TipoFecha;
    private String fecha;
    private String TipoMonitor;
    private String Mensaje;

    public DistributionEventsUnit(int value, String status, String date, String monitorType, String message,DateTypes tipo){
        super(value,status);
        this.fecha = date;
        this.TipoMonitor = monitorType;
        this.Mensaje = message;
        this.TipoFecha = tipo;
    }



    public DateTypes getTipoFecha() {
        return TipoFecha;
    }

    public void setTipoFecha(DateTypes tipoFecha) {
        TipoFecha = tipoFecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoMonitor() {
        return TipoMonitor;
    }

    public void setTipoMonitor(String tipoMonitor) {
        TipoMonitor = tipoMonitor;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }
}
