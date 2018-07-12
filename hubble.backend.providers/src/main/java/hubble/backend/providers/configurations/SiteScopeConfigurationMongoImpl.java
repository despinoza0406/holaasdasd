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
                filter((a) -> a.isActive() &&
                        a.isEnabledTaskRunner() &&
                        a.getKpis().getEvents().getEnabled() &&
                        a.getKpis().getEvents().getSiteScope().isEnabled() &&
                        a.getKpis().getEvents().getSiteScope().isEnabledInTaskRunner()).collect(Collectors.toList());
        HashMap<String,String> mapApplications = new HashMap<>();
        for(ApplicationStorage application: applications){
            String hubbleName = application.getApplicationName();
            String siteScopeName = application.getKpis().getEvents().getSiteScope().getApplicationName();
            mapApplications.put(hubbleName,siteScopeName);
        }

        return mapApplications;
    }

    public boolean taskEnabled(){ return providersRepository.siteScope().isEnabled() && providersRepository.siteScope().getTaskRunner().isEnabled();}

    /*private SiteScope.Configuration getConfiguration(){
        return providersRepository.siteScope().getConfiguration();
    }*/
}
