package hubble.backend.storage;

import hubble.backend.storage.configurations.StorageComponentConfiguration;
import hubble.backend.storage.configurations.environment.StorageEnvironment;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StorageComponentConfiguration.class)
public class TestEnvironmentConfigurationUnitTests {

    @Autowired
    public StorageEnvironment storageConfiguration;

    @Test
    public void EnvironmentConfiguration_getting_properties_for_test_env_are_expected() {
        assertEquals("host", "localhost", storageConfiguration.getHost());
        assertEquals("dbName", "hubble-test", storageConfiguration.getDbname());
    }
}
