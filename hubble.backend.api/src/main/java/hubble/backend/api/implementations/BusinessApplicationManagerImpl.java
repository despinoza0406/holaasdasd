package hubble.backend.api.implementations;

import hubble.backend.api.configurations.mappers.ApplicationMapper;
import hubble.backend.business.services.configurations.mappers.AvailabilityMapper;
import hubble.backend.api.configurations.mappers.UptimeMapper;
import hubble.backend.api.interfaces.BusinessApplicationManager;
import hubble.backend.api.models.*;
import hubble.backend.business.services.configurations.mappers.EventsMapper;
import hubble.backend.business.services.configurations.mappers.IssuesMapper;
import hubble.backend.business.services.configurations.mappers.TasksMapper;
import hubble.backend.business.services.interfaces.services.*;
import hubble.backend.business.services.interfaces.services.kpis.KpiAveragesService;
import hubble.backend.business.services.models.*;
import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.business.services.models.distValues.DistributionValues;
import hubble.backend.business.services.models.measures.Uptime;
import hubble.backend.business.services.models.tables.*;
import hubble.backend.core.enums.MonitoringFields;
import hubble.backend.core.enums.Results;
import hubble.backend.core.utils.CalendarHelper;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import hubble.backend.core.utils.DateHelper;

import static hubble.backend.storage.models.KPITypes.*;
import static java.util.stream.Collectors.toList;

