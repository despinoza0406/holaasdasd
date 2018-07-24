package hubble.backend.api.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import hubble.backend.api.models.EnabledDisabledEntity;
import hubble.backend.api.interfaces.BusinessApplicationManager;
import hubble.backend.api.interfaces.RolAdminRequired;
import hubble.backend.api.interfaces.RolUserRequired;
import hubble.backend.api.interfaces.TokenRequired;
import hubble.backend.api.models.BusinessApplicationFrontend;
import hubble.backend.api.models.BusinessApplicationLigth;
import hubble.backend.business.services.interfaces.services.ApplicationService;
import hubble.backend.api.models.NewApplication;
import hubble.backend.storage.models.ApplicationStorage;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applications")
public class ApplicationsController {

    @Autowired
    private BusinessApplicationManager businessAppMgr;

    private final ApplicationService applicationService;

    public ApplicationsController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @CrossOrigin
    @TokenRequired
    @RolUserRequired
    @GetMapping(value = "/{id}")
    public BusinessApplicationFrontend getApplicationFrontend(HttpServletRequest req,
            @PathVariable("id") String applicationId,
            @RequestParam(value = "periodo", defaultValue = "default") String timePeriod) {

        
        BusinessApplicationFrontend applicationFrontend = businessAppMgr.getBusinessApplicationFrontendDistValues(applicationId, timePeriod);

        return applicationFrontend;
    }

    @CrossOrigin
    @TokenRequired
    @RolUserRequired
    @GetMapping(value = "/dashBoard")
    public List<BusinessApplicationFrontend> getApplications(HttpServletRequest req,
            @RequestParam("include-inactives") Optional<Boolean> includeInactives,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit,
            @RequestParam(value = "periodo", defaultValue = "default") String periodo) {
        return businessAppMgr.getBusinessApplicationsFrontend(includeInactives.orElse(false), periodo);
    }

    @CrossOrigin
    @TokenRequired
    @RolUserRequired
    @GetMapping(value = "/{id}/kpis")
    public KPIs getApplicationKPIs(@PathVariable("id") String appId) {
        return businessAppMgr.getKPIs(appId);
    }

    /** 
     * No tiene puesto rol required pq se usa con cualquiera de los dos!
     * @param req
     * @param includeInactives
     * @return 
     */
    @CrossOrigin
    @TokenRequired
    @GetMapping(value = "/ligth", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusinessApplicationLigth> getApplicationsLigth(HttpServletRequest req, @RequestParam("include-inactives") Optional<Boolean> includeInactives) {

        List<BusinessApplicationLigth> businessApplicationLigth = businessAppMgr.getApplicationsLigth(includeInactives.orElse(false));

        return businessApplicationLigth;
    }

    @CrossOrigin
    @TokenRequired
    @RolAdminRequired
    @PutMapping(value = "/enabled", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity habilitarDeshabilitar(@RequestBody EnabledDisabledEntity enabledDisabledEntity) {

        try {

            applicationService.enabledDisabled(enabledDisabledEntity.getId(), enabledDisabledEntity.isEnabled());
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Throwable t) {
            return new ResponseEntity(new hubble.backend.api.models.Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error", t.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @CrossOrigin
    @TokenRequired
    @RolAdminRequired
    @PutMapping(value = "/taskRunner/enabled")
    public ResponseEntity habilitarDeshabilitarTaskRunner(@RequestBody EnabledDisabledEntity enabledDisabledEntity) {

        try {
            applicationService.enabledDisabledTaskRunner(enabledDisabledEntity.getId(), enabledDisabledEntity.isEnabled());
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Throwable t) {
            return new ResponseEntity(new hubble.backend.api.models.Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error", t.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @CrossOrigin
    @TokenRequired
    @RolAdminRequired
    @PostMapping(path = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody NewApplication app) {

        try {
            ApplicationStorage created = applicationService.create(
                    app.getApplicationId(),
                    app.getName(),
                    app.getDescription()
            );
            return new ResponseEntity<>(
                    String.format("/applications/%s", created.getId()),
                    HttpStatus.CREATED
            );
        } catch (Throwable t) {
            return new ResponseEntity(new hubble.backend.api.models.Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error", t.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @TokenRequired
    @RolAdminRequired
    @GetMapping(value = "/details", produces = {MediaType.APPLICATION_JSON_VALUE})
    @JsonIgnoreProperties(ignoreUnknown = true)
    public @ResponseBody
    ApplicationStorage getById(@RequestParam("id") String id) {
        return this.applicationService.findById(id);
    }

    @CrossOrigin
    @TokenRequired
    @RolAdminRequired
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @JsonIgnoreProperties(ignoreUnknown = true)
    public ResponseEntity edit(@RequestBody JsonNode jsonNode) {
        try {

            this.applicationService.editApplicationFromJson(jsonNode);

            return new ResponseEntity<>(
                    "Aplicación modificada",
                    HttpStatus.OK
            );

        } catch (Throwable t) {
            return new ResponseEntity(new hubble.backend.api.models.Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error", t.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
