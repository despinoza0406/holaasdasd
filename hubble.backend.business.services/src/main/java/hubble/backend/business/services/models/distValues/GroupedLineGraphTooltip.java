package hubble.backend.business.services.models.distValues;

public class GroupedLineGraphTooltip implements LineGraphTooltip {
    private int total_de_muestras;

    public GroupedLineGraphTooltip() {}

    public int getTotal_de_muestras() {
        return total_de_muestras;
    }

    public void setTotal_de_muestras(int total_de_muestras) {
        this.total_de_muestras = total_de_muestras;
    }
}
