package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.interfaces.operations.kpis.EventKpiOperations;
import hubble.backend.business.services.interfaces.services.EventService;
import hubble.backend.business.services.models.DistValues;
import hubble.backend.business.services.models.DistributionValues;
import hubble.backend.business.services.models.Event;
import hubble.backend.business.services.models.measures.kpis.EventsKpi;
import hubble.backend.business.services.models.measures.kpis.WorkItemsKpi;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    MapperConfiguration mapper;
    @Autowired
    EventKpiOperations eventKpiOperation;

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
        List<DistValues> distValues = new ArrayList<>();
        periodo = this.calculatePeriod(periodo);

        Date startDate = DateHelper.getStartDate(periodo);
        Date endDate = DateHelper.getEndDate(periodo);


        List<EventStorage> eventsStorage =
                eventRepository.findEventsByApplicationIdBetweenDatesAndDifferentStatus(id,startDate,endDate, "Good");
        List<Integer> distValuesInt = new ArrayList<>();
        for (EventStorage eventStorage : eventsStorage){
            distValuesInt.add((int) eventStorage.getSeverityPoints());
        }
        for (Integer distValueInt : distValuesInt){
            distValues.add(new DistributionValues(distValueInt));
        }
        return distValues;
    }

    public String calculatePeriod(String periodo){
        if (periodo.equals("default")){ //esto se hace por como funciona el date helper
            return "hora";
        }else {
            return periodo;
        }
    }
}
