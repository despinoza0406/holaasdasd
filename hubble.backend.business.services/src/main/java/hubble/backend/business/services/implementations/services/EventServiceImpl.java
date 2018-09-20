package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.interfaces.operations.kpis.EventKpiOperations;
import hubble.backend.business.services.interfaces.services.EventService;
import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.business.services.models.distValues.DistributionValues;
import hubble.backend.business.services.models.Event;
import hubble.backend.business.services.models.distValues.events.DistributionEventsGroup;
import hubble.backend.business.services.models.distValues.events.DistributionEventsUnit;
import hubble.backend.business.services.models.measures.kpis.EventsKpi;
import hubble.backend.core.enums.DateTypes;
import hubble.backend.core.enums.Results;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.models.TaskRunnerExecution;
import hubble.backend.storage.models.Threashold;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.EventRepository;
import hubble.backend.storage.repositories.TaskRunnerRepository;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    TaskRunnerRepository taskRunnerRepository;
    @Autowired
    MapperConfiguration mapper;
    @Autowired
    EventKpiOperations eventKpiOperation;



    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    public List<Event> getLastDay(String applicationId) {

        Calendar yesterday = CalendarHelper.getNow();
        yesterday.add(Calendar.HOUR, -24);
        Date today = new GregorianCalendar().getTime();

        List<EventStorage> events = eventRepository.findEventsByApplicationIdBetweenDates(applicationId, yesterday.getTime(), today);

        return mapper.mapToEventDtoList(events);
    }

    @Override
    public List<Event> getLastMonth(String applicationId) {
        Calendar lastmonth = CalendarHelper.getNow();
        lastmonth.add(Calendar.MONTH, -1);
        Date today = new GregorianCalendar().getTime();

        List<EventStorage> events = eventRepository.findEventsByApplicationIdBetweenDates(applicationId, lastmonth.getTime(), today);

        return mapper.mapToEventDtoList(events);
    }

    @Override
    public EventsKpi calculateLastMonthKpiByApplication(String applicationId) {
        return eventKpiOperation.calculateLastMonthKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public EventsKpi calculateLastDayKpiByApplication(String applicationId) {
        return eventKpiOperation.calculateLastDayKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public double calculateLastDaySeverityKpi(ApplicationStorage application){
        return eventKpiOperation.calculateLastDayKPI(application);
    }

    @Override
    public double calculateLastHourSeverityKpi(ApplicationStorage application){
        return eventKpiOperation.calculateLastHourKPI(application);
    }

    @Override
    public double calculateSeverityKPI(ApplicationStorage application,String periodo){
        return eventKpiOperation.calculateKPI(application,periodo);
    }

    @Override
    public double calculatePastDaySeverityKpi(ApplicationStorage application) {
        return eventKpiOperation.calculatePastDayKPI(application);
    }

    @Override
    public double calculatePastHourSeverityKpi(ApplicationStorage application) {
        return eventKpiOperation.calculatePastHourKPI(application);
    }

    @Override
    public List<Integer> getDistValuesLastDay(String id) {
        List<EventStorage> eventsStorage =
            eventRepository.findEventsByApplicationIdAndDifferentStatusLastDay(id, "Good");
        List<Integer> distValuesInt = new ArrayList<>();
        for (EventStorage eventStorage : eventsStorage){
            distValuesInt.add((int) eventStorage.getSeverityPoints());
        }
        return distValuesInt;
    }

    @Override
    public List<Integer> getDistValuesLastHour(String id) {
        List<EventStorage> eventsStorage =
                eventRepository.findEventsByApplicationIdAndDifferentStatusLastHour(id, "Good");
        List<Integer> distValuesInt = new ArrayList<>();
        for (EventStorage eventStorage : eventsStorage){
            distValuesInt.add((int) eventStorage.getSeverityPoints());
        }
        return distValuesInt;
    }

    @Override
    public List<DistValues> getDistValues(String id, String periodo) {
        List<DistValues> distValues;
        String period = this.calculatePeriod(periodo);

        if(period.equals("hora")){
            distValues = this.getUnitDistValues(id,period);
        }else {
            distValues = this.getGroupDistValues(id,period);
        }


        return distValues;
    }

    private List<DistValues> getUnitDistValues(String id, String periodo){
        List<DistValues> distValues = new ArrayList<>();
        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);


        List<EventStorage> eventsStorage =
                eventRepository.findEventsByApplicationIdBetweenDatesAndDifferentStatus(id,startDate,endDate, "Good");
        for (EventStorage eventStorage : eventsStorage){
            distValues.add(new DistributionEventsUnit(
                    eventStorage.getSeverityPoints(),
                    eventStorage.getStatus().equals("Error") ? "Critical" : eventStorage.getStatus(),
                    dateFormat.format(eventStorage.getUpdatedDate()),
                    eventStorage.getType(),
                    eventStorage.getSummary(),
                    DateTypes.Captura
            ));
        }
        return distValues;
    }

    private List<DistValues> getGroupDistValues(String id, String periodo){
        List<DistValues> distValues;
        Date endDate = DateHelper.getEndDate(periodo);
        Date startDate = DateHelper.getStartDate(periodo);
        List<Date> startDates = new ArrayList<>();
        List<Date> endDates = new ArrayList<>();
        List<EventStorage> eventsStorage =
                eventRepository.findEventsByApplicationIdBetweenDatesAndDifferentStatus(id,startDate,endDate, "Good");
        ApplicationStorage  applicationStorage = applicationRepository.findApplicationById(id);
        Threashold threshold;
        Date aux = startDate;
        switch (periodo){
            case "dia":
                threshold = applicationStorage.getKpis().getEvents().getHourThreashold();
                while (aux.before(endDate)){
                    startDates.add(aux);
                    if(DateUtils.addHours(aux,1).after(endDate)){
                        endDates.add(endDate);
                    }else {
                        endDates.add(DateUtils.addHours(aux, 1));
                    }
                    aux = DateUtils.addHours(aux,1);
                }
                break;
            case "semana":
                threshold = applicationStorage.getKpis().getEvents().getDayThreashold();
                while (aux.before(endDate)){
                    startDates.add(aux);
                    if(DateUtils.addDays(aux,1).after(endDate)){
                        endDates.add(endDate);
                    }else {
                        endDates.add(DateUtils.addDays(aux, 1));
                    }
                    aux = DateUtils.addDays(aux,1);
                }
                break;
            case "mes":
                threshold = applicationStorage.getKpis().getEvents().getWeekThreashold();
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
                threshold = applicationStorage.getKpis().getEvents().getDayThreashold();
                distValues = this.getUnitDistValues(id, periodo);
                return distValues;
        }
        distValues = this.getDistValuesByPeriod(eventsStorage,threshold,startDates,endDates);
        return distValues;
    }

    private List<DistValues> getDistValuesByPeriod(List<EventStorage> eventStorageList,Threashold threshold,List<Date> startDates, List<Date> endDates){
        List<DistValues> distValues = new ArrayList<>();
        double inferior = threshold.getInferior();
        double warningThreshold = threshold.getWarning();
        double criticalThreshold = threshold.getCritical();
        double superior = threshold.getSuperior();


        List<String> monitors = eventStorageList.stream().
                map(eventStorage -> eventStorage.getName()).
                distinct().collect(Collectors.toList());
        for (String monitor : monitors){
            for(int j = 0; j<startDates.size(); j++){
                final int index = j;
                List<EventStorage> monitorEvents = eventStorageList.stream().filter(
                        eventStorage -> eventStorage.getName().equals(monitor) &&
                                eventStorage.getUpdatedDate().compareTo(startDates.get(index)) >= 0 &&
                                eventStorage.getUpdatedDate().compareTo(endDates.get(index)) <= 0).
                        collect(Collectors.toList());
                if(!monitorEvents.isEmpty()) {

                    int value = monitorEvents.stream().mapToInt(eventStorage ->
                            eventStorage.getSeverityPoints()).
                            sum();
                    String status = "Critical";
                    if (value <= warningThreshold) {
                        status = "OK";
                    }
                    if (value >= warningThreshold && value < criticalThreshold) {
                        status = "Warning";
                    }

                    String date = dateFormat.format(startDates.get(j)) + " - " + dateFormat.format(endDates.get(j));
                    distValues.add(new DistributionEventsGroup(
                            value,
                            status,
                            date,
                            monitor,
                            DateTypes.Rango
                    ));
                }
            }
        }
        return distValues;
    }

    @Override
    public Results.RESULTS calculateKpiResult(String applicationId,String periodo){
        return eventKpiOperation.calculateKpiResult(applicationId,periodo);
    }

    @Override
    public List<TaskRunnerExecution> getTaskRunnerExecutions(String application,String periodo){
        return eventKpiOperation.getTaskRunnerExecutions(application,periodo);
    }

    public String calculatePeriod(String periodo){
        if (periodo.equals("default")){ //esto se hace por como funciona el date helper
            return "hora";
        }else {
            return periodo;
        }
    }
}
