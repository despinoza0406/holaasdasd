package hubble.backend.providers.tests.parsers.alm;

import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.parsers.interfaces.alm.AlmApplicationParser;
import hubble.backend.providers.transports.interfaces.AlmTransport;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class AlmApplicationParserIntegrationTest {

    @Autowired
    private AlmApplicationParser almApplicationParser;
    @Autowired
    private AlmTransport almTransport;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    public void alm_transport_should_be_instantiated() {
        assertNotNull(almTransport);
    }

    @Test
    public void alm_application_parser_should_be_instantiated() {
        assertNotNull(almApplicationParser);
    }

    @Test
    public void alm_data_parser_should_retrieve_applications() {
        almTransport.login();
        assertTrue(almTransport.isAuthenticated());
        Map<String, String> cookies = almTransport.getSessionCookies();
        JSONObject allDefects = almTransport.getDefects(cookies,0);
        List<JSONObject> defects = almApplicationParser.parseList(allDefects);

        for (JSONObject defect : defects) {
            assertNotNull(almApplicationParser.parse(defect).getName());
        }

        almTransport.logout();
        assertFalse(almTransport.isAuthenticated());

    }

    @Test
    public void alm_data_parser_when_it_runs_should_connect_get_data_and_save_it() {
        //Assign
        applicationRepository.deleteAll();

        //Act
        almApplicationParser.run();
        List<ApplicationStorage> applicationStorages = applicationRepository.findAll();

        //Assert
        assertTrue(applicationStorages.size() > 0);
        applicationStorages.forEach((application) -> {
            assertTrue(applicationRepository.exist(application));
        });

        //Clean
        applicationRepository.deleteAll();
    }
}
