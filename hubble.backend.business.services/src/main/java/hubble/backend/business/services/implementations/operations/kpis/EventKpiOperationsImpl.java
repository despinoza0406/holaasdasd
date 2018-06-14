package hubble.backend.business.services.implementations.operations.kpis;

import hubble.backend.business.services.interfaces.operations.kpis.CalculateKpiLowNumberBestIndicator;
import hubble.backend.business.services.interfaces.operations.kpis.EventKpiOperations;

import hubble.backend.business.services.interfaces.operations.rules.EventGroupRulesOperations;
import hubble.backend.business.services.models.measures.kpis.EventsKpi;
import hubble.backend.business.services.models.measures.rules.EventsGroupRule;
import hubble.backend.core.enums.MonitoringFields;
import hubble.backend.core.utils.KpiHelper;
import hubble.backend.core.utils.Threshold;
import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventKpiOperationsImpl implements EventKpiOperations {

    @Autowired
    CalculateKpiLowNumberBestIndicator calculateIssuesKpi;
    @Autowired
    EventGroupRulesOperations eventsRulesOperation;
    @Autowired
    EventRepository eventRepository;

    private double warningKpiThreshold;
    private double criticalKpiThreshold;

    private long lWarningKpiThreshold;
    private long lCriticalKpiThreshold;
    private long okKpiThreshold;
    private final long warningIndexThreshold = 6;
    private final long criticalIndexThreshold = 8;

    private double warningIdxThreshold;
    private double criticalIdxThreshold;


    @Override
    public double calculateKeyPerformanceIndicator(EventsGroupRule eventsGroupRule) {

        if (eventsGroupRule == null) {
            return 0d;
        }

        calculateIssuesKpi.setWarningKpiThreshold(this.warningKpiThreshold);
        calculateIssuesKpi.setCriticalKpiThreshold(this.criticalKpiThreshold);
        calculateIssuesKpi.setWarningIdxThreshold(this.warningIdxThreshold);
        calculateIssuesKpi.setCriticalIdxThreshold(this.criticalIdxThreshold);
        calculateIssuesKpi.setValue(eventsGroupRule.get());

        return calculateIssuesKpi.calculateIndex();
    }

    @Override
    public EventsKpi calculateLastWeekKeyPerformanceIndicatorByApplication(String applicationId) {

        EventsGroupRule availabilityLastDayRuleGroup = eventsRulesOperation.calculateLastWeekGroupRuleByApplication(applicationId);

        setKpiThresholdForWeek();

        double kpiLastDay = calculateKeyPerformanceIndicator(availabilityLastDayRuleGroup);

       EventsKpi issuesKpi = new EventsKpi();
        issuesKpi.set(kpiLastDay);

        issuesKpi.setStatus(calculateKpiStatus(kpiLastDay));
        return issuesKpi;

    }

    @Override
    public EventsKpi calculateLastMonthKeyPerformanceIndicatorByApplication(String applicationId) {
        EventsGroupRule availabilityLastMonthRuleGroup = eventsRulesOperation.calculateLastMonthGroupRuleByApplication(applicationId);

        setKpiThresholdForMonth();

        double kpiLastMonth = calculateKeyPerformanceIndicator(availabilityLastMonthRuleGroup);

        EventsKpi eventsKpi = new EventsKpi();
        eventsKpi.set(kpiLastMonth);

        eventsKpi.setStatus(calculateKpiStatus(kpiLastMonth));
        return eventsKpi;
    }

    @Override
    public EventsKpi calculateLastDayKeyPerformanceIndicatorByApplication(String applicationId) {
        EventsGroupRule availabilityLastMonthRuleGroup = eventsRulesOperation.calculateLastDayGroupRuleByApplication(applicationId);

        setKpiThresholdForMonth();

        double kpiLastMonth = calculateKeyPerformanceIndicator(availabilityLastMonthRuleGroup);

       EventsKpi eventsKpi = new EventsKpi();
        eventsKpi.set(kpiLastMonth);

        eventsKpi.setStatus(calculateKpiStatus(kpiLastMonth));
        return eventsKpi;
    }

    @Override
    public MonitoringFields.STATUS calculateKpiStatus(Double measure) {

        if (measure == 0) {
            return MonitoringFields.STATUS.NO_DATA;
        }

        if (measure <= this.warningKpiThreshold) {
            return MonitoringFields.STATUS.SUCCESS;
        } else if (measure > this.warningKpiThreshold
                && measure < this.criticalKpiThreshold) {
            return MonitoringFields.STATUS.WARNING;
        } else if (measure > this.criticalKpiThreshold) {
            return MonitoringFields.STATUS.CRITICAL;
        }
        return MonitoringFields.STATUS.NO_DATA;
    }

    @Override
    public double getWarningKpiThreshold() {
        return this.warningKpiThreshold;
    }

    @Override
    public void setWarningKpiThreshold(double threshold) {
        this.warningKpiThreshold = threshold;
    }

    @Override
    public double getCriticalKpiThreshold() {
        return this.criticalKpiThreshold;
    }

    @Override
    public void setCriticalKpiThreshold(double threshold) {
        this.criticalKpiThreshold = threshold;
    }

    @Override
    public double getWarningIdxThreshold() {
        return this.warningIdxThreshold;
    }

    @Override
    public void setWarningIdxThreshold(double threshold) {
        this.warningIdxThreshold = threshold;
    }

    @Override
    public double getCriticalIdxThreshold() {
        return this.criticalIdxThreshold;
    }

    @Override
    public void setCriticalIdxThreshold(double threshold) {
        this.criticalIdxThreshold = threshold;
    }


    private void setKpiThresholdForWeek() {
        this.warningKpiThreshold = Threshold.WorkItems.WARNING_WORKITEMS_WEEK_DEFAULT;
        this.criticalKpiThreshold = Threshold.WorkItems.CRITICAL_WORKITEMS_WEEK_DEFAULT;
        this.warningIdxThreshold = KpiHelper.WorkItems.WARNING_WORKITEMS_KPI_DEFAULT;
        this.criticalIdxThreshold = KpiHelper.WorkItems.CRITICAL_WORKITEMS_KPI_DEFAULT;
    }

    private void setKpiThresholdForMonth() {
        this.warningKpiThreshold = Threshold.WorkItems.WARNING_WORKITEMS_MONTH_DEFAULT;
        this.criticalKpiThreshold = Threshold.WorkItems.CRITICAL_WORKITEMS_MONTH_DEFAULT;
        this.warningIdxThreshold = KpiHelper.WorkItems.WARNING_WORKITEMS_KPI_DEFAULT;
        this.criticalIdxThreshold = KpiHelper.WorkItems.CRITICAL_WORKITEMS_KPI_DEFAULT;
    }

    @Override
    public long calculateLastDayKPI(String applicationId){
        List<EventStorage> events = eventRepository.findEventsByApplicationIdAndDifferentStatusLastDay(applicationId,
                "Good");
        this.lWarningKpiThreshold = 100;
        this.lCriticalKpiThreshold = 150;
        this.okKpiThreshold = 5;
        return calculateKPI(events);
    }

    @Override
    public long calculatePastDayKPI(String applicationId){
        List<EventStorage> events = eventRepository.findEventsByApplicationIdAndDifferentStatusPastDay(applicationId,
                "Good");
        this.lWarningKpiThreshold = 100;
        this.lCriticalKpiThreshold = 150;
        this.okKpiThreshold = 5;
        return calculateKPI(events);
    }

    private long calculateKPI(List<EventStorage> events){
        long severityPointsTotal = 0;
        long a = 0;
        long b = 0;
        long x = 0;
        long y = 0;
        long kpi;

        for(EventStorage eventStorage : events) {
            severityPointsTotal = severityPointsTotal + eventStorage.getSeverityPoints();
        }

        if (severityPointsTotal <= this.okKpiThreshold) {
            return 10;
        }

        if (severityPointsTotal >= this.lCriticalKpiThreshold ) {
            return 0;
        }

        //warning thresholds setting
        if (severityPointsTotal > this.okKpiThreshold & severityPointsTotal < this.lWarningKpiThreshold) {
            a = 1;
            b = this.lWarningKpiThreshold;
            x = 6;
            y = 10;
        }

        //critical threshold setting
        if (severityPointsTotal >= this.lWarningKpiThreshold & severityPointsTotal <= this.lCriticalKpiThreshold) {
            a = this.lWarningKpiThreshold;
            b = this.lCriticalKpiThreshold;
            x = 1;
            y = 6;
        }

        kpi = ((severityPointsTotal - a)/(b-a)) * (y-x) + x;

        return kpi;
    }


}
