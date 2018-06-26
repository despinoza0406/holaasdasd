package hubble.backend.providers.tests.transports;

import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.transports.interfaces.PpmTransport;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class PpmTransportIntegrationTest {

    @Autowired
    private PpmTransport ppmTransport;

    @Test
    public void ppm_transport_should_be_instantiated() {
        assertNotNull(ppmTransport);
    }

    @Test
    public void ppm_environment_retrieve_correct_configuration() {
        assertEquals("8888", ppmTransport.getEnvironment().getPort());
    }

    @Test
    public void ppm_transport_should_retrieve_all_requestTypes() {
        String encodedAuthString = ppmTransport.encodeToBase64(
                ppmTransport.getEnvironment().getUser(),
                ppmTransport.getEnvironment().getPassword());
        List<JSONObject> dataList;
        dataList = ppmTransport.getAllRequestTypes(encodedAuthString);

        assertTrue(dataList.size() > 0);
    }

    @Test
    public void ppm_transport_should_retrieve_requestTypes_based_on_ids() {
        String encodedAuthString = ppmTransport.encodeToBase64(
                ppmTransport.getEnvironment().getUser(),
                ppmTransport.getEnvironment().getPassword());
        List<JSONObject> dataList;
        List<String> requestIds = new ArrayList();

        requestIds.add("30597");
        requestIds.add("30971");

        dataList = ppmTransport.getRequestTypes(encodedAuthString, requestIds);

        assertEquals(2, dataList.size());
    }

    @Test
    public void ppm_transport_should_retrieve_multiple_requests_based_on_requestType_id() {
        String encodedAuthString = ppmTransport.encodeToBase64(
                ppmTransport.getEnvironment().getUser(),
                ppmTransport.getEnvironment().getPassword());
        String requestTypeId = "30691";
        List<JSONObject> dataList;

        dataList = ppmTransport.getRequests(encodedAuthString, requestTypeId);
        assertTrue(dataList.size() > 0);
    }

    @Test
    public void ppm_transport_should_retrieve_one_requests_based_on_requestType_id() {
        String encodedAuthString = ppmTransport.encodeToBase64(
                ppmTransport.getEnvironment().getUser(),
                ppmTransport.getEnvironment().getPassword());
        String requestTypeId = "30597";
        List<JSONObject> dataList;

        dataList = ppmTransport.getRequests(encodedAuthString, requestTypeId);
        assertTrue(dataList.size() > 0);
    }

    @Test
    public void ppm_transport_should_retrieve_request_details_based_on_request_id() {
        String encodedAuthString = ppmTransport.encodeToBase64(
                ppmTransport.getEnvironment().getUser(),
                ppmTransport.getEnvironment().getPassword());
        JSONObject request;
        String requestId = "34260";

        request = ppmTransport.getRequestDetails(encodedAuthString, requestId);
        assertNotNull(request);
    }

    @Test
    public void ppm_transport_should_retrieve_requestIds_based_on_configuration() {
        String encodedAuthString = ppmTransport.encodeToBase64(
                ppmTransport.getEnvironment().getUser(),
                ppmTransport.getEnvironment().getPassword());
        List<String> configuredRequestTypes = new ArrayList();

        configuredRequestTypes = ppmTransport.getConfiguredRequestTypes(encodedAuthString);

        assertTrue(configuredRequestTypes.size() > 0);
    }

    @Test
    public void ppm_transport_should_retrieve_requests_to_be_parsed() {
        String encodedAuthString = ppmTransport.encodeToBase64(
                ppmTransport.getEnvironment().getUser(),
                ppmTransport.getEnvironment().getPassword());
        List<JSONObject> requests = new ArrayList();
        List<String> requestTypeIds = ppmTransport.getConfiguredRequestTypes(encodedAuthString);
        for (String id : requestTypeIds) {
            List<JSONObject> requestsByRequestType = ppmTransport.getRequests(encodedAuthString, id);
            if (requestsByRequestType != null) {
                requests.addAll(requestsByRequestType);
            }
        }
        assertTrue(requests.size() > 0);
    }

    @Test
    public void ppm_transport_should_retrieve_all_request_details_based_on_request_type() {
        String encodedAuthString = ppmTransport.encodeToBase64(
                ppmTransport.getEnvironment().getUser(),
                ppmTransport.getEnvironment().getPassword());
        List<JSONObject> requestsToBeParsed = new ArrayList();
        List<JSONObject> detailedRequests = new ArrayList();
        List<String> requestTypeIds = ppmTransport.getConfiguredRequestTypes(encodedAuthString);

        for (String id : requestTypeIds) {
            requestsToBeParsed.addAll(ppmTransport.getRequests(encodedAuthString, id));
        }

        for (JSONObject request : requestsToBeParsed) {
            JSONObject reqDetails = ppmTransport.getRequestDetails(encodedAuthString, request.getString("id"));
            if (reqDetails != null) {
                detailedRequests.add(reqDetails);
            }
        }

        assertTrue(detailedRequests.size() > 0);
    }
}
