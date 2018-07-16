package hubble.backend.api.controllers;

import hubble.backend.api.interfaces.BusinessApplicationManager;
import hubble.backend.api.interfaces.TokenRequired;
import hubble.backend.api.models.BusinessApplicationFrontend;
import hubble.backend.api.models.BusinessApplicationLigth;
import hubble.backend.api.models.BusinessApplicationProfile;
import hubble.backend.business.services.interfaces.services.ApplicationService;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import hubble.backend.storage.models.KPIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ApplicationsController {

    @Autowired
    private BusinessApplicationManager businessAppMgr;

    private final ApplicationService applicationService;

    public ApplicationsController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }


    @TokenRequired
    @GetMapping(value = "/applications/{id}")
    public BusinessApplicationFrontend getApplicationFrontend(HttpServletRequest req,
            @PathVariable("id") String applicationId,
            @RequestParam(value = "periodo", defaultValue = "default") String timePeriod) {

        BusinessApplicationFrontend applicationFrontend = businessAppMgr.getBusinessApplicationFrontendDistValues(applicationId, timePeriod);

        return applicationFrontend;
    }

 
    @TokenRequired
    @GetMapping(value = "/applications")
    public List<BusinessApplicationFrontend> getApplications(HttpServletRequest req,
            @RequestParam("include-inactives") Optional<Boolean> includeInactives,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit,
            @RequestParam(value = "periodo", defaultValue = "default") String periodo) {
        return businessAppMgr.getBusinessApplicationsFrontend(includeInactives.orElse(false), periodo);
    }

    @TokenRequired
    @GetMapping(value = "/applications/{id}/kpis")
    public KPIs getApplicationKPIs(@PathVariable("id") String appId) {
        return businessAppMgr.getKPIs(appId);
    }

    @TokenRequired
    @GetMapping(value = "/applications/ligth", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusinessApplicationLigth> getApplicationsLigth(HttpServletRequest req) {

        List<BusinessApplicationLigth> businessApplicationLigth = businessAppMgr.getApplicationsLigth();

        return businessApplicationLigth;
    }

    @TokenRequired
    @PutMapping(value = "/applications/enabled", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity habilitarDeshabilitar(@RequestBody EnabledDisabledEntity enabledDisabledEntity) {

        try {

            applicationService.enabledDisabled(enabledDisabledEntity.getId(), enabledDisabledEntity.isEnabled());
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Throwable t) {
            return new ResponseEntity(new hubble.backend.api.models.Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error", t.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @TokenRequired
    @PutMapping(value = "/applications/taskRunner/enabled")
    public ResponseEntity habilitarDeshabilitarTaskRunner(@RequestBody EnabledDisabledEntity enabledDisabledEntity) {

        try {

            applicationService.enabledDisabledTaskRunner(enabledDisabledEntity.getId(), enabledDisabledEntity.isEnabled());
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Throwable t) {
            return new ResponseEntity(new hubble.backend.api.models.Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error", t.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
