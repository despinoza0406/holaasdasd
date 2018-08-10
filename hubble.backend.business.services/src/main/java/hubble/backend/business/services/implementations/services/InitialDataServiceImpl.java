package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.interfaces.services.InitialDataService;
import hubble.backend.business.services.models.Roles;
import hubble.backend.storage.models.*;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.ProvidersRepository;
import hubble.backend.storage.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
@Service
public class InitialDataServiceImpl implements InitialDataService {
    
    private static final String ADMIN_EMAIL = "admin@tsoftlatam.com";
    private static final TaskRunner EVERY_DAY_AT_9 = new TaskRunner(
            true,
            new Schedule(Days.EVERY_DAY, HourRange._9_to_18, Frecuency.DAYLY)
    );
    private static final TaskRunner EVERY_FIVE_MINUTES = new TaskRunner(
            true,
            new Schedule(Days.EVERY_DAY, HourRange._24hs, Frecuency.EVERY_5_MINUTES)
    );
    private static final TaskRunner EVERY_HOUR = new TaskRunner(
            true,
            new Schedule(Days.EVERY_DAY, HourRange._24hs, Frecuency.HOURLY)
    );
    private final UsersRepository users;
    private final ProvidersRepository providers;
    private final ApplicationRepository applications;
    
    @Autowired
    public InitialDataServiceImpl(UsersRepository users, ProvidersRepository providers, ApplicationRepository applications) {
        this.users = users;
        this.providers = providers;
        this.applications = applications;
    }
    
    @Transactional
    @Override
    public void createData() {
        dropCollections();
        configureProviders();
        configureApplications();
        createAdminUser();
    }

    private void dropCollections(){
        //users.deleteAll();
        providers.deleteAll();
        applications.deleteAll();
    }
    
    private void createAdminUser() {
        if (!exists(adminSample())) {
            users.save(admin());
        }
    }
    
    private void configureApplications() {
        guardarAplicacion("Benchmark Home Banking", "Home Banking","Descripción de Benchmark Home Banking","Benchmark Home Banking", "Banca por Internet", "Home Banking", "HB","HB", "Home Banking");
        guardarAplicacion("Benchmark Mobile","Mobile Banking","Descripción de Benchmark Mobile","Benchmark Mobile", "Banca Presencial", "Mobile", "Mobile Banking","MB", "Mobile Banking");
        guardarAplicacion("CRM","CRM","Descripción de CRM","CRM", "Retail banking","CRM", "CRM","CR","CRM");
    }
    
    private void guardarAplicacion(String id, String nombre, String descripcion,String nombreEnBSM, String nombreEnPPM, String nombreEnALM, String nombreEnJira,String projectKey, String nombreEnSiteScope) {
        List<Threashold> threasholds = crearThreasholds();
        ApplicationStorage application = createApplicationStorage(id, nombre, descripcion, threasholds,nombreEnBSM, nombreEnPPM, nombreEnALM, nombreEnJira,projectKey, nombreEnSiteScope);
        applications.save(application);
    }
    
    private static ApplicationStorage createApplicationStorage(String id, String nombre, String descripcion, List<Threashold> th,String nombreEnBSM, String nombreEnPPM, String nombreEnALM, String nombreEnJira,String projectKeyEnJira, String nombreEnSiteScope) {
        return new ApplicationStorage(
                id,
                nombre,
                descripcion,
                true,
                new KPIs(
                        new Tasks(true, th.get(0), th.get(0), th.get(0), ApplicationInProvider.standard(nombreEnPPM, Boolean.TRUE)),
                        new Defects(true, th.get(3), th.get(3), th.get(3), ApplicationInProvider.standard(nombreEnALM, Boolean.TRUE), ApplicationInProviderJira.standard(nombreEnJira,projectKeyEnJira, Boolean.TRUE)),
                        new Availavility(true, th.get(1), th.get(1), th.get(1), th.get(1), ApplicationInProvider.standard(nombreEnBSM, Boolean.TRUE), ApplicationInProvider.standard("", Boolean.TRUE)),
                        new Performance(true, th.get(2), th.get(2), th.get(2), th.get(2), ApplicationInProvider.standard(nombreEnBSM, Boolean.TRUE), ApplicationInProvider.standard("", Boolean.TRUE)),
                        new Events(true, th.get(0), th.get(0), th.get(0), th.get(0), ApplicationInProvider.standard(nombreEnSiteScope, Boolean.TRUE))),
                true
        );
    }
    
    private boolean exists(UserStorage adminSample) {
        return users.findOne(Example.of(adminSample)) != null;
    }
    
