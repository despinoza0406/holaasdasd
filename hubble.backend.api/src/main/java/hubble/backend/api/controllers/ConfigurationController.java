package hubble.backend.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ConfigurationController {

    @GetMapping("/configuration/indexes")
    public ResponseEntity getIndexes() {
        
    }
}
