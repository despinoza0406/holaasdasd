package hubble.backend.providers.parsers.implementations.apppulse;

import com.fasterxml.jackson.databind.ObjectMapper;
import hubble.backend.providers.configurations.mappers.apppulse.AppPulseMapperConfiguration;
import hubble.backend.providers.models.apppulse.AvailabilityProviderModel;
import hubble.backend.providers.parsers.interfaces.apppulse.AppPulseActiveDataParser;
import hubble.backend.providers.transports.interfaces.AppPulseActiveTransport;
import hubble.backend.storage.models.AvailabilityStorage;
import hubble.backend.storage.repositories.AvailabilityRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import static org.apache.commons.lang.StringUtils.EMPTY;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppPulseActiveDataParserImpl implements AppPulseActiveDataParser {

    private AppPulseMapperConfiguration mapperConfifuration;
    private AppPulseActiveTransport appPulseActiveTransport;
    private List<AvailabilityStorage> availabilitiesStorage;
    private AvailabilityRepository availabilityRepository;

    private final Logger logger = LoggerFactory.getLogger(AppPulseActiveDataParserImpl.class);

    @Autowired
    public AppPulseActiveDataParserImpl(
            AppPulseActiveTransport appPulseActiveTransport,
            AppPulseMapperConfiguration mapperConfifuration,
            AvailabilityRepository availabilityRepository) {
        this.appPulseActiveTransport = appPulseActiveTransport;
        this.mapperConfifuration = mapperConfifuration;
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public AvailabilityProviderModel parse(JSONObject data) {

        if (data == null) {
            return null;
        }

        byte[] dataBytes = data.toString().getBytes();
        InputStream dataStream = new ByteArrayInputStream(dataBytes);

        AvailabilityProviderModel appPulseActivities = this.extract(dataStream);

        return appPulseActivities;
    }

    public AvailabilityProviderModel extract(InputStream appPulseTransactions) {

        ObjectMapper objMapper = new ObjectMapper();
        AvailabilityProviderModel records;
        try {
            records = objMapper.readValue(appPulseTransactions, AvailabilityProviderModel.class);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            return null;
        }

        return records;
    }

    public List<AvailabilityStorage> convert(AvailabilityProviderModel appPulseProv) {

        return this.mapperConfifuration.mapToAvailabilitiesStorage(appPulseProv);
    }

    public void save(List<AvailabilityStorage> appPulseRecords) {
        appPulseRecords.stream().forEach((availabilityRecordToSave) -> {
            if (!availabilityRepository.exist(availabilityRecordToSave)) {
                availabilityRepository.save(availabilityRecordToSave);
            }
        });
    }

    @Override
    public void run() {

        //healthy check.
        if (appPulseActiveTransport.getToken().equals(EMPTY)) {
            return;
        }

        appPulseActiveTransport.setLastRetrievedSequenceId(EMPTY); //Always from the beginning.
        boolean hasMoreData = true;
        while (hasMoreData) {

            JSONObject data = appPulseActiveTransport.getData();

            if (data == null || data.length() == 0) {
                break;
            }

            hasMoreData = appPulseActiveTransport.hasMoreData();

            AvailabilityProviderModel dataProviderModel = parse(data);
            this.availabilitiesStorage = convert(dataProviderModel);

            if (this.availabilitiesStorage == null) {
                return;
            }

            //Guardar.
            availabilitiesStorage.stream().forEach(availabilityStorage -> {

                if (!availabilityRepository.exist(availabilityStorage)) {
                    availabilityRepository.save(availabilityStorage);
                }
            });
        }
    }

    @Override
    public List<AvailabilityStorage> getAvailabilitiesStorage() {
        return availabilitiesStorage;
    }
}
