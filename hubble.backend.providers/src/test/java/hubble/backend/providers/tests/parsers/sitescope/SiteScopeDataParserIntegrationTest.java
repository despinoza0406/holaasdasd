package hubble.backend.providers.tests.parsers.sitescope;

import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.parsers.interfaces.sitescope.SiteScopeDataParser;
import hubble.backend.providers.transports.interfaces.SiteScopeTransport;
import hubble.backend.storage.repositories.WorkItemRepository;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class SiteScopeDataParserIntegrationTest {

    @Autowired
    private SiteScopeDataParser siteScopeDataParser;
    @Autowired
    private SiteScopeTransport siteScopeTransport;
    @Autowired
    private WorkItemRepository workItemRepository;

    @Test
    public void ppmDataParser_should_be_instantiated() {
        assertNotNull(siteScopeDataParser);
    }

    @Test
    public void ppmTransport_should_be_instantiated() {
        assertNotNull(siteScopeTransport);
    }

    @Test
    void siteScopeDataParser_should_parse_x(){

    }
}
