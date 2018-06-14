package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.models.Event;
import hubble.backend.business.services.models.measures.kpis.EventsKpi;

import java.util.List;

public interface EventService extends ServiceBase<Event> {

    EventsKpi calculateLastDayKpiByApplication(String applicationId);

    EventsKpi calculateLastMonthKpiByApplication(String applicationId);

    double calculatePastDaySeverityKpi(String applicationId);

    double calculateLastDaySeverityKpi(String applicationId);

    List<Integer> getDistValuesLastDay(String id);
}
