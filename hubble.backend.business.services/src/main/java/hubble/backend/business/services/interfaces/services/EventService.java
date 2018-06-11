package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.models.Event;
import hubble.backend.business.services.models.measures.kpis.EventsKpi;

public interface EventService extends ServiceBase<Event> {

    public EventsKpi calculateLastDayKpiByApplication(String applicationId);

    public EventsKpi calculateLastMonthKpiByApplication(String applicationId);

    public long calculatePastDaySeverityKpi(String applicationId);

    public long calculateLastDaySeverityKpi(String applicationId);
}
