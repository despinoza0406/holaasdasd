package hubble.backend.providers.configurations;


import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.SiteScope;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class SiteScopeConfigurationMongoImpl implements SiteScopeConfiguration
{

    @Autowired
    ProvidersRepository providersRepository;
    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public String getApplicationFieldName() {
        return this.getConfiguration().getBusinessApplicationFieldName();
    }

    @Override
    public HashMap<String, String> getApplicationValueToIdMap() {
        List<ApplicationStorage> applications = applicationRepository.findAll().stream().
                filter((a) ->
                        a.isEnabledTaskRunner() &&
                        a.getKpis().getEvents().isEnabled()&&
                        a.getKpis().getEvents().getSiteScope().isEnabledInTaskRunner()).collect(Collectors.toList());
        HashMap<String,String> mapApplications = new HashMap<>();
        for(ApplicationStorage application: applications){
            String hubbleId = application.getApplicationId();
            String siteScopeName = application.getKpis().getEvents().getSiteScope().getApplicationName();
            mapApplications.put(hubbleId,siteScopeName);
        }

        return mapApplications;
    }

    public String getProviderName(){
        return providersRepository.siteScope().getName();
    }

    public boolean taskEnabled(){ return providersRepository.siteScope().isEnabled() && providersRepository.siteScope().getTaskRunner().isEnabled();}

    private SiteScope.Configuration getConfiguration(){
        return providersRepository.siteScope().getConfiguration();
    }
}
