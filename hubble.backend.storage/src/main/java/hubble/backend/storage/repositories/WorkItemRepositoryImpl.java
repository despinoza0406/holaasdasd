package hubble.backend.storage.repositories;

import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.storage.models.WorkItemStorage;
import hubble.backend.storage.operations.WorkItemOperations;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class WorkItemRepositoryImpl implements WorkItemOperations {

    @Autowired
    MongoOperations mongo;

    @Override
    public boolean exist(WorkItemStorage workItem) {
        Criteria isSameIssueExternalId = Criteria.where("externalId").is(workItem.getExternalId());
        Criteria isSameProviderName = Criteria.where("providerName").is(workItem.getProviderName());

        List<WorkItemStorage> workItems = mongo
                .find(Query
                        .query(isSameIssueExternalId.andOperator(isSameProviderName)),
                        WorkItemStorage.class);

        return !workItems.isEmpty();
    }

    @Override
    public List<WorkItemStorage> findWorkItemsByApplicationIdBetweenDates(String applicationId, Date startDate, Date endDate) {
        Criteria applicationIdCriteria = Criteria.where("businessApplicationId").is(applicationId);
        Criteria startDateCriteria = Criteria.where("registeredDate").gte(startDate);
        Criteria endDateCriteria = Criteria.where("registeredDate").lte(endDate);

        List<WorkItemStorage> workItems = mongo
                .find(Query
                        .query(applicationIdCriteria.andOperator(
                                startDateCriteria, endDateCriteria)),
                        WorkItemStorage.class);
        return workItems;
    }

    @Override
    public List<WorkItemStorage> findWorkItemsByApplicationIdAndStatusLastDay(String applicationId, String status){
        Criteria applicationIdCriteria = Criteria.where("businessApplicationId").is(applicationId);
        Criteria statusCriteria = Criteria.where("status").is(status);
        Criteria deflectionCriteria = Criteria.where("deflectionDays").gt(0);
        Date now = getDateNow();
        Criteria startDateCriteria = Criteria.where("timestamp").gte(getYesterday());
        Criteria endDateCriteria = Criteria.where("timestamp").lte(getDateNow());

        List<WorkItemStorage> workItems = mongo
                .find(Query
                                .query(applicationIdCriteria
                                        .andOperator(startDateCriteria, endDateCriteria)
                                ),
                        WorkItemStorage.class);
        return workItems;
    }

    @Override
    public List<WorkItemStorage> findWorkItemsByApplicationIdAndDurationMinutes(int duration, String applicationId) {

        Calendar from = CalendarHelper.getNow();
        from.add(Calendar.MINUTE, -duration);

        Criteria applicationIdCriteria = Criteria.where("businessApplicationId").is(applicationId);
        Criteria startDateCriteria = Criteria.where("registeredDate").gte(from);

        List<WorkItemStorage> issues = mongo
                .find(Query
                        .query(applicationIdCriteria.andOperator(
                                startDateCriteria)),
                        WorkItemStorage.class);
        return issues;

    }

    @Override
    public List<WorkItemStorage> findWorkItemsByApplicationIdAndDurationMonths(int duration, String applicationId) {
        Calendar from = CalendarHelper.getNow();
        from.add(Calendar.MONTH, -duration);

        Criteria applicationIdCriteria = Criteria.where("businessApplicationId").is(applicationId);
        Criteria startDateCriteria = Criteria.where("registeredDate").gte(from);

        List<WorkItemStorage> issues = mongo
                .find(Query
                        .query(applicationIdCriteria.andOperator(
                                startDateCriteria)),
                        WorkItemStorage.class);
        return issues;
    }

    private Date getDateNow() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    private Date getYesterday(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}
