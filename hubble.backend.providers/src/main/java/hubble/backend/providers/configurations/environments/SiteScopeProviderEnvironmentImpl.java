package hubble.backend.providers.configurations.environments;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Primary
@PropertySource("classpath:config/sitescope.properties")
public class SiteScopeProviderEnvironmentImpl implements SiteScopeProviderEnvironment {

    @Value("${sitescope.host}")
    private String host;
    @Value("${sitescope.port}")
    private String port;
    @Value("${sitescope.userName}")
    private String userName;
    @Value("${sitescope.password}")
    private String password;



    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String getPort() {
        return port;
    }

    @Override
    public String getUser() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }


}
