package hubble.backend.providers.tests.parsers.ppm;

import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.models.ppm.PpmApplicationProviderModel;
import hubble.backend.providers.parsers.interfaces.ppm.PpmApplicationParser;
import hubble.backend.providers.transports.interfaces.PpmTransport;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class PpmApplicationParserIntegrationTest {

    @Autowired
    private PpmApplicationParser applicationParser;
    @Autowired
    private PpmTransport ppmTransport;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    public void ppmTransport_should_be_instantiated() {
        assertNotNull(ppmTransport);
    }

    @Test
    public void ppmApplicationParser_should_be_instantiated() {
        assertNotNull(applicationParser);
    }

    @Ignore
    @Test
    public void ppm_applications_parser_should_retrieve_applications() {
        String encodedAuthString = ppmTransport.encodeToBase64(
                ppmTransport.getEnvironment().getUser(),
                ppmTransport.getEnvironment().getPassword());

        List<JSONObject> requestsToBeParsed = new ArrayList();
        List<JSONObject> detailedRequests = new ArrayList();
        List<PpmApplicationProviderModel> parsedRequests = new ArrayList();
        List<String> requestTypeIds = ppmTransport.getConfiguredRequestTypes(encodedAuthString);

        for (String id : requestTypeIds) {
            requestsToBeParsed.addAll(ppmTransport.getRequests(encodedAuthString, id));
        }

        for (JSONObject request : requestsToBeParsed) {
            detailedRequests.add(ppmTransport.getRequestDetails(encodedAuthString, request.getString("id")));
        }

        for (JSONObject detailedRequest : detailedRequests) {
            parsedRequests.add(applicationParser.parse(detailedRequest));
        }

        assertNotNull(parsedRequests);
    }

    @Ignore
    @Test
    public void ppm_application_parser_when_it_runs_should_connect_get_data_and_save_it() {
        //Assign
        applicationRepository.deleteAll();

        //Act
        applicationParser.run();
        List<ApplicationStorage> applicationStorages = applicationRepository.findAll();

        //Assert
        assertTrue(applicationStorages.size() > 0);
        for (ApplicationStorage application : applicationStorages) {
            assertTrue(applicationRepository.exist(application));
        }

        //Clean
        applicationRepository.deleteAll();
    }
}
