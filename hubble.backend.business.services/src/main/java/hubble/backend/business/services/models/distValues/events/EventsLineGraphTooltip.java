package hubble.backend.business.services.models.distValues.events;

import hubble.backend.business.services.models.distValues.LineGraphTooltip;

public class EventsLineGraphTooltip implements LineGraphTooltip {
    private String origen;
    private String tipo;
    private String estado;

    public EventsLineGraphTooltip() {
    }

    public EventsLineGraphTooltip(String origen, String tipo, String estado) {
        this.origen = origen;
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String servidor) {
        this.tipo = servidor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
