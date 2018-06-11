package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.interfaces.services.kpis.WorkItemsOperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.models.WorkItem;
import hubble.backend.business.services.models.measures.quantities.WorkItemQuantity;

import java.util.List;

public interface WorkItemService extends ServiceBase<WorkItem>,
        WorkItemsOperationsKeyPerformanceIndicatorServiceBase {

    public WorkItemQuantity calculateWorkItemQuantityLastMonth(String applicationId);

    public WorkItemQuantity calculateWorkItemQuantityLastWeek(String applicationId);

    public long calculateLastDayDeflectionDaysKpi(String applicationId);

    public long calculatePastDayDeflectionDaysKpi(String applicationId);

    List<Integer> getDistValuesLastDay(String id);
}
