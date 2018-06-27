package hubble.backend.providers.tests.parsers.ppm;

import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.models.ppm.PpmProgramIssueProviderModel;
import hubble.backend.providers.parsers.interfaces.ppm.PpmDataParser;
import hubble.backend.providers.transports.interfaces.PpmTransport;
import hubble.backend.storage.models.WorkItemStorage;
import hubble.backend.storage.repositories.WorkItemRepository;
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
public class PpmDataParserIntegrationTest {

    @Autowired
    private PpmDataParser ppmDataParser;
    @Autowired
    private PpmTransport ppmTransport;
    @Autowired
    private WorkItemRepository workItemRepository;

    @Test
    public void ppmDataParser_should_be_instantiated() {
        assertNotNull(ppmDataParser);
    }

    @Test
    public void ppmTransport_should_be_instantiated() {
        assertNotNull(ppmTransport);
    }

    @Ignore
    @Test
    public void ppmDataParser_should_parse_requests() {
        String encodedAuthString = ppmTransport.encodeToBase64(
                ppmTransport.getEnvironment().getUser(),
                ppmTransport.getEnvironment().getPassword());
        List<JSONObject> requestsToBeParsed = new ArrayList();
        List<JSONObject> detailedRequests = new ArrayList();
        List<PpmProgramIssueProviderModel> parsedRequests = new ArrayList();
        List<String> requestTypeIds = ppmTransport.getConfiguredRequestTypes(encodedAuthString);

        for (String id : requestTypeIds) {
            requestsToBeParsed.addAll(ppmTransport.getRequests(encodedAuthString, id));
        }

        for (JSONObject request : requestsToBeParsed) {
            detailedRequests.add(ppmTransport.getRequestDetails(encodedAuthString, request.getString("id")));
        }

        for (JSONObject detailedRequest : detailedRequests) {
            parsedRequests.add(ppmDataParser.parse(detailedRequest));
        }

        assertTrue(parsedRequests.size() > 0);
        assertNotNull(parsedRequests.get(0).getId());
    }

    @Ignore
    @Test
    public void ppm_data_parser_when_it_runs_should_connect_get_data_and_save_it() {
        //Assign
//        workItemRepository.deleteAll();

        //Act
        ppmDataParser.run();
        List<WorkItemStorage> workItemStorages = workItemRepository.findAll();

        //Assert
        assertTrue(workItemStorages.size() > 0);
        for (WorkItemStorage workItem : workItemStorages) {
            assertTrue(workItemRepository.exist(workItem));
        }

        //Clean
//        workItemRepository.deleteAll();
    }
}
