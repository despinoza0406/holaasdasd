package hubble.backend.providers.configurations.mappers.apppulse;

import hubble.backend.providers.models.apppulse.ApplicationProviderModel;
import hubble.backend.providers.models.apppulse.AvailabilityProviderModel;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.AvailabilityStorage;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AppPulseMapperConfiguration {

    private ModelMapper mapper;

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
}
