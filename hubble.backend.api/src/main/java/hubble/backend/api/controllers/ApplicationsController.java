package hubble.backend.api.controllers;

import hubble.backend.api.interfaces.BusinessApplicationManager;
import hubble.backend.api.models.BusinessApplication;
import hubble.backend.api.models.BusinessApplicationFrontend;
import hubble.backend.api.models.BusinessApplicationLigth;
import hubble.backend.api.models.BusinessApplicationProfile;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;

import hubble.backend.storage.models.KPIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ApplicationsController {

    @Autowired
    private BusinessApplicationManager businessAppMgr;

    @GetMapping(value = "/applications/{applicationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BusinessApplicationProfile get(HttpServletRequest req, @PathVariable String applicationId) {

        BusinessApplicationProfile applicationView = businessAppMgr.getBusinessApplicationView(applicationId);

        return applicationView;
    }

    @GetMapping(value = "/applications/{id}")
    public BusinessApplicationFrontend getApplicationFrontend(HttpServletRequest req,
                                                              @PathVariable("id") String applicationId,
                                                              @RequestParam(value = "periodo", defaultValue = "default") String timePeriod) {

        BusinessApplicationFrontend applicationFrontend = businessAppMgr.getBusinessApplicationFrontendDistValues(applicationId,timePeriod);

        return applicationFrontend;
    }

    //@GetMapping(value = "applications/all")
    public List<BusinessApplication> getAll(HttpServletRequest req) {

        List<BusinessApplication> applications = businessAppMgr.getAllApplications();

        return applications;
    }

    @GetMapping(value = "/applications")
    public List<BusinessApplicationFrontend> getApplications(HttpServletRequest req,
        @RequestParam("include-inactives") Optional<Boolean> includeInactives,
        @RequestParam("page") int page,
        @RequestParam("limit") int limit,
        @RequestParam(value = "periodo", defaultValue = "default") String periodo) {
        return businessAppMgr.getBusinessApplicationsFrontend(includeInactives.orElse(false),periodo);
    }

    @GetMapping(value = "/applications/{id}/kpis")
    public KPIs getApplicationKPIs(@PathVariable("id") String appId) {
        return businessAppMgr.getKPIs(appId);
    }
    
    @GetMapping(value = "/applications/ligth", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusinessApplicationLigth> getApplicationsLigth(HttpServletRequest req) {

        List<BusinessApplicationLigth> businessApplicationLigth = businessAppMgr.getApplicationsLigth();

        return businessApplicationLigth;
    }

    
}
