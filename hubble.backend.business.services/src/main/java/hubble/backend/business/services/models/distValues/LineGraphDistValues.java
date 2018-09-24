package hubble.backend.business.services.models.distValues;

public class LineGraphDistValues {
    private String id;
    private int yAxis;     //Siempre deberia ser un valor numerico
    private String xAxis; //Puede ser fechas, u otras cosas
    private LineGraphTooltip tooltip;

    public LineGraphDistValues(){

    }

    public LineGraphDistValues(String id, int yAxis, String xAxis, LineGraphTooltip tooltip){
        this.id = id;
        this.yAxis = yAxis;
        this.xAxis = xAxis;
        this.tooltip = tooltip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getyAxis() {
        return yAxis;
    }

    public void setyAxis(int yAxis) {
        this.yAxis = yAxis;
    }

    public String getxAxis() {
        return xAxis;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public LineGraphTooltip getTooltip() {
        return tooltip;
    }

    public void setTooltip(LineGraphTooltip tooltip) {
        this.tooltip = tooltip;
    }
}
