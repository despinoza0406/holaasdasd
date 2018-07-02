package hubble.backend.providers.configurations.mappers.ppm;

import hubble.backend.providers.models.ppm.PpmApplicationProviderModel;
import hubble.backend.storage.models.ApplicationStorage;
import org.modelmapper.PropertyMap;

public class ApplicationPropertyMap extends PropertyMap<PpmApplicationProviderModel, ApplicationStorage>{
    
    @Override
    protected void configure() {
        skip().setId(null);
        skip().setActive(true);
        skip().setApplicationConfigurationVersion(0);
        map().setApplicationId(source.getApplicationId());
        map().setApplicationName(source.getName());
    }
}
