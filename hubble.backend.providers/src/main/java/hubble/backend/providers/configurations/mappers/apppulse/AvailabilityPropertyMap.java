package hubble.backend.providers.configurations.mappers.apppulse;

import hubble.backend.core.enums.Providers;
import hubble.backend.providers.models.apppulse.AvailabilityDataProviderModel;
import hubble.backend.storage.models.AvailabilityStorage;
import java.util.Date;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;

public class AvailabilityPropertyMap extends PropertyMap<AvailabilityDataProviderModel, AvailabilityStorage> {

    @Override
    protected void configure() {

        String providerOrigin = Providers.PROVIDERS_NAME.APP_PULSE.toString();

        skip().setId(null);
        map().setProviderOrigin(providerOrigin);
        map().setProviderName(providerOrigin);
        using(timeStampConverter).map(source.getTimeStamp()).setTimeStamp(null);
    }

    Converter<Long, Date> timeStampConverter = (MappingContext<Long, Date> context) -> {
        return new Date((long) context.getSource() * 1000);//Epoch in milliseconds.
    };
};