    private UserStorage adminSample() {
        UserStorage sample = new UserStorage();
        sample.setEmail(ADMIN_EMAIL);
        return sample;
    }
    
    private UserStorage admin() {
        
        Threashold th = new Threashold(0, 2, 5d,100);
        
        return new UserStorage(
                ADMIN_EMAIL,
                "Administrator",
                "administrator".toCharArray(),
                new HashSet<>(asList(Roles.ADMINISTRATOR.name(), Roles.USER.name())),
                new HashSet<>(applications.findAll())
        );
    }
    
    private void configureProviders() {
        configureALM();
        configureAppPulse();
        configureBSM();
        configureJira();
        configurePPM();
        configureSiteScope();
    }
    
    private void configureALM() {
        if (providers.alm() == null) {
            providers.save(defaultALM());
        }
    }
    
    private ALM defaultALM() {
        return new ALM(
            true,
            EVERY_DAY_AT_9,
            new ALM.Environment("10.10.20.170", 8080, "matias.lapalma", "", "TSAR_SOFTWAREFACTORY", "HUBBLE"),
            new ALM.Configuration(
                "project",

                new ALM.Configuration.Status("status", new HashSet<>(asList("Nuevo", "Abierto", "Reabierto"))),
                "project",
                new ALM.Configuration.Provider("Alm", "Alm Tsoft")
            )
        );
    }
    
    private void configureAppPulse() {
        if (providers.appPulse() == null) {
            providers.save(defaultAppPulse());
        }
    }
    
    private AppPulse defaultAppPulse() {
        return new AppPulse(
                true,
                EVERY_DAY_AT_9,
                new AppPulse.Environment(
                        "https://apppulse-active.saas.hpe.com/openapi/rest/v1/949143007/",
                        "949143007#C1",
                        "d3e5ad40-4eca-48d0-9db0-a410f76b45e7"
                ),
                new AppPulse.Configuration("project")
        );
    }
    
    private void configureBSM() {
        if (providers.bsm() == null) {
            providers.save(defaultBSM());
        }
    }
    
    private BSM defaultBSM() {
        return new BSM(
                true,
                EVERY_HOUR,
                new BSM.Environment(
                        new SoapEndpoint(
                                "http://t-srvbacapplsar.tsoftglobal.com/topaz/gdeopenapi/services/GdeWsOpenAPI?wsdl",
                                "http://t-srvbacapplsar.tsoftglobal.com/topaz/gdeopenapi/services/GdeWsOpenAPI"
                        ),
                        "admin",
                        "admin"
                ),
                new BSM.Configuration("project")
        );
    }
    
    private void configureJira() {
        if (providers.jira() == null) {
            providers.save(jiraDefault());
        }
    }
    
    private Jira jiraDefault() {
        return new Jira(
            true,
            EVERY_DAY_AT_9,
            new Jira.Environment("10.10.20.175", 8888, "tsoftlatam", "Tsoft2018"),
            new Jira.Configuration("HB", "project")
        );
    }
    
    private void configurePPM() {
        if (providers.ppm() == null) {
            providers.save(ppmDefault());
        }
    }
    
    private PPM ppmDefault() {
        return new PPM(
                true,
                EVERY_DAY_AT_9,
                new PPM.Environment("demoppm.tsoftglobal.com", 8888, "admin", "ppm931"),
                new PPM.Configuration(
                        "REQ.KNTA_PROGRAM_REFERENCE",
                        "not_defined",
                        new PPM.Configuration.Provider("Ppm", "Ppm Tsoft"),
                        new HashSet<>(asList(30219))
                )
        );
    }
    
    private void configureSiteScope() {
        if (providers.siteScope() == null) {
            providers.save(siteScopeDefault());
        }
    }
    
    private SiteScope siteScopeDefault() {
        return new SiteScope(
                true,
                EVERY_FIVE_MINUTES,
                new SiteScope.Environment("10.10.20.248", 8080, "root", "root"),
                new SiteScope.Configuration("project")
        );
    }

    private List<Threashold> crearThreasholds(){
            List<Threashold> threasholds = new ArrayList<>();
            threasholds.add(new Threashold(0,5,100,Double.POSITIVE_INFINITY)); //Eventos y Tasks
            threasholds.add(new Threashold(100,98,90,0)); //Disponibilidad
            threasholds.add(new Threashold(0,4000,8000,Double.POSITIVE_INFINITY)); //performance
            threasholds.add(new Threashold(0,0,15,Double.POSITIVE_INFINITY)); //Defects

        return threasholds;

    }
}
