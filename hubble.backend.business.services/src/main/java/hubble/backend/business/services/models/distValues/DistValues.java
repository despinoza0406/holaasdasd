package hubble.backend.business.services.models.distValues;

public abstract class DistValues {
    private float valor;
    private String status;

    public DistValues(){

    }

    public DistValues(float valor,String status){
        this.valor = valor;
        this.status = status;
    }


    public float getValor() {
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
}
