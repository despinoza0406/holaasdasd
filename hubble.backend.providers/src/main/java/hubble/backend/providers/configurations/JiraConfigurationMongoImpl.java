package hubble.backend.providers.configurations;


import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.Jira;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@Primary
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
                filter((a) ->
                        a.isEnabledTaskRunner() &&
                        a.getKpis().getDefects().getEnabled() &&
                        a.getKpis().getDefects().getJira().isEnabledInTaskRunner()).collect(Collectors.toList());
        HashMap<String,String> mapApplications = new HashMap<>();
        for(ApplicationStorage application: applications){
            String hubbleName = application.getApplicationId();
            String jiraName = application.getKpis().getDefects().getJira().getApplicationName();
            mapApplications.put(hubbleName,jiraName);
        }

        return mapApplications;
    }

    @Override
    public String[] getProjectKeys() throws NullPointerException {
        List<ApplicationStorage> applications = applicationRepository.findAll().stream().
                filter((a) ->
                                a.isEnabledTaskRunner() &&
                                a.getKpis().getDefects().getEnabled() &&
                                a.getKpis().getDefects().getJira().isEnabledInTaskRunner()).collect(Collectors.toList())
                                ;
        applications = applications.stream().filter(distinctByKey((a) -> a.getKpis().getDefects().getJira().getProjectKey())).collect(Collectors.toList());
        String[] projectKeys = new String[applications.size()];
        int i = 0;
        for (ApplicationStorage applicationStorage : applications) {
            projectKeys[i] = applicationStorage.getKpis().getDefects().getJira().getProjectKey();
            i++;
        }
        return projectKeys;
    }

    public boolean taskEnabled() { return providersRepository.jira().isEnabled() && providersRepository.jira().getTaskRunner().isEnabled();}

    private Jira.Configuration getConfiguration(){
        return providersRepository.jira().getConfiguration();
    }

    //Esta para darme solo los que tengan project keys unicas
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
