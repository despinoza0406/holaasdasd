package hubble.backend.providers.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.print.DocFlavor;
import java.util.HashMap;

@Component
@Primary
@PropertySource("classpath:config/jira.config.properties")
public class JiraConfigurationImpl implements JiraConfiguration {
    @Value("${jira.projectKey}")
    private String projectKey;
    @Value("${jira.businessApplication.fieldName}")
    private String applicationFieldName;
    @Value("${jira.businessApplication.valuesToIdMap}")
    private String valuesToIdMap;

    @Override    
    public String getApplicationFieldName() {
        return applicationFieldName;
    }

    @Override
    public HashMap<String,String> getValuesToIdMap() {
        HashMap<String,String> appToIdMap= new HashMap<>();
        String[] applicationsIdMap = valuesToIdMap.split(",");
        for (String applicationId : applicationsIdMap) {
            appToIdMap.put(applicationId.split(":")[1],applicationId.split(":")[0]);
        }
        return appToIdMap;
    }

    @Override
    public boolean taskEnabled() {
        return true;
    }

    @Override
    public String getProjectKey() {
            return projectKey;
    }
}
