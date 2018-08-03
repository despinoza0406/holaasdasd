package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.models.DistValues;
import hubble.backend.business.services.models.Event;
import hubble.backend.business.services.models.measures.kpis.EventsKpi;
import hubble.backend.storage.models.ApplicationStorage;

import java.util.List;

public interface EventService extends ServiceBase<Event> {

    EventsKpi calculateLastDayKpiByApplication(String applicationId);

    EventsKpi calculateLastMonthKpiByApplication(String applicationId);

    double calculatePastDaySeverityKpi(ApplicationStorage application);

    double calculatePastHourSeverityKpi(ApplicationStorage application);

    double calculateLastDaySeverityKpi(ApplicationStorage application);

    double calculateLastHourSeverityKpi(ApplicationStorage application);

    double calculateSeverityKPI(ApplicationStorage application,String periodo);

    List<Integer> getDistValuesLastDay(String id);

    List<Integer> getDistValuesLastHour(String id);

    List<DistValues> getDistValues(String id, String periodo);

    String calculatePeriod(String periodo);
}
