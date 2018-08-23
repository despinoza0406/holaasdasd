package hubble.backend.business.services.interfaces.operations.kpis;

import hubble.backend.business.services.models.measures.kpis.EventsKpi;
import hubble.backend.business.services.models.measures.rules.EventsGroupRule;
import hubble.backend.core.enums.Results;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.TaskRunnerExecution;

import java.util.List;

public interface EventKpiOperations extends
        KeyPerformanceIndicatorOperationsBase<EventsGroupRule>, KpiThresholdSetup {

    public EventsKpi calculateLastDayKeyPerformanceIndicatorByApplication(String applicationId);

    public EventsKpi calculateLastWeekKeyPerformanceIndicatorByApplication(String applicationId);

    public EventsKpi calculateLastMonthKeyPerformanceIndicatorByApplication(String applicationId);

    public double calculateLastDayKPI(ApplicationStorage application);

    public double calculatePastDayKPI(ApplicationStorage application);

    public double calculatePastHourKPI(ApplicationStorage application);

    public double calculateLastHourKPI(ApplicationStorage application);

    public double calculateKPI(ApplicationStorage application,String periodo);

    Results.RESULTS calculateKpiResult(String applicationId,String periodo);

    List<TaskRunnerExecution> getTaskRunnerExecutions(String applicationId,String periodo);
}
