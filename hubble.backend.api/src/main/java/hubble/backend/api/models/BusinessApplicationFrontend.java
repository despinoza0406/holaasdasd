package hubble.backend.api.models;

import java.util.List;

public class BusinessApplicationFrontend extends ApiResponseBase {
    private String id;
    private double healthIndex;
    private double healthIndexPast;
    private List<KpiFrontend> kpis;

    public BusinessApplicationFrontend() {}

    public BusinessApplicationFrontend(String id, int healthIndex, int healthIndexPast) {
        this.id = id;
        this.healthIndex = healthIndex;
        this.healthIndexPast = healthIndexPast;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getHealthIndex() {
        return healthIndex;
    }

    public void setHealthIndex(double healthIndex) {
        this.healthIndex = healthIndex;
    }

    public double getHealthIndexPast() {
        return healthIndexPast;
    }

    public void setHealthIndexPast(double healthIndexPast) {
        this.healthIndexPast = healthIndexPast;
    }

    public List<KpiFrontend> getKpis() {
        return kpis;
    }

    public void setKpis(List<KpiFrontend> kpis) {
        this.kpis = kpis;
    }
}
