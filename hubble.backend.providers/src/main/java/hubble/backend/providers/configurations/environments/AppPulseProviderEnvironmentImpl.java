package hubble.backend.providers.configurations.environments;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Provides a specification of configuration for HP App Pulse in Default
 * Environment.
 *
 * @author Ismael J. Tisminetzky
 */
@Component
@Primary
@PropertySource("classpath:config/apppulse.properties")
public class AppPulseProviderEnvironmentImpl implements ProviderEnvironment {

    @Value("${app.url}")
    private String url;

    @Value("${app.client}")
    private String client;

    @Value("${app.secret}")
    private String secret;

    @Override
    public String getClient() {
        return client;
    }

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public String getUrl() {
        return url;
    }

}
