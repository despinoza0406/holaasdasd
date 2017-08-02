package hubble.frontend.managers.configurations.mappers;

import hubble.backend.business.services.models.ApplicationDto;
import hubble.backend.business.services.models.TransactionDto;
import hubble.frontend.managers.models.entities.BusinessApplication;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapperConfiguration {

    private ModelMapper mapper;

    public ApplicationMapperConfiguration() {
        mapper = new ModelMapper();
        this.mapper.addMappings(new ApplicationPropertyMap());
    }

    public ModelMapper getMapper() {
        return mapper;
    }

    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public BusinessApplication mapToBusinessApplication(ApplicationDto applicationDto){
        BusinessApplication businessApplication = mapper.map(applicationDto, BusinessApplication.class);
        List<String> transactionIds = new ArrayList();
        if(applicationDto.getTransactions()!=null)
            for(TransactionDto transaction : applicationDto.getTransactions()){
                transactionIds.add(transaction.getTransactionId());
            }
        businessApplication.setTransactionIds(transactionIds);
        return businessApplication;
    }

    public List<BusinessApplication> mapToBusinessApplicationList(List<ApplicationDto> applicationDtoList){
        Type applicationDtoTypeList = new TypeToken<List<BusinessApplication>>() {}.getType();
        return mapper.map(applicationDtoList, applicationDtoTypeList);
    }
}
