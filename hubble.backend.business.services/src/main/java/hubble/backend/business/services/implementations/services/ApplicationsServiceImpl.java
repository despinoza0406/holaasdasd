package hubble.backend.business.services.implementations.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hubble.backend.business.services.interfaces.services.ApplicationService;
import hubble.backend.storage.models.*;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.ProvidersRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Guelmy Díaz <guelmy.diaz.blanco@fit.com.ar>
 */
@Service
public class ApplicationsServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ProvidersRepository providersRepository;

    public ApplicationsServiceImpl(ApplicationRepository applicationRepository, ProvidersRepository providersRepository) {
        this.applicationRepository = applicationRepository;
        this.providersRepository = providersRepository;
    }

    @Override
    public List<ApplicationStorage> getAll() {
        return applicationRepository.findAll();
    }

    @Override
    public void enabledDisabled(String id, boolean enabled) {

        try {
            ApplicationStorage app = applicationRepository.findOne(id);
            if (app == null) {
                throw new RuntimeException("No existe una aplicación con el ID: " + id);
            } else {
                app.setActive(enabled);
                applicationRepository.save(app);
            }

        } catch (Throwable t) {
            throw new RuntimeException("Ocurrió un error mientras se habilitaba/deshabilitaba la aplicación. Causa: " + t.getMessage());
        }

    }

    @Override
    public void enabledDisabledTaskRunner(String id, boolean enabled) {
        try {
            ApplicationStorage app = applicationRepository.findOne(id);
            if (app == null) {
                throw new RuntimeException("No existe una aplicación con el ID: " + id);
            } else {
                app.setEnabledTaskRunner(enabled);
                applicationRepository.save(app);
            }

        } catch (Throwable t) {
            throw new RuntimeException("Ocurrió un error mientras se habilitaba/deshabilitaba la aplicación en el taskRunner. Causa: " + t.getMessage());
        }
    }

    @Override
    public ApplicationStorage create(String applicationId, String name, String description) {

        if (applicationRepository.existAppName(name)) {
            throw new RuntimeException(String.format("Ya existe una apliacación con el nombre %s", name));
        }

        //Se crea una app solo con los datos ingresados por panatalla y el resto con valores x defecto.
        ApplicationStorage app = new ApplicationStorage(applicationId, name, description, Boolean.FALSE, getKPIsDefault(), Boolean.FALSE);

        return applicationRepository.save(app);
    }

    @Override
    public ApplicationStorage findById(String id) {
        
        ApplicationStorage applicationStorage = applicationRepository.findOne(id);

        applicationStorage.getKpis().getTasks().getPpm().setActiveProvider(providersRepository.ppm().isEnabled());

        applicationStorage.getKpis().getDefects().getAlm().setActiveProvider(providersRepository.alm().isEnabled());
        applicationStorage.getKpis().getDefects().getJira().setActiveProvider(providersRepository.jira().isEnabled());

        applicationStorage.getKpis().getAvailability().getAppPulse().setActiveProvider(providersRepository.appPulse().isEnabled());
        applicationStorage.getKpis().getAvailability().getBsm().setActiveProvider(providersRepository.bsm().isEnabled());

        applicationStorage.getKpis().getPerformance().getAppPulse().setActiveProvider(providersRepository.appPulse().isEnabled());
        applicationStorage.getKpis().getPerformance().getBsm().setActiveProvider(providersRepository.bsm().isEnabled());

        applicationStorage.getKpis().getEvents().getSiteScope().setActiveProvider(providersRepository.siteScope().isEnabled());

        return applicationStorage;
    }

    private KPIs getKPIsDefault() {
        List<Threashold> th = crearThreasholdsDefaults();

        return new KPIs(
                new Tasks(false, th.get(0), th.get(0), th.get(0), ApplicationInProvider.standard("")),
                new Defects(false, th.get(3), th.get(3), th.get(3), ApplicationInProvider.standard(""), ApplicationInProviderJira.standard("", "")),
                new Availavility(false, th.get(1), th.get(1), th.get(1), th.get(1), ApplicationInProvider.standard(""), ApplicationInProvider.standard("")),
                new Performance(false, th.get(2), th.get(2), th.get(2), th.get(2), ApplicationInProvider.standard(""), ApplicationInProvider.standard("")),
                new Events(false, th.get(0), th.get(0), th.get(0), th.get(0), ApplicationInProvider.standard("")));
    }

    private List<Threashold> crearThreasholdsDefaults() {
        List<Threashold> threasholds = new ArrayList<>();
        threasholds.add(new Threashold(0, 0, 0, 0)); //Eventos y Tasks
        threasholds.add(new Threashold(0, 0, 0, 0)); //Disponibilidad
        threasholds.add(new Threashold(0, 0, 0, 0)); //performance
        threasholds.add(new Threashold(0, 0, 0, 0)); //Defects

        return threasholds;

    }

    @Override
    public ApplicationStorage editApplicationFromJson(JsonNode jsonNode) {

        ApplicationStorage app = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            app = mapper.readValue(jsonNode.toString(), ApplicationStorage.class);
        } catch (Exception ex) {
            throw new RuntimeException("No se ha podido convertir el json a un objeto del tipo ApplicationStorage");
        }

        return applicationRepository.save(app);
    }
}
