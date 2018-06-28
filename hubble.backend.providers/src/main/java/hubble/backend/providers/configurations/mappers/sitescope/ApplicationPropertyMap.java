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
        map().setApplicationId(source.getApplicationId());
        map().setApplicationName(source.getName());
    }
}
