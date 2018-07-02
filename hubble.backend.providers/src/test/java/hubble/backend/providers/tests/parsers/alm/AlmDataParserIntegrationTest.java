package hubble.backend.providers.tests.parsers.alm;

import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.parsers.interfaces.alm.AlmDataParser;
import hubble.backend.providers.transports.interfaces.AlmTransport;
import hubble.backend.storage.models.IssueStorage;
import hubble.backend.storage.repositories.IssueRepository;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class AlmDataParserIntegrationTest {

    @Autowired
    private AlmDataParser almDataParser;
    @Autowired
    private AlmTransport almTransport;
    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void alm_transport_should_be_instantiated() {
        assertNotNull(almTransport);
    }

    @Test
    public void alm_data_parser_should_be_instantiated() {
        assertNotNull(almDataParser);
    }

    @Test
    public void alm_data_parser_should_retrieve_defects() {
        almTransport.login();
        assertTrue(almTransport.isAuthenticated());
        Map<String, String> cookies = almTransport.getSessionCookies();
        JSONObject allDefects = almTransport.getOpenDefects(cookies,1);
        List<JSONObject> defects = almDataParser.parseList(allDefects);

        for (JSONObject defect : defects) {
            assertNotNull(almDataParser.parse(defect).getId());
        }

        almTransport.logout();
        assertFalse(almTransport.isAuthenticated());

    }

    @Test
    public void alm_data_parser_when_it_runs_should_connect_get_data_and_save_it() {
        //Assign
        issueRepository.deleteAll();

        //Act
        almDataParser.run();
        List<IssueStorage> issueStorages = issueRepository.findAll();

        //Assert
        assertTrue(issueStorages.size() > 0);
        for (IssueStorage issue : issueStorages) {
            assertTrue(issueRepository.exist(issue));
        }

        //Clean
        issueRepository.deleteAll();
    }
}
