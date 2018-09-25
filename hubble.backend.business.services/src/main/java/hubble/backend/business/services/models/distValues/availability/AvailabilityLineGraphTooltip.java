package hubble.backend.business.services.models.distValues.availability;

import hubble.backend.business.services.models.distValues.LineGraphTooltip;

public class AvailabilityLineGraphTooltip implements LineGraphTooltip {
    private String origen;
    private String transaccion;
    private String locacion;

    public AvailabilityLineGraphTooltip() {
    }

    public AvailabilityLineGraphTooltip(String origen, String transaccion, String locacion) {
        this.origen = origen;
        this.transaccion = transaccion;
        this.locacion = locacion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public String getLocacion() {
        return locacion;
    }

    public void setLocacion(String locacion) {
        this.locacion = locacion;
    }
}
