package hubble.backend.business.services.models.tables;

public class TasksTable implements FrontEndTable {
    private String origen;
    private String id;
    private String titulo;
    private String fechaDeAlta;
    private String registradoPor;
    private String asignadoA;
    private String estado;
    private String descripcion;
    private String fechaDeRealizacion;
    private String diasDesviados;

    public TasksTable() {}

    public TasksTable(String origen, String id, String titulo, String fechaDeAlta, String registradoPor, String asignadoA, String estado, String descripcion, String fechaDeRealizacion, String diasDesviados) {
        this.origen = origen;
        this.id = id;
        this.titulo = titulo;
        this.fechaDeAlta = fechaDeAlta;
        this.registradoPor = registradoPor;
        this.asignadoA = asignadoA;
        this.estado = estado;
        this.descripcion = descripcion;
        this.fechaDeRealizacion = fechaDeRealizacion;
        this.diasDesviados = diasDesviados;

    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFechaDeAlta() {
        return fechaDeAlta;
    }

    public void setFechaDeAlta(String fechaDeAlta) {
        this.fechaDeAlta = fechaDeAlta;
    }

    public String getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(String registradoPor) {
        this.registradoPor = registradoPor;
    }

    public String getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(String asignadoA) {
        this.asignadoA = asignadoA;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechadDeRealizacion() {
        return fechaDeRealizacion;
    }

    public void setFechadDeRealizacion(String fechadDeRealizacion) {
        this.fechaDeRealizacion = fechadDeRealizacion;
    }

    public String getDiasDesviados() {
        return diasDesviados;
    }

    public void setDiasDesviados(String diasDesviados) {
        this.diasDesviados = diasDesviados;
    }
}
