package hubble.backend.business.services.models.business;

import hubble.backend.business.services.models.Application;
import hubble.backend.business.services.models.measures.averages.AvailabilityAverage;
import hubble.backend.business.services.models.measures.averages.PerformanceAverage;
import hubble.backend.business.services.models.measures.kpis.AvailabilityKpi;
import hubble.backend.business.services.models.measures.kpis.IssuesKpi;
import hubble.backend.business.services.models.measures.kpis.PerformanceKpi;
import hubble.backend.business.services.models.measures.quantities.AvailabilityQuantity;
import hubble.backend.business.services.models.measures.quantities.PerformanceQuantity;
import hubble.backend.business.services.models.measures.rules.AvailabilityGroupRule;
import hubble.backend.business.services.models.measures.rules.IssuesGroupRule;
import hubble.backend.business.services.models.measures.rules.PerformanceGroupRule;

/**
 * ApplicationAvg is an Application with its average value for performance,
 * availability, quantity of measures of performance and availability. The class
 * holds differente business calculation on different periods for example: 10
 * minutes, 1 hours, 24 hours, 1 month.
 *
 * TODO: This is an old version because ApplicationAvgDto should be composed and
 * not inheritance by Application
 *
 * @author Ismael J. Tisminetzky
 */
public class ApplicationIndicators extends Application {

    private AvailabilityAverage availabilityAverage;
    private PerformanceAverage performanceAverage;
    private PerformanceQuantity measuresQtyPerformance;
    private AvailabilityQuantity measuresQtyAvailability;
    private AvailabilityGroupRule availabilityGroupRule;
    private PerformanceGroupRule performanceGroupRule;
    private AvailabilityKpi availabilityKpi;
    private PerformanceKpi performanceKpi;
    private IssuesGroupRule issueGroupRule;
    private IssuesKpi issuesKpi10Min;
    private IssuesKpi issuesKpiLastHour;
    private IssuesKpi issuesKpiLastDay;
    private IssuesKpi issuesKpiLastMonth;

    public ApplicationIndicators() {
        this.availabilityAverage = new AvailabilityAverage();
        this.measuresQtyAvailability = new AvailabilityQuantity();
        this.measuresQtyPerformance = new PerformanceQuantity();
        this.performanceAverage = new PerformanceAverage();
    }

    public AvailabilityAverage getAvailabilityAverage() {
        return availabilityAverage;
    }

    public void setAvailabilityAverage(AvailabilityAverage average) {
        this.availabilityAverage = average;
    }

    public PerformanceQuantity getMeasuresQtyPerformance() {
        return measuresQtyPerformance;
    }

    public void setMeasuresQtyPerformance(PerformanceQuantity measuresQtyPerformance) {
        this.measuresQtyPerformance = measuresQtyPerformance;
    }

    public AvailabilityQuantity getMeasuresQtyAvailability() {
        return measuresQtyAvailability;
    }

    public void setMeasuresQtyAvailability(AvailabilityQuantity measuresQtyAvailability) {
        this.measuresQtyAvailability = measuresQtyAvailability;
    }

    public void setMeasuresQtyAvailability(int size) {
        this.measuresQtyAvailability.setQuantity(size);
    }

    public PerformanceAverage getPerformanceAverage() {
        return performanceAverage;
    }

    public void setPerformanceAverage(PerformanceAverage performanceAverage) {
        this.performanceAverage = performanceAverage;
    }

    public void setAvailabilityAverage(Double availabilityAverage) {
        this.availabilityAverage.set(availabilityAverage);
    }

    public void setPerformanceAverage(Double performanceAverage) {
        this.getPerformanceAverage().set(performanceAverage);
    }

    public Double getPerformanceAverageValue() {
        return this.performanceAverage.get();
    }

    public Double getAvailabilityAverageValue() {
        return this.availabilityAverage.get();
    }

    public AvailabilityGroupRule getAvailabilityGroupRule() {
        return availabilityGroupRule;
    }

    public void setAvailabilityIndicator(AvailabilityGroupRule availabilityIndicator) {
        this.availabilityGroupRule = availabilityIndicator;
    }

    public PerformanceGroupRule getPerformanceGroupRule() {
        return performanceGroupRule;
    }

    public void setPerformanceIndicator(PerformanceGroupRule performanceIndicator) {
        this.performanceGroupRule = performanceIndicator;
    }

    public IssuesGroupRule getIssueGroupRule() {
        return issueGroupRule;
    }

    public void setIssueGroupRule(IssuesGroupRule issueIndicator) {
        this.issueGroupRule = issueIndicator;
    }

    public AvailabilityKpi getAvailabilityKpi() {
        return availabilityKpi;
    }

    public void setAvailabilityKpi(AvailabilityKpi availabilityKpi) {
        this.availabilityKpi = availabilityKpi;
    }

    public IssuesKpi getIssuesKpi10Min() {
        return issuesKpi10Min;
    }

    public void setIssuesKpi10Min(IssuesKpi issuesKpi10Min) {
        this.issuesKpi10Min = issuesKpi10Min;
    }

    public IssuesKpi getIssuesKpiLastHour() {
        return issuesKpiLastHour;
    }

    public void setIssuesKpiLastHour(IssuesKpi issuesKpiLastHour) {
        this.issuesKpiLastHour = issuesKpiLastHour;
    }

    public IssuesKpi getIssuesKpiLastDay() {
        return issuesKpiLastDay;
    }

    public void setIssuesKpiLastDay(IssuesKpi issuesKpiLastDay) {
        this.issuesKpiLastDay = issuesKpiLastDay;
    }

    public IssuesKpi getIssuesKpiLastMonth() {
        return issuesKpiLastMonth;
    }

    public void setIssuesKpiLastMonth(IssuesKpi issuesKpiLastMonth) {
        this.issuesKpiLastMonth = issuesKpiLastMonth;
    }

    public PerformanceKpi getPerformanceKpi() {
        return performanceKpi;
    }

    public void setPerformanceKpi(PerformanceKpi performanceKpi) {
        this.performanceKpi = performanceKpi;
    }
}
