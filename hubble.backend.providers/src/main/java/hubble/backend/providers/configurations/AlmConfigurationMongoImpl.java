package hubble.backend.providers.configurations;

import hubble.backend.storage.models.ALM;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return providersRepository.alm().getName();
    }

    @Override
    public String getStatusFieldName() {
        return this.getConfiguration().getStatus().getStatus();
    }

    @Override
    public String getTransactionFieldName() {
        return providersRepository.alm().getName();
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
                filter((a) -> false || a.isActive()).collect(Collectors.toList());
        HashMap<String,String> mapApplications = new HashMap<>();
        for(ApplicationStorage application: applications){
            String hubbleName = application.getId();
            String almName = "";
            mapApplications.put(hubbleName,almName);
        }

        return mapApplications;
    }

    private ALM.Configuration getConfiguration(){
        return providersRepository.alm().getConfiguration();
    }
}
