package hubble.backend.storage.operations;

import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.storage.models.EventStorage;

import java.util.Date;
import java.util.List;

public interface EventOperations {

    boolean exist(EventStorage issue);

    List<EventStorage> findEventsByApplicationIdAndDifferentStatusLastDay(String applicationId, String status);

    List<EventStorage> findEventsByApplicationIdAndDifferentStatusPastDay(String applicationId, String status);

    List<EventStorage> findEventsByApplicationIdAndDifferentStatusLastHour(String applicationId, String status);

    List<EventStorage> findEventsByApplicationIdAndDifferentStatusPastHour(String applicationId, String status);

    List<EventStorage> findEventsByApplicationIdBetweenDates(String applicationId, Date startDate, Date endDate);

    List<EventStorage> findEventsByApplicationIdAndDurationMinutes(int duration, String applicationId);

    List<EventStorage> findEventsByApplicationIdAndDurationMonths(int duration, String applicationId);
}
