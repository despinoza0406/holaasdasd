package hubble.backend.storage.models;

import java.util.Set;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class PPM extends ProviderStorage<PPM.Environment, PPM.Configuration> {

    public static class Environment {

        private String host;
        private Integer port;
        private String username;
        private String password;

        public Environment() {
        }

        public Environment(String host, Integer port, String username, String password) {
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

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
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

    }

    public static class Configuration {

        public static class Provider {

            private String origin;
            private String name;

            public Provider() {
            }

            public Provider(String origin, String name) {
                this.origin = origin;
                this.name = name;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }

        private String businessApplicationFieldName;
        private String transactionFieldName;
        private Provider provider;
        private Set<Integer> requestTypeIds;

        public Configuration() {
        }

        public Configuration(String businessApplicationFieldName, String transactionFieldName, Provider provider, Set<Integer> requestTypeIds) {
            this.businessApplicationFieldName = businessApplicationFieldName;
            this.transactionFieldName = transactionFieldName;
            this.provider = provider;
            this.requestTypeIds = requestTypeIds;
        }

        public String getBusinessApplicationFieldName() {
            return businessApplicationFieldName;
        }

        public void setBusinessApplicationFieldName(String businessApplicationFieldName) {
            this.businessApplicationFieldName = businessApplicationFieldName;
        }

        public String getTransactionFieldName() {
            return transactionFieldName;
        }

        public void setTransactionFieldName(String transactionFieldName) {
            this.transactionFieldName = transactionFieldName;
        }

        public Provider getProvider() {
            return provider;
        }

        public void setProvider(Provider provider) {
            this.provider = provider;
        }

        public Set<Integer> getRequestTypeIds() {
            return requestTypeIds;
        }

        public void setRequestTypeIds(Set<Integer> requestTypeIds) {
            this.requestTypeIds = requestTypeIds;
        }

    }

    public PPM() {
    }

    public PPM(boolean enabled, TaskRunner taskRunner, Environment environment, Configuration configuration) {
        super("ppm", "PPM", enabled, taskRunner, environment, configuration);
    }

}
