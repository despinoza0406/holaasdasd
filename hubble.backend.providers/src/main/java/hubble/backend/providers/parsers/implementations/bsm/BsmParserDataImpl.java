package hubble.backend.providers.parsers.implementations.bsm;

import hubble.backend.providers.configurations.BSMConfiguration;
import hubble.backend.providers.configurations.mappers.bsm.BsmMapperConfiguration;
import hubble.backend.providers.models.bsm.BsmProviderModel;
import hubble.backend.providers.parsers.interfaces.bsm.BsmDataParser;
import hubble.backend.providers.transports.interfaces.BsmTransport;
import hubble.backend.storage.models.AvailabilityStorage;
import hubble.backend.storage.repositories.AvailabilityRepository;
import java.util.ArrayList;
import java.util.List;
import javax.xml.soap.SOAPBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BsmParserDataImpl implements BsmDataParser {

    @Autowired
    private BSMConfiguration configuration;
    private BsmMapperConfiguration mapperConfifuration;
    private List<AvailabilityStorage> availabilitiesStorage;
    private AvailabilityRepository availabilityRepository;
    private BsmTransport bsmTransport;

    private final Logger logger = LoggerFactory.getLogger(BsmParserDataImpl.class);

    @Autowired
    public BsmParserDataImpl(
            BsmTransport appPulseActiveTransport,
            BsmMapperConfiguration mapperConfiguration,
            AvailabilityRepository availabilityRepository) {
        this.bsmTransport = appPulseActiveTransport;
        this.mapperConfifuration = mapperConfiguration;
        this.availabilityRepository = availabilityRepository;
            }

    @Override
    public void run() {

        if(configuration.taskEnabled()) {

            SOAPBody data = bsmTransport.getData();

            List<BsmProviderModel> transactions = parse(data);

            if (transactions == null) {
                return;
            }

            this.availabilitiesStorage = new ArrayList<>();
            this.availabilitiesStorage = mapperConfifuration.mapToAvailabilitiesStorage(transactions);

            this.save(availabilitiesStorage);
        }
    }

    @Override
    public List<BsmProviderModel> parse(SOAPBody data) {

        if (data == null) {
            return null;
        }

        List<BsmProviderModel> transactions = mapperConfifuration.mapDataToBsmProviderModel(data);

        return transactions;
    }

    @Override
    public List<AvailabilityStorage> getAvailabilitiesStorage() {
        return this.availabilitiesStorage;
    }

    public void save(List<AvailabilityStorage> bsmRecords) {
        bsmRecords.stream().forEach((availabilityRecordToSave) -> {
            if (!availabilityRepository.exist(availabilityRecordToSave)) {
                availabilityRepository.save(availabilityRecordToSave);
            }
        });
    }
}
