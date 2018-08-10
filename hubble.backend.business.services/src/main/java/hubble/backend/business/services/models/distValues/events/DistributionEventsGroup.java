package hubble.backend.business.services.models.distValues.events;

import hubble.backend.business.services.models.distValues.DistValues;

public class DistributionEventsGroup extends DistValues {
    private String fecha;
    private String monitor;

    public DistributionEventsGroup(int valor, String status, String fecha, String monitor){
        super(valor,status);
        this.fecha = fecha;
        this.monitor = monitor;
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
}
