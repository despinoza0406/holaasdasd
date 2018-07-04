package hubble.backend.business.services.implementations.operations.kpis;

import hubble.backend.business.services.interfaces.operations.kpis.CalculateKpiLowNumberBestIndicator;
import hubble.backend.business.services.interfaces.operations.kpis.EventKpiOperations;

import hubble.backend.business.services.interfaces.operations.rules.EventGroupRulesOperations;
import hubble.backend.business.services.models.measures.kpis.EventsKpi;
import hubble.backend.business.services.models.measures.rules.EventsGroupRule;
import hubble.backend.core.enums.MonitoringFields;
import hubble.backend.core.utils.CalculationHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.core.utils.KpiHelper;
import hubble.backend.core.utils.Threshold;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.models.Events;
import hubble.backend.storage.models.Threashold;
import hubble.backend.storage.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
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

    private double lWarningKpiThreshold;
    private double lCriticalKpiThreshold;
    private double okKpiThreshold;
    private final long warningIndexThreshold = 6;
    private final long criticalIndexThreshold = 8;

    private double warningIdxThreshold;
    private double criticalIdxThreshold;


    @Override
    public double calculateKeyPerformanceIndicator(EventsGroupRule eventsGroupRule) {

        if (eventsGroupRule == null) {
            return 0;
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
    public double calculateLastDayKPI(ApplicationStorage application){
        Events eventos = application.getKpis().getEvents();
        Threashold lastDayThreshold = eventos.getDayThreashold();
        List<EventStorage> events = eventRepository.findEventsByApplicationIdAndDifferentStatusLastDay(application.getId(),
                "Good");
        this.lWarningKpiThreshold = lastDayThreshold.getWarning();
        this.lCriticalKpiThreshold = lastDayThreshold.getCritical();
        this.okKpiThreshold = lastDayThreshold.getOk();
        return calculateKPI(events);
    }

    @Override
    public double calculatePastDayKPI(ApplicationStorage application){
        Events eventos = application.getKpis().getEvents();
        Threashold lastDayThreshold = eventos.getDayThreashold();
        List<EventStorage> events = eventRepository.findEventsByApplicationIdAndDifferentStatusPastDay(application.getId(),
                "Good");
        this.lWarningKpiThreshold = lastDayThreshold.getWarning();
        this.lCriticalKpiThreshold = lastDayThreshold.getCritical();
        this.okKpiThreshold = lastDayThreshold.getOk();
        return calculateKPI(events);
    }

    @Override
    public double calculateLastHourKPI(ApplicationStorage application){
        Events eventos = application.getKpis().getEvents();
        Threashold lastHourThreshold = eventos.getHourThreashold();
        List<EventStorage> events = eventRepository.findEventsByApplicationIdAndDifferentStatusLastHour(application.getId(),
                "Good");
        this.lWarningKpiThreshold = lastHourThreshold.getWarning();
        this.lCriticalKpiThreshold = lastHourThreshold.getCritical();
        this.okKpiThreshold = lastHourThreshold.getOk();
        return calculateKPI(events);
    }

    @Override
    public double calculateKPI(ApplicationStorage application,String periodo){
        Threashold threshold = application.getKpis().getEvents().getThreashold(periodo);

        if (periodo.equals("default")){ //esto se hace por como funciona el date helper
            periodo = "hora";
        }

        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);

        this.lCriticalKpiThreshold = threshold.getCritical();
        this.lWarningKpiThreshold = threshold.getWarning();
        this.okKpiThreshold = threshold.getOk();
        List<EventStorage> events = eventRepository.findEventsByApplicationIdBetweenDatesAndDifferentStatus(application.getId()
                ,startDate,endDate,"Good");
        return calculateKPI(events);
    }

    @Override
    public double calculatePastHourKPI(ApplicationStorage application){
        Events eventos = application.getKpis().getEvents();
        Threashold lastHourThreshold = eventos.getHourThreashold();
        List<EventStorage> events = eventRepository.findEventsByApplicationIdAndDifferentStatusPastHour(application.getId(),
                "Good");
        this.lWarningKpiThreshold = lastHourThreshold.getWarning();
        this.lCriticalKpiThreshold = lastHourThreshold.getCritical();
        this.okKpiThreshold = lastHourThreshold.getOk();
        return calculateKPI(events);
    }

    private double calculateKPI(List<EventStorage> events){
        long severityPointsTotal = 0;

        for(EventStorage eventStorage : events) {
            severityPointsTotal = severityPointsTotal + eventStorage.getSeverityPoints();
        }

        if (severityPointsTotal <= this.okKpiThreshold) {
            return CalculationHelper.calculateOkHealthIndex(severityPointsTotal,0,okKpiThreshold); //lo minimo es 0
        }

        if (severityPointsTotal >= this.lCriticalKpiThreshold ) {
            return 1;
        }

        //warning thresholds setting
        if (severityPointsTotal > this.okKpiThreshold & severityPointsTotal < this.lWarningKpiThreshold) {
            return CalculationHelper.calculateWarningHealthIndex(severityPointsTotal,okKpiThreshold,lWarningKpiThreshold);
        }

        //critical threshold setting
        if (severityPointsTotal >= this.lWarningKpiThreshold & severityPointsTotal <= this.lCriticalKpiThreshold) {
            return CalculationHelper.calculateCriticalHealthIndex(severityPointsTotal,lWarningKpiThreshold,lCriticalKpiThreshold);
        }

        return 0;

    }


}
