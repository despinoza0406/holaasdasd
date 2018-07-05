package hubble.backend.storage.repositories;

import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.storage.models.IssueStorage;
import hubble.backend.storage.operations.IssueOperations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class IssueRepositoryImpl implements IssueOperations {

    @Autowired
    MongoOperations mongo;

    @Override
    public boolean exist(IssueStorage issue) {
        Criteria isSameIssueExternalId = Criteria.where("externalId").is(issue.getExternalId());
        Criteria isSameProviderName = Criteria.where("providerName").is(issue.getProviderName());

        List<IssueStorage> issues = mongo
                .find(Query
                        .query(isSameIssueExternalId.andOperator(isSameProviderName)),
                        IssueStorage.class);

        return !issues.isEmpty();
    }

    @Override
    public List<IssueStorage> findIssuesByApplicationIdBetweenDates(String applicationId, Date startDate, Date endDate) {
        Criteria applicationIdCriteria = Criteria.where("businessApplicationId").is(applicationId);
        Criteria startDateCriteria = Criteria.where("registeredDate").gte(startDate);
        Criteria endDateCriteria = Criteria.where("registeredDate").lte(endDate);

        List<IssueStorage> issues = mongo
                .find(Query
                        .query(applicationIdCriteria.andOperator(
                                startDateCriteria, endDateCriteria)),
                        IssueStorage.class);
        return issues;
    }

    @Override
    public List<IssueStorage> findIssuesByApplicationIdAndDurationMinutes(int duration, String applicationId) {

        Calendar from = CalendarHelper.getNow();
        from.add(Calendar.MINUTE, -duration);

        Criteria applicationIdCriteria = Criteria.where("businessApplicationId").is(applicationId);
        Criteria startDateCriteria = Criteria.where("registeredDate").gte(from);

        List<IssueStorage> issues = mongo
                .find(Query
                        .query(applicationIdCriteria.andOperator(
                                startDateCriteria)),
                        IssueStorage.class);
        return issues;

    }

    @Override
    public List<IssueStorage> findIssuesByApplicationIdAndDurationMonths(int duration, String applicationId) {
        Calendar from = CalendarHelper.getNow();
        from.add(Calendar.MONTH, -duration);

        Criteria applicationIdCriteria = Criteria.where("businessApplicationId").is(applicationId);
        Criteria startDateCriteria = Criteria.where("registeredDate").gte(from);

        List<IssueStorage> issues = mongo
                .find(Query
                        .query(applicationIdCriteria.andOperator(
                                startDateCriteria)),
                        IssueStorage.class);
        return issues;
    }

    @Override
    public List<IssueStorage> findIssuesByApplicationIdBetweenTimestampDates(String applicationId, Date yesterday, Date dateNow) {
        Criteria applicationIdCriteria = Criteria.where("businessApplicationId").is(applicationId);
        Criteria startDateCriteria = Criteria.where("timestamp").gte(yesterday);
        Criteria endDateCriteria = Criteria.where("timestamp").lte(dateNow);
        List<String> statuses = new ArrayList<>();
        statuses.add("Nuevo");
        statuses.add("Por hacer");
        Criteria status = Criteria.where("status").in(statuses);

        List<IssueStorage> issues = mongo
                .find(Query.query(applicationIdCriteria.andOperator(
                                        startDateCriteria, endDateCriteria))
                        .addCriteria(status),
                        IssueStorage.class);
        return issues;
    }
}
