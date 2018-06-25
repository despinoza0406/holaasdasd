package hubble.backend.storage.models;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class AppPulse extends ProviderStorage<AppPulse.Environment, NoConfig> {

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

        private String url;
        private String client;
        private String secret;

        public Environment() {
        }

        public Environment(String url, String client, String secret) {
            this.url = url;
            this.client = client;
            this.secret = secret;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
        
        public AppPulse.Environment fromJson(JsonNode jsonNode) {

            this.url = jsonNode.get("url").asText();
            this.client = jsonNode.get("client").asText();
            this.secret = jsonNode.get("secret").asText();

            return this;
        }

    }

    public AppPulse() {
    }

    public AppPulse(boolean enabled, TaskRunner taskRunner, Environment environment) {
        super("apppulse", "AppPulse", enabled, taskRunner, environment, new NoConfig());
    }

}
