package hubble.backend.providers.configurations.environments;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/sitescope.properties")
public class SiteScopeProviderEnviromentImpl implements SiteScopeProviderEnviroment {

    @Value("${sitescope.host}")
    private String host;
    @Value("${sitescope.port}")
    private String port;
    @Value("${sitescope.userName}")
    private String userName;
    @Value("${sitescope.password}")
    private String password;
    @Value("${sitescope.domain}")
    private String domain;
    @Value("${sitescope.project}")
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
