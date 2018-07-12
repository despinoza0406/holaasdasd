package hubble.backend.providers.configurations.mappers.bsm;

import hubble.backend.providers.configurations.BSMConfiguration;
import hubble.backend.providers.models.bsm.BsmProviderModel;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.AvailabilityStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.xml.soap.SOAPBody;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BsmMapperConfigurationImpl implements BsmMapperConfiguration {

    private ModelMapper mapper;
    @Autowired
    private BSMConfiguration configuration;

    private final Logger logger = LoggerFactory.getLogger(BsmMapperConfiguration.class);

    public BsmMapperConfigurationImpl() {
        mapper = new ModelMapper();
        this.mapper.addMappings(new AvailabilityPropertyMap());
        this.mapper.addMappings(new ApplicationPropertyMap());
    }

    @Override
    public ModelMapper getMapper() {
        return mapper;
    }

    @Override
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<AvailabilityStorage> mapToAvailabilitiesStorage(List<BsmProviderModel> bsmProviderModels) {

        if (bsmProviderModels == null) {
            return null;
        }

        List<AvailabilityStorage> availabilitiesRecordsToBeSaved = new ArrayList<>();
        bsmProviderModels.forEach(item -> {
            AvailabilityStorage newBsmRecord = new AvailabilityStorage();

            this.mapper.map(item, newBsmRecord);
            availabilitiesRecordsToBeSaved.add(newBsmRecord);
        });

        return availabilitiesRecordsToBeSaved;
    }

    @Override
    public List<ApplicationStorage> mapToApplicationsStorage(List<BsmProviderModel> bsmProviderModels) {

        if (bsmProviderModels == null) {
            return null;
        }

        List<ApplicationStorage> applicationsRecordsToBeSaved = new ArrayList<>();
        bsmProviderModels.forEach(item -> {
            ApplicationStorage newBsmRecord = new ApplicationStorage();

            this.mapper.map(item, newBsmRecord);
            applicationsRecordsToBeSaved.add(newBsmRecord);
        });

        return applicationsRecordsToBeSaved;
    }

    @Override
    public List<BsmProviderModel> mapDataToBsmProviderModel(SOAPBody data) {
        if (data == null) {
            return null;
        }

        String content = data.getFirstChild().getFirstChild().getChildNodes().item(0).getNodeValue();
        List<BsmProviderModel> transactions = new ArrayList<>();

        String[] row = content.split("\\r?\\n");
        for (int i = 1; i < row.length; i++) {
            String[] record = row[i].split(",");
            BsmProviderModel newBsmProviderModel = new BsmProviderModel();
            newBsmProviderModel.setProfile_name(record[0]);
            newBsmProviderModel.setSztransactionname(record[1]);
            newBsmProviderModel.setSzlocationname(record[2]);
            newBsmProviderModel.setSzstatusname(record[3]);
            newBsmProviderModel.setIcomponenterrorcount(Integer.parseInt(record[4]));
            newBsmProviderModel.setTime_stamp((long) Double.parseDouble(record[5]));
            newBsmProviderModel.setSzscriptname(record[6]);
            newBsmProviderModel.setDresponsetime((int) Double.parseDouble(record[7]));
            newBsmProviderModel.setDgreenthreshold((int) Double.parseDouble(record[8]));
            newBsmProviderModel.setDredthreshold((int) Double.parseDouble(record[9]));
            newBsmProviderModel.setApplicationId(resolveApplicationIdFromConfiguration(newBsmProviderModel.getProfile_name()));

            transactions.add(newBsmProviderModel);
        }

        return transactions;
    }

    @Override
    public List<BsmProviderModel> mapApplicationsToBsmProviderModel(SOAPBody data) {

        if (data == null
                || data.getFirstChild() == null
                || data.getFirstChild().getFirstChild() == null) {
            return null;
                }
        String content = data.getFirstChild().getFirstChild().getChildNodes().item(0).getNodeValue();
        List<BsmProviderModel> transactions = new ArrayList<>();

        String[] row = content.split("\\r?\\n");
        for (int i = 1; i < row.length; i++) {
            String[] record = row[i].split(",");
            BsmProviderModel newBsmProviderModel = new BsmProviderModel();
            newBsmProviderModel.setProfile_name(record[0]);
//            newBsmProviderModel.setDgreenthreshold((int) Double.parseDouble(record[1]));
//            newBsmProviderModel.setDredthreshold((int) Double.parseDouble(record[2]));
            transactions.add(newBsmProviderModel);
        }

        return transactions;
    }

    private String resolveApplicationIdFromConfiguration(String applicationName) {
        HashMap<String,String> applicationsIdMap = configuration.getApplicationValueToIdMap();
        Set<String> keySet = applicationsIdMap.keySet();
        for (String key : keySet) {
            if (applicationName.equals(applicationsIdMap.get(key))) {
                return key;
            }
        }

        logger.warn("BSM field for applications and ids map not correctly"
                + " configured in properties file for specified app name: "
                + applicationName);
        return null;
    }

}
