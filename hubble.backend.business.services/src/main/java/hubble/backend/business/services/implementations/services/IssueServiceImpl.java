package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.interfaces.operations.averages.IssueOperations;
import hubble.backend.business.services.interfaces.operations.kpis.IssuesKpiOperations;
import hubble.backend.business.services.interfaces.services.IssueService;
import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.business.services.models.Issue;
import hubble.backend.business.services.models.distValues.GroupedLineGraphTooltip;
import hubble.backend.business.services.models.distValues.LineGraphDistValues;
import hubble.backend.business.services.models.distValues.issues.DistributionIssuesGroup;
import hubble.backend.business.services.models.distValues.issues.DistributionIssuesUnit;
import hubble.backend.business.services.models.distValues.issues.IssuesLineGraphTooltip;
import hubble.backend.business.services.models.measures.quantities.IssuesQuantity;
import hubble.backend.business.services.models.measures.kpis.IssuesKpi;
import hubble.backend.core.enums.DateTypes;
import hubble.backend.core.enums.Results;
import hubble.backend.core.utils.CalculationHelper;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.storage.models.*;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.IssueRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import hubble.backend.storage.repositories.TaskRunnerRepository;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.Math.round;

@Component
public class IssueServiceImpl implements IssueService {

    @Autowired
    IssueRepository issueRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    TaskRunnerRepository taskRunnerRepository;
    @Autowired
    MapperConfiguration mapper;
    @Autowired
    IssueOperations issueOperation;
    @Autowired
    IssuesKpiOperations issueKpiOperation;


    private double superior;
    private double inferior;
    private double lWarningKpiThreshold;
    private double lCriticalKpiThreshold;

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    public List<Issue> getLastDay(String applicationId) {

        Calendar yesterday = CalendarHelper.getNow();
        yesterday.add(Calendar.HOUR, -24);
        Date today = new GregorianCalendar().getTime();

        List<IssueStorage> issues = issueRepository.findIssuesByApplicationIdBetweenDates(applicationId, yesterday.getTime(), today);

        return mapper.mapToIssueDtoList(issues);
    }

    @Override
    public List<Issue> getLastMonth(String applicationId) {
        Calendar lastmonth = CalendarHelper.getNow();
        lastmonth.add(Calendar.MONTH, -1);
        Date today = new GregorianCalendar().getTime();

        List<IssueStorage> issues = issueRepository.findIssuesByApplicationIdBetweenDates(applicationId, lastmonth.getTime(), today);

        return mapper.mapToIssueDtoList(issues);
    }

