package hubble.backend.storage.models;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class BSM extends ProviderStorage<BSM.Environment, NoConfig> {

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

    }

    public BSM() {
    }

    public BSM(boolean enabled, TaskRunner taskRunner, Environment environment) {
        super("bsm", "BSM", enabled, taskRunner, environment, new NoConfig());
    }

}
