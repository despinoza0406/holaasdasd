package hubble.backend.api.models;

import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.business.services.models.distValues.LineGraphDistValues;
import hubble.backend.core.enums.Results;
import hubble.backend.storage.models.TaskRunnerExecution;

import java.util.List;

public class KpiFrontend {
    private String kpiName;
    private String kpiShortName;
    private String kpiBackendName;
    private double kpiValue;
    private String kpiComment;
    private String kpiPeriod;
    private String kpiPeriodFront;
    private Results.RESULTS kpiResult;
    private List<TaskRunnerExecution> kpiTaskRunners;
    private List<DistValues> distribution;
    private List<LineGraphDistValues> lineGraphValues;
    private String xAxisValue;
    private String yAxisValue;

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

    public List<DistValues> getDistribution() {
        return distribution;
    }

    public void setDistribution(List<DistValues> distribution) {
        this.distribution = distribution;
    }

    public String getKpiPeriod() {
        return kpiPeriod;
    }

    public void setKpiPeriod(String kpiPeriod) {
        this.kpiPeriod = kpiPeriod;
    }

    public Results.RESULTS getKpiResult() {
        return kpiResult;
    }

    public void setKpiResult(Results.RESULTS kpiResult) {
        this.kpiResult = kpiResult;
    }

    public List<TaskRunnerExecution> getKpiTaskRunners() {
        return kpiTaskRunners;
    }

    public void setKpiTaskRunners(List<TaskRunnerExecution> kpiTaskRunners) {
        this.kpiTaskRunners = kpiTaskRunners;
    }

    public List<LineGraphDistValues> getLineGraphValues() {
        return lineGraphValues;
    }

    public void setLineGraphValues(List<LineGraphDistValues> lineGraphValues) {
        this.lineGraphValues = lineGraphValues;
    }

    public String getKpiBackendName() {
        return kpiBackendName;
    }

    public void setKpiBackendName(String kpiBackendName) {
        this.kpiBackendName = kpiBackendName;
    }

    public String getxAxisValue() {
        return xAxisValue;
    }

    public void setxAxisValue(String xAxisValue) {
        this.xAxisValue = xAxisValue;
    }

    public String getyAxisValue() {
        return yAxisValue;
    }

    public void setyAxisValue(String yAxisValue) {
        this.yAxisValue = yAxisValue;
    }

    public String getKpiPeriodFront() {
        return kpiPeriodFront;
    }

    public void setKpiPeriodFront(String kpiPeriodFront) {
        this.kpiPeriodFront = kpiPeriodFront;
    }
}