    @Override
    public IssuesKpi calculateLast10MinutesKpiByApplication(String applicationId) {
        return issueKpiOperation.calculateLast10MinutesKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public IssuesKpi calculateLastHourKpiByApplication(String applicationId) {
        return issueKpiOperation.calculateLastHourKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public IssuesKpi calculateLastDayKpiByApplication(String applicationId) {
        return issueKpiOperation.calculateLastDayKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public double calculateHistoryDayBeforeKpiByApplication(ApplicationStorage application) {
        Defects issues = application.getKpis().getDefects();
        Threashold lastDayThreshold = issues.getDayThreashold();

        this.superior = lastDayThreshold.getSuperior();
        this.inferior = lastDayThreshold.getInferior();
        this.lCriticalKpiThreshold = lastDayThreshold.getCritical();
        this.lWarningKpiThreshold = lastDayThreshold.getWarning();
        List<IssueStorage> issuesStorage =
                issueRepository.findIssuesByApplicationIdBetweenTimestampDates(application.getId(), DateHelper.getNDaysBefore(2), DateHelper.getYesterday());
        return calculateKPI(issuesStorage);
    }

    @Override
    public double calculateHistoryLastDayKpiByApplication(ApplicationStorage application) {
        Defects issues = application.getKpis().getDefects();
        Threashold lastDayThreshold = issues.getDayThreashold();
        this.superior = lastDayThreshold.getSuperior();
        this.inferior = lastDayThreshold.getInferior();
        this.lCriticalKpiThreshold = lastDayThreshold.getCritical();
        this.lWarningKpiThreshold = lastDayThreshold.getWarning();
        List<IssueStorage> issuesStorage =
                issueRepository.findIssuesByApplicationIdBetweenTimestampDates(application.getId(), DateHelper.getYesterday(), DateHelper.getDateNow());
        return calculateKPI(issuesStorage);
    }

    @Override
    public double calculateHistoryKPIByApplication(ApplicationStorage application,String periodo) {
        Threashold threshold = application.getKpis().getDefects().getThreashold(periodo);

        periodo = this.calculatePeriod(periodo);

        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);

        this.superior = threshold.getSuperior();
        this.inferior = threshold.getInferior();
        this.lCriticalKpiThreshold = threshold.getCritical();
        this.lWarningKpiThreshold = threshold.getWarning();
        List<IssueStorage> issuesStorage =
                issueRepository.findIssuesByApplicationIdBetweenTimestampDates(application.getId(), startDate, endDate);
        if(issuesStorage.isEmpty() && !this.calculateKpiResult(application.getApplicationId(),periodo).equals(Results.RESULTS.FAILURE)){
            return 10;
        }
        return calculateKPI(issuesStorage);
    }

    @Override
    public IssuesKpi calculateLastMonthKpiByApplication(String applicationId) {
        return issueKpiOperation.calculateLastMonthKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public IssuesQuantity calculateIssuesQuantityLastDay(String applicationId) {
        return issueOperation.calculateIssuesQuantityLastDay(applicationId);
    }

    @Override
    public List<Integer> getDistValuesLastDay(String id) {
        List<IssueStorage> issuesStorage =
                issueRepository.findIssuesByApplicationIdBetweenDates(id, DateHelper.getYesterday(), DateHelper.getDateNow());
        List<Integer> distValuesInt = new ArrayList<>();
        for (IssueStorage issue : issuesStorage) {
            float criticity = (this.getPriority(issue) + this.getSeverity(issue)) / 2;
            distValuesInt.add((int) criticity);
        }
        return distValuesInt;
    }

    @Override
    public List<DistValues> getDistValues(String id, String periodo) {
        List<DistValues> distValues;
        String period = this.calculatePeriod(periodo);


        if(period.equals("dia")) {
            distValues = this.getUnitDistValues(id,period);
        }else{
            distValues = this.getGroupDistValues(id,period);
        }


        return distValues;
    }


    @Override
    public List<LineGraphDistValues> getLineGraphDistValues(String id, String periodo){
        List<LineGraphDistValues> distValues;
        String period = this.calculatePeriod(periodo);


        if(period.equals("dia")) {
            distValues = this.getLineGraphUnitDistValues(id,period);
        }else{
            distValues = this.getLineGraphGroupDistValues(id,period);
        }


        return distValues;
    }

    private List<LineGraphDistValues> getLineGraphUnitDistValues(String id, String periodo){
        List<LineGraphDistValues> distValues = new ArrayList<>();
        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);

        List<IssueStorage> issuesStorage =
                issueRepository.findIssuesByApplicationIdBetweenTimestampDates(id,startDate,endDate);

        distValues = issuesStorage.stream()
                .map(issue ->

                        new LineGraphDistValues(issue.getId(),(this.getPriority(issue) + this.getSeverity(issue)) / 2,
                        issue.getProviderOrigin() + "-" + issue.getExternalId(),
                                new IssuesLineGraphTooltip(issue.getProviderName(), issue.getTitle(), issue.getStatus())))
                .sorted(Comparator.comparing(LineGraphDistValues::getxAxis))
                .collect(Collectors.toList());

        return distValues;
    }

    private List<LineGraphDistValues> getLineGraphGroupDistValues(String id, String periodo){
        List<LineGraphDistValues> distValues = new ArrayList<>();
        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);
        List<Date> startDates = new ArrayList<>();
        List<Date> endDates = new ArrayList<>();

        List<IssueStorage> issuesStorage =
                issueRepository.findIssuesByApplicationIdBetweenTimestampDates(id,startDate,endDate);

        ApplicationStorage  applicationStorage = applicationRepository.findApplicationById(id);
        Threashold threshold;
        switch (periodo){
            case "semana":
                dateFormat = new SimpleDateFormat("dd/MM");
                threshold = applicationStorage.getKpis().getDefects().getDayThreashold();
                for(int i=0; i<7; i++){
                    startDates.add(DateUtils.addDays(startDate,i));
                    endDates.add(DateUtils.addDays(startDate,i+1));
                }
                break;
            case "mes":
                dateFormat = new SimpleDateFormat("dd/MM");
                threshold = applicationStorage.getKpis().getDefects().getWeekThreashold();
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
                distValues = this.getLineGraphUnitDistValues(id, periodo);
                return distValues;
        }
        distValues = this.getLineGraphDistValuesByPeriod(issuesStorage,threshold,startDates,endDates);

        return distValues;
    }

    private List<LineGraphDistValues> getLineGraphDistValuesByPeriod(List<IssueStorage> issuesList,Threashold threshold,List<Date> startDates, List<Date> endDates){
        List<LineGraphDistValues> distValues = new ArrayList<>();
        inferior = threshold.getInferior();
        lWarningKpiThreshold = threshold.getWarning();
        lCriticalKpiThreshold = threshold.getCritical();
        superior = threshold.getSuperior();

        for(int j = 0; j<startDates.size(); j++){
            final int index = j;
            List<IssueStorage> issueStorageList = issuesList.stream().filter(
                    issueStorage ->
                            issueStorage.getTimestamp().compareTo(startDates.get(index)) >= 0 &&
                                    issueStorage.getTimestamp().compareTo(endDates.get(index)) <= 0).
                    collect(Collectors.toList());
            if(!issueStorageList.isEmpty()) {
                List<String> fechas = new ArrayList<>();
                fechas.add(startDates.get(index).toString());
                fechas.add(endDates.get(index).toString());
                String id = String.join("-",fechas);
                int value = issueStorageList.stream().mapToInt(issue ->
                        (this.getPriority(issue) + this.getSeverity(issue)) / 2).
                        sum();
                String status = "Critical";
                if (value <= lWarningKpiThreshold) {
                    status = "OK";
                }
                if (value <= lCriticalKpiThreshold && value > lWarningKpiThreshold) {
                    status = "Warning";
                }


                String yAxis = dateFormat.format(startDates.get(index));
                distValues.add(new LineGraphDistValues(id,value,yAxis,new GroupedLineGraphTooltip(issueStorageList.size(),status)));
            }
        }

        return distValues;

    }

    private List<DistValues> getUnitDistValues(String id, String periodo){
        List<DistValues> distValues = new ArrayList<>();

        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);
        List<IssueStorage> issuesStorage =
                issueRepository.findIssuesByApplicationIdBetweenTimestampDates(id,startDate,endDate);
        for (IssueStorage issue : issuesStorage) {
            String status = "Critical";
            int criticity = (this.getPriority(issue) + this.getSeverity(issue)) / 2;
            if(criticity == 2){
                status = "Warning";
            }
            if(criticity == 1){
                status = "OK";
            }
            distValues.add(new DistributionIssuesUnit(
                    criticity,
                    status,
                    issue.getDescription(),
                    dateFormat.format(issue.getRegisteredDate()),
                    DateTypes.Alta
            ));
        }
        return distValues;
    }

