package hubble.backend.providers.configurations.environments;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Primary
@PropertySource("classpath:config/alm.properties")
public class AlmProviderEnvironmentImpl implements AlmProviderEnvironment {

    @Value("${alm.host}")
    private String host;
    @Value("${alm.port}")
    private String port;
    @Value("${alm.userName}")
    private String userName;
    @Value("${alm.password}")
    private String password;
    @Value("${alm.domain}")
    private String domain;
    @Value("${alm.project}")
    private String project;

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getPort() {
        return port;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public String getProject() {
        return project;
    }
}
