package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.interfaces.operations.WorkItemOperations;
import hubble.backend.business.services.interfaces.operations.kpis.WorkItemKpiOperations;
import hubble.backend.business.services.interfaces.services.WorkItemService;
import hubble.backend.business.services.models.Application;
import hubble.backend.business.services.models.WorkItem;
import hubble.backend.business.services.models.measures.kpis.WorkItemsKpi;
import hubble.backend.business.services.models.measures.quantities.WorkItemQuantity;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.WorkItemStorage;
import hubble.backend.storage.repositories.WorkItemRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Serves all base information regarding Items To Do for a specific application.
 * Calculate the total remaining Items and brings them too. TODO: The only class
 * that doesn't return a ApplicationIndicators.
 *
 * @author Ismael J. Tisminetzky
 */
@Component
public class WorkItemServiceImpl implements WorkItemService {

    @Autowired
    WorkItemRepository workItemRepository;
    @Autowired
    MapperConfiguration mapper;
    @Autowired
    WorkItemOperations workItemOperation;
    @Autowired
    WorkItemKpiOperations workItemKpiOperation;

    @Override
    public List<WorkItem> getLastDay(String applicationId) {

        Calendar yesterday = CalendarHelper.getNow();
        yesterday.add(Calendar.HOUR, -24);
        Date today = new GregorianCalendar().getTime();

        List<WorkItemStorage> issues = workItemRepository.findWorkItemsByApplicationIdBetweenDates(applicationId, yesterday.getTime(), today);

        return mapper.mapToWorkItemDtoList(issues);
    }

    @Override
    public List<WorkItem> getLastMonth(String applicationId) {
        Calendar lastmonth = CalendarHelper.getNow();
        lastmonth.add(Calendar.MONTH, -1);
        Date today = new GregorianCalendar().getTime();

        List<WorkItemStorage> workitems = workItemRepository.findWorkItemsByApplicationIdBetweenDates(applicationId, lastmonth.getTime(), today);

        return mapper.mapToWorkItemDtoList(workitems);
    }

    @Override
    public WorkItemQuantity calculateWorkItemQuantityLastMonth(String applicationId) {
        return workItemOperation.calculateWorkItemQuantityLastMonth(applicationId);
    }

    @Override
    public WorkItemQuantity calculateWorkItemQuantityLastWeek(String applicationId) {
        return workItemOperation.calculateWorkItemQuantityLastWeek(applicationId);
    }

    @Override
    public WorkItemsKpi calculateLastWeekKpiByApplication(String applicationId) {
        return workItemKpiOperation.calculateLastWeekKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public WorkItemsKpi calculateLastMonthKpiByApplication(String applicationId) {
        return workItemKpiOperation.calculateLastMonthKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public WorkItemsKpi calculateLastDayKpiByApplication(String applicationId) {
        return workItemKpiOperation.calculateLastDayKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public double calculateLastDayDeflectionDaysKpi(ApplicationStorage application){
        return workItemKpiOperation.calculateLastDayKPI(application);
    }

    @Override
    public double calculateDeflectionDaysKPI(ApplicationStorage application,String periodo){
        return workItemKpiOperation.calculateKPI(application,periodo);
    }

    @Override
    public double calculatePastDayDeflectionDaysKpi(ApplicationStorage application) {
        return workItemKpiOperation.calculatePastDayKPI(application);
    }

    @Override
    public List<Integer> getDistValuesLastDay(String id) {
        List<WorkItemStorage> workItemsStorage =
                workItemRepository.findWorkItemsByApplicationIdAndStatusLastDay(id, "IN_PROGRESS");
        List<Integer> distValuesInt = new ArrayList<>();
        for (WorkItemStorage workItemStorage : workItemsStorage){
            distValuesInt.add((int) workItemStorage.getDeflectionDays());
        }
        return distValuesInt;
    }

    @Override
    public List<Integer> getDistValues(String id,String periodo) {

        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);

        List<WorkItemStorage> workItemsStorage =
                workItemRepository.findWorkItemsByApplicationIdBetweenDatesAndStatus(id,startDate,endDate, "IN_PROGRESS");
        List<Integer> distValuesInt = new ArrayList<>();
        for (WorkItemStorage workItemStorage : workItemsStorage){
            distValuesInt.add((int) workItemStorage.getDeflectionDays());
        }
        return distValuesInt;
    }

    public String calculatePeriod(String periodo){
        if (periodo.equals("default")){ //esto se hace por como funciona el date helper
            return "dia";
        }else {
            return periodo;
        }
    }
}
