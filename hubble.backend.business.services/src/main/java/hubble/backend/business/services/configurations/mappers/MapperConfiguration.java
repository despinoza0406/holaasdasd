package hubble.backend.business.services.configurations.mappers;

import hubble.backend.business.services.models.*;
import hubble.backend.business.services.models.business.ApplicationIndicators;
import hubble.backend.storage.models.*;

import java.lang.reflect.Type;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

@Component
public class MapperConfiguration {

    private ModelMapper mapper;

    public MapperConfiguration() {
        mapper = new ModelMapper();
        this.mapper.addMappings(new IssuePropertyMap());
        this.mapper.addMappings(new WorkItemPropertyMap());
    }

    public ModelMapper getMapper() {
        return mapper;
    }

    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public List<Availability> mapToAvailabilityDtoList(List<AvailabilityStorage> availabilityStorageList) {
        if (availabilityStorageList == null) {
            return null;
        }

        Type availabilityDtoTypeList = new TypeToken<List<Availability>>() {
        }.getType();
        return mapper.map(availabilityStorageList, availabilityDtoTypeList);
    }

    public Availability mapToAvailabilityDto(AvailabilityStorage availabilityStorage) {
        if (availabilityStorage == null) {
            return null;
        }

        return mapper.map(availabilityStorage, Availability.class);
    }

    public Application mapToApplicationDto(ApplicationStorage applicationStorage) {
        if (applicationStorage == null) {
            return null;
        }
        return mapper.map(applicationStorage, Application.class);
    }

    public List<Application> mapToApplicationDtoList(List<ApplicationStorage> applicationStorageList) {
        if (applicationStorageList == null) {
            return null;
        }

        Type applicationDtoTypeList = new TypeToken<List<Application>>() {
        }.getType();
        return mapper.map(applicationStorageList, applicationDtoTypeList);
    }

    public Transaction mapToTransactionDto(TransactionStorage transactionStorage) {
        if (transactionStorage == null) {
            return null;
        }

        return mapper.map(transactionStorage, Transaction.class);
    }

    public List<Transaction> mapToTransactionDtoList(List<TransactionStorage> transactionStorageList) {
        if (transactionStorageList == null) {
            return null;
        }

        Type transactionDtoTypeList = new TypeToken<List<Transaction>>() {
        }.getType();
        return mapper.map(transactionStorageList, transactionDtoTypeList);
    }

    public ApplicationIndicators mapToApplicationIndicatorsDto(ApplicationStorage applicationStorage) {
        if (applicationStorage == null) {
            return null;
        }

        return mapper.map(applicationStorage, ApplicationIndicators.class);
    }

    public List<ApplicationIndicators> mapToApplicationIndicatorsDtoList(List<ApplicationStorage> applicationStorageList) {
        if (applicationStorageList == null) {
            return null;
        }

        Type applicationAvailabilityAvgTypeList = new TypeToken<List<ApplicationIndicators>>() {
        }.getType();
        return mapper.map(applicationStorageList, applicationAvailabilityAvgTypeList);
    }

    public List<TransactionAvg> mapToTransactionAvailabilityAvgList(List<TransactionStorage> transactionStorageList) {
        if (transactionStorageList == null) {
            return null;
        }
        Type transactionAvailabilityAvgTypeList = new TypeToken<List<TransactionAvg>>() {
        }.getType();
        return mapper.map(transactionStorageList, transactionAvailabilityAvgTypeList);
    }

    public TransactionAvg mapToTransactionAvailabilityAvg(TransactionStorage transactionStorage) {
        if (transactionStorage == null) {
            return null;
        }

        return mapper.map(transactionStorage, TransactionAvg.class);
    }

    public List<Performance> mapToPerformanceDtoList(List<AvailabilityStorage> availabilityStorageList) {
        if (availabilityStorageList == null) {
            return null;
        }
        Type availabilityDtoTypeList = new TypeToken<List<Availability>>() {
        }.getType();
        return mapper.map(availabilityStorageList, availabilityDtoTypeList);
    }

    public Performance mapToPerformanceDto(AvailabilityStorage availabilityStorage) {
        if (availabilityStorage == null) {
            return null;
        }
        return mapper.map(availabilityStorage, Performance.class);
    }

    public Issue mapToIssueDto(IssueStorage issueStorage) {
        if (issueStorage == null) {
            return null;
        }
        return mapper.map(issueStorage, Issue.class);
    }

    public List<Issue> mapToIssueDtoList(List<IssueStorage> issueStorageList) {
        if (issueStorageList == null) {
            return null;
        }
        Type issueDtoTypeList = new TypeToken<List<Issue>>() {
        }.getType();
        return mapper.map(issueStorageList, issueDtoTypeList);
    }

    public WorkItem mapToWorkItemDto(WorkItemStorage workItemStorage) {
        if (workItemStorage == null) {
            return null;
        }
        return mapper.map(workItemStorage, WorkItem.class);
    }

    public List<WorkItem> mapToWorkItemDtoList(List<WorkItemStorage> workItemStorageList) {
        if (workItemStorageList == null) {
            return null;
        }
        Type issueDtoTypeList = new TypeToken<List<WorkItem>>() {
        }.getType();
        return mapper.map(workItemStorageList, issueDtoTypeList);
    }

    public List<Event> mapToEventDtoList(List<EventStorage> eventStorageList){
        if (eventStorageList == null) {
            return null;
        }
        Type issueDtoTypeList = new TypeToken<List<Event>>() {
        }.getType();
        return mapper.map(eventStorageList, issueDtoTypeList);
    }

}
