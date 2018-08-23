package hubble.backend.api.models;

import hubble.backend.core.enums.Results;

import java.util.Date;
import java.util.List;

public class BusinessApplicationFrontend extends ApiResponseBase {
    private String id;
    private Date lastUpdate;
    private double healthIndex;
    private Date pastUpdate;
    private double healthIndexPast;
    private List<KpiFrontend> kpis;
    private Results.RESULTS result;

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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Date getPastUpdate() {
        return pastUpdate;
    }

    public void setPastUpdate(Date pastUpdate) {
        this.pastUpdate = pastUpdate;
    }

    public List<KpiFrontend> getKpis() {
        return kpis;
    }

    public void setKpis(List<KpiFrontend> kpis) {
        this.kpis = kpis;
    }

    public Results.RESULTS getResult() {
        return result;
    }

    public void setResult(Results.RESULTS result) {
        this.result = result;
    }
}
