package hubble.backend.providers.configurations.environments;

import hubble.backend.storage.models.PPM;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PpmProviderEnviromentMongoImpl implements PpmProviderEnvironment{

    @Autowired
    ProvidersRepository providersRepository;

    @Override
    public String getHost() {
        return this.getEnviroment().getHost();
    }

    @Override
    public String getPort() {
        return String.valueOf(this.getEnviroment().getPort());
    }

    @Override
    public String getUser() {
        return this.getEnviroment().getUsername();
    }

    @Override
    public String getPassword() {
        return this.getEnviroment().getPassword();
    }

    private PPM.Environment getEnviroment(){
        return providersRepository.ppm().getEnvironment();
    }
}
