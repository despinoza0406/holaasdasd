package hubble.backend.api.controllers;

import hubble.backend.core.utils.CalculationHelper;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ConfigurationController {

    @GetMapping(value = "/configuration/indexes")
    public ResponseEntity<Object> getIndexes() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("criticalIndex", CalculationHelper.getCriticalIndex());
        jsonObject.put("warningIndex", CalculationHelper.getWarningIndex());
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    @PostMapping(value = "/configuration/indexes")
    public ResponseEntity<Void> getIndexes(@RequestBody JSONObject jsonObject) {
        CalculationHelper.setCriticalIndex(Double.valueOf(jsonObject.get("criticalIndex").toString()));
        CalculationHelper.setWarningIndex(Double.valueOf(jsonObject.get("warningIndex").toString()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
