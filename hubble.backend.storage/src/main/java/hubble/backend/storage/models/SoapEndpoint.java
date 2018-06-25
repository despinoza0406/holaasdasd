package hubble.backend.storage.models;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Configuración de un endpoint SoapEndpoint.
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class SoapEndpoint {

    private String endpoint;
    private String action;

    public SoapEndpoint() {
    }

    public SoapEndpoint(String endpoint, String action) {
        this.endpoint = endpoint;
        this.action = action;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
      public SoapEndpoint fromJson(JsonNode jsonNode) {

            this.endpoint = jsonNode.get("endpoint").asText();
            this.action = jsonNode.get("action").asText();

            return this;
        }
    

}
