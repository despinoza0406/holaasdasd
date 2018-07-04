package hubble.backend.storage.operations;

import hubble.backend.storage.models.AvailabilityStorage;
import java.util.Date;
import java.util.List;

public interface AvailabilityOperations {

    List<AvailabilityStorage> findAvailabilitiesByDurationMinutes(int duration);

    List<AvailabilityStorage> findAvailabilitiesByDurationMonths(int duration);

    boolean exist(AvailabilityStorage availability);

    List<AvailabilityStorage> findAvailabilitiesByApplicationId(String applicationId);

    List<AvailabilityStorage> findAvailabilitiesByApplicationIdAndDurationMinutes(int duration, String applicationId);

    List<AvailabilityStorage> findAvailabilitiesByApplicationIdAndDurationMonths(int duration, String applicationId);

    List<AvailabilityStorage> findAvailabilitiesBydAndPeriod(Date startDate, Date endDate);

    List<AvailabilityStorage> findAvailabilitiesByTransactionId(String transactionId);

    List<AvailabilityStorage> findAvailabilitiesByTransactionIdAndDurationMinutes(int duration, String transactionId);

    List<AvailabilityStorage> findAvailabilitiesByTransactionIdAndDurationMonths(int duration, String transactionId);

    List<AvailabilityStorage> findAvailabilitiesByTransactionIdAndPeriod(String transactionId, Date startDate, Date endDate);
    
    List<AvailabilityStorage> findAvailabilitiesByApplicationIdAndPeriod(String applicationId, Date startDate, Date endDate);


}
