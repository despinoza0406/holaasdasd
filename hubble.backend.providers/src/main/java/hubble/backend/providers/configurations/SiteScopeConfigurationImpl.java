package hubble.backend.providers.configurations;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Primary
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

    @Override
    public HashMap<String,String> getApplicationValueToIdMap() {

        HashMap<String,String> appToIdMap= new HashMap<>();
        String[] applicationsIdMap = applicationValueToIdMap.split(",");
        for (String applicationId : applicationsIdMap) {
            appToIdMap.put(applicationId.split(":")[1],applicationId.split(":")[0]);
        }
        return appToIdMap;

    }

    @Override
    public boolean taskEnabled() {
        return true;
    }
}
