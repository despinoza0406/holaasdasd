package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.interfaces.services.averages.OperationsAverageCalculationServiceBase;
import hubble.backend.business.services.interfaces.services.kpis.InstantOperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.interfaces.services.kpis.OperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.business.services.models.Performance;
import hubble.backend.business.services.models.business.ApplicationIndicators;
import hubble.backend.business.services.models.distValues.LineGraphDistValues;
import hubble.backend.core.enums.Results;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.TaskRunnerExecution;

import java.util.List;

public interface PerformanceService extends
        OperationsAverageCalculationServiceBase<ApplicationIndicators>,
        OperationsKeyPerformanceIndicatorServiceBase<ApplicationIndicators>,
        InstantOperationsKeyPerformanceIndicatorServiceBase<ApplicationIndicators>,
        ApplicationServiceBase<Performance> {

    public List<Performance> getAll();

    public Performance getById(String id);

    public List<Performance> getLast10Minutes(String applicationId);

    public List<Performance> getLastHour(String applicationId);

    List<Integer> getDistValuesLastHour(String id);

    List<DistValues> getDistValues(String id, String periodo);

    List<LineGraphDistValues> getLineGraphDistValues(String id, String periodo);

    public double calculateHealthIndexKPILastHour(ApplicationStorage applicationStorage);

    public double calculateHealthIndexKPI(ApplicationStorage applicationStorage,String periodo);

    public double calculateHealthIndexKPILastMonth(ApplicationStorage application);

    public String calculatePeriod(String periodo);

    Results.RESULTS calculateKpiResult(String applicationId,String periodo);

    List<TaskRunnerExecution> getTaskRunnerExecutions(String applicationId,String periodo);
}
