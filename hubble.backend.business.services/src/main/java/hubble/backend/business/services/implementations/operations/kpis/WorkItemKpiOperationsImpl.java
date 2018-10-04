package hubble.backend.business.services.implementations.operations.kpis;

import hubble.backend.business.services.interfaces.operations.kpis.CalculateKpiLowNumberBestIndicator;
import hubble.backend.business.services.interfaces.operations.kpis.WorkItemKpiOperations;
import hubble.backend.business.services.interfaces.operations.rules.WorkItemGroupRuleOperations;
import hubble.backend.business.services.models.measures.kpis.WorkItemsKpi;
import hubble.backend.business.services.models.measures.rules.WorkItemsGroupRule;
import hubble.backend.core.enums.MonitoringFields;
import hubble.backend.core.enums.Results;
import hubble.backend.core.utils.CalculationHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.core.utils.KpiHelper;
import hubble.backend.core.utils.Threshold;
import hubble.backend.storage.models.*;
import hubble.backend.storage.repositories.TaskRunnerRepository;
import hubble.backend.storage.repositories.WorkItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class WorkItemKpiOperationsImpl implements WorkItemKpiOperations {

    @Autowired
    CalculateKpiLowNumberBestIndicator calculateIssuesKpi;
    @Autowired
    WorkItemGroupRuleOperations workItemsRulesOperation;
    @Autowired
    WorkItemRepository workItemRepository;
    @Autowired
    TaskRunnerRepository taskRunnerRepository;

    private double warningKpiThreshold;
    private double criticalKpiThreshold;

    private double superior;
    private double inferior;
    private double lWarningKpiThreshold;
    private double lCriticalKpiThreshold;

    private double warningIdxThreshold;
    private double criticalIdxThreshold;

    @Override
    public double calculateKeyPerformanceIndicator(WorkItemsGroupRule workItemsGroupRule) {

        if (workItemsGroupRule == null) {
            return 0d;
        }

        calculateIssuesKpi.setWarningKpiThreshold(this.warningKpiThreshold);
        calculateIssuesKpi.setCriticalKpiThreshold(this.criticalKpiThreshold);
        calculateIssuesKpi.setWarningIdxThreshold(this.warningIdxThreshold);
        calculateIssuesKpi.setCriticalIdxThreshold(this.criticalIdxThreshold);
        calculateIssuesKpi.setValue(workItemsGroupRule.get());

        return calculateIssuesKpi.calculateIndex();
    }

    @Override
    public WorkItemsKpi calculateLastWeekKeyPerformanceIndicatorByApplication(String applicationId) {

        WorkItemsGroupRule availabilityLastDayRuleGroup = workItemsRulesOperation.calculateLastWeekGroupRuleByApplication(applicationId);

        setKpiThresholdForWeek();

        double kpiLastDay = calculateKeyPerformanceIndicator(availabilityLastDayRuleGroup);

        WorkItemsKpi issuesKpi = new WorkItemsKpi();
        issuesKpi.set(kpiLastDay);

        issuesKpi.setStatus(calculateKpiStatus(kpiLastDay));
        return issuesKpi;

    }

    @Override
    public WorkItemsKpi calculateLastMonthKeyPerformanceIndicatorByApplication(String applicationId) {
        WorkItemsGroupRule availabilityLastMonthRuleGroup = workItemsRulesOperation.calculateLastMonthGroupRuleByApplication(applicationId);

        setKpiThresholdForMonth();

        double kpiLastMonth = calculateKeyPerformanceIndicator(availabilityLastMonthRuleGroup);

        WorkItemsKpi issuesKpi = new WorkItemsKpi();
        issuesKpi.set(kpiLastMonth);

        issuesKpi.setStatus(calculateKpiStatus(kpiLastMonth));
        return issuesKpi;
    }

    @Override
    public WorkItemsKpi calculateLastDayKeyPerformanceIndicatorByApplication(String applicationId) {
        WorkItemsGroupRule availabilityLastMonthRuleGroup = workItemsRulesOperation.calculateLastDayGroupRuleByApplication(applicationId);

        setKpiThresholdForMonth();

        double kpiLastMonth = calculateKeyPerformanceIndicator(availabilityLastMonthRuleGroup);

        WorkItemsKpi issuesKpi = new WorkItemsKpi();
        issuesKpi.set(kpiLastMonth);

        issuesKpi.setStatus(calculateKpiStatus(kpiLastMonth));
        return issuesKpi;
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

    //Privates Methods
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
        Tasks tareas = application.getKpis().getTasks();
        Threashold lastDayThreshold = tareas.getDayThreashold();
        List<WorkItemStorage> workItems = workItemRepository.findWorkItemsByApplicationIdAndStatusLastDay(application.getId(),
                "IN_PROGRESS");
        this.inferior = lastDayThreshold.getInferior();
        this.superior = lastDayThreshold.getSuperior();
        this.lCriticalKpiThreshold = lastDayThreshold.getCritical();
        this.lWarningKpiThreshold = lastDayThreshold.getWarning();
        return calculateKPI(workItems);
    }

    @Override
    public double calculateKPI(ApplicationStorage application,String periodo){
        Threashold threshold = application.getKpis().getTasks().getThreashold(periodo);
        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);

        this.inferior = threshold.getInferior();
        this.superior = threshold.getSuperior();
        this.lCriticalKpiThreshold = threshold.getCritical();
        this.lWarningKpiThreshold = threshold.getWarning();
        List<WorkItemStorage> workItems = workItemRepository.findWorkItemsByApplicationIdBetweenDatesAndStatus(application.getId(),
                startDate,endDate,
                "IN_PROGRESS");

        if(workItems.isEmpty() && !this.calculateKpiResult(application.getApplicationId(),periodo).equals(Results.RESULTS.FAILURE)){
            return 10;
        }

        if(workItems.isEmpty() && this.calculateKpiResult(application.getApplicationId(),periodo).equals(Results.RESULTS.FAILURE)){
            return 1;
        }

        return calculateKPI(workItems);
    }

    @Override
    public double calculatePastDayKPI(ApplicationStorage application){
        Tasks tareas = application.getKpis().getTasks();
        Threashold lastDayThreshold = tareas.getDayThreashold();
        List<WorkItemStorage> workItems = workItemRepository.findWorkItemsByApplicationIdAndStatusPastDay(application.getId(),
                "IN_PROGRESS");
        this.inferior = lastDayThreshold.getInferior();
        this.superior = lastDayThreshold.getSuperior();
        this.lCriticalKpiThreshold = lastDayThreshold.getCritical();
        this.lWarningKpiThreshold = lastDayThreshold.getWarning();
        return calculateKPI(workItems);
    }

    private double calculateKPI(List<WorkItemStorage> workItems){
        double deflectionDaysTotal = 0;

        for(WorkItemStorage workItemStorage : workItems) {
            deflectionDaysTotal = deflectionDaysTotal + workItemStorage.getDeflectionDays();
        }

        if (deflectionDaysTotal < this.lWarningKpiThreshold) {
            return CalculationHelper.calculateOkHealthIndex(deflectionDaysTotal,inferior,lWarningKpiThreshold); //lo minimo es 0
        }

        if (deflectionDaysTotal < this.lCriticalKpiThreshold ) {
            return CalculationHelper.calculateWarningHealthIndex(deflectionDaysTotal,lWarningKpiThreshold,lCriticalKpiThreshold);
        }

        return CalculationHelper.calculateMinInfiniteCriticalHealthIndex(deflectionDaysTotal,lCriticalKpiThreshold);//,10d);

    }


    @Override
    public Results.RESULTS calculateKpiResult(String applicationId,String periodo){
        List<TaskRunnerExecution> taskExecutions = this.getTaskRunnerExecutions(applicationId,periodo);

        if (taskExecutions.isEmpty()){
            return Results.RESULTS.SUCCESS;
        }
        Date endDate = DateHelper.getEndDate(periodo);
        Date startDate = DateHelper.getStartDate(periodo);
        List<WorkItemStorage> workItems = workItemRepository.findWorkItemsByApplicationIdBetweenDatesAndStatus(applicationId,
                startDate,endDate,
                "IN_PROGRESS");
        if (allFailures(taskExecutions)){ //Si hubo fallos y no se tienen datos
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

        List<TaskRunnerExecution> taskExecutions = taskRunnerRepository.findExecutionsByProviderAndApplicationIdAndPeriod("ppm",applicationId,startDate,endDate);
        return taskExecutions;
    }

    public String calculatePeriod(String periodo){
        if (periodo.equals("default")){ //esto se hace por como funciona el date helper
            return "dia";
        }else {
            return periodo;
        }
    }

}
