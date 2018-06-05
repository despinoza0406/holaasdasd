package hubble.backend.business.services.interfaces.operations.kpis;

import hubble.backend.business.services.models.measures.kpis.WorkItemsKpi;
import hubble.backend.business.services.models.measures.rules.WorkItemsGroupRule;

public interface WorkItemKpiOperations extends
        KeyPerformanceIndicatorOperationsBase<WorkItemsGroupRule>, KpiThresholdSetup {

    public WorkItemsKpi calculateLastDayKeyPerformanceIndicatorByApplication(String applicationId);

    public WorkItemsKpi calculateLastWeekKeyPerformanceIndicatorByApplication(String applicationId);

    public WorkItemsKpi calculateLastMonthKeyPerformanceIndicatorByApplication(String applicationId);

    public long calculateLastDayKPI(String applicationId);

    public long calculatePastDayKPI(String applicationId);
}
