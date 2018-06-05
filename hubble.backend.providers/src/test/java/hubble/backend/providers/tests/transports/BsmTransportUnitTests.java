package hubble.backend.providers.tests.transports;

import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.transports.implementations.bsm.BsmTransportImpl;
import hubble.backend.providers.transports.interfaces.BsmTransport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.soap.SOAPBody;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class BsmTransportUnitTests {
    @Autowired
    BsmTransportImpl bsmTransport;

    @Test
    public void BsmTransport_should_get_applications_from_bsm(){
        SOAPBody data = bsmTransport.getApplications();

        assertNotNull(data);
    }
}
