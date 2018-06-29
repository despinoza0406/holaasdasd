package hubble.backend.providers.tests.configurations;

import hubble.backend.providers.configurations.AlmConfiguration;
import hubble.backend.providers.configurations.PpmConfiguration;
import hubble.backend.providers.configurations.ProvidersConfiguration;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class ProvidersConfigurationsTests {

    @Autowired
    private PpmConfiguration ppmConfiguration;

    @Autowired
    private AlmConfiguration almConfiguration;

    @Test
    public void ppmConfiguration_should_be_instantiated() {
        assertNotNull(ppmConfiguration);
    }

    @Test
    public void almConfiguration_should_be_instantiated() {
        assertNotNull(almConfiguration);
    }

    @Test
    public void ppmConfiguration_should_get_configurations_correctly() {
        String applicationFieldName = ppmConfiguration.getApplicationFieldName();
        HashMap<String,String> applicationValueToIdMap = ppmConfiguration.getApplicationValueToIdMap();
        String providerName = ppmConfiguration.getProviderName();
        String providerOrigin = ppmConfiguration.getProviderOrigin();
        String requestTypeIds = ppmConfiguration.getRequestTypeIds();
        String transactionFieldName = ppmConfiguration.getTransactionFieldName();

        assertNotNull(applicationFieldName);
        assertNotNull(applicationValueToIdMap);
        assertNotNull(providerName);
        assertNotNull(providerOrigin);
        assertNotNull(requestTypeIds);
        assertNotNull(transactionFieldName);

        assertEquals("Ppm", providerOrigin);
    }

    @Test
    public void almConfiguration_should_get_configurations_correctly() {
        String applicationFieldName = almConfiguration.getApplicationFieldName();
        HashMap<String,String> applicationValueToIdMap = almConfiguration.getApplicationValueToIdMap();
        String providerName = almConfiguration.getProviderName();
        String providerOrigin = almConfiguration.getProviderOrigin();
        String transactionFieldName = almConfiguration.getTransactionFieldName();
        String statusFieldName = almConfiguration.getStatusFieldName();
        Set<String> statusOpenValues = almConfiguration.getStatusOpenValues();

        assertNotNull(applicationFieldName);
        assertNotNull(applicationValueToIdMap);
        assertNotNull(providerName);
        assertNotNull(providerOrigin);
        assertNotNull(transactionFieldName);
        assertNotNull(statusFieldName);
        assertNotNull(statusOpenValues);

        assertEquals("Alm", providerOrigin);
    }

}
