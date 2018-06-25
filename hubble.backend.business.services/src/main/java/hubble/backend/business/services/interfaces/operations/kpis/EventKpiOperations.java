package hubble.backend.business.services.interfaces.operations.kpis;

import hubble.backend.business.services.models.measures.kpis.EventsKpi;
import hubble.backend.business.services.models.measures.rules.EventsGroupRule;

public interface EventKpiOperations extends
        KeyPerformanceIndicatorOperationsBase<EventsGroupRule>, KpiThresholdSetup {

    public EventsKpi calculateLastDayKeyPerformanceIndicatorByApplication(String applicationId);

    public EventsKpi calculateLastWeekKeyPerformanceIndicatorByApplication(String applicationId);

    public EventsKpi calculateLastMonthKeyPerformanceIndicatorByApplication(String applicationId);

    public double calculateLastDayKPI(String applicationId);

    public double calculatePastDayKPI(String applicationId);

    double calculateLastHourKPI(String applicationId);
}
