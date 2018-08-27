package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.interfaces.operations.averages.AvailabilityOperations;
import hubble.backend.business.services.interfaces.operations.kpis.AvailabilityKpiOperations;
import hubble.backend.business.services.interfaces.services.AvailabilityService;
import hubble.backend.business.services.models.*;
import hubble.backend.business.services.models.distValues.availability.DistributionAvailabilityGroup;
import hubble.backend.business.services.models.distValues.availability.DistributionAvailabilityUnit;
import hubble.backend.business.services.models.business.ApplicationIndicators;
import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.core.enums.Results;
import hubble.backend.core.utils.CalculationHelper;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.storage.models.*;
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
public class AvailabilityServiceImpl implements AvailabilityService {

    @Autowired
    AvailabilityRepository availabilityRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    TaskRunnerRepository taskRunnerRepository;
    @Autowired
    AvailabilityOperations availavilityOperation;
    @Autowired
    AvailabilityKpiOperations availavilityKpiOperation;

    @Autowired
    MapperConfiguration mapper;


    double n = 0;
    double inferior = 0;
    double warningThreshold = 0;
    double criticalThreshold = 0;
    double superior = 0;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    @Override
    public List<Availability> getAll() {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAll();
        return mapper.mapToAvailabilityDtoList(availabilityStorageList);
    }

    @Override
    public Availability get(String id) {
        return mapper.mapToAvailabilityDto(availabilityRepository.findOne(id));
    }

    @Override
    public List<Availability> getLast10Minutes(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMinutes(CalendarHelper.TEN_MINUTES, applicationId);
        return mapper.mapToAvailabilityDtoList(availabilityStorageList);
    }

    @Override
    public List<Availability> getLastHour(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMinutes(CalendarHelper.ONE_HOUR, applicationId);
        return mapper.mapToAvailabilityDtoList(availabilityStorageList);
    }

    @Override
    public List<Availability> getLastDay(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMinutes(CalendarHelper.ONE_DAY, applicationId);
        return mapper.mapToAvailabilityDtoList(availabilityStorageList);
    }

    @Override
    public List<Availability> getLastMonth(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationIdAndDurationMonths(CalendarHelper.ONE_MONTH, applicationId);
        return mapper.mapToAvailabilityDtoList(availabilityStorageList);
    }

    @Override
    public List<Availability> getAll(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList = availabilityRepository.findAvailabilitiesByApplicationId(applicationId);
        return mapper.mapToAvailabilityDtoList(availabilityStorageList);
    }

    @Override
    public Application getApplication(String applicationId) {
        ApplicationStorage applicationStorage = applicationRepository.findApplicationById(applicationId);
        return mapper.mapToApplicationDto(applicationStorage);
    }

