package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.interfaces.services.kpis.WorkItemsOperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.models.WorkItem;
import hubble.backend.business.services.models.measures.quantities.WorkItemQuantity;

public interface WorkItemService extends ServiceBase<WorkItem>,
        WorkItemsOperationsKeyPerformanceIndicatorServiceBase {

    public WorkItemQuantity calculateWorkItemQuantityLastMonth(String applicationId);

    public WorkItemQuantity calculateWorkItemQuantityLastWeek(String applicationId);

    public long calculateLastDayDeflectionDaysKpi(String applicationId);
}
