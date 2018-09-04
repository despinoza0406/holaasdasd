package hubble.backend.tasksrunner.communication.controllers;

import hubble.backend.providers.transports.implementations.alm.AlmTransportImpl;
import hubble.backend.providers.transports.implementations.bsm.BsmTransportImpl;
import hubble.backend.providers.transports.implementations.jira.JiraTransportImpl;
import hubble.backend.providers.transports.implementations.ppm.PpmTransportImpl;
import hubble.backend.providers.transports.implementations.sitescope.SiteScopeTransportImpl;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestConnectionController {

    public TestConnectionController() {}

    @CrossOrigin
    @RequestMapping(value = "/providers/alm/test", method = RequestMethod.POST)
    public ResponseEntity<Void> testALMConnection(@RequestBody JSONObject request) {
        AlmTransportImpl almTransport = new AlmTransportImpl();
        boolean loginCorrect = almTransport.testConnection(
                request.get("host").toString(),
                request.get("port").toString(),
                request.get("user").toString(),
                request.get("password").toString());

        HttpStatus httpStatus;

        if (loginCorrect) {
            httpStatus = HttpStatus.OK;
            almTransport.logout(request.get("host").toString(), request.get("port").toString());
        } else {
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity<>(httpStatus);
    }

    @CrossOrigin
    @RequestMapping(value = "/providers/bsm/test", method = RequestMethod.POST)
    public ResponseEntity<Void> testBSMConnection(@RequestBody JSONObject request) {
        BsmTransportImpl bsmTransport = new BsmTransportImpl();
        boolean loginCorrect = bsmTransport.testConnection(
                request.get("soapEndpointUri").toString(),
                request.get("user").toString(),
                request.get("password").toString(),
                request.get("soapAction").toString());

        HttpStatus httpStatus;

        if (loginCorrect) {
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity<>(httpStatus);
    }

    @CrossOrigin
    @RequestMapping(value = "/providers/jira/test", method = RequestMethod.POST)
    public ResponseEntity<Void> testJIRAConnection(@RequestBody JSONObject request) {
        JiraTransportImpl jiraTransport = new JiraTransportImpl();
        boolean loginCorrect = jiraTransport.testConnection(
                request.get("host").toString(),
                request.get("port").toString(),
                request.get("user").toString(),
                request.get("password").toString());

        HttpStatus httpStatus;

        if (loginCorrect) {
            httpStatus = HttpStatus.OK;
            jiraTransport.logout(
                    request.get("host").toString(),
                    request.get("port").toString(),
                    request.get("user").toString(),
                    request.get("password").toString());
        } else {
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity<>(httpStatus);
    }

    @CrossOrigin
    @RequestMapping(value = "/providers/ppm/test", method = RequestMethod.POST)
    public ResponseEntity<Void> testPPMConnection(@RequestBody JSONObject request) {
        PpmTransportImpl ppmTransport = new PpmTransportImpl();
        boolean loginCorrect = ppmTransport.testConnection(
                request.get("host").toString(),
                request.get("port").toString(),
                request.get("user").toString(),
                request.get("password").toString());

        HttpStatus httpStatus;

        if (loginCorrect) {
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity<>(httpStatus);
    }

    @CrossOrigin
    @RequestMapping(value = "/providers/sitescope/test", method = RequestMethod.POST)
    public ResponseEntity<Void> testSitescopeConnection(@RequestBody JSONObject request) {
        SiteScopeTransportImpl siteScopeTransport = new SiteScopeTransportImpl();
        boolean loginCorrect = siteScopeTransport.testConnection(
                request.get("host").toString(),
                request.get("port").toString(),
                request.get("user").toString(),
                request.get("password").toString());

        HttpStatus httpStatus;

        if (loginCorrect) {
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity<>(httpStatus);
    }
}
