package hubble.backend.providers.configurations.environments;

import hubble.backend.storage.models.Jira;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class JiraProviderEnvironmentMongoImpl implements JiraProviderEnvironment{

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

    private Jira.Environment getEnviroment(){
        return providersRepository.jira().getEnvironment();
    }

}
