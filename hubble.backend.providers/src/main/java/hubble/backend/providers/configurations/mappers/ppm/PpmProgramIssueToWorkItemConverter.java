package hubble.backend.providers.configurations.mappers.ppm;

import hubble.backend.providers.models.ppm.PpmProgramIssueProviderModel;
import hubble.backend.storage.models.WorkItemStorage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.modelmapper.AbstractConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PpmProgramIssueToWorkItemConverter extends AbstractConverter<PpmProgramIssueProviderModel, WorkItemStorage> {

    private final Logger logger = LoggerFactory.getLogger(PpmProgramIssueToWorkItemConverter.class);

    @Override
    protected WorkItemStorage convert(PpmProgramIssueProviderModel source) {
        WorkItemStorage workItem = new WorkItemStorage();

        workItem.setExternalId(source.getId());
        workItem.setTitle(source.getDescription());
        workItem.setDescription(source.getDetailedDescription());
        workItem.setStatus(source.getStatusCode());
        workItem.setAssignee(source.getAssignee());
        workItem.setRegisteredBy(source.getCreatedBy());
        workItem.setProviderOrigin(source.getProviderOrigin());
        workItem.setProviderName(source.getProviderName());
        workItem.setBusinessApplication(source.getBusinessApplication());
        workItem.setBusinessApplicationId(source.getApplicationId());
        workItem.setTransaction(source.getTransaction());
        workItem.setTransactionId(source.getTransactionId());
        workItem.setPercentCompleted(source.getPercentComplete());
        workItem.setPriority(priorityResolver(source.getPriority()));
        workItem.setRegisteredDate(stringToDate(source.getCreatedDate(), "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
        workItem.setModifiedDate(stringToDate(source.getLastUpdateDate(), "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
        workItem.setDueDate(stringToDate(source.getDueDate(), "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
        Date now = getDateNow();
        long millisNow = now.getTime();
        if(workItem.getDueDate() != null) {
            long millisDueDate = workItem.getDueDate().getTime();
            long diffMillis = millisNow - millisDueDate;
            long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
            if (diffDays > 0) {
                workItem.setDeflectionDays(diffDays);
            } else {
                workItem.setDeflectionDays(0);
            }
        }
        return workItem;
    }

    private int priorityResolver(String priority) {
        switch (priority) {
            case "Bajo/a":
                return 4;
            case "Normal":
                return 3;
            case "Alto/a":
                return 2;
            case "CrÃ­tico/a(s)":
                return 1;
            default:
                return 0;
        }
    }

    private Date stringToDate(String dateFormatString, String format) {
        if (!isValid(dateFormatString)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date dateToReturn = new Date();
        try {
            dateToReturn = formatter.parse(dateFormatString);
        } catch (ParseException ex) {
            logger.error(ex.getMessage());
        }
        return dateToReturn;
    }

    private Date getDateNow() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    private boolean isValid(String stringToValidate) {
        if (stringToValidate == null || stringToValidate.equals("")) {
            return false;
        }
        return true;
    }

}
