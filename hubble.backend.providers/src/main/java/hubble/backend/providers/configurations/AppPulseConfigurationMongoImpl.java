package hubble.backend.providers.configurations;

import hubble.backend.storage.models.AppPulse;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppPulseConfigurationMongoImpl implements AppPulseConfiguration {

    @Autowired
    ProvidersRepository providersRepository;
    @Autowired
    ApplicationRepository applicationRepository;

    public  boolean taskEnabled(){
        return providersRepository.appPulse().getTaskRunner().isEnabled();
    }
    public HashMap<String,String> getApplicationValueToIdMap(){
        List<ApplicationStorage> applications = applicationRepository.findAll().stream().
                filter((a) ->
                        a.isEnabledTaskRunner() &&
                                (a.getKpis().getAvailability().getEnabled() || a.getKpis().getPerformance().getEnabled()) &&
                                (a.getKpis().getAvailability().getAppPulse().isEnabledInTaskRunner() || a.getKpis().getPerformance().getAppPulse().isEnabledInTaskRunner())).collect(Collectors.toList());
        HashMap<String,String> mapApplications = new HashMap<>();
        for(ApplicationStorage application: applications){
            String hubbleName = application.getApplicationId();
            String appPulseName = application.getKpis().getAvailability().getAppPulse().getApplicationName();
            mapApplications.put(hubbleName,appPulseName);
        }

        return mapApplications;
    }

    private AppPulse.Configuration getConfiguration(){
        return providersRepository.appPulse().getConfiguration();
    }
}