    @Override
    public ApplicationIndicators calculateLast10MinutesAverageByApplication(String applicationId) {
        return availavilityOperation.calculateLast10MinutesAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLastHourAverageByApplication(String applicationId) {
        return availavilityOperation.calculateLastHourAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLastDayAverageByApplication(String applicationId) {
        return availavilityOperation.calculateLastDayAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLastMonthAverageByApplication(String applicationId) {
        return availavilityOperation.calculateLastMonthAverageByApplication(applicationId);
    }

    @Override
    public ApplicationIndicators calculateLast10MinutesKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setAvailabilityKpi(availavilityKpiOperation.calculateLast10MinutesKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public ApplicationIndicators calculateLastHourKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setAvailabilityKpi(availavilityKpiOperation.calculateLastHourKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public ApplicationIndicators calculateLastDayKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setAvailabilityKpi(availavilityKpiOperation.calculateLastDayKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public ApplicationIndicators calculateLastMonthKpiByApplication(String applicationId) {
        ApplicationIndicators appDto = new ApplicationIndicators();
        appDto.setAvailabilityKpi(availavilityKpiOperation.calculateLastMonthKeyPerformanceIndicatorByApplication(applicationId));
        return appDto;
    }

    @Override
    public List<Application> getAllApplications() {
        //TODO: Mover a un servicio de aplicación.
        List<ApplicationStorage> applicationsStorage = applicationRepository.findAllApplications();
        return mapper.mapToApplicationDtoList(applicationsStorage);
    }

    @Override
    public List<Integer> getDistValuesLastHour(String applicationId) {
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(applicationId,DateHelper.getAnHourAgo(), DateHelper.getDateNow());
        List<Integer> distValuesInt = new ArrayList<>();
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            distValuesInt.add(availabilityStorage.getAvailabilityStatus().equals("Failed") ? 0 : 1);
        }
        return distValuesInt;
    }

    @Override
    public List<DistValues> getDistValues(String applicationId, String period) {
        List<DistValues> distValues;
        String availabilityperiod = this.calculatePeriod(period);


        if(availabilityperiod.equals("hora")) {
            distValues = this.getUnitDistValues(applicationId,availabilityperiod);
        }else{
            distValues = this.getGroupDistValues(applicationId,availabilityperiod);
        }


        return distValues;
    }

    private List<DistValues> getUnitDistValues(String applicationId, String availabilityperiod){
        List<DistValues> distValues = new ArrayList<>();
        Date endDate = DateHelper.getEndDate(availabilityperiod);
        Date startDate = DateHelper.getStartDate(availabilityperiod);

        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(applicationId,startDate,endDate);
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {

            distValues.add(new DistributionAvailabilityUnit(
                    availabilityStorage.getAvailabilityStatus().equals("Failed") ? 0 : 100,
                    availabilityStorage.getAvailabilityStatus(),
                    availabilityStorage.getTransactionName(),
                    dateFormat.format(availabilityStorage.getTimeStamp()))
            );
        }
        return distValues;
    }

    private List<DistValues> getGroupDistValues(String applicationId, String availabilityperiod){
        List<DistValues> distValues = new ArrayList<>();
        Date endDate = DateHelper.getEndDate(availabilityperiod);
        Date startDate = DateHelper.getStartDate(availabilityperiod);
        List<Date> startDates = new ArrayList<>();
        List<Date> endDates = new ArrayList<>();
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(applicationId,startDate,endDate);

        ApplicationStorage  applicationStorage = applicationRepository.findApplicationById(applicationId);
        Threashold threshold;
        switch (availabilityperiod){
            case "dia":
                threshold = applicationStorage.getKpis().getAvailability().getHourThreashold();
                for(int i=0; i<24; i++){
                    startDates.add(DateUtils.addHours(startDate,i));
                    endDates.add(DateUtils.addHours(startDate,i+1));
                }
                break;
            case "semana":
                threshold = applicationStorage.getKpis().getAvailability().getDayThreashold();
                for(int i=0; i<7; i++){
                    startDates.add(DateUtils.addDays(startDate,i));
                    endDates.add(DateUtils.addDays(startDate,i+1));
                }
                break;
            case "mes":
                threshold = applicationStorage.getKpis().getAvailability().getWeekThreashold();
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
                for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
                    distValues.add(new DistributionAvailabilityUnit(
                            availabilityStorage.getAvailabilityStatus().equals("Failed") ? 0 : 100,
                            availabilityStorage.getAvailabilityStatus().equals("Failed") ? "Critical" : "OK",
                            availabilityStorage.getTransactionName(),
                            dateFormat.format(availabilityStorage.getTimeStamp()))
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
                    String status = "";
                    int value = transactionAvailability.stream().mapToInt(availabilityStorage ->
                            availabilityStorage.getAvailabilityStatus().equals("Failed") ? 0 : 100).
                            sum() / transactionAvailability.size();
                    status = "Critical";
                    if (value >= warningThreshold) {
                        status = "OK";
                    }
                    if (value <= warningThreshold && value > criticalThreshold) {
                        status = "Warning";
                    }

                    String date = dateFormat.format(startDates.get(j)) + " - " + dateFormat.format(endDates.get(j));

                    distValues.add(new DistributionAvailabilityGroup(
                            value,
                            status,
                            transaction,
                            date
                    ));
                }
            }
        }
        return distValues;
    }

    @Override
    public double calculateHealthIndexKPI(ApplicationStorage application,String periodo) {

        Threashold threshold = application.getKpis().getAvailability().getThreashold(periodo);

        periodo = this.calculatePeriod(periodo);

        Date endDate = DateHelper.getEndDate(periodo);
        Date startDate = DateHelper.getStartDate(periodo);
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(application.getId(),startDate,endDate);
        if(availabilityStorageList.isEmpty() && !this.calculateKpiResult(application.getApplicationId(),periodo).equals(Results.RESULTS.FAILURE)){
            return 1;
        }
        double totalOk = 0;
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            totalOk = totalOk + (availabilityStorage.getAvailabilityStatus().equals("Failed") ? 0 : 1);
        }

        n = (totalOk * 100d) / (double) availabilityStorageList.size() ;

        inferior = threshold.getInferior();
        warningThreshold = threshold.getWarning();
        criticalThreshold = threshold.getCritical();
        superior = threshold.getSuperior();


        if (n >= warningThreshold) {
            return CalculationHelper.calculateDispOkHealthIndex(n, inferior , warningThreshold);
        }

        if (n <= warningThreshold && n > criticalThreshold) {
            return CalculationHelper.calculateDispWarningHealthIndex(n, warningThreshold, criticalThreshold);
        }

        if (n <= criticalThreshold) {
            return CalculationHelper.calculateDispCriticalHealthIndex(n, criticalThreshold, superior);
        }

        return 1;
    }

    @Override
    public double calculateHealthIndexKPILastHour(ApplicationStorage application) {
        Availavility dispon = application.getKpis().getAvailability();
        Threashold threashold = dispon.getHourThreashold();
        List<AvailabilityStorage> availabilityStorageList =
                availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(application.getId(),DateHelper.getAnHourAgo(), DateHelper.getDateNow());
        double totalOk = 0;
        for (AvailabilityStorage availabilityStorage : availabilityStorageList) {
            totalOk = totalOk + (availabilityStorage.getAvailabilityStatus().equals("Failed") ? 0 : 1);
        }

        inferior = threashold.getInferior();
        warningThreshold = threashold.getWarning();
        criticalThreshold = threashold.getCritical();
        superior = threashold.getSuperior();


        if (n >= warningThreshold) {
            return CalculationHelper.calculateDispOkHealthIndex(n, inferior , warningThreshold);
        }

        if (n >= warningThreshold && n < criticalThreshold) {
            return CalculationHelper.calculateDispWarningHealthIndex(n, warningThreshold, criticalThreshold);
        }

        if (n <= criticalThreshold) {
            return CalculationHelper.calculateDispCriticalHealthIndex(n, criticalThreshold, superior);
        }

        return 0;
    }

    @Override
    public Results.RESULTS calculateKpiResult(String applicationId,String periodo){
        List<TaskRunnerExecution> taskExecutions = this.getTaskRunnerExecutions(applicationId,periodo);

        Date endDate = DateHelper.getEndDate(periodo);
        Date startDate = DateHelper.getStartDate(periodo);
        List<AvailabilityStorage> availabilities = availabilityRepository.findAvailabilitiesByApplicationIdAndPeriod(applicationId,startDate,endDate);

        if (allFailures(taskExecutions)){ //Si todos fallaron y no se tienen datos
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
