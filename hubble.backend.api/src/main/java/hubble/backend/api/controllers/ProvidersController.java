package hubble.backend.api.controllers;

import hubble.backend.api.models.EnabledDisabledEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import hubble.backend.api.interfaces.TokenRequired;
import hubble.backend.business.services.interfaces.services.ProvidersService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import hubble.backend.storage.models.ProviderStorage;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Guelmy Díaz <guelmy.diaz.blanco@fit.com.ar>
 */
@RestController
@RequestMapping("/providers")
@CrossOrigin
public class ProvidersController {

    private final ProvidersService providersService;

    @Autowired
    public ProvidersController(ProvidersService providersService) {
        this.providersService = providersService;
    }

    @TokenRequired
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void edit(@RequestBody JsonNode jsonNode) {
        try {
            this.providersService.editProviderFromJson(jsonNode);
        } catch (Exception ex) {
            Logger.getLogger(ProvidersController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @TokenRequired
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    List<ProviderStorage> get(@RequestParam("include-inactives") Optional<Boolean> includeInactives) {
        return this.providersService.findAll(includeInactives.orElse(false));
    }

    @TokenRequired
    @GetMapping(value = "/provider",produces = {MediaType.APPLICATION_JSON_VALUE})
    @JsonIgnoreProperties(ignoreUnknown = true)
    public @ResponseBody
    ProviderStorage getById(@RequestParam("id") String id) {
        return this.providersService.findById(id);
    }

    @TokenRequired
    @PutMapping(value = "/enabled")
    public ResponseEntity habilitarDeshabilitar(@RequestBody EnabledDisabledEntity enabledDisabledEntity) {

        try {
            
            providersService.enabledDisabled(enabledDisabledEntity.getId(), enabledDisabledEntity.isEnabled());
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Throwable t) {
           return new ResponseEntity(new hubble.backend.api.models.Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error", t.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @TokenRequired
    @PutMapping(value = "/taskRunner/enabled")
    public ResponseEntity habilitarDeshabilitarTaskRunner(@RequestBody EnabledDisabledEntity enabledDisabledEntity) {

        try {
            
            providersService.enabledDisabled(enabledDisabledEntity.getId(), enabledDisabledEntity.isEnabled());
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Throwable t) {
           return new ResponseEntity(new hubble.backend.api.models.Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error", t.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
}
