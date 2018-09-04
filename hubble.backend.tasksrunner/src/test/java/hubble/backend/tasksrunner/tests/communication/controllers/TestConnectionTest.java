package hubble.backend.tasksrunner.tests.communication.controllers;

import hubble.backend.tasksrunner.communication.controllers.TestConnectionController;
import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertTrue;

public class TestConnectionTest {

    static TestConnectionController testConnectionController;

    @BeforeClass
    public static void init(){
        testConnectionController = new TestConnectionController();
    }

    @Test
    public void alm_should_test_ok_connection() {
        JSONObject fakeRequest = new JSONObject();
        fakeRequest.put("host", "10.10.20.170");
        fakeRequest.put("port", 8080);
        fakeRequest.put("user", "matias.lapalma");
        fakeRequest.put("password", "");
        ResponseEntity responseEntity = testConnectionController.testALMConnection(fakeRequest);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void alm_should_test_fail_connection() {
        JSONObject fakeRequest = new JSONObject();
        fakeRequest.put("host", "10.10.20.17");
        fakeRequest.put("port", 8080);
        fakeRequest.put("user", "matias.lapalma");
        fakeRequest.put("password", "");
        ResponseEntity responseEntity = testConnectionController.testALMConnection(fakeRequest);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.CONFLICT);
    }

    @Test
    public void bsm_should_test_ok_connection() {
        JSONObject fakeRequest = new JSONObject();
        fakeRequest.put("soapEndpointUri", "http://t-srvbacapplsar.tsoftglobal.com/topaz/gdeopenapi/services/GdeWsOpenAPI?wsdl");
        fakeRequest.put("user", "jorg");
        fakeRequest.put("password", "admin");
        fakeRequest.put("soapAction", "http://t-srvbacapplsar.tsoftglobal.com/topaz/gdeopenapi/services/GdeWsOpenAPI");
        ResponseEntity responseEntity = testConnectionController.testBSMConnection(fakeRequest);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void bsm_should_test_fail_connection() {
        JSONObject fakeRequest = new JSONObject();
        fakeRequest.put("soapEndpointUri", "http://t-srvbacapplsar.tsoftglobal.com/topaz/gdeopenapi//GdeWsOpenAPI?wsdl");
        fakeRequest.put("user", "admin");
        fakeRequest.put("password", "admin");
        fakeRequest.put("soapAction", "http://t-srvbacapplsar.tsoftglobal.com/topaz/gdeopenapi/services/GdeWsOpenAPI");
        ResponseEntity responseEntity = testConnectionController.testBSMConnection(fakeRequest);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.CONFLICT);
    }

    @Test
    public void jira_should_test_ok_connection() {
        JSONObject fakeRequest = new JSONObject();
        fakeRequest.put("host", "10.10.20.175");
        fakeRequest.put("port", "8888");
        fakeRequest.put("user", "tsoftlatam");
        fakeRequest.put("password", "Tsoft2018");
        ResponseEntity responseEntity = testConnectionController.testJIRAConnection(fakeRequest);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void jira_should_test_fail_connection() {
        JSONObject fakeRequest = new JSONObject();
        fakeRequest.put("host", "10.10.20.175");
        fakeRequest.put("port", "8888");
        fakeRequest.put("user", "wronguser");
        fakeRequest.put("password", "Tsoft2018");
        ResponseEntity responseEntity = testConnectionController.testJIRAConnection(fakeRequest);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.CONFLICT);
    }

    @Test
    public void ppm_should_test_ok_connection() {
        JSONObject fakeRequest = new JSONObject();
        fakeRequest.put("host", "demoppm.tsoftglobal.com");
        fakeRequest.put("port", "8888");
        fakeRequest.put("user", "admin");
        fakeRequest.put("password", "ppm931");
        ResponseEntity responseEntity = testConnectionController.testPPMConnection(fakeRequest);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void ppm_should_test_fail_connection() {
        JSONObject fakeRequest = new JSONObject();
        fakeRequest.put("host", "demoppm.tsoftglobal.com");
        fakeRequest.put("port", "8888");
        fakeRequest.put("user", "admin");
        fakeRequest.put("password", "wrongPass");
        ResponseEntity responseEntity = testConnectionController.testPPMConnection(fakeRequest);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.CONFLICT);
    }

    @Test
    public void sitescope_should_test_ok_connection() {
        JSONObject fakeRequest = new JSONObject();
        fakeRequest.put("host", "10.10.20.248");
        fakeRequest.put("port", "8080");
        fakeRequest.put("user", "root");
        fakeRequest.put("password", "root");
        ResponseEntity responseEntity = testConnectionController.testSitescopeConnection(fakeRequest);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void sitescope_should_test_fail_connection() {
        JSONObject fakeRequest = new JSONObject();
        fakeRequest.put("host", "10.10.20.248");
        fakeRequest.put("port", "8080");
        fakeRequest.put("user", "wronguser");
        fakeRequest.put("password", "wrongPass");
        ResponseEntity responseEntity = testConnectionController.testSitescopeConnection(fakeRequest);
        assertTrue(responseEntity.getStatusCode() == HttpStatus.CONFLICT);
    }
}
