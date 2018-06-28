package hubble.backend.business.services.tests.kpis;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.implementations.operations.IssueOperationsImpl;
import hubble.backend.business.services.implementations.operations.rules.IssuesRulesOperationsImpl;
import hubble.backend.business.services.models.measures.rules.IssuesGroupRule;
import hubble.backend.business.services.tests.StorageTestsHelper;
import hubble.backend.business.services.tests.configurations.ServiceBaseConfigurationTest;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.IssueStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.IssueRepository;
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
public class IssuesGroupRulesOperationUnitTests {

    @Mock
    private IssueRepository issueRepository;
    @Mock
    private ApplicationRepository applicationRepository;
    @InjectMocks
    private IssueOperationsImpl issueOperation;
    @InjectMocks
    private IssuesRulesOperationsImpl issuesRuleOperation;
    @Spy
    private MapperConfiguration mapper;

    private List<IssueStorage> issueStorageList;
    private StorageTestsHelper storageHelper = new StorageTestsHelper();

    @Before
    public void Before() {
        issueStorageList = new ArrayList();
    }

    @Test
    public void issue_operation_should_be_instantiated() {
        assertNotNull(issuesRuleOperation);
    }

    @Test
    public void issue_operation_should_calculateKeyPerformanceIndicatorFor10_With_No_Results() {
        String applicationId = "1";
        issueStorageList = new ArrayList<>();

        when(issueRepository.findIssuesByApplicationIdAndDurationMonths(1, applicationId)).thenReturn(issueStorageList);

        int issuesKpi = issuesRuleOperation.calculateGroupRule(issueStorageList).intValue();

        assertEquals(0, issuesKpi);
    }

    @Test
    public void issue_operation_should_calculateKeyPerformanceIndicatorForAMonth() {
        String applicationId = "1";
        issueStorageList = storageHelper.getFakeListOfIssueStorage();

        when(issueRepository.findIssuesByApplicationIdAndDurationMonths(1, applicationId)).thenReturn(issueStorageList);

        int issuesKpi = issuesRuleOperation.calculateGroupRule(issueStorageList).intValue();

        assertEquals(5, issuesKpi);
    }

    @Test
    public void issue_operation_should_calculateLastMonthIssueGroupRule() {
        String applicationId = "1";
        issueStorageList = storageHelper.getFakeListOfIssueStorage();

        ApplicationStorage appStorage = new ApplicationStorage();
        appStorage.setId("1");
        appStorage.setApplicationName("fake-app");
        appStorage.setApplicationId("Home Banking");
        appStorage.setActive(true);
        appStorage.setApplicationConfigurationVersion(1);
        appStorage.setApplicationConfigurationVersion(1);
        when(applicationRepository.findApplicationById(applicationId)).thenReturn(appStorage);
        when(issueRepository.findIssuesByApplicationIdAndDurationMonths(1, applicationId)).thenReturn(issueStorageList);

        IssuesGroupRule issueGroupRule = issuesRuleOperation.calculateLastMonthGroupRuleByApplication("1");

        assertEquals(5, issueGroupRule.get(), 1d);
    }

}
