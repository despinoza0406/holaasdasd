package hubble.backend.providers.tests.parsers.sitescope;


import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.parsers.interfaces.sitescope.SiteScopeApplicationParser;
import hubble.backend.providers.transports.interfaces.SiteScopeTransport;
import hubble.backend.storage.repositories.ApplicationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class SiteScopeApplicationParserIntegrationTest {

    @Autowired
    private SiteScopeApplicationParser applicationParser;
    @Autowired
    private SiteScopeTransport siteScopeTransport;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    public void siteScopeTransport_should_be_instantiated() {
        assertNotNull(siteScopeTransport);
    }

    @Test
    public void siteScopeApplicationParser_should_be_instantiated() {
        assertNotNull(applicationParser);
    }
}
