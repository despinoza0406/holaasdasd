package hubble.backend.providers.configurations.mappers.alm;

import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.providers.models.alm.AlmDefectProviderModel;
import hubble.backend.storage.models.IssueStorage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.apache.commons.lang.StringUtils.EMPTY;
import org.modelmapper.AbstractConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlmDefectToIssueConverter extends AbstractConverter<AlmDefectProviderModel, IssueStorage> {

    private final Logger logger = LoggerFactory.getLogger(AlmDefectToIssueConverter.class);

    @Override
    protected IssueStorage convert(AlmDefectProviderModel source) {
        IssueStorage issue = new IssueStorage();
        issue.setTimestamp(DateHelper.getDateNow());
        issue.setClosedDate(stringToDate(source.getClosedDate(), "yyyy-MM-dd"));
        issue.setModifiedDate(stringToDate(source.getModifiedDate(), "yyyy-MM-dd"));
        issue.setPriority(selectorToInteger(source.getPriority()));
        issue.setRegisteredDate(stringToDate(source.getRegisteredDate(), "yyyy-MM-dd"));
        issue.setReproducible(reproducibleToBoolean(source.getReproducible()));
        issue.setSeverity(selectorToInteger(source.getSeverity()));
        issue.setBusinessApplicationId(source.getApplicationId());
        issue.setExternalId(source.getId());
        issue.setAssignee(source.getAssignee());
        issue.setBusinessApplication(source.getBusinessApplication());
        issue.setCorrectedOnRelease(source.getCorrectedOnRelease());
        issue.setDescription(source.getDescription());
        issue.setDetectedBy(source.getDetectedBy());
        issue.setDetectedOnRelease(source.getDetectedOnRelease());
        issue.setExternalId(source.getId());
        issue.setProviderName(source.getProviderName());
        issue.setProviderOrigin(source.getProviderOrigin());
        issue.setStatus(source.getStatus());
        issue.setTitle(source.getTitle());
        issue.setTransaction(source.getTransaction());

        return issue;
    }

    private Boolean reproducibleToBoolean(String isReproducible) {
        if (isReproducible == null) {
            return false;
        }
        return isReproducible.equals("Y");
    }

    private int selectorToInteger(String comboBoxSelectedValue) {
        if (!isValid(comboBoxSelectedValue)) {
            return 0;
        }
        String subString = comboBoxSelectedValue.split("-")[0].split(" ")[0];
        return Integer.valueOf(subString);
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

    private boolean isValid(String stringToValidate) {
        return !(stringToValidate == null || stringToValidate.equals(EMPTY));
    }
}
