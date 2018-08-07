package hubble.backend.business.services.models.distValues.performance;

import hubble.backend.business.services.models.distValues.DistValues;

public class DistributionPerformanceUnit extends DistValues {
    private String transaction;
    private String fecha;

    public DistributionPerformanceUnit(int valor, String status, String transaction, String fecha){
        super(valor,status);
        this.transaction = transaction;
        this.fecha = fecha;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
