package hubble.backend.storage.operations;

import hubble.backend.storage.models.IssueStorage;
import java.util.Date;
import java.util.List;

public interface IssueOperations {

    boolean exist(IssueStorage issue);

    List<IssueStorage> findIssuesByApplicationIdBetweenDates(String applicationId, Date startDate, Date endDate);

    List<IssueStorage> findIssuesByApplicationIdAndDurationMinutes(int duration, String applicationId);

    List<IssueStorage> findIssuesByApplicationIdAndDurationMonths(int duration, String applicationId);

    List<IssueStorage> findIssuesByApplicationIdBetweenTimestampDates(String applicationId, Date yesterday, Date dateNow);
}
