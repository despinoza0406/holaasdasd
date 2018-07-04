package hubble.backend.business.services.implementations.operations.kpis;

import hubble.backend.business.services.interfaces.operations.kpis.CalculateKpiLowNumberBestIndicator;
import hubble.backend.business.services.interfaces.operations.kpis.WorkItemKpiOperations;
import hubble.backend.business.services.interfaces.operations.rules.WorkItemGroupRuleOperations;
import hubble.backend.business.services.models.measures.kpis.WorkItemsKpi;
import hubble.backend.business.services.models.measures.rules.WorkItemsGroupRule;
import hubble.backend.core.enums.MonitoringFields;
import hubble.backend.core.utils.CalculationHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.core.utils.KpiHelper;
import hubble.backend.core.utils.Threshold;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.Tasks;
import hubble.backend.storage.models.Threashold;
import hubble.backend.storage.models.WorkItemStorage;
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
        this.lWarningKpiThreshold = lastDayThreshold.getWarning();
        this.lCriticalKpiThreshold = lastDayThreshold.getCritical();
        this.okKpiThreshold = lastDayThreshold.getOk();
        return calculateKPI(workItems);
    }

    @Override
    public double calculateKPI(ApplicationStorage application,String periodo){
        Threashold threshold = application.getKpis().getTasks().getThreashold(periodo);
        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);

        this.lCriticalKpiThreshold = threshold.getCritical();
        this.lWarningKpiThreshold = threshold.getWarning();
        this.okKpiThreshold = threshold.getOk();
        List<WorkItemStorage> workItems = workItemRepository.findWorkItemsByApplicationIdBetweenDatesAndStatus(application.getId(),
                startDate,endDate,
                "IN_PROGRESS");

        return calculateKPI(workItems);
    }

    @Override
    public double calculatePastDayKPI(ApplicationStorage application){
        Tasks tareas = application.getKpis().getTasks();
        Threashold lastDayThreshold = tareas.getDayThreashold();
        List<WorkItemStorage> workItems = workItemRepository.findWorkItemsByApplicationIdAndStatusPastDay(application.getId(),
                "IN_PROGRESS");
        this.lWarningKpiThreshold = lastDayThreshold.getWarning();
        this.lCriticalKpiThreshold = lastDayThreshold.getCritical();
        this.okKpiThreshold = lastDayThreshold.getOk();
        return calculateKPI(workItems);
    }

    private double calculateKPI(List<WorkItemStorage> workItems){
        double deflectionDaysTotal = 0;

        for(WorkItemStorage workItemStorage : workItems) {
            deflectionDaysTotal = deflectionDaysTotal + workItemStorage.getDeflectionDays();
        }

        if (deflectionDaysTotal < this.okKpiThreshold) {
            return CalculationHelper.calculateOkHealthIndex(deflectionDaysTotal,0,okKpiThreshold); //lo minimo es 0
        }

        if (deflectionDaysTotal > this.lCriticalKpiThreshold ) {
            return 1;
        }

        //warning thresholds setting
        if (deflectionDaysTotal >= this.okKpiThreshold & deflectionDaysTotal < this.lWarningKpiThreshold) {
            return CalculationHelper.calculateWarningHealthIndex(deflectionDaysTotal,okKpiThreshold,lWarningKpiThreshold);
        }

        //critical threshold setting
        if (deflectionDaysTotal >= this.lWarningKpiThreshold & deflectionDaysTotal <= this.lCriticalKpiThreshold) {
            return CalculationHelper.calculateCriticalHealthIndex(deflectionDaysTotal,lWarningKpiThreshold,lCriticalKpiThreshold);
        }

        return 0;
    }
}
