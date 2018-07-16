package hubble.backend.providers.configurations;

import hubble.backend.storage.models.ALM;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class AlmConfigurationMongoImpl implements AlmConfiguration {

    @Autowired
    ProvidersRepository providersRepository;
    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public String getApplicationFieldName() {
        return providersRepository.alm().getConfiguration().getBusinessApplicationFieldName();
    }

    @Override
    public String getStatusFieldName() {
        return this.getConfiguration().getStatus().getStatus();
    }

    @Override
    public String getTransactionFieldName() {
        return providersRepository.alm().getConfiguration().getTransactionFieldName();
    }

    public String getProviderOrigin() {
        return this.getConfiguration().getProvider().getOrigin();
    }

    public String getProviderName() {
        return this.getConfiguration().getProvider().getName();
    }

    public Set<String> getStatusOpenValues() {
        return this.getConfiguration().getStatus().getOpenValues();
    }

    public HashMap<String,String> getApplicationValueToIdMap() {
        List<ApplicationStorage> applications = applicationRepository.findAll().stream().
                filter((a) -> a.isActive() &&
                        a.isEnabledTaskRunner() &&
                        a.getKpis().getDefects().getEnabled() &&
                        a.getKpis().getDefects().getAlm().isEnabled() &&
                        a.getKpis().getDefects().getAlm().isEnabledInTaskRunner()).collect(Collectors.toList());
        HashMap<String,String> mapApplications = new HashMap<>();
        for(ApplicationStorage application: applications){
            String hubbleName = application.getApplicationId();
            String almName = application.getKpis().getDefects().getAlm().getApplicationName();
            mapApplications.put(hubbleName,almName);
        }

        return mapApplications;
    }

    @Override
    public boolean taskEnabled() {
        return providersRepository.alm().isEnabled() && providersRepository.alm().getTaskRunner().isEnabled();
    }

    private ALM.Configuration getConfiguration(){
        return providersRepository.alm().getConfiguration();
    }
}
