package hubble.backend.providers.configurations;

import hubble.backend.storage.models.PPM;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PpmConfigurationMongoImpl implements PpmConfiguration{


    @Autowired
    ProvidersRepository providersRepository;

    @Override
    public String getApplicationFieldName() {
        return this.getConfiguration().getBusinessApplicationFieldName();
    }

    @Override
    public String getTransactionFieldName() {
        return this.getConfiguration().getTransactionFieldName();
    }

    @Override
    public String getProviderOrigin() {
        return this.getConfiguration().getProvider().getOrigin();
    }

    @Override
    public String getProviderName() {
        return this.getConfiguration().getProvider().getName();
    }

    @Override
    public Set<Integer> getRequestTypeIds() {
        return this.getConfiguration().getRequestTypeIds();
    }

    @Override
    public String getApplicationValueToIdMap() {
        return null;//applicationValueToIdMap;
    }

    private PPM.Configuration getConfiguration(){
        return providersRepository.ppm().getConfiguration();
    }
}
