package hubble.backend.storage.repositories;

import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.operations.EventOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static hubble.backend.core.utils.DateHelper.getDateNow;

public class EventRepositoryImpl implements EventOperations {

    @Autowired
    MongoOperations mongo;

    @Override
    public boolean exist(EventStorage issue) {
        Criteria isSameObjectId= Criteria.where("_id").is(issue.getId());
        Criteria isSameProviderName = Criteria.where("providerName").is(issue.getProviderName());

        List<EventStorage> issues = mongo.find(Query.query(isSameProviderName.andOperator(isSameObjectId)),
                                                EventStorage.class);

        return !issues.isEmpty();
    }
    public List<EventStorage> findEventsByApplicationIdAndDifferentStatusLastDay(String applicationId, String status){
        Criteria applicationIdCriteria = Criteria.where("businessApplicationId").is(applicationId);
        Criteria statusCriteria = Criteria.where("status").ne(status);
        Criteria startDateCriteria = Criteria.where("updatedDate").gte(getYesterday());
        Criteria endDateCriteria = Criteria.where("updatedDate").lte(getDateNow());

        List<EventStorage> events = mongo
                .find(Query.query(applicationIdCriteria
                                .andOperator(startDateCriteria, endDateCriteria))
                                .addCriteria(statusCriteria),
                        EventStorage.class);
        return events;
    }

    public List<EventStorage> findEventsByApplicationIdAndDifferentStatusPastDay(String applicationId, String status){
        Criteria applicationIdCriteria = Criteria.where("businessApplicationId").is(applicationId);
        Criteria statusCriteria = Criteria.where("status").ne(status);
        Criteria startDateCriteria = Criteria.where("updatedDate").gte(getBeforeYesterday());
        Criteria endDateCriteria = Criteria.where("updatedDate").lte(getYesterday());

        List<EventStorage> events = mongo
                .find(Query.query(applicationIdCriteria
                                .andOperator(startDateCriteria, endDateCriteria))
                                .addCriteria(statusCriteria),
                        EventStorage.class);
        return events;
    }

    @Override
    public List<EventStorage> findEventsByApplicationIdBetweenDates(String applicationId, Date startDate, Date endDate) {
        Criteria applicationIdCriteria = Criteria.where("businessApplicationId").is(applicationId);
        Criteria startDateCriteria = Criteria.where("registeredDate").gte(startDate);
        Criteria endDateCriteria = Criteria.where("registeredDate").lte(endDate);

        List<EventStorage> workItems = mongo
                .find(Query
                                .query(applicationIdCriteria.andOperator(
                                        startDateCriteria, endDateCriteria)),
                        EventStorage.class);
        return workItems;
    }

    @Override
    public List<EventStorage> findEventsByApplicationIdAndDurationMinutes(int duration, String applicationId) {

        Calendar from = CalendarHelper.getNow();
        from.add(Calendar.MINUTE, -duration);

        Criteria applicationIdCriteria = Criteria.where("businessApplicationId").is(applicationId);
        Criteria startDateCriteria = Criteria.where("registeredDate").gte(from);

        List<EventStorage> issues = mongo
                .find(Query
                                .query(applicationIdCriteria.andOperator(
                                        startDateCriteria)),
                        EventStorage.class);
        return issues;

    }

    @Override
    public List<EventStorage> findEventsByApplicationIdAndDurationMonths(int duration, String applicationId) {
        Calendar from = CalendarHelper.getNow();
        from.add(Calendar.MONTH, -duration);

        Criteria applicationIdCriteria = Criteria.where("businessApplicationId").is(applicationId);
        Criteria startDateCriteria = Criteria.where("registeredDate").gte(from);

        List<EventStorage> issues = mongo
                .find(Query
                                .query(applicationIdCriteria.andOperator(
                                        startDateCriteria)),
                        EventStorage.class);
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

    private Date getBeforeYesterday(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        return cal.getTime();
    }



}
