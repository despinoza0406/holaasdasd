package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.interfaces.services.averages.OperationsAverageCalculationServiceBase;
import hubble.backend.business.services.interfaces.services.kpis.InstantOperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.interfaces.services.kpis.OperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.models.Availability;
import hubble.backend.business.services.models.business.ApplicationIndicators;
import hubble.backend.storage.models.ApplicationStorage;

import java.util.List;

public interface AvailabilityService extends
        OperationsAverageCalculationServiceBase<ApplicationIndicators>,
        OperationsKeyPerformanceIndicatorServiceBase<ApplicationIndicators>,
        InstantOperationsKeyPerformanceIndicatorServiceBase<ApplicationIndicators>,
        ApplicationServiceBase<Availability> {

    public List<Availability> getAll();

    public Availability get(String id);

    public List<Availability> getLast10Minutes(String applicationId);

    public List<Availability> getLastHour(String applicationId);

    public List<Integer> getDistValuesLastHour(String applicationId);

    public double calculateHealthIndexKPILastHour(ApplicationStorage application);

}
