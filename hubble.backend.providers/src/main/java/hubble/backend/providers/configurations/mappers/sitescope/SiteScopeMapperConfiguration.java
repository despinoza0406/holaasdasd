package hubble.backend.providers.configurations.mappers.sitescope;

import hubble.backend.providers.configurations.mappers.alm.ApplicationPropertyMap;
import hubble.backend.providers.configurations.mappers.alm.IssuePropertyMap;
import hubble.backend.providers.models.alm.AlmApplicationProviderModel;
import hubble.backend.providers.models.alm.AlmDefectProviderModel;
import hubble.backend.providers.models.sitescope.SiteScopeApplicationProviderModel;
import hubble.backend.providers.models.sitescope.SiteScopeDefectProviderModel;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.IssueStorage;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

//Falta cambiar todo a lo que sea que tenga que convertir de SiteScope

public class SiteScopeMapperConfiguration {

    private ModelMapper mapper;

    public SiteScopeMapperConfiguration(){
        mapper = new ModelMapper();
        mapper.addMappings(new IssuePropertyMap());
        mapper.addMappings(new ApplicationPropertyMap());
       // mapper.addConverter(new SiteScopeDefectToIssueConverter());
    }

    public ModelMapper getMapper() {
        return mapper;
    }

    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public IssueStorage maptoIssueStorage(SiteScopeDefectProviderModel siteScopeProviderModel){
        if(siteScopeProviderModel==null)
            return null;
        return mapper.map(siteScopeProviderModel, IssueStorage.class);
    }

    public List<IssueStorage> maptoIssueStorageList(List<SiteScopeDefectProviderModel> siteScopeProviderModel){
        if(siteScopeProviderModel==null)
            return null;
        Type typeList = new TypeToken<List<IssueStorage>>() {
        }.getType();
        return mapper.map(siteScopeProviderModel, typeList);
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
