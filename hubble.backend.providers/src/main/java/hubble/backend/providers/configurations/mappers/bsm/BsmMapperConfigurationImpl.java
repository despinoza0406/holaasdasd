package hubble.backend.providers.configurations.mappers.bsm;

import hubble.backend.providers.models.bsm.BsmProviderModel;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.AvailabilityStorage;
import java.util.ArrayList;
import java.util.List;
import javax.xml.soap.SOAPBody;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BsmMapperConfigurationImpl implements BsmMapperConfiguration {

    private ModelMapper mapper;

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

}
