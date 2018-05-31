package hubble.backend.business.services.implementations.operations.kpis;

import hubble.backend.business.services.interfaces.operations.kpis.CalculateKpiLowNumberBestIndicator;
import hubble.backend.business.services.interfaces.operations.kpis.WorkItemKpiOperations;
import hubble.backend.business.services.interfaces.operations.rules.WorkItemGroupRuleOperations;
import hubble.backend.business.services.models.measures.kpis.WorkItemsKpi;
import hubble.backend.business.services.models.measures.rules.WorkItemsGroupRule;
import hubble.backend.core.enums.MonitoringFields;
import hubble.backend.core.utils.KpiHelper;
import hubble.backend.core.utils.Threshold;
import hubble.backend.storage.models.WorkItemStorage;
import hubble.backend.storage.repositories.WorkItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public long calculateLastDayKPI(String applicationId){
        List<WorkItemStorage> workItems = workItemRepository.findWorkItemsByApplicationIdAndStatusLastDay(applicationId,
                "IN_PROGRESS");
        long deflectionDaysTotal = 0;

        for(WorkItemStorage workItemStorage : workItems) {
            deflectionDaysTotal = deflectionDaysTotal + workItemStorage.getDeflectionDays();
        }

        long kpi = 0;

        for (int i = 0; i <= 10; i++ ) {
            if (deflectionDaysTotal==i){
                kpi = 10 - i;
                break;
            }
        }

        return kpi;
    }
}
