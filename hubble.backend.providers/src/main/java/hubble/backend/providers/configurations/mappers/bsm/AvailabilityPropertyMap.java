package hubble.backend.providers.configurations.mappers.bsm;

import hubble.backend.core.enums.Providers;
import hubble.backend.core.enums.Status.STATUS_NAMES;
import hubble.backend.providers.models.bsm.BsmProviderModel;
import hubble.backend.storage.models.AvailabilityStorage;
import java.util.Date;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;

public class AvailabilityPropertyMap extends PropertyMap<BsmProviderModel, AvailabilityStorage> {

    final String FAIL = "Fail";
    final String PASSED = "Pass";

    @Override
    protected void configure() {

        String providerOrigin = Providers.PROVIDERS_NAME.BSM.toString();

        skip().setId(null);

        skip().setTransactionId(null);
        skip().setLocationId(null);
        skip().setServerName(null);
        skip().setPerformanceStatus(null);
        skip().setAvailabilityFailIfAbove(0);
        skip().setErrors(null);

        map().setApplicationId(source.getApplicationId());
        map().setApplicationName(source.getProfile_name());
        map().setTransactionName(source.getSztransactionname());
        map().setLocationName(source.getSzlocationname());
        map().setResponseTime(source.getDresponsetime());
        map().setScriptName(source.getSzscriptname());
        map().setNumberOfErrors(source.getIcomponenterrorcount());

        map().setProviderName(source.getProviderName());
        map().setProviderOrigin(providerOrigin);
        using(timeStampConverter).map(source.getTime_stamp()).setTimeStamp(null);
        using(statusConverter).map(source.getSzstatusname()).setAvailabilityStatus("");
    }
    Converter<Long, Date> timeStampConverter = (MappingContext<Long, Date> context) -> {
        return new Date((long) context.getSource() * 1000);
    };

    Converter<String, String> statusConverter = (MappingContext<String, String> context) -> {
        String status = context.getSource();
        if (FAIL.equals(status)) {
            status = STATUS_NAMES.FAILED.toString();
        } else if (PASSED.equals(status)) {
            status = STATUS_NAMES.SUCCESS.toString();
        }
        return status;
    };
};
