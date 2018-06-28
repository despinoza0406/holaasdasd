package hubble.backend.business.services.tests.configurations.mappers;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.models.business.ApplicationIndicators;
import hubble.backend.business.services.models.Application;
import hubble.backend.business.services.models.Availability;
import hubble.backend.business.services.models.Issue;
import hubble.backend.business.services.models.TransactionAvg;
import hubble.backend.business.services.models.Transaction;
import hubble.backend.business.services.models.WorkItem;
import hubble.backend.business.services.tests.AvailabilityHelper;
import hubble.backend.business.services.tests.StorageTestsHelper;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.AvailabilityStorage;
import hubble.backend.storage.models.IssueStorage;
import hubble.backend.storage.models.TransactionStorage;
import hubble.backend.storage.models.WorkItemStorage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class MapperConfigurationUnitTests {

    MapperConfiguration mapperConfiguration = new MapperConfiguration();
    AvailabilityHelper availabilityHelper = new AvailabilityHelper();
    StorageTestsHelper storageHelper = new StorageTestsHelper();

    @Test
    public void mapper_should_map_availabilitystorage_to_availabilitydto() {
        AvailabilityStorage availabilityStorage = mock(AvailabilityStorage.class);

        when(availabilityStorage.getApplicationId()).thenReturn("1");
        when(availabilityStorage.getApplicationName()).thenReturn("Ripley - HomeBanking");
        when(availabilityStorage.getAvailabilityStatus()).thenReturn("Success");
        when(availabilityStorage.getId()).thenReturn("asdasd54142356");
        when(availabilityStorage.getLocationId()).thenReturn("1");
        when(availabilityStorage.getLocationName()).thenReturn("Sonda Interna");
        when(availabilityStorage.getNumberOfErrors()).thenReturn(0);
        when(availabilityStorage.getProviderOrigin()).thenReturn("AppPulse Active");
        when(availabilityStorage.getTransactionId()).thenReturn("2");
        when(availabilityStorage.getTransactionName()).thenReturn("HomeBanking - Login");
        when(availabilityStorage.getTimeStamp()).thenReturn(new Date());
        when(availabilityStorage.getServerName()).thenReturn("Banco Ripley - App Pulse Active");
        when(availabilityStorage.getScriptName()).thenReturn("homebanking");
        when(availabilityStorage.getErrors()).thenReturn(null);

        Availability availabilityDto = mapperConfiguration.getMapper().map(availabilityStorage, Availability.class);

        Assert.assertEquals(availabilityStorage.getApplicationId(), availabilityDto.getApplicationId());
        Assert.assertEquals(availabilityStorage.getApplicationName(), availabilityDto.getApplicationName());
        Assert.assertEquals(availabilityStorage.getAvailabilityStatus(), availabilityDto.getAvailabilityStatus());
        Assert.assertEquals(availabilityStorage.getId(), availabilityDto.getId());
        Assert.assertEquals(availabilityStorage.getLocationId(), availabilityDto.getLocationId());
        Assert.assertEquals(availabilityStorage.getLocationName(), availabilityDto.getLocationName());
        Assert.assertEquals(availabilityStorage.getNumberOfErrors(), availabilityDto.getNumberOfErrors());
        Assert.assertEquals(availabilityStorage.getProviderOrigin(), availabilityDto.getProviderOrigin());
        Assert.assertEquals(availabilityStorage.getTransactionName(), availabilityDto.getTransactionName());
        Assert.assertEquals(availabilityStorage.getTimeStamp(), availabilityDto.getTimeStamp());
        Assert.assertEquals(availabilityStorage.getTransactionId(), availabilityDto.getTransactionId());
        Assert.assertEquals(availabilityStorage.getServerName(), availabilityDto.getServerName());
        Assert.assertEquals(availabilityStorage.getScriptName(), availabilityDto.getScriptName());
    }

    @Test
    public void mapper_convert_availabilityStorage_to_availabilityDto_model() {
        //Assign
        AvailabilityStorage availabilityStorage = availabilityHelper.mockOneAvailabilityStorage();

        //Act
        Availability availabilityDto = mapperConfiguration.mapToAvailabilityDto(availabilityStorage);

        //Assert
        assertNotNull(availabilityDto);
        assertEquals("1", availabilityDto.getApplicationId());
        assertEquals("BancoRipley - HomeBanking", availabilityDto.getApplicationName());
        assertEquals("Washington DC, AT&T", availabilityDto.getLocationName());
    }

    @Test
    public void mapper_convert_availabilityStorageList_to_availabilityDtoList_model() {
        //Assign
        List<AvailabilityStorage> availabilityStorageList = availabilityHelper.mockData();

        //Act
        List<Availability> availabilityDtoList = mapperConfiguration.mapToAvailabilityDtoList(availabilityStorageList);

        //Assert
        assertNotNull(availabilityDtoList);
        assertEquals("1", availabilityDtoList.get(0).getApplicationId());
        assertEquals("BancoRipley - HomeBanking", availabilityDtoList.get(0).getApplicationName());
        assertEquals("Washington DC, AT&T", availabilityDtoList.get(0).getLocationName());
    }

    @Test
    public void mapper_should_map_applicationStorage_to_applicationDto() {
        ApplicationStorage applicationStorage = mock(ApplicationStorage.class);

        when(applicationStorage.getApplicationId()).thenReturn("1234");
        when(applicationStorage.getApplicationName()).thenReturn("BancoRipley - HomeBanking");
        when(applicationStorage.isActive()).thenReturn(true);


        Application applicationDto = mapperConfiguration.mapToApplicationDto(applicationStorage);

        assertEquals("1234", applicationDto.getApplicationId());
        assertEquals("BancoRipley - HomeBanking", applicationDto.getApplicationName());
        assertEquals(true, applicationDto.isActive());
    }

    @Test
    public void mapper_should_map_transactionStorage_to_transactionDto() {
        TransactionStorage transactionStorage = mock(TransactionStorage.class);

        when(transactionStorage.getTransactionId()).thenReturn("1234");
        when(transactionStorage.getTransactionName()).thenReturn("Transaction Name");
        when(transactionStorage.getCriticalThreshold()).thenReturn(12000);
        when(transactionStorage.isAssigned()).thenReturn(true);
        when(transactionStorage.getOkThreshold()).thenReturn(8000);
        when(transactionStorage.getTransactionType()).thenReturn("script");
        when(transactionStorage.getScriptName()).thenReturn("Script Name");

        Transaction transactionDto = mapperConfiguration.mapToTransactionDto(transactionStorage);

        assertEquals("1234", transactionDto.getTransactionId());
        assertEquals("Transaction Name", transactionDto.getTransactionName());
        assertEquals(12000, transactionDto.getCriticalThreshold());
        assertEquals(true, transactionDto.isAssigned());
        assertEquals(8000, transactionDto.getOkThreshold());
        assertEquals("script", transactionDto.getTransactionType());
        assertEquals("Script Name", transactionDto.getScriptName());
    }

    @Test
    public void mapper_convert_applicationStorageList_to_applicationDtoList_model() {
        //Assign
        List<ApplicationStorage> applicationStorageList = new ArrayList();
        applicationStorageList.add(availabilityHelper.mockApplicationStorage());

        //Act
        List<Application> applicationDtoList = mapperConfiguration.mapToApplicationDtoList(applicationStorageList);

        //Assert
        assertNotNull(applicationDtoList);
        assertEquals("b566958ec4ff28028672780d15edcf56", applicationDtoList.get(0).getApplicationId());
        assertEquals("BancoRipley - HomeBanking", applicationDtoList.get(0).getApplicationName());
    }

    @Test
    public void mapper_convert_transactionStorageList_to_transactionDtoList_model() {
        //Assign
        List<TransactionStorage> transactionStorageList = availabilityHelper.mockTransactionStorage();

        //Act
        List<Transaction> transactionDtoList = mapperConfiguration.mapToTransactionDtoList(transactionStorageList);

        //Assert
        assertNotNull(transactionDtoList);
        assertEquals("2eae220e082697be3a0646400e5b54fa", transactionDtoList.get(0).getTransactionId());
        assertEquals("Auntenticacion Biometrica", transactionDtoList.get(0).getTransactionName());
    }

    @Test
    public void mapper_should_map_transactionStorage_to_transactionAvailabilityAverage() {
        TransactionStorage transactionStorage = mock(TransactionStorage.class);

        when(transactionStorage.getTransactionId()).thenReturn("1234");
        when(transactionStorage.getTransactionName()).thenReturn("Transaction Name");
        when(transactionStorage.getCriticalThreshold()).thenReturn(12000);
        when(transactionStorage.isAssigned()).thenReturn(true);
        when(transactionStorage.getOkThreshold()).thenReturn(8000);
        when(transactionStorage.getTransactionType()).thenReturn("script");
        when(transactionStorage.getScriptName()).thenReturn("Script Name");

        TransactionAvg transactionAvailabilityAvg = mapperConfiguration.mapToTransactionAvailabilityAvg(transactionStorage);

        assertEquals("1234", transactionAvailabilityAvg.getTransactionId());
        assertEquals("Transaction Name", transactionAvailabilityAvg.getTransactionName());
        assertEquals(12000, transactionAvailabilityAvg.getCriticalThreshold());
        assertEquals(true, transactionAvailabilityAvg.isAssigned());
        assertEquals(8000, transactionAvailabilityAvg.getOkThreshold());
        assertEquals("script", transactionAvailabilityAvg.getTransactionType());
        assertEquals("Script Name", transactionAvailabilityAvg.getScriptName());
    }

    @Test
    public void mapper_should_map_transactionStorage_to_applicationAvailabilityAverage() {
        ApplicationStorage applicationStorage = mock(ApplicationStorage.class);

        when(applicationStorage.getApplicationId()).thenReturn("1234");
        when(applicationStorage.getApplicationName()).thenReturn("BancoRipley - HomeBanking");
        when(applicationStorage.isActive()).thenReturn(true);


        ApplicationIndicators applicationAvailabilityAvg = mapperConfiguration.mapToApplicationIndicatorsDto(applicationStorage);

        assertEquals("1234", applicationAvailabilityAvg.getApplicationId());
        assertEquals("BancoRipley - HomeBanking", applicationAvailabilityAvg.getApplicationName());
        assertEquals(true, applicationAvailabilityAvg.isActive());
    }

    @Test
    public void mapper_convert_transactionStorageList_to_transactionAvailabilityAvgList_model() {
        //Assign
        List<TransactionStorage> transactionStorageList = availabilityHelper.mockTransactionStorage();

        //Act
        List<TransactionAvg> transactionAvailabilityAvgList = mapperConfiguration.mapToTransactionAvailabilityAvgList(transactionStorageList);

        //Assert
        assertNotNull(transactionAvailabilityAvgList);
        assertEquals("2eae220e082697be3a0646400e5b54fa", transactionAvailabilityAvgList.get(0).getTransactionId());
        assertEquals("Auntenticacion Biometrica", transactionAvailabilityAvgList.get(0).getTransactionName());
    }

    @Test
    public void mapper_convert_applicationStorageList_to_applicationAvailabilityAvgList_model() {
        //Assign
        List<ApplicationStorage> applicationStorageList = new ArrayList();
        applicationStorageList.add(availabilityHelper.mockApplicationStorage());

        //Act
        List<ApplicationIndicators> applicationAvailabilityAvgList = mapperConfiguration.mapToApplicationIndicatorsDtoList(applicationStorageList);

        //Assert
        assertNotNull(applicationAvailabilityAvgList);
        assertEquals("b566958ec4ff28028672780d15edcf56", applicationAvailabilityAvgList.get(0).getApplicationId());
        assertEquals("BancoRipley - HomeBanking", applicationAvailabilityAvgList.get(0).getApplicationName());
    }

    @Test
    public void mappers_should_return_null_from_null_parameters(){
        assertNull(mapperConfiguration.mapToApplicationIndicatorsDto(null));
        assertNull(mapperConfiguration.mapToApplicationIndicatorsDtoList(null));
        assertNull(mapperConfiguration.mapToApplicationDto(null));
        assertNull(mapperConfiguration.mapToApplicationDtoList(null));
        assertNull(mapperConfiguration.mapToAvailabilityDto(null));
        assertNull(mapperConfiguration.mapToAvailabilityDtoList(null));
        assertNull(mapperConfiguration.mapToIssueDto(null));
        assertNull(mapperConfiguration.mapToIssueDtoList(null));
        assertNull(mapperConfiguration.mapToPerformanceDto(null));
        assertNull(mapperConfiguration.mapToPerformanceDtoList(null));
        assertNull(mapperConfiguration.mapToTransactionAvailabilityAvg(null));
        assertNull(mapperConfiguration.mapToTransactionAvailabilityAvgList(null));
        assertNull(mapperConfiguration.mapToTransactionDto(null));
        assertNull(mapperConfiguration.mapToTransactionDtoList(null));
        assertNull(mapperConfiguration.mapToWorkItemDto(null));
        assertNull(mapperConfiguration.mapToWorkItemDtoList(null));
    }

    @Test
    public void mapper_convert_issueStorage_to_issueDto_model() {
        //Assign
        IssueStorage issueStorage = storageHelper.getFakeIssueStorage();

        //Act
        Issue issueDto = mapperConfiguration.mapToIssueDto(issueStorage);

        //Assert
        assertNotNull(issueDto);
        assertEquals("Benchmark Home Banking", issueDto.getBusinessApplicationId());
        assertEquals(1, issueDto.getExternalId());
    }

    @Test
    public void mapper_convert_workItemStorage_to_workItemDto_model() {
        //Assign
        WorkItemStorage workItemStorage = storageHelper.getFakeWorkItemStorage();

        //Act
        WorkItem workItemDto = mapperConfiguration.mapToWorkItemDto(workItemStorage);

        //Assert
        assertNotNull(workItemDto);
        assertEquals("Benchmark Home Banking", workItemDto.getBusinessApplicationId());
        assertEquals(1, workItemDto.getExternalId());
    }
}
