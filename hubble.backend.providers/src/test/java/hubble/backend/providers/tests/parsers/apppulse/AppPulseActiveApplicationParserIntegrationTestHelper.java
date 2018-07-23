package hubble.backend.providers.tests.parsers.apppulse;

import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.parsers.interfaces.apppulse.AppPulseActiveApplicationsParser;
import hubble.backend.providers.tests.AppPulseBaseUnitTestsHelper;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class AppPulseActiveApplicationParserIntegrationTestHelper extends AppPulseBaseUnitTestsHelper {

    @Autowired
    private AppPulseActiveApplicationsParser appPulseActiveParser;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    public void AppPulseActiveParser_should_be_instantiated() {
        assertNotNull(appPulseActiveParser);
    }

    @Ignore("AppPulse API is no longer active")
    @Test
    public void AppPulseActiveParser_when_it_runs_should_connect_get_applications_and_save_it() {

        //Assign
        applicationRepository.deleteAll();
        //Act
        appPulseActiveParser.run();

        //Assert
        List<ApplicationStorage> applications = appPulseActiveParser.getApplicationStorage();
        assertNotNull(applications);
        assertTrue(applications.stream().allMatch((applicationsFromAppPulse) -> {
            return applicationRepository.exist(applicationsFromAppPulse);
        }));

        applications.stream().forEach((applicationsFromAppPulse) -> {

            ApplicationStorage a = applicationRepository.findOne(applicationsFromAppPulse.getId());

        });

        applications.stream().forEach((applicationsFromAppPulse) -> {
            applicationRepository.delete(applicationsFromAppPulse);
        });
    }
}
