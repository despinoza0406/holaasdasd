package hubble.backend.storage.models;

import java.util.Set;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class ALM extends ProviderStorage<ALM.Environment, ALM.Configuration> {

    public static class Environment {

        private String host;
        private int port;
        private String username;
        private String password;
        private String domain;
        private String project;

        public Environment() {
        }

        public Environment(String host, int port, String username, String password, String domain, String project) {
            this.host = host;
            this.port = port;
            this.username = username;
            this.password = password;
            this.domain = domain;
            this.project = project;
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

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

    }

    public static class Configuration {

        public static class Status {

            private String status;
            private Set<String> openValues;

            public Status() {
            }

            public Status(String status, Set<String> openValues) {
                this.status = status;
                this.openValues = openValues;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Set<String> getOpenValues() {
                return openValues;
            }

            public void setOpenValues(Set<String> openValues) {
                this.openValues = openValues;
            }
        }

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
        private Status status;
        private String transactionFieldName;
        private Provider provider;

        public Configuration() {
        }

        public Configuration(String businessApplicationFieldName, Status status, String transactionFieldName, Provider provider) {
            this.businessApplicationFieldName = businessApplicationFieldName;
            this.status = status;
            this.transactionFieldName = transactionFieldName;
            this.provider = provider;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Provider getProvider() {
            return provider;
        }

        public void setProvider(Provider provider) {
            this.provider = provider;
        }

    }

    public ALM() {
    }

    public ALM(boolean enabled, TaskRunner taskRunner, Environment environment, Configuration configuration) {
        super("alm", "ALM", enabled, taskRunner, environment, configuration);
    }

}
