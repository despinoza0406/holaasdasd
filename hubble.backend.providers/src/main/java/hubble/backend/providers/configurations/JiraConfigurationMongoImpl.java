package hubble.backend.providers.configurations;


import hubble.backend.storage.models.Jira;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JiraConfigurationMongoImpl implements JiraConfiguration{

    @Autowired
    ProvidersRepository providersRepository;

    @Override
    public String getApplicationFieldName() {
        return this.getConfiguration().getBusinessApplicationFieldName();
    }

    @Override
    public String getValuesToIdMap() {
        return null;
    } //POR AHORA, FALTA ESTO

    @Override
    public String getProjectKey() {
        return this.getConfiguration().getProjectKey();
    }

    private Jira.Configuration getConfiguration(){
        return providersRepository.jira().getConfiguration();
    }
}
