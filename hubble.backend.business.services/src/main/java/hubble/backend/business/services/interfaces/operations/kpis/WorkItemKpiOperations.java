package hubble.backend.business.services.interfaces.operations.kpis;

import hubble.backend.business.services.models.Application;
import hubble.backend.business.services.models.measures.kpis.WorkItemsKpi;
import hubble.backend.business.services.models.measures.rules.WorkItemsGroupRule;
import hubble.backend.core.enums.Results;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.TaskRunnerExecution;

import java.util.List;

public interface WorkItemKpiOperations extends
        KeyPerformanceIndicatorOperationsBase<WorkItemsGroupRule>, KpiThresholdSetup {

    public WorkItemsKpi calculateLastDayKeyPerformanceIndicatorByApplication(String applicationId);

    public WorkItemsKpi calculateLastWeekKeyPerformanceIndicatorByApplication(String applicationId);

    public WorkItemsKpi calculateLastMonthKeyPerformanceIndicatorByApplication(String applicationId);

    public double calculateLastDayKPI(ApplicationStorage application);

    public double calculateKPI(ApplicationStorage application,String periodo);

    public double calculatePastDayKPI(ApplicationStorage application);

    Results.RESULTS calculateKpiResult(String applicationId,String periodo);

    List<TaskRunnerExecution> getTaskRunnerExecutions(String applicaitonId, String periodo);
}
