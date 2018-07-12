package hubble.backend.providers.configurations.environments;


import hubble.backend.storage.models.AppPulse;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class AppPulseProviderEnvironmentMongoImpl implements ProviderEnvironment  {


    @Autowired
    ProvidersRepository providersRepository;

    @Override
    public String getClient() {
        return this.getEnviroment().getClient();
    }

    @Override
    public String getSecret() {
        return this.getEnviroment().getSecret();
    }

    @Override
    public String getUrl() {
        return this.getEnviroment().getUrl();
    }

    private AppPulse.Environment getEnviroment(){
        return providersRepository.appPulse().getEnvironment();
    }
}
