package hubble.backend.api.models;

public class KpiFrontend {
    private String kpiName;
    private String kpiShortName;
    private double kpiValue;
    private String kpiComment;

    public KpiFrontend() {
    }

    public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }

    public String getKpiShortName() {
        return kpiShortName;
    }

    public void setKpiShortName(String kpiShortName) {
        this.kpiShortName = kpiShortName;
    }

    public double getKpiValue() {
        return kpiValue;
    }

    public void setKpiValue(double kpiValue) {
        this.kpiValue = kpiValue;
    }

    public String getKpiComment() {
        return kpiComment;
    }

    public void setKpiComment(String kpiComment) {
        this.kpiComment = kpiComment;
    }
}