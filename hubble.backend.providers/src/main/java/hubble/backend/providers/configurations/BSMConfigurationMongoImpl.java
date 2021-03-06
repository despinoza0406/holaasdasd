package hubble.backend.providers.configurations;

import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.BSM;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BSMConfigurationMongoImpl implements BSMConfiguration {

    @Autowired
    ProvidersRepository providersRepository;
    @Autowired
    ApplicationRepository applicationRepository;

    public boolean taskEnabled() {
        return providersRepository.bsm().isEnabled() && providersRepository.bsm().getTaskRunner().isEnabled();
    }

    @Override
    public String getApplicationFieldName(){
        return providersRepository.bsm().getConfiguration().getBusinessApplicationFieldName();
    }


    @Override
    public String getProviderName(){
        return providersRepository.bsm().getName();
    }

    @Override
    public HashMap<String,String> getApplicationValueToIdMap() {
        List<ApplicationStorage> applications = applicationRepository.findAll().stream().
                filter((a) ->
                        a.isEnabledTaskRunner() &&
                        (a.getKpis().getAvailability().isEnabled()|| a.getKpis().getPerformance().isEnabled()) &&
                        (a.getKpis().getAvailability().getBsm().isEnabledInTaskRunner() || a.getKpis().getPerformance().getBsm().isEnabledInTaskRunner())).collect(Collectors.toList());
        HashMap<String,String> mapApplications = new HashMap<>();
        for(ApplicationStorage application: applications){
            String hubbleName = application.getApplicationId();
            String ppmName = application.getKpis().getAvailability().getBsm().getApplicationName();
            mapApplications.put(hubbleName,ppmName);
        }

        return mapApplications;

    }



    //private BSM.Configuration getConfiguration(){ return providersRepository.bsm().getConfiguration(); }
}
