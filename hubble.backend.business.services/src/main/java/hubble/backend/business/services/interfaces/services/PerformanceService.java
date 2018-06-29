package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.interfaces.services.averages.OperationsAverageCalculationServiceBase;
import hubble.backend.business.services.interfaces.services.kpis.InstantOperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.interfaces.services.kpis.OperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.models.Performance;
import hubble.backend.business.services.models.business.ApplicationIndicators;
import hubble.backend.storage.models.ApplicationStorage;

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

    public double calculateHealthIndexKPILastHour(ApplicationStorage applicationStorage);

    public double calculateHealthIndexKPILastMonth(ApplicationStorage application);
}
