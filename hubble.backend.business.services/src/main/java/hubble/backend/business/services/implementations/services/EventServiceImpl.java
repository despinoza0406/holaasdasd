package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.interfaces.operations.kpis.EventKpiOperations;
import hubble.backend.business.services.interfaces.services.EventService;
import hubble.backend.business.services.models.Event;
import hubble.backend.business.services.models.measures.kpis.EventsKpi;
import hubble.backend.business.services.models.measures.kpis.WorkItemsKpi;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
    public long calculateLastDaySeverityKpi(String applicationId){
        return eventKpiOperation.calculateLastDayKPI(applicationId);
    }

    @Override
    public long calculatePastDaySeverityKpi(String applicationId) {
        return eventKpiOperation.calculatePastDayKPI(applicationId);
    }
}
