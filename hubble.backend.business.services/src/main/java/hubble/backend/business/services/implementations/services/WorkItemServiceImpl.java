package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.interfaces.operations.WorkItemOperations;
import hubble.backend.business.services.interfaces.operations.kpis.WorkItemKpiOperations;
import hubble.backend.business.services.interfaces.services.WorkItemService;
import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.business.services.models.distValues.DistributionValues;
import hubble.backend.business.services.models.WorkItem;
import hubble.backend.business.services.models.distValues.LineGraphDistValues;
import hubble.backend.business.services.models.distValues.tasks.DistributionTasksGroup;
import hubble.backend.business.services.models.distValues.tasks.DistributionTasksUnit;
import hubble.backend.business.services.models.measures.kpis.WorkItemsKpi;
import hubble.backend.business.services.models.measures.quantities.WorkItemQuantity;
import hubble.backend.core.enums.DateTypes;
import hubble.backend.core.enums.Results;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.TaskRunnerExecution;
import hubble.backend.storage.models.Threashold;
import hubble.backend.storage.models.WorkItemStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.TaskRunnerRepository;
import hubble.backend.storage.repositories.WorkItemRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang.time.DateUtils;
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
    ApplicationRepository applicationRepository;
    @Autowired
    TaskRunnerRepository taskRunnerRepository;
    @Autowired
    MapperConfiguration mapper;
    @Autowired
    WorkItemOperations workItemOperation;
    @Autowired
    WorkItemKpiOperations workItemKpiOperation;


    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

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
    public List<DistValues> getDistValues(String id, String periodo) {
        List<DistValues> distValues;
        String period = this.calculatePeriod(periodo);

        if(period.equals("dia")){
            distValues = this.getUnitDistValues(id,period);
        }else {
            distValues = this.getGroupDistValues(id,period);
        }
        return distValues;

    }

    @Override
    public List<LineGraphDistValues> getLineGraphDistValues(String id, String periodo){
        List<LineGraphDistValues> distValues;
        String period = this.calculatePeriod(periodo);

        if(period.equals("dia")){
            distValues = this.getLineGraphUnitDistValues(id,period);
        }else {
            distValues = this.getLineGraphGroupDistValues(id,period);
        }
        return distValues;
    }

    private List<LineGraphDistValues> getLineGraphUnitDistValues(String id,String periodo){
        List<LineGraphDistValues> distValues;
        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);

        ApplicationStorage application = applicationRepository.findApplicationById(id);
        Threashold threshold = application.getKpis().getTasks().getUnitaryThreashold();

        double inferior = threshold.getInferior();
        double superior = threshold.getSuperior();
        double criticalThreshold = threshold.getCritical();
        double warningThreshold = threshold.getWarning();

        List<WorkItemStorage> workItemsStorage =
                workItemRepository.findWorkItemsByApplicationIdBetweenDatesAndStatus(id,startDate,endDate, "IN_PROGRESS");

        distValues = workItemsStorage.stream()
                .map( workItem ->
                new LineGraphDistValues(workItem.getId(),(int)workItem.getDeflectionDays(),workItem.getProviderOrigin() + "-" + Integer.toString(workItem.getExternalId())))
                .sorted(Comparator.comparing(LineGraphDistValues::getxAxis))
                .collect(Collectors.toList());

        return distValues;
    }

    private List<LineGraphDistValues> getLineGraphGroupDistValues(String id, String periodo){
        List<LineGraphDistValues> distValues = new ArrayList<>();
        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);

        List<WorkItemStorage> workItemsStorage =
                workItemRepository.findWorkItemsByApplicationIdBetweenDatesAndStatus(id,startDate,endDate, "IN_PROGRESS");

        return distValues;
    }


    private List<DistValues> getUnitDistValues(String id, String periodo){
        List<DistValues> distValues = new ArrayList<>();
        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);

        ApplicationStorage application = applicationRepository.findApplicationById(id);
        Threashold threshold = application.getKpis().getTasks().getUnitaryThreashold();

        double inferior = threshold.getInferior();
        double superior = threshold.getSuperior();
        double criticalThreshold = threshold.getCritical();
        double warningThreshold = threshold.getWarning();

        List<WorkItemStorage> workItemsStorage =
                workItemRepository.findWorkItemsByApplicationIdBetweenDatesAndStatus(id,startDate,endDate, "IN_PROGRESS");
        for (WorkItemStorage workItemStorage : workItemsStorage){
            String status = "Critical";
            if (workItemStorage.getDeflectionDays() <= warningThreshold){
                status = "OK";
            }
            if (workItemStorage.getDeflectionDays() > warningThreshold && workItemStorage.getDeflectionDays() <= criticalThreshold){
                status = "Warning";
            }
            distValues.add(new DistributionTasksUnit(
                    (int)workItemStorage.getDeflectionDays()
                    ,status
                    ,workItemStorage.getTitle()
                    ,workItemStorage.getStatus()
                    ,workItemStorage.getDueDate() == null ? "No due date" : dateFormat.format(workItemStorage.getDueDate())
                    ,DateTypes.FIN)
            );
        }

        return distValues;
    }

    private List<DistValues> getGroupDistValues(String id, String periodo){
        List<DistValues> distValues = new ArrayList<>();
        Date endDate = DateHelper.getEndDate(periodo);
        Date startDate = DateHelper.getStartDate(periodo);
        List<Date> startDates = new ArrayList<>();
        List<Date> endDates = new ArrayList<>();
        List<WorkItemStorage> workItemsStorage =
                workItemRepository.findWorkItemsByApplicationIdBetweenDatesAndStatus(id,startDate,endDate, "IN_PROGRESS");

        ApplicationStorage  applicationStorage = applicationRepository.findApplicationById(id);
        Threashold threshold;
        switch (periodo){
            case "semana":
                threshold = applicationStorage.getKpis().getTasks().getDayThreashold();
                for(int i=0; i<7; i++){
                    startDates.add(DateUtils.addDays(startDate,i));
                    endDates.add(DateUtils.addDays(startDate,i+1));
                }
                break;
            case "mes":
                threshold = applicationStorage.getKpis().getTasks().getWeekThreashold();
                Date aux = startDate;
                while (aux.before(endDate)){
                    startDates.add(aux);
                    if(DateUtils.addWeeks(aux,1).after(endDate)){
                        endDates.add(endDate);
                    }else {
                        endDates.add(DateUtils.addWeeks(aux, 1));
                    }
                    aux = DateUtils.addWeeks(aux,1);
                }
                break;
            default:
                threshold = applicationStorage.getKpis().getTasks().getDayThreashold();
                distValues = this.getUnitDistValues(id, periodo);
                return distValues;
        }
        distValues = this.getDistValuesByPeriod(workItemsStorage,threshold,startDates,endDates);
        return distValues;
    }


    private List<DistValues> getDistValuesByPeriod(List<WorkItemStorage> workItemList,Threashold threshold,List<Date> startDates, List<Date> endDates){
        List<DistValues> distValues = new ArrayList<>();
        double inferior = threshold.getInferior();
        double lWarningKpiThreshold = threshold.getWarning();
        double lCriticalKpiThreshold = threshold.getCritical();
        double superior = threshold.getSuperior();


        for(int j = 0; j<startDates.size(); j++){
            final int index = j;
            List<WorkItemStorage> workItemStorageList = workItemList.stream().filter(
                    workItemStorage ->
                            workItemStorage.getTimestamp().compareTo(startDates.get(index)) >= 0 &&
                                    workItemStorage.getTimestamp().compareTo(endDates.get(index)) <= 0).
                    collect(Collectors.toList());
            if(!workItemStorageList.isEmpty()) {
                double value = workItemStorageList.stream().mapToDouble(workItemStorage ->
                        workItemStorage.getDeflectionDays()).
                        sum();
                String status = "Critical";
                if (value <= lWarningKpiThreshold) {
                    status = "OK";
                }
                if (value <= lCriticalKpiThreshold && value > lWarningKpiThreshold) {
                    status = "Warning";
                }

                String date = dateFormat.format(startDates.get(j)) + " - " + dateFormat.format(endDates.get(j));

                distValues.add(new DistributionTasksGroup(
                        (int) value,
                        status,
                        date,
                        DateTypes.RANGO
                ));
            }
        }

        return distValues;
    }

    @Override
    public Results.RESULTS calculateKpiResult(String applicationId,String periodo){
        return workItemKpiOperation.calculateKpiResult(applicationId,periodo);
    }
    @Override
    public List<TaskRunnerExecution> getTaskRunnerExecutions(String applicationId,String periodo){
        return workItemKpiOperation.getTaskRunnerExecutions(applicationId,periodo);
    }

    public String calculatePeriod(String periodo){
        if (periodo.equals("default")){ //esto se hace por como funciona el date helper
            return "dia";
        }else {
            return periodo;
        }
    }
}