import hubble.backend.storage.models.*;
import hubble.backend.storage.models.Performance;
import hubble.backend.storage.repositories.ApplicationRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessApplicationManagerImpl implements BusinessApplicationManager {

    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    AvailabilityService availabilityService;
    @Autowired
    PerformanceService performanceService;
    @Autowired
    IssueService issueService;
    @Autowired
    ApplicationMapper applicationMapper;
    @Autowired
    UptimeDowntimeService uptimeService;
    @Autowired
    WorkItemService workItemService;
    @Autowired
    EventService eventService;
    @Autowired
    UptimeMapper uptimeMapper;
    @Autowired
    KpiAveragesService kpiAverageService;
    @Autowired
    ApplicationService applicationService;

    @Override
    public BusinessApplicationProfile getBusinessApplicationView(String id) {

        BusinessApplicationProfile businessView = new BusinessApplicationProfile();
        businessView.setId(id);
        //availability Kpi
        businessView.setAvailabilityLast10MinKpi(availabilityService.calculateLast10MinutesKpiByApplication(id).getAvailabilityKpi().get());
        businessView.setAvailabilityLastHourKpi(availabilityService.calculateLastHourKpiByApplication(id).getAvailabilityKpi().get());
        businessView.setAvailabilityLastDayKpi(availabilityService.calculateLastDayKpiByApplication(id).getAvailabilityKpi().get());
        businessView.setAvailabilityLastMonthKpi(availabilityService.calculateLastMonthKpiByApplication(id).getAvailabilityKpi().get());

        //performance Kpi
        businessView.setPerformanceLast10MinKpi(performanceService.calculateLast10MinutesKpiByApplication(id).getPerformanceKpi().get());
        businessView.setPerformanceLastHourKpi(performanceService.calculateLastHourKpiByApplication(id).getPerformanceKpi().get());
        businessView.setPerformanceLastDayKpi(performanceService.calculateLastDayKpiByApplication(id).getPerformanceKpi().get());
        businessView.setPerformanceLastMonthKpi(performanceService.calculateLastMonthKpiByApplication(id).getPerformanceKpi().get());

        //Issues Kpi
        businessView.setIssuesKpiLastDay(issueService.calculateLastDayKpiByApplication(id).get());
        businessView.setIssuesKpiLastMonth(issueService.calculateLastMonthKpiByApplication(id).get());

        //Tareas Kpi
        businessView.setWorkItemsKpiLastDay(workItemService.calculateLastDayKpiByApplication(id).get());
        businessView.setWorkItemsKpiLastMonth(workItemService.calculateLastMonthKpiByApplication(id).get());

        //Events Kpi
        /* No tiene sentido porque al final termina pidiendole un campo a mongo que no esta. Registered date. Se tendria que agregar
        businessView.setEventsKpiLastDay(eventService.calculateLastDayKpiByApplication(id).get());
        businessView.setEventsKpiLastMonth(eventService.calculateLastMonthKpiByApplication(id).get());
         */
        //Kpi Averages
        businessView.setHealthIndex(kpiAverageService.getStandardHealthIndex(id));
        //TODO: Eventos Kpi.
        return businessView;
    }

    @Override
    public List<BusinessApplication> getAllApplications() {
        List<Application> applicationDtoList = availabilityService.getAllApplications();
        return applicationMapper.mapToBusinessApplicationList(applicationDtoList);
    }

    @Override
    public List<ApplicationUptime> getUptimeLastMonth(String applicationId) {

        Calendar startCalendar = CalendarHelper.getNow();
        startCalendar.add(Calendar.MONTH, -1);
        Date startDate = startCalendar.getTime();

        Calendar endCalendar = CalendarHelper.getNow();
        endCalendar.add(Calendar.HOUR, 24);
        Date endDate = endCalendar.getTime();

        Uptime uptimeDto = uptimeService.getUptimeByApplication(applicationId, MonitoringFields.FRECUENCY.DAY, startDate, endDate);

        List<ApplicationUptime> uptimes = uptimeMapper.mapToUptimeList(uptimeDto);
        return uptimes;
    }

    @Override
    public List<BusinessApplicationProfile> getBusinessApplicationsPageLimit(int page, int limit) {

        List<BusinessApplication> applications = getAllApplications();
        List<BusinessApplicationProfile> applicationsProfiles = new ArrayList<>();

        for (BusinessApplication businessApplication : applications) {

            if (businessApplication.getId() == null) {
                continue;
            }

            BusinessApplicationProfile currentBusinessApplicationProfile = getBusinessApplicationView(businessApplication.getId());

            applicationsProfiles.add(currentBusinessApplicationProfile);

        }

        return applicationsProfiles;
    }

    @Override
    public BusinessApplicationFrontend getBusinessApplicationFrontend(String id,String periodo) {
        ApplicationStorage applicationStorage = applicationRepository.findApplicationById(id);

        BusinessApplicationFrontend businessApplicationFrontend = new BusinessApplicationFrontend();

        businessApplicationFrontend.setId(applicationStorage.getId());
        businessApplicationFrontend.setLastUpdate(DateHelper.lastExecutionDate);
        businessApplicationFrontend.setPastUpdate(DateHelper.addDaysToDate(DateHelper.lastExecutionDate, -1));
        setKPIs(businessApplicationFrontend, applicationStorage, periodo);
        setResult(businessApplicationFrontend);
        setHealthIndex(businessApplicationFrontend, businessApplicationFrontend.getKpis());
        setPastHealthIndex(businessApplicationFrontend, applicationStorage);


        return businessApplicationFrontend;
    }

    @Override
    public BusinessApplicationFrontend getBusinessApplicationFrontendDistValues(String id,String period) {
        BusinessApplicationFrontend businessApplicationFrontend = getBusinessApplicationFrontend(id,period);
        setDistValues(businessApplicationFrontend.getKpis(), id,period);
        return businessApplicationFrontend;
    }

    private void setDistValues(List<KpiFrontend> kpis, String id,String period) {
        List<DistValues> distValues;
        for (KpiFrontend kpi : kpis) {
            distValues = getDistValuesOf(kpi.getKpiName(), id,period);
            kpi.setDistribution(distValues);
        }
    }

    @Override
    public List<BusinessApplicationFrontend> getBusinessApplicationsFrontend(boolean includeInactives,String periodo) {
        return getAllApplications()
            .stream()
            .filter((a) -> includeInactives || a.isActive())
            .map((a) -> getBusinessApplicationFrontend(a.getId(),periodo))
            .collect(toList());
    }

    public void setHealthIndex(BusinessApplicationFrontend businessApplicationFrontend, List<KpiFrontend> kpisFront) {

        List<Double> kpis;
        kpis = kpisFront.stream().filter(x -> !x.getKpiResult().equals(Results.RESULTS.FAILURE))
                .map(x -> x.getKpiValue()).collect(Collectors.toList()); //me lo mapea a los valores de los kpi

        double healthIndex = getKPIAverage(kpis);
        businessApplicationFrontend.setHealthIndex(healthIndex);
    }

    public void setResult(BusinessApplicationFrontend businessApplicationFrontend){
        List<KpiFrontend> kpis = businessApplicationFrontend.getKpis();
        if (kpis.stream().allMatch(kpi -> kpi.getKpiResult().equals(Results.RESULTS.SUCCESS))){
            businessApplicationFrontend.setResult(Results.RESULTS.SUCCESS);
        }else{
            businessApplicationFrontend.setResult(Results.RESULTS.FAILURE);
        }

    }

    public void setPastHealthIndex(BusinessApplicationFrontend businessApplicationFrontend, ApplicationStorage application) {
        double availabilityKPImonth = availabilityService.calculateHealthIndexKPILastHour(application);
        double performanceKPIday = performanceService.calculateHealthIndexKPILastMonth(application);
        double issuesKPIday = issueService.calculateHistoryDayBeforeKpiByApplication(application);
        double workItemKPIday = workItemService.calculatePastDayDeflectionDaysKpi(application);
        double eventKPIday = eventService.calculatePastHourSeverityKpi(application);

        List<Double> kpis = new ArrayList<>();
        kpis.add(availabilityKPImonth);
        kpis.add(performanceKPIday);
        kpis.add(issuesKPIday);
        kpis.add(workItemKPIday);
        kpis.add(eventKPIday);

        double pastHealthIndex = getKPIAverage(kpis);
        businessApplicationFrontend.setHealthIndexPast(pastHealthIndex);
    }

    public void setKPIs(BusinessApplicationFrontend businessApplicationFrontend, ApplicationStorage application,String periodo) {
        List<KpiFrontend> kpis = new ArrayList<>();
        Set<KPITypes> kpiTypes = application.getKpis().getEnabledKPIs();

        if (kpiTypes.contains(AVAILABILITY)) {
            KpiFrontend availabilityKpi = new KpiFrontend();
            availabilityKpi.setKpiName("Disponibilidad");
            availabilityKpi.setKpiShortName("D");
            availabilityKpi.setKpiValue(availabilityService.calculateHealthIndexKPI(application,periodo));
            availabilityKpi.setKpiPeriod(availabilityService.calculatePeriod(periodo));
            availabilityKpi.setKpiResult(availabilityService.calculateKpiResult(application.getApplicationId(),periodo));
            availabilityKpi.setKpiTaskRunners(availabilityService.getTaskRunnerExecutions(application.getApplicationId(),periodo));
            if(Double.isNaN(availabilityKpi.getKpiValue())) {
                availabilityKpi.setKpiComment("No hay datos de availability");
            }
            kpis.add(availabilityKpi);
        }

        if (kpiTypes.contains(PERFORMANCE)){
            KpiFrontend performanceKpi = new KpiFrontend();
            performanceKpi.setKpiName("Performance");
            performanceKpi.setKpiShortName("P");
            performanceKpi.setKpiValue(performanceService.calculateHealthIndexKPI(application,periodo));
            performanceKpi.setKpiPeriod(performanceService.calculatePeriod(periodo));
            performanceKpi.setKpiResult(performanceService.calculateKpiResult(application.getApplicationId(),periodo));
            performanceKpi.setKpiTaskRunners(performanceService.getTaskRunnerExecutions(application.getApplicationId(),periodo));
            if(Double.isNaN(performanceKpi.getKpiValue())) {
                performanceKpi.setKpiComment("No hay datos de performance");
            }
            kpis.add(performanceKpi);
        }
        if(kpiTypes.contains(DEFECTS)) {
            KpiFrontend issuesKpi = new KpiFrontend();
            issuesKpi.setKpiName("Incidencias");
            issuesKpi.setKpiShortName("I");
            issuesKpi.setKpiValue(issueService.calculateHistoryKPIByApplication(application,periodo));
            issuesKpi.setKpiPeriod(issueService.calculatePeriod(periodo));
            issuesKpi.setKpiResult(issueService.calculateKpiResult(application.getApplicationId(),periodo));
            issuesKpi.setKpiTaskRunners(issueService.getTaskRunnerExecutions(application.getApplicationId(),periodo));
            if(Double.isNaN(issuesKpi.getKpiValue())) {
                issuesKpi.setKpiComment("No hay datos de issues");
            }
            kpis.add(issuesKpi);
        }
        if(kpiTypes.contains(TASKS)) {
            KpiFrontend workitemKpi = new KpiFrontend();
            workitemKpi.setKpiName("Tareas");
            workitemKpi.setKpiShortName("T");
            workitemKpi.setKpiValue(workItemService.calculateDeflectionDaysKPI(application,periodo));
            workitemKpi.setKpiPeriod(workItemService.calculatePeriod(periodo));
            workitemKpi.setKpiResult(workItemService.calculateKpiResult(application.getApplicationId(),periodo));
            workitemKpi.setKpiTaskRunners(workItemService.getTaskRunnerExecutions(application.getApplicationId(),periodo));
            if(Double.isNaN(workitemKpi.getKpiValue())){
                workitemKpi.setKpiComment("No hay datos de tareas");
            }
            kpis.add(workitemKpi);
        }
        if(kpiTypes.contains(EVENTS)) {
            KpiFrontend eventKpi = new KpiFrontend();
            eventKpi.setKpiName("Eventos");
            eventKpi.setKpiShortName("E");
            eventKpi.setKpiValue(eventService.calculateSeverityKPI(application,periodo));
            eventKpi.setKpiPeriod(eventService.calculatePeriod(periodo));
            eventKpi.setKpiResult(eventService.calculateKpiResult(application.getApplicationId(),periodo));
            eventKpi.setKpiTaskRunners(eventService.getTaskRunnerExecutions(application.getApplicationId(),periodo));
            if(Double.isNaN(eventKpi.getKpiValue())) {
                eventKpi.setKpiComment("No hay datos de eventos");
            }
            kpis.add(eventKpi);
        }
        businessApplicationFrontend.setKpis(kpis);
    }

    public double getKPIAverage(List<Double> kpis) {
        double average = 0;

        if(kpis.isEmpty()){ //Basicamente si fallaron todos los taskrunners
            return 1;
        }

        for (Double kpi : kpis) {
            if(!Double.isNaN(kpi)) {
                average = average + kpi;
            }
        }

        return average / (double) kpis.size();
    }

    private List<DistValues> getDistValuesOf(String kpiName, String id, String period) {
        List<DistValues> distValues;

        switch (kpiName) {
            case "Disponibilidad":
                distValues = availabilityService.getDistValues(id,period);
                break;
            case "Performance":
                distValues = performanceService.getDistValues(id,period);
                break;
            case "Incidencias":
                distValues = issueService.getDistValues(id,period);
                break;
            case "Tareas":
                distValues = workItemService.getDistValues(id,period);
                break;
            case "Eventos":
                distValues = eventService.getDistValues(id,period);
                break;
            default:
                distValues = new ArrayList<>();
                break;
        }
        return distValues;
    }

    @Override
    public KPIs getKPIs(String id,String periodo) {
        ApplicationStorage applicationStorage = applicationRepository.findApplicationById(id);
        return this.filterKPIsByPeriod(applicationStorage.getKpis(),periodo);
    }

    private List<DistValues> convertIntegerListToDistValues(List<Integer> distValuesInt) {
        List<DistValues> distValues = new ArrayList<>();
        for (Integer distValue : distValuesInt) {
            distValues.add(new DistributionValues(distValue));
        }
        return distValues;
    }

    @Override
    public List<BusinessApplicationLigth> getApplicationsLigth(boolean includeInactives) {
        
        List<ApplicationStorage> applicationDtoList = applicationService.getAll().stream().filter((a) -> includeInactives || a.isActive()).collect(toList());
        return applicationMapper.mapToBusinessApplicationLigthList(applicationDtoList);
        
    }

    private KPIs filterKPIsByPeriod(KPIs kpis,String periodo){
        KPIs filteredKPIs = new KPIs();


        Events events = new Events();
        Availavility availavility = new Availavility();
        Performance performance = new Performance();
        Defects defects = new Defects();
        Tasks tasks = new Tasks();


        switch (periodo){
            case "default":
                events.setUnitaryThreashold(kpis.getEvents().getUnitaryThreashold());
                tasks.setUnitaryThreashold(kpis.getTasks().getUnitaryThreashold());
                performance.setUnitaryThreashold(kpis.getPerformance().getUnitaryThreashold());
                availavility.setUnitaryThreashold(kpis.getAvailability().getUnitaryThreashold());
                defects.setUnitaryThreashold(kpis.getDefects().getUnitaryThreashold());
                break;
            case "dia":
                events.setHourThreashold(kpis.getEvents().getHourThreashold());
                tasks.setDayThreashold(kpis.getTasks().getDayThreashold());
                performance.setHourThreashold(kpis.getPerformance().getHourThreashold());
                availavility.setHourThreashold(kpis.getAvailability().getHourThreashold());
                defects.setDayThreashold(kpis.getDefects().getDayThreashold());
                break;
            case "semana":
                events.setDayThreashold(kpis.getEvents().getDayThreashold());
                tasks.setDayThreashold(kpis.getTasks().getDayThreashold());
                performance.setDayThreashold(kpis.getPerformance().getDayThreashold());
                availavility.setDayThreashold(kpis.getAvailability().getDayThreashold());
                defects.setDayThreashold(kpis.getDefects().getDayThreashold());
                break;
            case "mes":
                events.setWeekThreashold(kpis.getEvents().getWeekThreashold());
                tasks.setWeekThreashold(kpis.getTasks().getWeekThreashold());
                performance.setWeekThreashold(kpis.getPerformance().getWeekThreashold());
                availavility.setWeekThreashold(kpis.getAvailability().getWeekThreashold());
                defects.setWeekThreashold(kpis.getDefects().getWeekThreashold());
                break;
            default:
                events.setUnitaryThreashold(kpis.getEvents().getUnitaryThreashold());
                tasks.setUnitaryThreashold(kpis.getTasks().getUnitaryThreashold());
                performance.setUnitaryThreashold(kpis.getPerformance().getUnitaryThreashold());
                availavility.setUnitaryThreashold(kpis.getAvailability().getUnitaryThreashold());
                defects.setUnitaryThreashold(kpis.getDefects().getUnitaryThreashold());
                break;

        }
        filteredKPIs.setTasks(tasks);
        filteredKPIs.setPerformance(performance);
        filteredKPIs.setAvailability(availavility);
        filteredKPIs.setEvents(events);
        filteredKPIs.setDefects(defects);
        return filteredKPIs;
    }


    @Override
    public LineGraphTableResponse getTablesByFilter(String appId, String kpi, JSONObject filter){
        LineGraphTableResponse results = new LineGraphTableResponse();
        switch (kpi) {
            case "disponibilidad":
                results = this.getAllAvailabilityByFilter(appId, filter);
                break;
            case "performance":
                results = this.getAllAvailabilityByFilter(appId, filter);
                break;
            case "incidentes":
                results = this.getAllIssuesByFilter(appId,filter);
                break;
            case "tareas":  
                results = this.getAllTasksByFilter(appId,filter);
                break;
            case "eventos":
                results = this.getAllEventsByFilter(appId,filter);
                break;
        }
        return results;
    }

    private LineGraphTableResponse getAllEventsByFilter(String appId, JSONObject filter) {
        EventsMapper mapper = new EventsMapper();
        List<FrontEndTable> eventsTable = new ArrayList<>();
        List<String> properties;
        if(filter.has("id")){
            Event event = eventService.get(filter.getString("id"));
            eventsTable.add(mapper.mapEventToEventsTable(event));
        }else {
            //traer eventos con los dates
            //mappear a eventsTable
        }
        properties = this.getTableProperties("eventos");
        return new LineGraphTableResponse(eventsTable,properties);

    }

    private LineGraphTableResponse getAllTasksByFilter(String appId, JSONObject filter) {
        TasksMapper mapper = new TasksMapper();
        List<FrontEndTable> tasksTable = new ArrayList<>();
        List<String> properties;
        if(filter.has("id")){
            WorkItem workItem = workItemService.get(filter.getString("id"));
            tasksTable.add(mapper.mapWorkItemToTasksTable(workItem));
        }else {
            //traer workitems con los dates
            //mappear a tasksTable
        }
        properties = this.getTableProperties("tareas");
        return new LineGraphTableResponse(tasksTable,properties);
    }

    private LineGraphTableResponse getAllIssuesByFilter(String appId, JSONObject filter) {
        IssuesMapper mapper = new IssuesMapper();
        List<FrontEndTable> issuesTable = new ArrayList<>();
        List<String> properties;
        if(filter.has("id")){
            Issue issue = issueService.get(filter.getString("id"));
            issuesTable.add((mapper.mapIssueToIssuesTable(issue)));
        }else {
            //traer issues con los dates
            //mappear a issuesTable
        }
        properties = this.getTableProperties("incidentes");
        return new LineGraphTableResponse(issuesTable,properties);

    }

    private LineGraphTableResponse getAllAvailabilityByFilter(String appId, JSONObject filter) {
        AvailabilityMapper mapper = new AvailabilityMapper();
        List<FrontEndTable> availabilityTable = new ArrayList<>();
        List<String> properties;
        if (filter.has("id")) {
            Availability availability = availabilityService.get(filter.getString("id"));
            availabilityTable.add(mapper.mapAvailabilityToAvailabilityTable(availability));

        } else {
            //traerse availability con dateFrom y dateTo
            //mapear a AvailabilityTable
        }

        properties = this.getTableProperties("disponibilidad");

        return new LineGraphTableResponse(availabilityTable,properties);
    }

    private List<String> getTableProperties (String kpi){
        List<String> propiedades = new ArrayList<>();
        Field[] fields = null;
        switch (kpi){
            case "disponibilidad":
                fields = AvailabilityTable.class.getDeclaredFields();
                break;
            case "tareas":
                fields = TasksTable.class.getDeclaredFields();
                break;
            case "incidentes":
                fields = IssuesTable.class.getDeclaredFields();
                break;
            case "eventos":
                fields = EventsTable.class.getDeclaredFields();
                break;

        }

        for (Field field: fields){
            propiedades.add(field.getName());
        }
        return propiedades;
    }

}
