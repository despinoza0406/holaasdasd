package hubble.backend.providers.configurations;


import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.SiteScope;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SiteScopeConfigurationMongoImpl //implements SiteScopeConfiguration
{

    @Autowired
    ProvidersRepository providersRepository;
    @Autowired
    ApplicationRepository applicationRepository;

   // @Override
    //public String getApplicationFieldName() {
    //    return this.getConfiguration().getBusinessApplicationFieldName();
    //}

    //@Override
    public HashMap<String, String> getApplicationValueToIdMap() {
        List<ApplicationStorage> applications = applicationRepository.findAll().stream().
                filter((a) -> false || a.isActive()).collect(Collectors.toList());
        HashMap<String,String> mapApplications = new HashMap<>();
        for(ApplicationStorage application: applications){
            String hubbleName = application.getId();
            String siteScopeName = "";
            mapApplications.put(hubbleName,siteScopeName);
        }

        return mapApplications;
    }

    /*private SiteScope.Configuration getConfiguration(){
        return providersRepository.siteScope().getConfiguration();
    }*/
}
