package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.interfaces.services.kpis.WorkItemsOperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.business.services.models.WorkItem;
import hubble.backend.business.services.models.measures.quantities.WorkItemQuantity;
import hubble.backend.core.enums.Results;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.TaskRunnerExecution;

import java.util.List;

public interface WorkItemService extends ServiceBase<WorkItem>,
        WorkItemsOperationsKeyPerformanceIndicatorServiceBase {

    public WorkItemQuantity calculateWorkItemQuantityLastMonth(String applicationId);

    public WorkItemQuantity calculateWorkItemQuantityLastWeek(String applicationId);

    public double calculateLastDayDeflectionDaysKpi(ApplicationStorage application);

    public double calculateDeflectionDaysKPI(ApplicationStorage application,String periodo);

    public double calculatePastDayDeflectionDaysKpi(ApplicationStorage application);

    List<Integer> getDistValuesLastDay(String id);

    List<DistValues> getDistValues(String id, String periodo);

    String calculatePeriod(String periodo);

    Results.RESULTS calculateKpiResult(String applicationId,String periodo);

    List<TaskRunnerExecution> getTaskRunnerExecutions(String application, String periodo);
}
