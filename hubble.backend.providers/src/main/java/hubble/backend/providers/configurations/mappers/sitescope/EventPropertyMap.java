package hubble.backend.providers.configurations.mappers.sitescope;


import hubble.backend.providers.models.sitescope.SiteScopeEventProviderModel;
import hubble.backend.storage.models.EventStorage;
import org.modelmapper.PropertyMap;

import java.util.Date;


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
        map().setApplicationId(source.getApplicationId());
        map().setStatus(source.getStatus());
        map().setName(source.getName());
        map().setType(source.getType());

    }

}
