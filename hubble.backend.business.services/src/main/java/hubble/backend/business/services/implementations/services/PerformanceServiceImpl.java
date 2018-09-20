package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.interfaces.operations.averages.PerformanceOperations;
import hubble.backend.business.services.interfaces.operations.kpis.PerformanceKpiOperations;
import hubble.backend.business.services.interfaces.services.PerformanceService;
import hubble.backend.business.services.models.Application;
import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.business.services.models.distValues.DistributionValues;
import hubble.backend.business.services.models.Performance;
import hubble.backend.business.services.models.business.ApplicationIndicators;
import hubble.backend.business.services.models.distValues.performance.DistributionPerformanceGroup;
import hubble.backend.business.services.models.distValues.performance.DistributionPerformanceUnit;
import hubble.backend.core.enums.DateTypes;
import hubble.backend.core.enums.Results;
import hubble.backend.core.utils.CalculationHelper;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.AvailabilityStorage;
import hubble.backend.storage.models.TaskRunnerExecution;
import hubble.backend.storage.models.Threashold;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.AvailabilityRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import hubble.backend.storage.repositories.TaskRunnerRepository;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PerformanceServiceImpl implements PerformanceService {

    @Autowired
    AvailabilityRepository availabilityRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    TaskRunnerRepository taskRunnerRepository;
    @Autowired
    PerformanceOperations performanceOperation;
    @Autowired
    PerformanceKpiOperations performanceKpiOperations;
    @Autowired
    MapperConfiguration mapper;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private double inferior;
    private double criticalThreshold;
    private double warningThreshold;
    private double superior;

    @Override
    public List<Performance> getAll() {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAll();
        return mapper.mapToPerformanceDtoList(availabilityStorageList);
    }

    @Override
    public Performance getById(String id) {
        return mapper.mapToPerformanceDto(availabilityRepository.findOne(id));
    }

    @Override
    public ApplicationIndicators calculateLast10MinutesAverageByApplication(String applicationId) {
        return performanceOperation.calculateLast10MinutesAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLastHourAverageByApplication(String applicationId) {
        return performanceOperation.calculateLastHourAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLastDayAverageByApplication(String applicationId) {
        return performanceOperation.calculateLastDayAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLastMonthAverageByApplication(String applicationId) {
        return performanceOperation.calculateLastMonthAverageByApplication(applicationId);
    }

    @Override
    public Application getApplication(String applicationId) {
        ApplicationStorage applicationStorage = applicationRepository.findApplicationById(applicationId);
        return mapper.mapToApplicationDto(applicationStorage);
    }

    @Override
    public List<Performance> getAll(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationId(applicationId);
        return mapper.mapToPerformanceDtoList(availabilityStorageList);
    }

    @Override
    public List<Performance> getLast10Minutes(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMinutes(CalendarHelper.TEN_MINUTES, applicationId);
        return mapper.mapToPerformanceDtoList(availabilityStorageList);
    }

    @Override
    public List<Performance> getLastHour(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMinutes(CalendarHelper.ONE_HOUR, applicationId);
        return mapper.mapToPerformanceDtoList(availabilityStorageList);
    }

    @Override
    public List<Performance> getLastDay(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMinutes(CalendarHelper.ONE_DAY, applicationId);
        return mapper.mapToPerformanceDtoList(availabilityStorageList);
    }

    @Override
    public List<Performance> getLastMonth(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMonths(CalendarHelper.ONE_MONTH, applicationId);
        return mapper.mapToPerformanceDtoList(availabilityStorageList);
    }

    @Override
    public List<Application> getAllApplications() {
        List<ApplicationStorage> applicationsStorage = applicationRepository.findAll();
        return mapper.mapToApplicationDtoList(applicationsStorage);
    }

    @Override
    public ApplicationIndicators calculateLastDayKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setPerformanceKpi(performanceKpiOperations.calculateLastDayKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public ApplicationIndicators calculateLastMonthKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setPerformanceKpi(performanceKpiOperations.calculateLastMonthKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public double calculateHealthIndexKPILastMonth(ApplicationStorage applicationStorage) {
        Threashold lastHourThreshold = applicationStorage.getKpis().getPerformance().getMonthThreashold();
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(applicationStorage.getApplicationId(), DateHelper.getNDaysBefore(30), DateHelper.getDateNow());
        double totalPerformance = 0;
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            totalPerformance = totalPerformance + availabilityStorage.getResponseTime();
        }

        double averagePerformance = totalPerformance / (double) availabilityStorageList.size();
        inferior = lastHourThreshold.getInferior();
        superior = lastHourThreshold.getSuperior();
        criticalThreshold = lastHourThreshold.getCritical();
        warningThreshold = lastHourThreshold.getWarning();

        if (averagePerformance <= warningThreshold) {
            return CalculationHelper.calculateOkHealthIndex(averagePerformance, inferior, warningThreshold);
        }

        if (averagePerformance <= criticalThreshold && averagePerformance > warningThreshold) {
            return CalculationHelper.calculateWarningHealthIndex(averagePerformance, warningThreshold, criticalThreshold);
        }

        return CalculationHelper.calculateMinInfiniteCriticalHealthIndex(averagePerformance, criticalThreshold);//, 1000d);
    }


    @Override
    public double calculateHealthIndexKPILastHour(ApplicationStorage applicationStorage) {
        Threashold lastHourThreshold = applicationStorage.getKpis().getPerformance().getHourThreashold();
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(applicationStorage.getApplicationId(), DateHelper.getAnHourAgo(), DateHelper.getDateNow());
        double totalPerformance = 0;
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            totalPerformance = totalPerformance + availabilityStorage.getResponseTime();
        }

        double averagePerformance = totalPerformance / (double) availabilityStorageList.size();
        inferior = lastHourThreshold.getInferior();
        superior = lastHourThreshold.getSuperior();
        criticalThreshold = lastHourThreshold.getCritical();
        warningThreshold = lastHourThreshold.getWarning();

        if (averagePerformance <= warningThreshold) {
            return CalculationHelper.calculateOkHealthIndex(averagePerformance, inferior, warningThreshold);
        }

        if (averagePerformance <= criticalThreshold && averagePerformance > warningThreshold) {
            return CalculationHelper.calculateWarningHealthIndex(averagePerformance, warningThreshold, criticalThreshold);
        }

        return CalculationHelper.calculateMinInfiniteCriticalHealthIndex(averagePerformance, criticalThreshold);//, 1000d);
    }

    @Override
    public double calculateHealthIndexKPI(ApplicationStorage application, String periodo) {
        Threashold threshold = application.getKpis().getPerformance().getThreashold(periodo);

        periodo = this.calculatePeriod(periodo);

        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(application.getId(),startDate,endDate);
        if(availabilityStorageList.isEmpty() && !this.calculateKpiResult(application.getApplicationId(),periodo).equals(Results.RESULTS.FAILURE)){
            return 1;
        }
        double totalPerformance = 0;
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            totalPerformance = totalPerformance + availabilityStorage.getResponseTime();
        }
        double averagePerformance = totalPerformance / (double) availabilityStorageList.size();
        inferior = threshold.getInferior();
        superior = threshold.getSuperior();
        criticalThreshold = threshold.getCritical();
        warningThreshold = threshold.getWarning();

        if (averagePerformance <= warningThreshold) {
            return CalculationHelper.calculateOkHealthIndex(averagePerformance, inferior, warningThreshold);
        }

        if (averagePerformance <= criticalThreshold && averagePerformance > warningThreshold) {
            return CalculationHelper.calculateWarningHealthIndex(averagePerformance, warningThreshold, criticalThreshold);
        }

        return CalculationHelper.calculateMinInfiniteCriticalHealthIndex(averagePerformance, criticalThreshold);//, 1000d);
    }

    @Override
    public ApplicationIndicators calculateLast10MinutesKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setPerformanceKpi(performanceKpiOperations.calculateLast10MinutesKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public ApplicationIndicators calculateLastHourKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setPerformanceKpi(performanceKpiOperations.calculateLast10MinutesKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public List<Integer> getDistValuesLastHour(String id) {
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(id, DateHelper.getAnHourAgo(), DateHelper.getDateNow());
        List<Integer> distValuesInt = new ArrayList<>();
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            distValuesInt.add((int) availabilityStorage.getResponseTime());
        }
        return distValuesInt;
    }

    @Override
    public List<DistValues> getDistValues(String id, String periodo) {
        List<DistValues> distValues;
        String performancePeriod = this.calculatePeriod(periodo);


        if(performancePeriod.equals("hora")) {
            distValues = this.getUnitDistValues(id,performancePeriod);
        }else{
            distValues = this.getGroupDistValues(id,performancePeriod);
        }

        return distValues;
    }

    private List<DistValues> getUnitDistValues(String id, String periodo){
        List<DistValues> distValues = new ArrayList<>();
        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);
        ApplicationStorage application = applicationRepository.findApplicationById(id);
        Threashold threshold = application.getKpis().getPerformance().getUnitaryThreashold();

        inferior = threshold.getInferior();
        superior = threshold.getSuperior();
        criticalThreshold = threshold.getCritical();
        warningThreshold = threshold.getWarning();

        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(id,startDate,endDate)
                .stream().filter(performance -> !performance.getAvailabilityStatus().equals("Failed"))
                .collect(Collectors.toList());

        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            String status = "Critical";

            if (availabilityStorage.getResponseTime() <= warningThreshold) {
                status = "OK";
            }

            if (availabilityStorage.getResponseTime() <= criticalThreshold && availabilityStorage.getResponseTime() > warningThreshold) {
                status = "Warning";
            }

            distValues.add(new DistributionPerformanceUnit(
                    (int) availabilityStorage.getResponseTime(), //Podria dar overflow porque un long tiene mas valores que un int
                    status,
                    availabilityStorage.getTransactionName(),
                    dateFormat.format(availabilityStorage.getTimeStamp()),
                    DateTypes.Captura)
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
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(id,startDate,endDate)
                        .stream().filter(performance -> !performance.getAvailabilityStatus().equals("Failed"))
                        .collect(Collectors.toList());

        ApplicationStorage  applicationStorage = applicationRepository.findApplicationById(id);
        Threashold threshold;
        switch (periodo){
            case "dia":
                threshold = applicationStorage.getKpis().getPerformance().getHourThreashold();
                for(int i=0; i<24; i++){
                    startDates.add(DateUtils.addHours(startDate,i));
                    endDates.add(DateUtils.addHours(startDate,i+1));
                }
                break;
            case "semana":
                threshold = applicationStorage.getKpis().getPerformance().getDayThreashold();
                for(int i=0; i<7; i++){
                    startDates.add(DateUtils.addDays(startDate,i));
                    endDates.add(DateUtils.addDays(startDate,i+1));
                }
                break;
            case "mes":
                threshold = applicationStorage.getKpis().getPerformance().getWeekThreashold();
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
                threshold = applicationStorage.getKpis().getPerformance().getHourThreashold();
                for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
                    String status = "Critical";

                    if (availabilityStorage.getResponseTime() <= warningThreshold) {
                        status = "OK";
                    }

                    if (availabilityStorage.getResponseTime() <= criticalThreshold && availabilityStorage.getResponseTime() > warningThreshold) {
                        status = "Warning";
                    }

                    distValues.add(new DistributionPerformanceUnit(
                            (int) availabilityStorage.getResponseTime(), //Podria dar overflow porque un long tiene mas valores que un int
                            status,
                            availabilityStorage.getTransactionName(),
                            dateFormat.format(availabilityStorage.getTimeStamp()),
                            DateTypes.Captura)
                    );
                }
                return distValues;
        }
        distValues = this.getDistValuesByPeriod(availabilityStorageList,threshold,startDates,endDates);
        return distValues;
    }

    private List<DistValues> getDistValuesByPeriod(List<AvailabilityStorage> availabilityStorageList,Threashold threshold,List<Date> startDates, List<Date> endDates){
        List<DistValues> distValues = new ArrayList<>();
        inferior = threshold.getInferior();
        warningThreshold = threshold.getWarning();
        criticalThreshold = threshold.getCritical();
        superior = threshold.getSuperior();


        List<String> transactions = availabilityStorageList.stream().
                map(availabilityStorage -> availabilityStorage.getTransactionName()).
                distinct().collect(Collectors.toList());
        for (String transaction : transactions){
            for(int j = 0; j<startDates.size(); j++){
                final int index = j;
                List<AvailabilityStorage> transactionAvailability = availabilityStorageList.stream().filter(
                        availabilityStorage -> availabilityStorage.getTransactionName().equals(transaction) &&
                                availabilityStorage.getTimeStamp().compareTo(startDates.get(index)) >= 0 &&
                                availabilityStorage.getTimeStamp().compareTo(endDates.get(index)) <= 0).
                        collect(Collectors.toList());
                if(!transactionAvailability.isEmpty()) {

                    int value = transactionAvailability.stream().mapToInt(availabilityStorage ->
                            (int)availabilityStorage.getResponseTime()).
                            sum() / transactionAvailability.size();
                    String status = "Critical";
                    if (value >= warningThreshold) {
                        status = "OK";
                    }
                    if (value <= warningThreshold && value > criticalThreshold) {
                        status = "Warning";
                    }

                    String date = dateFormat.format(startDates.get(j)) + " - " + dateFormat.format(endDates.get(j));
                    distValues.add(new DistributionPerformanceGroup(
                            value,
                            status,
                            transaction,
                            date,
                            DateTypes.Rango
                    ));
                }
            }
        }
        return distValues;
    }


    @Override
    public Results.RESULTS calculateKpiResult(String applicationId,String periodo){
        List<TaskRunnerExecution> taskExecutions = this.getTaskRunnerExecutions(applicationId,periodo);

        Date endDate = DateHelper.getEndDate(periodo);
        Date startDate = DateHelper.getStartDate(periodo);
        List<AvailabilityStorage> availabilities = availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(applicationId,startDate,endDate);

        if (allFailures(taskExecutions)){ //Si hubo fallos y no se tienen datos
            return Results.RESULTS.FAILURE;
        }
        if (containsAFailure(taskExecutions) || containsWarning(taskExecutions)){ //Si hubo fallos y se tienen datos
            return Results.RESULTS.WARNING;
        }
        if(containsNoData(taskExecutions)){
            return Results.RESULTS.NO_DATA;
        }
        return Results.RESULTS.SUCCESS;
    }

    public boolean allFailures(final List<TaskRunnerExecution> taskExecutions){
        return taskExecutions.stream().allMatch(execution -> execution.getResult().equals(Results.RESULTS.FAILURE));
    }

    public boolean containsAFailure(final List<TaskRunnerExecution> taskExecutions){
        return taskExecutions.stream().anyMatch(execution -> execution.getResult().equals(Results.RESULTS.FAILURE));
    }

    public boolean containsNoData(final List<TaskRunnerExecution> taskExecutions){
        return taskExecutions.stream().anyMatch(execution -> execution.getResult().equals(Results.RESULTS.NO_DATA));
    }

    public boolean containsWarning(final List<TaskRunnerExecution> taskExecutions){
        return taskExecutions.stream().anyMatch(execution -> execution.getResult().equals(Results.RESULTS.WARNING));
    }

    @Override
    public List<TaskRunnerExecution> getTaskRunnerExecutions(String applicationId,String periodo){
        String periodoTaskRunner = this.calculatePeriod(periodo);

        Date startDate = DateHelper.getStartDate(periodoTaskRunner);
        Date endDate = DateHelper.getEndDate(periodoTaskRunner);

        List<TaskRunnerExecution> taskExecutions = taskRunnerRepository.findExecutionsByProviderAndApplicationIdAndPeriod("bsm",applicationId,startDate,endDate);
        taskExecutions.addAll(taskRunnerRepository.findExecutionsByProviderAndApplicationIdAndPeriod("apppulse",applicationId,startDate,endDate));
        return taskExecutions;
    }

    public String calculatePeriod(String periodo){
        if (periodo.equals("default")){ //esto se hace por como funciona el date helper
            return "hora";
        }else {
            return periodo;
        }
    }
}
