package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.business.services.models.Event;
import hubble.backend.business.services.models.distValues.LineGraphDistValues;
import hubble.backend.business.services.models.measures.kpis.EventsKpi;
import hubble.backend.core.enums.Results;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.TaskRunnerExecution;

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

    List<LineGraphDistValues> getLineGraphDistValues(String id, String periodo);

    String calculatePeriod(String periodo);

    String calculatePeriodFrontend(String periodo);

    Results.RESULTS calculateKpiResult(String applicationId,String periodo);

    List<TaskRunnerExecution> getTaskRunnerExecutions(String application,String periodo);

    Event get(String id);

    List<Event> getEventsBetweenDates(String appId,String dateFrom,String dateTo);
}
