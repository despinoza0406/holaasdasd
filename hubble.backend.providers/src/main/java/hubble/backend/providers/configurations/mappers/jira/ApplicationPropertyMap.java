package hubble.backend.providers.configurations.mappers.jira;

import org.modelmapper.PropertyMap;
import hubble.backend.providers.models.jira.JiraApplicationProviderModel;
import hubble.backend.storage.models.ApplicationStorage;

public class ApplicationPropertyMap extends PropertyMap<JiraApplicationProviderModel, ApplicationStorage> {

    @Override
    protected void configure() {
        skip().setId(null);
        skip().setActive(true);
        skip().setApplicationConfigurationVersion(0);
        map().setApplicationId(source.getApplicationId());
        map().setApplicationName(source.getName());
    }

}

