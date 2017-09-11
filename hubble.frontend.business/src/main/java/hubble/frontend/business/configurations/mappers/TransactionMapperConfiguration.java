package hubble.frontend.business.configurations.mappers;

import hubble.backend.business.services.models.TransactionDto;
import hubble.frontend.business.models.Transaction;
import java.lang.reflect.Type;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperConfiguration {

    private ModelMapper mapper;

    public TransactionMapperConfiguration() {
        mapper = new ModelMapper();
        this.mapper.addMappings(new TransactionPropertyMap());
    }

    public ModelMapper getMapper() {
        return mapper;
    }

    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Transaction mapToTransaction(TransactionDto transactionDto) {
        return mapper.map(transactionDto, Transaction.class);
    }

    public List<Transaction> mapToTransactionList(List<TransactionDto> transactionDtoList) {
        if (transactionDtoList == null) {
            return null;
        }

        Type transactionDtoTypeList = new TypeToken<List<Transaction>>() {
        }.getType();
        return mapper.map(transactionDtoList, transactionDtoTypeList);
    }
}