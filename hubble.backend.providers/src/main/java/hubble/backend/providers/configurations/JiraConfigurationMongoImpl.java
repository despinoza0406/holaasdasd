package hubble.backend.providers.configurations;


import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.Jira;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JiraConfigurationMongoImpl implements JiraConfiguration{

    @Autowired
    ProvidersRepository providersRepository;
    @Autowired
    ApplicationRepository applicationRepository;


    @Override
    public String getApplicationFieldName() {
        return this.getConfiguration().getBusinessApplicationFieldName();
    }

    @Override
    public HashMap<String,String> getValuesToIdMap() {
        List<ApplicationStorage> applications = applicationRepository.findAll().stream().
                filter((a) -> false || a.isActive()).collect(Collectors.toList());
        HashMap<String,String> mapApplications = new HashMap<>();
        for(ApplicationStorage application: applications){
            String hubbleName = application.getApplicationName();
            String jiraName = application.getKpis().getDefects().getJira().getApplicationName();
            mapApplications.put(hubbleName,jiraName);
        }

        return mapApplications;
    }

    @Override
    public String getProjectKey() {
        return this.getConfiguration().getProjectKey();
    }

    private Jira.Configuration getConfiguration(){
        return providersRepository.jira().getConfiguration();
    }
}
