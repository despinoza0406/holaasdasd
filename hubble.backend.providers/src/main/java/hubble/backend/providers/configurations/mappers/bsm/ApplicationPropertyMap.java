package hubble.backend.providers.configurations.mappers.bsm;

import hubble.backend.providers.models.bsm.BsmProviderModel;
import hubble.backend.storage.models.ApplicationStorage;
import org.modelmapper.PropertyMap;

public class ApplicationPropertyMap extends PropertyMap<BsmProviderModel, ApplicationStorage> {

    @Override
    protected void configure() {

        skip().setId(null);
        map().setApplicationId(source.getProfile_name());
        map().setApplicationName(source.getProfile_name());
        map().setActive(true);
    }
};
