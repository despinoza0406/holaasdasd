package hubble.backend.providers.tests.configurations.environments;

import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.configurations.environments.AppPulseProviderEnvironment;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class ProviderEnvironmentTest {

    @Autowired
    private AppPulseProviderEnvironment environment;

    @Test
    public void ProviderEnvironment_should_get_url() {

        assertNotNull(environment);
        assertNotNull(environment.getUrl());
    }
}
