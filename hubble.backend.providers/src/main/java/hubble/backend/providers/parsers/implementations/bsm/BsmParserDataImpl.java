package hubble.backend.providers.parsers.implementations.bsm;

import hubble.backend.core.enums.Results;
import hubble.backend.providers.configurations.BSMConfiguration;
import hubble.backend.providers.configurations.factories.TaskRunnerExecutionFactory;
import hubble.backend.providers.configurations.mappers.bsm.BsmMapperConfiguration;
import hubble.backend.providers.models.bsm.BsmProviderModel;
import hubble.backend.providers.parsers.interfaces.bsm.BsmDataParser;
import hubble.backend.providers.transports.interfaces.BsmTransport;
import hubble.backend.storage.models.AvailabilityStorage;
import hubble.backend.storage.models.TaskRunnerExecution;
import hubble.backend.storage.repositories.AvailabilityRepository;
import java.util.ArrayList;
import java.util.List;
import javax.xml.soap.SOAPBody;

import hubble.backend.storage.repositories.TaskRunnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BsmParserDataImpl implements BsmDataParser {

    @Autowired
    private BSMConfiguration configuration;
    @Autowired
    private TaskRunnerExecutionFactory executionFactory;
    @Autowired
    private TaskRunnerRepository taskRunnerRepository;
    private BsmMapperConfiguration mapperConfifuration;
    private List<AvailabilityStorage> availabilitiesStorage;
    private AvailabilityRepository availabilityRepository;
    private BsmTransport bsmTransport;


    private final Logger logger = LoggerFactory.getLogger(BsmParserDataImpl.class);

    @Autowired
    public BsmParserDataImpl(
            BsmTransport bsmTransport,
            BsmMapperConfiguration mapperConfiguration,
            AvailabilityRepository availabilityRepository) {
        this.bsmTransport = bsmTransport;
        this.mapperConfifuration = mapperConfiguration;
        this.availabilityRepository = availabilityRepository;
            }

    @Override
    public void run() {

        if(configuration.taskEnabled()) {

            SOAPBody data = bsmTransport.getData();

            List<BsmProviderModel> transactions = parse(data);


            this.availabilitiesStorage = new ArrayList<>();
            if (transactions != null) {

                this.availabilitiesStorage = mapperConfifuration.mapToAvailabilitiesStorage(transactions);

                this.save(availabilitiesStorage);
            }
            for (String hubbleAppName : configuration.getApplicationValueToIdMap().keySet()) {
                if (availabilitiesStorage.stream().noneMatch(availability -> availability.getApplicationId().equals(hubbleAppName)) && !bsmTransport.getResult().equals(Results.RESULTS.FAILURE)){
                    taskRunnerRepository.save(executionFactory.createExecution("bsm", hubbleAppName, Results.RESULTS.WARNING,
                            "No se obtuvo ninguna muestra que se pudiera mappear a " + hubbleAppName));
                }else {
                    taskRunnerRepository.save(executionFactory.createExecution("bsm", hubbleAppName, bsmTransport.getResult(), bsmTransport.getError()));
                }
            }
        }
    }

    @Override
    public List<BsmProviderModel> parse(SOAPBody data) {

        if (data == null) {
            logger.warn("No se obtuvo data de BSM");
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
