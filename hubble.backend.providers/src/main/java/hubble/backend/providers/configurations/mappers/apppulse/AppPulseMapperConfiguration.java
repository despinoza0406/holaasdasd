package hubble.backend.providers.configurations.mappers.apppulse;

import hubble.backend.providers.configurations.AppPulseConfiguration;
import hubble.backend.providers.models.apppulse.ApplicationProviderModel;
import hubble.backend.providers.models.apppulse.AvailabilityProviderModel;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.AvailabilityStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppPulseMapperConfiguration {

    private ModelMapper mapper;

    @Autowired
    private AppPulseConfiguration configuration;

    private final Logger logger = LoggerFactory.getLogger(AppPulseMapperConfiguration.class);
    public AppPulseMapperConfiguration() {
        mapper = new ModelMapper();
        this.mapper.addMappings(new AvailabilityPropertyMap());
    }

    public ModelMapper getMapper() {
        return mapper;
    }

    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<AvailabilityStorage> mapToAvailabilitiesStorage(AvailabilityProviderModel appPulseProv) {

        if (appPulseProv == null) {
            return null;
        }

        List<AvailabilityStorage> availabilitiesRecordsToBeSaved = new ArrayList<>();
        appPulseProv.getData().forEach(item -> {
            AvailabilityStorage newAppPulseRecord = new AvailabilityStorage();

            this.mapper.map(item, newAppPulseRecord);
            newAppPulseRecord.setApplicationId(resolveApplicationIdFromConfiguration(newAppPulseRecord.getApplicationId()));
            availabilitiesRecordsToBeSaved.add(newAppPulseRecord);
        });

        return availabilitiesRecordsToBeSaved;
    }

    public List<ApplicationStorage> mapToApplicationsStorage(ApplicationProviderModel appPulseProv) {

        if (appPulseProv == null) {
            return null;
        }

        List<ApplicationStorage> applicationsRecordsToBeSaved = new ArrayList<>();
        appPulseProv.getApplications().forEach(item -> {
            ApplicationStorage newAppPulseRecord = new ApplicationStorage();

            this.mapper.map(item, newAppPulseRecord);
            applicationsRecordsToBeSaved.add(newAppPulseRecord);
        });

        return applicationsRecordsToBeSaved;
    }

    private String resolveApplicationIdFromConfiguration(String applicationName) {
        HashMap<String,String> applicationsIdMap = configuration.getApplicationValueToIdMap();
        Set<String> keySet = applicationsIdMap.keySet();
        for (String key : keySet) {
            if (applicationName.equals(applicationsIdMap.get(key))) {
                return key;
            }
        }

        logger.warn("AppPulse field for applications and ids map not correctly"
                + " configured in properties file for specified app name: "
                + applicationName);
        return null;
    }
}
