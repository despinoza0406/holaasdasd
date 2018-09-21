package hubble.backend.business.services.models.tables;

public class AvailabilityTable implements FrontEndTable {
    private String origen;
    private String transaccion;
    private String locacion;
    private String timestamp;
    private String tiempo_de_respuesta;
    private String estado;
    private String sumario;

    public AvailabilityTable() {}

    public AvailabilityTable(String origen, String transaccion, String locacion, String timestamp, String tiempo_de_respuesta, String estado, String sumario) {
        this.origen = origen;
        this.transaccion = transaccion;
        this.locacion = locacion;
        this.timestamp = timestamp;
        this.tiempo_de_respuesta = tiempo_de_respuesta;
        this.estado = estado;
        this.sumario = sumario;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTiempo_de_respuesta() {
        return tiempo_de_respuesta;
    }

    public void setTiempo_de_respuesta(String tiempo_de_respuesta) {
        this.tiempo_de_respuesta = tiempo_de_respuesta;
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
