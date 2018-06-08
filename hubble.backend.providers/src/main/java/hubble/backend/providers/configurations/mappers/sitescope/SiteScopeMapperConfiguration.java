package hubble.backend.providers.configurations.mappers.sitescope;

import hubble.backend.providers.configurations.mappers.alm.ApplicationPropertyMap;
import hubble.backend.providers.configurations.mappers.alm.IssuePropertyMap;
import hubble.backend.providers.models.sitescope.SiteScopeApplicationProviderModel;
import hubble.backend.providers.models.sitescope.SiteScopeEventProviderModel;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.EventStorage;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

//Falta cambiar todo a lo que sea que tenga que convertir de SiteScope
@Component
public class SiteScopeMapperConfiguration {

    private ModelMapper mapper;

    public SiteScopeMapperConfiguration(){
        mapper = new ModelMapper();
        mapper.addMappings(new EventPropertyMap());
    }

    public ModelMapper getMapper() {
        return mapper;
    }

    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public EventStorage mapToEventStorage(SiteScopeEventProviderModel siteScopeProviderModel){
        if(siteScopeProviderModel==null)
            return null;
        return mapper.map(siteScopeProviderModel, EventStorage.class);
    }

    public List<EventStorage> mapToEventStorageList(List<SiteScopeEventProviderModel> siteScopeProviderModel){
        if(siteScopeProviderModel==null)
            return null;
        Type typeList = new TypeToken<List<EventStorage>>() {
        }.getType();
        return mapper.map(siteScopeProviderModel, typeList);   //Depende a que se mappea
    }

    public ApplicationStorage mapToApplicationStorage(SiteScopeApplicationProviderModel siteScopeApplication){
        if(siteScopeApplication==null)
            return null;
        return mapper.map(siteScopeApplication, ApplicationStorage.class);
    }

    public List<ApplicationStorage> mapToApplicationStorageList(List<SiteScopeApplicationProviderModel> siteScopeApplication){
        if(siteScopeApplication==null)
            return null;
        Type typeList = new TypeToken<List<ApplicationStorage>>() {
        }.getType();
        return mapper.map(siteScopeApplication, typeList);
    }
}
