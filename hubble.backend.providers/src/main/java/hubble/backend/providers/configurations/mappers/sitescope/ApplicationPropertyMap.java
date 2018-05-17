package hubble.backend.providers.configurations.mappers.sitescope;

import hubble.backend.providers.models.sitescope.SiteScopeApplicationProviderModel;
import hubble.backend.storage.models.ApplicationStorage;
import org.modelmapper.PropertyMap;

public class ApplicationPropertyMap extends PropertyMap <SiteScopeApplicationProviderModel, ApplicationStorage> {

    @Override
    protected void configure() {
        skip().setId(null);
        skip().setActive(true);
        skip().setApplicationConfigurationVersion(0);
        skip().setAvailabilityThreshold(0);
        skip().setCriticalThreshold(0);
        skip().setLocations(null);
        skip().setOkThreshold(0);
        skip().setOutlierThreshold(0);
        skip().setTimeZoneId("");
        skip().setTransactions(null);
        map().setApplicationId(source.getApplicationId());
        map().setApplicationName(source.getName());
    }
}
