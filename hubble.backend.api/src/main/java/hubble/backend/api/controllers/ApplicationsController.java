package hubble.backend.api.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import hubble.backend.api.models.*;
import hubble.backend.api.interfaces.BusinessApplicationManager;
import hubble.backend.api.interfaces.RolAdminRequired;
import hubble.backend.api.interfaces.RolUserRequired;
import hubble.backend.api.interfaces.TokenRequired;
import hubble.backend.business.services.interfaces.services.ApplicationService;
import hubble.backend.core.utils.KpiHelper;
import hubble.backend.storage.models.ApplicationStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import hubble.backend.storage.models.KPIs;
import hubble.backend.storage.models.UserStorage;
import java.io.IOException;
import static java.util.stream.Collectors.toList;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
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
    public BusinessApplicationFrontend getApplicationFrontend(HttpServletRequest req, HttpServletResponse resp,
            @PathVariable("id") String applicationId,
            @RequestParam(value = "periodo", defaultValue = "default") String timePeriod) throws IOException {

        BusinessApplicationFrontend applicationFrontend = null;

        UserStorage userAuthenticated = (UserStorage) req.getAttribute("authenticated-user");

        if (validateUserPermissions(userAuthenticated) && validateUserApps(userAuthenticated, applicationId)) {
            applicationFrontend = businessAppMgr.getBusinessApplicationFrontendDistValues(applicationId, timePeriod);
        } else {
            sendMethodNotAllowed(resp);
        }

        return applicationFrontend;

    }

    @CrossOrigin
    @TokenRequired
    @RolUserRequired
    @GetMapping(value = "/dashBoard")
    public List<BusinessApplicationFrontend> getApplications(HttpServletRequest req, HttpServletResponse resp,
            @RequestParam("include-inactives") Optional<Boolean> includeInactives,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit,
            @RequestParam(value = "periodo", defaultValue = "default") String periodo) throws IOException {

        UserStorage userAuthenticated = (UserStorage) req.getAttribute("authenticated-user");

        List<BusinessApplicationFrontend> applicationFrontends = null;

        if (validateUserPermissions(userAuthenticated)) {
            applicationFrontends = businessAppMgr.getBusinessApplicationsFrontend(includeInactives.orElse(false), periodo);
            //Filtro por las aplicaciones q puede ver el usuario autenticado!
            applicationFrontends = applicationFrontends.stream().filter(app -> userAuthenticated.getApplications().stream().map(ApplicationStorage::getId).anyMatch(id -> id.equalsIgnoreCase(app.getId()))).collect(toList());
        } else {
            sendMethodNotAllowed(resp);
        }

        return applicationFrontends;

    }

    @CrossOrigin
    @TokenRequired
    @RolUserRequired
    @GetMapping(value = "/{id}/kpis")
    public KPIs getApplicationKPIs(HttpServletRequest req, HttpServletResponse resp, @PathVariable("id") String appId,
            @RequestParam(value = "periodo", required = false, defaultValue = "") String periodo) throws IOException {

        UserStorage userAuthenticated = (UserStorage) req.getAttribute("authenticated-user");
        KPIs kpis = null;

        if (validateUserPermissions(userAuthenticated) && validateUserApps(userAuthenticated, appId)) {
            kpis = businessAppMgr.getKPIs(appId, periodo);
        } else {
            sendMethodNotAllowed(resp);
        }
        return kpis;
    }

    @CrossOrigin
    @TokenRequired
    @RolUserRequired
    @PostMapping(value = "/{id}/{kpi}")
    public ResponseEntity getApplicationKPIGroupedData(HttpServletRequest req,
                                                             HttpServletResponse resp,
                                                             @PathVariable("id") String appId,
                                                             @PathVariable("id") String kpi,
                                                             @RequestBody JSONObject body) throws IOException {

        UserStorage userAuthenticated = (UserStorage) req.getAttribute("authenticated-user");

        if (validateUserPermissions(userAuthenticated) && validateUserApps(userAuthenticated, appId)) {
            switch (kpi) {
                case "performance":
                    List<AvailabilityTable> results = businessAppMgr.getAllAvailabilityByFilter(appId, body);
                    return new ResponseEntity(results, HttpStatus.OK);
            }
        } else {
            sendMethodNotAllowed(resp);
        }

        return null;
    }

    /**
     * No tiene puesto rol required pq se usa con cualquiera de los dos!
     *
     * @param req
     * @param includeInactives
     * @return
     */
    @CrossOrigin
    @RolAdminRequired
    @TokenRequired
    @GetMapping(value = "/ligthAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusinessApplicationLigth> getApplicationsLigthAdmin(HttpServletRequest req, @RequestParam("include-inactives") Optional<Boolean> includeInactives) {

        return businessAppMgr.getApplicationsLigth(includeInactives.orElse(false));

    }

    /**
     * No tiene puesto rol required pq se usa con cualquiera de los dos!
     *
     * @param req
     * @param resp
     * @param includeInactives
     * @return
     */
    @CrossOrigin
    @TokenRequired
    @RolUserRequired
    @GetMapping(value = "/ligth", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusinessApplicationLigth> getApplicationsLigth(HttpServletRequest req, HttpServletResponse resp, @RequestParam("include-inactives") Optional<Boolean> includeInactives) throws IOException {

        UserStorage userAuthenticated = (UserStorage) req.getAttribute("authenticated-user");
        List<BusinessApplicationLigth> businessApplicationLigth  = null;

        if (validateUserPermissions(userAuthenticated)) {
             businessApplicationLigth = businessAppMgr.getApplicationsLigth(includeInactives.orElse(false));
            //Filtro por las aplicaciones q puede ver el usuario autenticado!
            businessApplicationLigth = businessApplicationLigth.stream().filter(app -> userAuthenticated.getApplications().stream().map(ApplicationStorage::getId).anyMatch(id -> id.equalsIgnoreCase(app.getId()))).collect(toList());
        } else {
            sendMethodNotAllowed(resp);
        }

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

    private void sendMethodNotAllowed(HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "No Autorizado");
    }

    private boolean validateUserPermissions(UserStorage userAuthenticated) {
        return userAuthenticated != null && userAuthenticated.isUser();
    }

    private boolean validateUserApps(UserStorage userAuthenticated, String applicationId) {
        return userAuthenticated.hasAccess(applicationId);
    }

}
