package hubble.backend.storage.models;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class SiteScope extends ProviderStorage<SiteScope.Environment, SiteScope.Configuration> {

    public SiteScope() {
    }

    public SiteScope(boolean enabled, TaskRunner taskRunner, Environment environment, Configuration configuration) {
        super("sitescope", "SiteScope", enabled, taskRunner, environment, configuration);
    }
    
    public static class Environment {

        private String host;
        private int port;
        private String username;
        private String password;

        public Environment() {
        }

        public Environment(String host, int port, String username, String password) {
            this.host = host;
            this.port = port;
            this.username = username;
            this.password = password;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
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

            this.host = jsonNode.get("host").asText();
            this.port = jsonNode.get("port").asInt();
            this.username = jsonNode.get("username").asText();
            this.password = jsonNode.get("password").asText();

            return this;
        }

    }
    
     public static class Configuration {

        private String businessApplicationFieldName;

        public Configuration() {
        }

        public Configuration(String businessApplicationFieldName) {           
            this.businessApplicationFieldName = businessApplicationFieldName;
        }

       
        public String getBusinessApplicationFieldName() {
            return businessApplicationFieldName;
        }

        public void setBusinessApplicationFieldName(String businessApplicationFieldName) {
            this.businessApplicationFieldName = businessApplicationFieldName;
        }

        public Configuration fromJson(JsonNode jsonNode) {

            this.businessApplicationFieldName = jsonNode.get("businessApplicationFieldName").asText();
            return this;
        }

    }

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

}
