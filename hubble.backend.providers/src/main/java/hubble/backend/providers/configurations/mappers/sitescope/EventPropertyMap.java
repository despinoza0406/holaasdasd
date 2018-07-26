package hubble.backend.providers.configurations.mappers.sitescope;


import hubble.backend.providers.models.sitescope.SiteScopeEventProviderModel;
import hubble.backend.storage.models.EventStorage;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


//Hay que cambiarlo para que sea de SiteScope
public class EventPropertyMap extends PropertyMap<SiteScopeEventProviderModel, EventStorage> {

    @Override
    protected void configure() {

        skip().setId(null);
        map().setSummary(source.getSummary());
        map().setBusinessApplication(source.getBusinessApplication());
        map().setUpdatedDate(source.getUpdatedDate());
        map().setDescription(source.getDescription());
        map().setProviderName(source.getProviderName());
        map().setProviderOrigin(source.getProviderOrigin());
        map().setBusinessApplicationId(source.getApplicationId());
        map().setStatus(source.getStatus());
        map().setName(source.getName());
        map().setType(source.getType());

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
