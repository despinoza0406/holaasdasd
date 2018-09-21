package hubble.backend.business.services.models.tables;

public class EventsTable implements FrontEndTable {
    private String origen;
    private String tipoDeMonitor;
    private String updatedDate;
    private String estado;
    private String sumario;

    public EventsTable(){}

    public EventsTable(String origen,String tipoDeMonitor,String updatedDate,String estado, String sumario){
        this.origen = origen;
        this.tipoDeMonitor = tipoDeMonitor;
        this.updatedDate = updatedDate;
        this.estado = estado;
        this.sumario = sumario;
    }


    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getTipoDeMonitor() {
        return tipoDeMonitor;
    }

    public void setTipoDeMonitor(String tipoDeMonitor) {
        this.tipoDeMonitor = tipoDeMonitor;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }
}
