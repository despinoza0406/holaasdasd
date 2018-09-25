package hubble.backend.business.services.models.distValues.tasks;

import hubble.backend.business.services.models.distValues.LineGraphTooltip;

public class WorkItemLineGraphTooltip implements LineGraphTooltip {
    private String origen;
    private String titulo;
    private String estado;

    public WorkItemLineGraphTooltip() {
    }

    public WorkItemLineGraphTooltip(String origen, String titulo, String estado) {
        this.origen = origen;
        this.titulo = titulo;
        this.estado = estado;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
