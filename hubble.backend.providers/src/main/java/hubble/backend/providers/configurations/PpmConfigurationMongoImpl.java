package hubble.backend.providers.configurations;

import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.PPM;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PpmConfigurationMongoImpl implements PpmConfiguration{


    @Autowired
    ProvidersRepository providersRepository;
    @Autowired
    ApplicationRepository applicationRepository;


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
    public String getRequestTypeIds() {

        //Basicamente para que me devuelva lo que tenia que devolver y  no tener que cambiar toda la implementacion
        Set<Integer> ints = this.getConfiguration().getRequestTypeIds();
        HashSet<String> strs = new HashSet<>(ints.size());
        ints.forEach(i->strs.add(i.toString()));
        StringBuilder builder = new StringBuilder();
        for(String str : strs){
            builder.append(str);
        }
        return builder.toString();
    }

    @Override
    public HashMap<String,String> getApplicationValueToIdMap() {
        List<ApplicationStorage> applications = applicationRepository.findAll().stream().
                                                filter((a) ->a.isActive() &&
                                                        a.isEnabledTaskRunner() &&
                                                        a.getKpis().getTasks().getEnabled() &&
                                                        a.getKpis().getTasks().getPpm().isEnabled() &&
                                                        a.getKpis().getTasks().getPpm().isEnabledInTaskRunner()).collect(Collectors.toList());
        HashMap<String,String> mapApplications = new HashMap<>();
        for(ApplicationStorage application: applications){
            String hubbleName = application.getApplicationName();
            String ppmName = application.getKpis().getTasks().getPpm().getApplicationName();
            mapApplications.put(hubbleName,ppmName);
        }

        return mapApplications;

    }

    public boolean taskEnabled(){ return providersRepository.ppm().isEnabled() && providersRepository.ppm().getTaskRunner().isEnabled();}

    private PPM.Configuration getConfiguration(){
        return providersRepository.ppm().getConfiguration();
    }
}
