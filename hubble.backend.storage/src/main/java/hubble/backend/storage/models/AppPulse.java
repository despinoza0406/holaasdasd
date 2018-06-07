package hubble.backend.storage.models;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class AppPulse extends ProviderStorage<AppPulse.Environment, NoConfig> {

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

    }

    public AppPulse() {
    }

    public AppPulse(boolean enabled, TaskRunner taskRunner, Environment environment) {
        super("apppulse", "AppPulse", enabled, taskRunner, environment, new NoConfig());
    }


}
