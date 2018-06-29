package hubble.backend.providers.configurations.environments;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Primary
@PropertySource("classpath:config/bsm.properties")
public class BsmProviderEnvironmentImpl implements BsmProviderEnvironment{
    
    @Value("${bsm.soapEndpointUrl}")
    private String soapEndpointUrl;
    @Value("${bsm.soapAction}")
    private String soapAction;
    @Value("${bsm.userName}")
    private String userName;
    @Value("${bsm.password}")
    private String password;
    
    @Override
    public String getSoapEndpointUrl() {
        return soapEndpointUrl;
    }

    @Override
    public String getSoapAction() {
        return soapAction;
    }
    
    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }


}
