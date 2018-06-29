package hubble.backend.providers.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Primary
@PropertySource("classpath:config/alm.config.properties")
public class AlmConfigurationImpl implements AlmConfiguration {

    @Value("${alm.businessApplication.fieldName}")
    private String applicationFieldName;
    @Value("${alm.status.fieldName}")
    private String statusFieldName;
    @Value("${alm.transaction.fieldName}")
    private String transactionFieldName;
    @Value("${alm.provider.origin}")
    private String providerOrigin;
    @Value("${alm.provider.name}")
    private String providerName;
    @Value("${alm.status.openValues}")
    private String statusOpenValues;
    @Value("${alm.businessApplication.valuesToIdMap}")
    private String applicationValueToIdMap;


    @Override
    public String getApplicationFieldName() {
        return applicationFieldName;
    }

    @Override
    public String getStatusFieldName() {
        return statusFieldName;
    }

    @Override
    public String getTransactionFieldName() {
        return transactionFieldName;
    }

    public String getProviderOrigin() {
        return providerOrigin;
    }

    public void setProviderOrigin(String providerOrigin) {
        this.providerOrigin = providerOrigin;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Set<String> getStatusOpenValues() {
         String[] openValues = statusOpenValues.split(",");
         Set<String> openValuesSet = new HashSet<>(Arrays.asList(openValues));

         return openValuesSet;

    }

    public void setStatusOpenValues(String statusOpenValues) {
        this.statusOpenValues = statusOpenValues;
    }

    public HashMap<String, String> getApplicationValueToIdMap() {
        HashMap<String,String> appToIdMap= new HashMap<>();
        String[] applicationsIdMap = applicationValueToIdMap.split(",");
        for (String applicationId : applicationsIdMap) {
            appToIdMap.put(applicationId.split(":")[1],applicationId.split(":")[0]);
        }
        return appToIdMap;
    }

    public void setApplicationValueToIdMap(String applicationValueToIdMap) {
        this.applicationValueToIdMap = applicationValueToIdMap;
    }
}
