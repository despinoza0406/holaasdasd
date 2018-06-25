package hubble.backend.storage.models;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class BSM extends ProviderStorage<BSM.Environment, NoConfig> {

    @Override
    public ProviderStorage fromJson(JsonNode jsonNode) {
        
          JsonNode configuration = jsonNode.get("configuration");
        this.getConfiguration().fromJson(configuration);

        JsonNode enviroment = jsonNode.get("environment");
        this.getEnvironment().fromJson(enviroment);

        this.setName(jsonNode.get("name").asText());
        this.setEnabled(jsonNode.get("enabled").asBoolean());

        JsonNode taskRunner = jsonNode.get("taskRunner");
        this.getTaskRunner().fromJson(taskRunner);

        return this;
    }

    public static class Environment {

        private SoapEndpoint soap;
        private String username;
        private String password;

        public Environment() {
        }

        public Environment(SoapEndpoint soap, String username, String password) {
            this.soap = soap;
            this.username = username;
            this.password = password;
        }

        public SoapEndpoint getSoap() {
            return soap;
        }

        public void setSoap(SoapEndpoint soap) {
            this.soap = soap;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Environment fromJson(JsonNode jsonNode) {

            JsonNode soapEndpoint = jsonNode.get("soap");
            
            this.getSoap().fromJson(soapEndpoint);
            
            this.username = jsonNode.get("username").asText();
            this.password = jsonNode.get("password").asText();

            return this;
        }
    }

    public BSM() {
    }

    public BSM(boolean enabled, TaskRunner taskRunner, Environment environment) {
        super("bsm", "BSM", enabled, taskRunner, environment, new NoConfig());
    }

}