    private List<DistValues> getGroupDistValues(String id, String periodo){
        List<DistValues> distValues = new ArrayList<>();
        Date endDate = DateHelper.getEndDate(periodo);
        Date startDate = DateHelper.getStartDate(periodo);
        List<Date> startDates = new ArrayList<>();
        List<Date> endDates = new ArrayList<>();
        List<IssueStorage> issuesStorage =
                issueRepository.findIssuesByApplicationIdBetweenTimestampDates(id,startDate,endDate);
        ApplicationStorage  applicationStorage = applicationRepository.findApplicationById(id);
        Threashold threshold;
        switch (periodo){
            case "semana":
                threshold = applicationStorage.getKpis().getDefects().getDayThreashold();
                for(int i=0; i<7; i++){
                    startDates.add(DateUtils.addDays(startDate,i));
                    endDates.add(DateUtils.addDays(startDate,i+1));
                }
                break;
            case "mes":
                threshold = applicationStorage.getKpis().getDefects().getWeekThreashold();
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
                for (IssueStorage issue : issuesStorage) {
                    String status = "Critical";
                    int criticity = (this.getPriority(issue) + this.getSeverity(issue)) / 2;
                    if(criticity == 2){
                        status = "Warning";
                    }
                    if(criticity == 1){
                        status = "OK";
                    }
                    distValues.add(new DistributionIssuesUnit(
                            criticity,
                            status,
                            issue.getDescription(),
                            dateFormat.format(issue.getRegisteredDate()),
                            DateTypes.Alta
                    ));
                }
                return distValues;
        }
        distValues = this.getDistValuesByPeriod(issuesStorage,threshold,startDates,endDates);
        return distValues;
    }

