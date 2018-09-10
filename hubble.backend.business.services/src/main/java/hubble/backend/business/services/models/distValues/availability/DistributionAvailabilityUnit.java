package hubble.backend.business.services.models.distValues.availability;

import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.core.enums.DateTypes;

public class DistributionAvailabilityUnit extends DistValues {
    private String transaction;
    private DateTypes TipoFecha;
    private String fecha;

    public DistributionAvailabilityUnit(int valor,String status,String transaction,String fecha,DateTypes tipo){
        super(valor,status);
        this.transaction = transaction;
        this.fecha = fecha;
        this.TipoFecha = tipo;
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

    public void setTipoFecha(DateTypes tipo) {
        this.TipoFecha = tipo;
    }
}
