package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.interfaces.services.averages.OperationsAverageCalculationServiceBase;
import hubble.backend.business.services.interfaces.services.kpis.InstantOperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.interfaces.services.kpis.OperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.models.Availability;
import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.business.services.models.business.ApplicationIndicators;
import hubble.backend.business.services.models.distValues.LineGraphDistValues;
import hubble.backend.core.enums.Results;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.TaskRunnerExecution;

import java.util.List;

public interface AvailabilityService extends
        OperationsAverageCalculationServiceBase<ApplicationIndicators>,
        OperationsKeyPerformanceIndicatorServiceBase<ApplicationIndicators>,
        InstantOperationsKeyPerformanceIndicatorServiceBase<ApplicationIndicators>,
        ApplicationServiceBase<Availability> {

    List<Availability> getAll();

    Availability get(String id);

    List<Availability> getAvailabilitiesBetweenDates(String appId,String dateFrom,String dateTo);

    List<Availability> getLast10Minutes(String applicationId);

    List<Availability> getLastHour(String applicationId);

    List<Integer> getDistValuesLastHour(String applicationId);

    List<DistValues> getDistValues(String applicationId, String period);

    List<LineGraphDistValues> getLineGraphDistValues(String applicationId, String period);

    double calculateHealthIndexKPILastHour(ApplicationStorage application);

    double calculateHealthIndexKPI(ApplicationStorage applicationStorage, String periodo,Results.RESULTS kpiResult);

    String calculatePeriod(String periodo);

    String calculatePeriodFrontend(String periodo);

    Results.RESULTS calculateKpiResult(String applicationId,String periodo,List<TaskRunnerExecution> taskExecutions);

    List<TaskRunnerExecution> getTaskRunnerExecutions(String applicationId, String periodo);



}
