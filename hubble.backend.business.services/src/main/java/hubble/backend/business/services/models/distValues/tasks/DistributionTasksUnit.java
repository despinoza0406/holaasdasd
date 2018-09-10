package hubble.backend.business.services.models.distValues.tasks;

import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.core.enums.DateTypes;

public class DistributionTasksUnit extends DistValues {
    private String name;
    private String taskStatus;
    private DateTypes TipoFecha;
    private String fecha;

    public DistributionTasksUnit(int valor, String status, String name, String taskStatus, String endDate, DateTypes tipoFecha){
        super(valor,status);
        this.name = name;
        this.taskStatus = taskStatus;
        this.fecha = endDate;
        this.TipoFecha = tipoFecha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
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
