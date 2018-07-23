package hubble.backend.providers.tests.parsers.bsm;

import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.parsers.interfaces.bsm.BsmDataParser;
import hubble.backend.providers.tests.AppPulseBaseUnitTestsHelper;
import hubble.backend.storage.models.AvailabilityStorage;
import hubble.backend.storage.repositories.AvailabilityRepository;
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
public class BsmDataParserIntegrationTest extends AppPulseBaseUnitTestsHelper {

    @Autowired
    private BsmDataParser bsmParser;
    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Test
    public void BsmParser_should_be_instantiated() {
        assertNotNull(bsmParser);
    }

    @Ignore("BSM is down")
    @Test
    public void BsmParser_when_it_runs_should_connect_get_data_and_save_it() {

        //Assign
        availabilityRepository.deleteAll();

        //Act
        bsmParser.run();

        //Assert
        List<AvailabilityStorage> availabilities = bsmParser.getAvailabilitiesStorage();
        assertNotNull(availabilities);
        assertTrue(availabilities.stream().allMatch((availabilityFromBsm) -> {
            return availabilityRepository.exist(availabilityFromBsm);
        }));

        assertTrue(availabilityRepository.count() == availabilities.size());
        availabilities.stream().forEach((availabilityFromBsm) -> {
            availabilityRepository.delete(availabilityFromBsm);
        });
    }
}
