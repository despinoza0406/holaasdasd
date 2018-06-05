package hubble.backend.api.controllers;

import hubble.backend.api.interfaces.BusinessApplicationManager;
import hubble.backend.api.models.BusinessApplication;
import hubble.backend.api.models.BusinessApplicationFrontend;
import hubble.backend.api.models.BusinessApplicationProfile;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
    public BusinessApplicationProfile get(HttpServletRequest req, @PathVariable String applicationId) {

        BusinessApplicationProfile applicationView = businessAppMgr.getBusinessApplicationView(applicationId);

        return applicationView;
    }

    @GetMapping(value = "applications/all")
    public List<BusinessApplication> getAll(HttpServletRequest req) {

        List<BusinessApplication> applications = businessAppMgr.getAllApplications();

        return applications;
    }

    @CrossOrigin(origins = "http://localhost:8888")
    @GetMapping(value = "applications")
    public List<BusinessApplicationProfile> getApplicationsProfiles(HttpServletRequest req,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {

        List<BusinessApplicationProfile> applications = businessAppMgr.getBusinessApplicationsPageLimit(page, limit);

        return applications;
    }

    @GetMapping(value = "frontendApplications/applications/")
    @CrossOrigin(origins = "http://localhost:8888")
    public List<BusinessApplicationFrontend> getApplications(HttpServletRequest req,
             @RequestParam("page") int page,
             @RequestParam("limit") int limit) {
        List<BusinessApplicationFrontend> applicationFrontends = businessAppMgr.getBusinessApplicationsFrontend();
        return applicationFrontends;
    }
}
