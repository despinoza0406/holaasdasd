package hubble.backend.business.services.tests.kpis;

import hubble.backend.business.services.interfaces.services.IssueService;
import hubble.backend.business.services.models.Application;
import hubble.backend.business.services.tests.StorageTestsHelper;
import hubble.backend.business.services.tests.configurations.ServiceBaseConfigurationTest;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.storage.configurations.StorageComponentConfiguration;
import hubble.backend.storage.models.*;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.IssueRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceBaseConfigurationTest.class, StorageComponentConfiguration.class})
public class IssueServiceTest {

    @Autowired
    IssueService issueService;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    StorageTestsHelper helper = new StorageTestsHelper();

    ApplicationStorage applicationStorage;

    @Before
    public void createApplicationStorage(){

        applicationStorage = helper.getTestAppStorage("TEST APP");
    }

    @Test
    public void should_calculate_health_index() {
        issueRepository.deleteAll();
        IssueStorage issueStorage1 = new IssueStorage();
        issueStorage1.setBusinessApplicationId("TEST APP");
        issueStorage1.setStatus("To Do");
        issueStorage1.setTimestamp(DateHelper.getNHoursAgo(5));
        issueStorage1.setPriority(2);
        issueStorage1.setSeverity(3);
        IssueStorage issueStorage2 = new IssueStorage();
        issueStorage2.setTimestamp(DateHelper.getNHoursAgo(5));
        issueStorage2.setBusinessApplicationId("TEST APP");
        issueStorage2.setStatus("Nuevo");
        issueStorage2.setPriority(2);
        issueStorage2.setSeverity(3);
        issueRepository.save(issueStorage1);
        issueRepository.save(issueStorage2);
        double health = issueService.calculateHistoryLastDayKpiByApplication(applicationStorage);
        assertTrue(health > 0 && health < 10);
    }
}
