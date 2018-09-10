package hubble.backend.business.services.models.distValues.performance;

import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.core.enums.DateTypes;

public class DistributionPerformanceUnit extends DistValues {
    private String transaction;
    private DateTypes TipoFecha;
    private String fecha;

    public DistributionPerformanceUnit(int valor, String status, String transaction, String fecha,DateTypes tipoFecha){
        super(valor,status);
        this.transaction = transaction;
        this.fecha = fecha;
        this.TipoFecha = tipoFecha;
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

    public DateTypes getTipoFecha() {
        return TipoFecha;
    }

    public void setTipoFecha(DateTypes tipoFecha) {
        TipoFecha = tipoFecha;
    }
}
