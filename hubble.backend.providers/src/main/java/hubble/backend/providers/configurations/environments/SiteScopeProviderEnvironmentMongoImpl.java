package hubble.backend.providers.configurations.environments;

import hubble.backend.storage.models.SiteScope;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SiteScopeProviderEnvironmentMongoImpl implements SiteScopeProviderEnvironment{

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

    private SiteScope.Environment getEnviroment(){
        return providersRepository.siteScope().getEnvironment();
    }

}
