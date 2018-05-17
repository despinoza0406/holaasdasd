package hubble.backend.providers.configurations.mappers.sitescope;

import hubble.backend.providers.models.alm.AlmDefectProviderModel;
import hubble.backend.storage.models.IssueStorage;
import org.modelmapper.PropertyMap;


//Hay que cambiarlo para que sea de SiteScope
public class IssuePropertyMap extends PropertyMap<AlmDefectProviderModel,IssueStorage> {

    @Override
    protected void configure() {

        map().setAssignee(source.getAssignee());
        map().setBusinessApplication(source.getBusinessApplication());
        map().setCorrectedOnRelease(source.getCorrectedOnRelease());
        map().setDescription(source.getDescription());
        map().setDetectedBy(source.getDetectedBy());
        map().setDetectedOnRelease(source.getDetectedOnRelease());
        map().setExternalId(source.getId());
        map().setProviderName(source.getProviderName());
        map().setProviderOrigin(source.getProviderOrigin());

        map().setStatus(source.getStatus());
        map().setTitle(source.getTitle());
        map().setTransaction(source.getTransaction());

    }

}
