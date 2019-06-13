package hubble.backend.providers.transports.implementations.sitescope;

import org.junit.Test;

import static org.junit.Assert.*;

public class SiteScopeTransportImplTest {

    @Test
    public void testConnection() {
        SiteScopeTransportImpl siteScopeTransport = new SiteScopeTransportImpl();
        assertTrue(siteScopeTransport.testConnection("10.10.20.231","8080","root","root"));
    }
}