package hubble.backend.business.services.tests.kpis;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.implementations.operations.WorkItemOperationsImpl;
import hubble.backend.business.services.implementations.operations.rules.WorkItemRulesOperationsImpl;
import hubble.backend.business.services.models.measures.rules.WorkItemsGroupRule;
import hubble.backend.business.services.tests.StorageTestsHelper;
import hubble.backend.business.services.tests.configurations.ServiceBaseConfigurationTest;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.WorkItemStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.WorkItemRepository;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ServiceBaseConfigurationTest.class)
public class WorkItemsGroupRulesOperationUnitTests {

    @Mock
    private WorkItemRepository workItemRepository;
    @Mock
    private ApplicationRepository applicationRepository;
    @InjectMocks
    private WorkItemOperationsImpl workItemOperation;
    @InjectMocks
    private WorkItemRulesOperationsImpl workItemsRuleOperation;
    @Spy
    private MapperConfiguration mapper;

    private List<WorkItemStorage> workItemsStorage;
    private StorageTestsHelper storageHelper = new StorageTestsHelper();

    @Before
    public void Before() {
        workItemsStorage = new ArrayList();
    }

    @Test
    public void issue_operation_should_be_instantiated() {
        assertNotNull(workItemsRuleOperation);
    }

    @Test
    public void workitem_operation_should_calculateKeyPerformanceIndicatorForAMonth() {
        String applicationId = "1";
        workItemsStorage = storageHelper.getFakeListOfWorkItemStorage();

        when(workItemRepository.findWorkItemsByApplicationIdAndDurationMonths(1, applicationId)).thenReturn(workItemsStorage);

        int workItemsKpi = workItemsRuleOperation.calculateGroupRule(workItemsStorage).intValue();

        assertEquals(33, workItemsKpi);
    }

    @Test
    public void workitem_operation_should_calculateLastMonthWorkItemGroupRule() {
        String applicationId = "1";
        workItemsStorage = storageHelper.getFakeListOfWorkItemStorage();

        ApplicationStorage appStorage = new ApplicationStorage();
        appStorage.setId("1");
        appStorage.setApplicationName("fake-app");
        appStorage.setApplicationId("Home Banking");
        appStorage.setActive(true);
        appStorage.setApplicationConfigurationVersion(1);
        appStorage.setApplicationConfigurationVersion(1);

        when(applicationRepository.findApplicationById(applicationId)).thenReturn(appStorage);
        when(workItemRepository.findWorkItemsByApplicationIdAndDurationMonths(1, applicationId)).thenReturn(workItemsStorage);

        WorkItemsGroupRule issueGroupRule = workItemsRuleOperation.calculateLastMonthGroupRuleByApplication("1");

        assertEquals(33, issueGroupRule.get(), 0.9d);
    }

    @Test
    public void workitem_operation_should_calculateLastWeekWorkItemGroupRule() {
        String applicationId = "1";
        workItemsStorage = storageHelper.getFakeListOfWorkItemStorageWeek();

        ApplicationStorage appStorage = new ApplicationStorage();
        appStorage.setId("1");
        appStorage.setApplicationName("fake-app");
        appStorage.setApplicationId("Home Banking");
        appStorage.setActive(true);
        appStorage.setApplicationConfigurationVersion(1);
        appStorage.setApplicationConfigurationVersion(1);

        when(applicationRepository.findApplicationById(applicationId)).thenReturn(appStorage);
        when(workItemRepository.findWorkItemsByApplicationIdAndDurationMinutes(CalendarHelper.ONE_WEEK, applicationId)).thenReturn(workItemsStorage);

        WorkItemsGroupRule issueGroupRule = workItemsRuleOperation.calculateLastWeekGroupRuleByApplication("1");

        assertEquals(4, issueGroupRule.get(), 0.9d);
    }

}
