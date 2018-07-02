package hubble.backend.providers.configurations.environments;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Primary
@PropertySource("classpath:config/ppm.properties")
public class PpmProviderEnvironmentImpl implements PpmProviderEnvironment {

    @Value("${ppm.host}")
    private String host;
    @Value("${ppm.port}")
    private String port;
    @Value("${ppm.user}")
    private String user;
    @Value("${ppm.password}")
    private String password;
    
    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public String getPort() {
        return this.port;
    }

    @Override
    public String getUser() {
        return this.user;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