    private List<DistValues> getDistValuesByPeriod(List<IssueStorage> issuesList,Threashold threshold,List<Date> startDates, List<Date> endDates){
        List<DistValues> distValues = new ArrayList<>();
        inferior = threshold.getInferior();
        lWarningKpiThreshold = threshold.getWarning();
        lCriticalKpiThreshold = threshold.getCritical();
        superior = threshold.getSuperior();


        for(int j = 0; j<startDates.size(); j++){
            final int index = j;
            List<IssueStorage> issueStorageList = issuesList.stream().filter(
                    issueStorage ->
                            issueStorage.getTimestamp().compareTo(startDates.get(index)) >= 0 &&
                            issueStorage.getTimestamp().compareTo(endDates.get(index)) <= 0).
                    collect(Collectors.toList());
            if(!issueStorageList.isEmpty()) {
                int value = issueStorageList.stream().mapToInt(issue ->
                        (this.getPriority(issue) + this.getSeverity(issue)) / 2).
                        sum();
                String status = "Critical";
                if (value <= lWarningKpiThreshold) {
                    status = "OK";
                }
                if (value <= lCriticalKpiThreshold && value > lWarningKpiThreshold) {
                    status = "Warning";
                }

                String date = dateFormat.format(startDates.get(j)) + " - " + dateFormat.format(endDates.get(j));

                distValues.add(new DistributionIssuesGroup(
                        value,
                        status,
                        date,
                        DateTypes.Rango
                ));
            }
        }

        return distValues;
    }


    private Integer getPriority(IssueStorage issue){
        int priority = issue.getPriority();
        if(issue.getProviderOrigin().equalsIgnoreCase("ALM")){
            if (priority >= 3){
                return 3;
            }else {
                return priority;
            }
        }else {
            if (priority >= 4){
                return 1;
            }
            if (priority >= 3){
                return 2;
            }else {
                return 3;
            }
        }
    }

    private Integer getSeverity(IssueStorage issue){
        int severity = issue.getSeverity();
        if(issue.getProviderOrigin().equalsIgnoreCase("ALM")){
            if (severity >= 3){
                return 3;
            }else {
                return severity;
            }
        }else {
            return severity;
        }
    }

    private double calculateKPI(List<IssueStorage> issuesStorage){
        double totalCriticity = 0;

        for(IssueStorage issueStorage : issuesStorage) {
            double criticity = round((this.getSeverity(issueStorage) + this.getPriority(issueStorage)) / 2);
            totalCriticity = totalCriticity + criticity;
        }

        if(totalCriticity <= lWarningKpiThreshold){
           return CalculationHelper.calculateOkHealthIndex(totalCriticity,inferior,lWarningKpiThreshold);
        }

        if(totalCriticity <= lCriticalKpiThreshold && totalCriticity > lWarningKpiThreshold){
            return CalculationHelper.calculateWarningHealthIndex(totalCriticity,lWarningKpiThreshold,lCriticalKpiThreshold);
        }

        return CalculationHelper.calculateMinInfiniteCriticalHealthIndex(totalCriticity,lCriticalKpiThreshold);//,10);

    }

    @Override
    public Results.RESULTS calculateKpiResult(String applicationId,String periodo){
        List<TaskRunnerExecution> taskExecutions = this.getTaskRunnerExecutions(applicationId,periodo);

        Date endDate = DateHelper.getEndDate(periodo);
        Date startDate = DateHelper.getStartDate(periodo);
        List<IssueStorage> issues = issueRepository.findIssuesByApplicationIdBetweenDates(applicationId,startDate,endDate);

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

        List<TaskRunnerExecution> taskExecutions = taskRunnerRepository.findExecutionsByProviderAndApplicationIdAndPeriod("jira",applicationId,startDate,endDate);
        taskExecutions.addAll(taskRunnerRepository.findExecutionsByProviderAndApplicationIdAndPeriod("alm",applicationId,startDate,endDate));
        return taskExecutions;
    }


    @Override
    public Issue get(String id){
        return mapper.mapToIssueDto(issueRepository.findOne(id));
    }

    @Override
    public List<Issue> getIssuesBetweenDates(String appId,String dateFrom, String dateTo){
        Date startDate = new Date();
        Date endDate = new Date();
        dateFormat = new SimpleDateFormat("dow mon dd hh:mm:ss zzz yyyy");
        try {
            startDate = dateFormat.parse(dateFrom);
            endDate = dateFormat.parse(dateTo);
        }catch (ParseException e){
            e.printStackTrace();
        }
        List<IssueStorage> issueStorages =
                issueRepository.findIssuesByApplicationIdBetweenTimestampDates(appId,startDate,endDate);

        return mapper.mapToIssueDtoList(issueStorages);
    }

    public String calculatePeriod(String periodo){
        if (periodo.equals("default")){ //esto se hace por como funciona el date helper
            return "dia";
        }else {
            return periodo;
        }
    }
}
