package hubble.backend.business.services.models.distValues.tasks;

import hubble.backend.business.services.models.distValues.DistValues;

public class DistributionTasksGroup extends DistValues {

    private String fecha;

    public DistributionTasksGroup(int valor, String status, String fecha)
    {
        super(valor,status);
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
