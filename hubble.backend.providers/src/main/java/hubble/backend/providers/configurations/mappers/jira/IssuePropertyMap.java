package hubble.backend.providers.configurations.mappers.jira;

import hubble.backend.core.enums.Providers;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.providers.models.jira.JiraIssueModel;
import hubble.backend.storage.models.IssueStorage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;

public class IssuePropertyMap extends PropertyMap<JiraIssueModel, IssueStorage> {

    @Override
    protected void configure() {

        String providerName = Providers.PROVIDERS_NAME.JIRA.toString();
        map().setTimestamp(DateHelper.getDateNow());
        map().setExternalId(source.getId());
        map().setAssignee(source.getFields().getAssignee().getName());
        map().setDescription(source.getFields().getDescription());
        map().setDetectedBy(source.getFields().getCreator().getName());
        map().setPriority(source.getFields().getPriority().getId());
        map().setStatus(source.getFields().getStatus().getName());
        map().setTitle(source.getFields().getSummary());
        map().setProviderOrigin(providerName);
        using(dateConverter).
                map(source.getFields().getResolutiondate()).setClosedDate(null);
        using(dateConverter).
                map(source.getFields().getCreated()).setRegisteredDate(null);
        using(dateConverter).
                map(source.getFields().getUpdated()).setModifiedDate(null);
        map().setBusinessApplication(source.getFields().getProject().getName());
        map().setBusinessApplicationId(source.getFields().getProject().getName());

        //TODO: Severity is not set by default.
        map().setSeverity(2);
    }

    Converter<String, Date> dateConverter = (MappingContext<String, Date> context) -> {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
        try {
            if (context.getSource() != null) {
                Date date = format.parse(context.getSource());
                return date;
            }
        } catch (ParseException e) {
            //Log
        }
        return new Date();
    };

}
