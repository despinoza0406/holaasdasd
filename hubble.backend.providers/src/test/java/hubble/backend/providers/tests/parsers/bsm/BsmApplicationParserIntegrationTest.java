package hubble.backend.providers.tests.parsers.bsm;

import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.parsers.interfaces.bsm.BsmApplicationParser;
import hubble.backend.providers.tests.AppPulseBaseUnitTestsHelper;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class BsmApplicationParserIntegrationTest extends AppPulseBaseUnitTestsHelper {

    @Autowired
    private BsmApplicationParser bsmParser;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    public void BsmApplicationParser_should_be_instantiated() {
        assertNotNull(bsmParser);
    }

    //@Ignore("BSM is down")
    @Test
    public void BsmApplicationParser_when_it_runs_should_connect_get_data_and_save_it() {

        //Assign
        applicationRepository.deleteAll();

        //Act
        bsmParser.run();

        //Assert
        List<ApplicationStorage> applications = bsmParser.getApplicationStorage();
        assertNotNull(applications);
        assertTrue(applications.stream().allMatch((applicationFromBsm) -> {
            return applicationRepository.exist(applicationFromBsm);
        }));

        assertTrue(applicationRepository.count() == applications.size());
        applications.stream().forEach((applicationFromBsm) -> {
            applicationRepository.delete(applicationFromBsm);
        });
    }
}
