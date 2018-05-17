package hubble.backend.providers.configurations;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/sitescope.config.properties")
public class SiteScopeConfigurationImpl implements SiteScopeConfiguration {
    @Value("${sitescope.businessApplication.fieldName}")
    private String applicationFieldName;

    @Value("${sitescope.businessApplication.valuesToIdMap}")
    private String applicationValueToIdMap;

    @Override
    public String getApplicationFieldName() {
        return applicationFieldName;
    }

    public void setApplicationValueToIdMap(String applicationValueToIdMap) {
        this.applicationValueToIdMap = applicationValueToIdMap;
    }
}
