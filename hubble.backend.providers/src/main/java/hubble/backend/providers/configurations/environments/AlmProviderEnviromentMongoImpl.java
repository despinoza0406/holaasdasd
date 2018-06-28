package hubble.backend.providers.configurations.environments;


import hubble.backend.storage.models.ALM;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlmProviderEnviromentMongoImpl implements AlmProviderEnvironment{

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
    public String getUserName() {
        return this.getEnviroment().getUsername();
    }

    @Override
    public String getPassword() {
        return this.getEnviroment().getPassword();
    }

    @Override
    public String getDomain() {
        return this.getEnviroment().getDomain();
    }

    @Override
    public String getProject() {
        return this.getEnviroment().getProject();
    }

    private ALM.Environment getEnviroment(){
        return providersRepository.alm().getEnvironment();
    }
}
