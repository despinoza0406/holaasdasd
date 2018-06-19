package hubble.backend.api.controllers;

import hubble.backend.api.interfaces.BusinessApplicationManager;
import hubble.backend.api.models.BusinessApplication;
import hubble.backend.api.models.BusinessApplicationFrontend;
import hubble.backend.api.models.BusinessApplicationProfile;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationsController {

    @Autowired
    private BusinessApplicationManager businessAppMgr;

    @GetMapping(value = "/applications/{applicationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin//(origins = "http://localhost:8888")
    public BusinessApplicationProfile get(HttpServletRequest req, @PathVariable String applicationId) {

        BusinessApplicationProfile applicationView = businessAppMgr.getBusinessApplicationView(applicationId);

        return applicationView;
    }

    @GetMapping(value = "/applications/{id}")
    @CrossOrigin//(origins = "http://localhost:8888")
    public BusinessApplicationFrontend getApplicationFrontend(HttpServletRequest req, @PathParam("id") String applicationId) {

        BusinessApplicationFrontend applicationFrontend = businessAppMgr.getBusinessApplicationFrontendDistValues(applicationId);

        return applicationFrontend;
    }

    //@GetMapping(value = "applications/all")
    public List<BusinessApplication> getAll(HttpServletRequest req) {

        List<BusinessApplication> applications = businessAppMgr.getAllApplications();

        return applications;
    }

    @GetMapping(value = "/applications")
    @CrossOrigin//(origins = "http://localhost:8888")
    public List<BusinessApplicationFrontend> getApplications(HttpServletRequest req,
        @RequestParam("include-inactives") Optional<Boolean> includeInactives,
        @RequestParam("page") int page,
        @RequestParam("limit") int limit) {
        return businessAppMgr.getBusinessApplicationsFrontend(includeInactives.orElse(false));
    }
}
