package hubble.backend.business.services.models.availability;

import hubble.backend.business.services.models.DistValues;

public class DistributionAvailabilityUnit implements DistValues {
    private int valor;
    private String status;
    private String transaction;
    private String fecha;

    public DistributionAvailabilityUnit(){

    }

    public DistributionAvailabilityUnit(int valor,String status,String transaction,String fecha){
        this.valor = valor;
        this.status = status;
        this.transaction = transaction;
        this.fecha = fecha;
    }


    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
