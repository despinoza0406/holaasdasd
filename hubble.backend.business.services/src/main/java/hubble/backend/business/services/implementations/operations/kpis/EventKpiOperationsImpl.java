package hubble.backend.business.services.implementations.operations.kpis;

import hubble.backend.business.services.interfaces.operations.kpis.CalculateKpiLowNumberBestIndicator;
import hubble.backend.business.services.interfaces.operations.kpis.EventKpiOperations;

import hubble.backend.business.services.interfaces.operations.rules.EventGroupRulesOperations;
import hubble.backend.business.services.models.measures.kpis.EventsKpi;
import hubble.backend.business.services.models.measures.rules.EventsGroupRule;
import hubble.backend.core.enums.MonitoringFields;
import hubble.backend.core.enums.Results;
import hubble.backend.core.utils.CalculationHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.core.utils.KpiHelper;
import hubble.backend.core.utils.Threshold;
import hubble.backend.storage.models.*;
import hubble.backend.storage.repositories.EventRepository;
import hubble.backend.storage.repositories.TaskRunnerRepository;
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
    @Autowired
    TaskRunnerRepository taskRunnerRepository;

    private double warningKpiThreshold;
    private double criticalKpiThreshold;

    private double inferior;
    private double lWarningKpiThreshold;
    private double lCriticalKpiThreshold;
    private double superior;

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
        this.superior = lastDayThreshold.getSuperior();
        this.inferior = lastDayThreshold.getInferior();
        this.lCriticalKpiThreshold = lastDayThreshold.getCritical();
        this.lWarningKpiThreshold = lastDayThreshold.getWarning();
        return calculateKPI(events);
    }

    @Override
    public double calculatePastDayKPI(ApplicationStorage application){
        Events eventos = application.getKpis().getEvents();
        Threashold lastDayThreshold = eventos.getDayThreashold();
        List<EventStorage> events = eventRepository.findEventsByApplicationIdAndDifferentStatusPastDay(application.getId(),
                "Good");
        this.superior = lastDayThreshold.getSuperior();
        this.inferior = lastDayThreshold.getInferior();
        this.lCriticalKpiThreshold = lastDayThreshold.getCritical();
        this.lWarningKpiThreshold = lastDayThreshold.getWarning();
        return calculateKPI(events);
    }

    @Override
    public double calculateLastHourKPI(ApplicationStorage application){
        Events eventos = application.getKpis().getEvents();
        Threashold lastHourThreshold = eventos.getHourThreashold();
        List<EventStorage> events = eventRepository.findEventsByApplicationIdAndDifferentStatusLastHour(application.getId(),
                "Good");
        this.superior = lastHourThreshold.getSuperior();
        this.inferior = lastHourThreshold.getInferior();
        this.lCriticalKpiThreshold = lastHourThreshold.getCritical();
        this.lWarningKpiThreshold = lastHourThreshold.getWarning();
        return calculateKPI(events);
    }

    @Override
    public double calculateKPI(ApplicationStorage application,String periodo,Results.RESULTS results){
        Threashold threshold = application.getKpis().getEvents().getThreashold(periodo);

        if (periodo.equals("default")){ //esto se hace por como funciona el date helper
            periodo = "hora";
        }

        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);

        this.superior = threshold.getSuperior();
        this.inferior = threshold.getInferior();
        this.lCriticalKpiThreshold = threshold.getCritical();
        this.lWarningKpiThreshold = threshold.getWarning();
        List<EventStorage> events = eventRepository.findEventsByApplicationIdBetweenDatesAndDifferentStatus(application.getId()
                ,startDate,endDate,"Good");
        if (events.isEmpty() && !results.equals(Results.RESULTS.FAILURE)){
            return 10;
        }
        return calculateKPI(events);
    }

    @Override
    public double calculatePastHourKPI(ApplicationStorage application){
        Events eventos = application.getKpis().getEvents();
        Threashold lastHourThreshold = eventos.getHourThreashold();
        List<EventStorage> events = eventRepository.findEventsByApplicationIdAndDifferentStatusPastHour(application.getId(),
                "Good");
        this.superior = lastHourThreshold.getSuperior();
        this.inferior = lastHourThreshold.getInferior();
        this.lCriticalKpiThreshold = lastHourThreshold.getCritical();
        this.lWarningKpiThreshold = lastHourThreshold.getWarning();
        return calculateKPI(events);
    }

    private double calculateKPI(List<EventStorage> events){
        long severityPointsTotal = 0;

        for(EventStorage eventStorage : events) {
            severityPointsTotal = severityPointsTotal + eventStorage.getSeverityPoints();
        }

        if (severityPointsTotal <= this.lWarningKpiThreshold) {
            return CalculationHelper.calculateOkHealthIndex(severityPointsTotal,inferior,lWarningKpiThreshold); //lo minimo es 0
        }

        //warning thresholds setting
        if (severityPointsTotal > this.lWarningKpiThreshold & severityPointsTotal < this.lCriticalKpiThreshold) {
            return CalculationHelper.calculateWarningHealthIndex(severityPointsTotal,lWarningKpiThreshold,lCriticalKpiThreshold);
        }



        return CalculationHelper.calculateMinInfiniteCriticalHealthIndex(severityPointsTotal,lCriticalKpiThreshold);//,10d);

    }

    @Override
    public Results.RESULTS calculateKpiResult(String applicationId,String periodo,List<TaskRunnerExecution> taskExecutions){

        if (taskExecutions.isEmpty()){
            return Results.RESULTS.SUCCESS;
        }

        Date endDate = DateHelper.getEndDate(periodo);
        Date startDate = DateHelper.getStartDate(periodo);
        List<EventStorage> events = eventRepository.findEventsByApplicationIdBetweenDatesAndDifferentStatus(applicationId
                ,startDate,endDate,"Good");
        if (allFailures(taskExecutions)){ //Si fueron todos fallos
            return Results.RESULTS.FAILURE;
        }
        if (containsAFailure(taskExecutions) || containsWarning(taskExecutions)){ //Si hubo fallos y se tienen datos
            return Results.RESULTS.WARNING;
        }
        if(containsNoData(taskExecutions)){
            return Results.RESULTS.NO_DATA;
        }
        return Results.RESULTS.SUCCESS;
    }

    public boolean allFailures(final List<TaskRunnerExecution> taskExecutions){
        return taskExecutions.stream().allMatch(execution -> execution.getResult().equals(Results.RESULTS.FAILURE));
    }

    public boolean containsAFailure(final List<TaskRunnerExecution> taskExecutions){
        return taskExecutions.stream().anyMatch(execution -> execution.getResult().equals(Results.RESULTS.FAILURE));
    }

    public boolean containsNoData(final List<TaskRunnerExecution> taskExecutions){
        return taskExecutions.stream().anyMatch(execution -> execution.getResult().equals(Results.RESULTS.NO_DATA));
    }

    public boolean containsWarning(final List<TaskRunnerExecution> taskExecutions){
        return taskExecutions.stream().anyMatch(execution -> execution.getResult().equals(Results.RESULTS.WARNING));
    }

    @Override
    public List<TaskRunnerExecution> getTaskRunnerExecutions(String applicationId,String periodo){
        String periodoTaskRunner = this.calculatePeriod(periodo);

        Date startDate = DateHelper.getStartDate(periodoTaskRunner);
        Date endDate = DateHelper.getEndDate(periodoTaskRunner);

        List<TaskRunnerExecution> taskExecutions = taskRunnerRepository.findExecutionsByProviderAndApplicationIdAndPeriod("sitescope",applicationId,startDate,endDate);
        return taskExecutions;
    }

    public String calculatePeriod(String periodo){
        if (periodo.equals("default")){ //esto se hace por como funciona el date helper
            return "hora";
        }else {
            return periodo;
        }
    }


}
