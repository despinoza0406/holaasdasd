package hubble.backend.providers.configurations.environments;

import hubble.backend.storage.models.BSM;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class BsmProviderEnvironmentMongoImpl implements BsmProviderEnvironment {

    @Autowired
    ProvidersRepository providersRepository;

    @Override
    public String getSoapEndpointUrl() {
        return this.getEnviroment().getSoap().getEndpoint();
    }

    @Override
    public String getSoapAction() {
        return this.getEnviroment().getSoap().getAction();
    }

    @Override
    public String getUserName() {
        return this.getEnviroment().getUsername();
    }

    @Override
    public String getPassword() {
        return this.getEnviroment().getPassword();
    }

    private BSM.Environment getEnviroment(){
        return providersRepository.bsm().getEnvironment();
    }

}
