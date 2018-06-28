package hubble.backend.providers.configurations;

import hubble.backend.storage.models.ALM;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class AlmConfigurationMongoImpl implements AlmConfiguration {

    @Autowired
    ProvidersRepository providersRepository;

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

    public String getApplicationValueToIdMap() {
        return  null;//providersRepository.alm().get;
    }

    private ALM.Configuration getConfiguration(){
        return providersRepository.alm().getConfiguration();
    }
}
