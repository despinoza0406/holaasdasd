package hubble.backend.api.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Guelmy Díaz <guelmy.diaz.blanco@fit.com.ar>
 */
@RestController
@RequestMapping("/providers")
public class ProvidersController {

    private final ProvidersService providersService;

    @Autowired
    public ProvidersController(ProvidersService providersService) {
        this.providersService = providersService;
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void edit(@RequestBody JsonNode jsonNode) {
        try {
            this.providersService.editProviderFromJson(jsonNode);
        } catch (Exception ex) {
            Logger.getLogger(ProvidersController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody List<ProviderStorage> consultar() {
        return this.providersService.findAll();
    }
    

    @RequestMapping(value = "/provider", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @JsonIgnoreProperties(ignoreUnknown=true)
    public @ResponseBody ProviderStorage findById(@RequestParam("id") String id) {
        return this.providersService.findById(id);
    }

}