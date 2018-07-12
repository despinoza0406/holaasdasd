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
    public HashMap<String,String> getApplicationValueToIdMap() {
        List<ApplicationStorage> applications = applicationRepository.findAll().stream().
                filter((a) ->a.isActive() &&
                        a.isEnabledTaskRunner() &&
                        (a.getKpis().getAvailability().getEnabled() || a.getKpis().getPerformance().getEnabled()) &&
                        (a.getKpis().getAvailability().getBsm().isEnabled() || a.getKpis().getPerformance().getBsm().isEnabled() )&&
                        (a.getKpis().getAvailability().getBsm().isEnabledInTaskRunner() || a.getKpis().getPerformance().getBsm().isEnabledInTaskRunner())).collect(Collectors.toList());
        HashMap<String,String> mapApplications = new HashMap<>();
        for(ApplicationStorage application: applications){
            String hubbleName = application.getApplicationName();
            String ppmName = application.getKpis().getAvailability().getBsm().getApplicationName();
            mapApplications.put(hubbleName,ppmName);
        }

        return mapApplications;

    }



    //private BSM.Configuration getConfiguration(){ return providersRepository.bsm().getConfiguration(); }
}
