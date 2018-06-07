package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.interfaces.services.InitialDataService;
import hubble.backend.business.services.models.Roles;
import hubble.backend.storage.models.ALM;
import hubble.backend.storage.models.AppPulse;
import hubble.backend.storage.models.BSM;
import hubble.backend.storage.models.Jira;
import hubble.backend.storage.models.PPM;
import hubble.backend.storage.models.SiteScope;
import hubble.backend.storage.models.SoapEndpoint;
import hubble.backend.storage.models.TaskRunner;
import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.repositories.ProvidersRepository;
import hubble.backend.storage.repositories.UsersRepository;
import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.HashSet;
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
    private static final TaskRunner EVERY_DAY_AT_9 = new TaskRunner(true, "0 0 9 * * *");
    private final UsersRepository users;
    private final ProvidersRepository providers;

    @Autowired
    public InitialDataServiceImpl(UsersRepository users, ProvidersRepository providers) {
        this.users = users;
        this.providers = providers;
    }

    @Transactional
    @Override
    public void createData() {
        createAdminUser();
        configureProviders();
    }

    private void createAdminUser() {
        if (!exists(adminSample())) {
            users.save(admin());
        }
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
        return new UserStorage(
            ADMIN_EMAIL,
            "Administrator",
            "administrator".toCharArray(),
            new HashSet<>(asList(Roles.ADMINISTRATOR.name())),
            Collections.EMPTY_SET
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
            )
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
            EVERY_DAY_AT_9,
            new BSM.Environment(
                new SoapEndpoint(
                    "http://t-srvbacapplsar.tsoftglobal.com/topaz/gdeopenapi/services/GdeWsOpenAPI?wsdl",
                    "http://t-srvbacapplsar.tsoftglobal.com/topaz/gdeopenapi/services/GdeWsOpenAPI"
                ),
                "admin",
                "admin"
            )
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
            new Jira.Environment("10.10.20.175", 8888, "andrevigneaux", "r3dem$0023"),
            new Jira.Configuration("HUB", "project")
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
        if (providers.ppm() == null) {
            providers.save(siteScopeDefault());
        }
    }

    private SiteScope siteScopeDefault() {
        return new SiteScope(true, EVERY_DAY_AT_9);
    }
}