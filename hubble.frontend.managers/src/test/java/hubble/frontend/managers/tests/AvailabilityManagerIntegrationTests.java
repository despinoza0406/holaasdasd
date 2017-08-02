package hubble.frontend.managers.tests;

import hubble.frontend.managers.interfaces.AvailabilityManager;
import hubble.frontend.managers.models.collections.Availability;
import hubble.frontend.managers.models.aggregations.AvailabilityBusinessApplicationAvg;
import hubble.frontend.managers.models.entities.BusinessApplication;
import hubble.frontend.managers.tests.configurations.BaseConfigurationTest;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BaseConfigurationTest.class)
public class AvailabilityManagerIntegrationTests {

    @Autowired
    AvailabilityManager availabilityManager;

    @Test
    public void availability_manager_should_be_instantiated() {
        //Assert
        assertNotNull(availabilityManager);
    }

    @Test
    public void availability_Manager_should_return_all_availabilities_from_db(){
        //Act
        List<Availability> availabilities = availabilityManager.findAllAvailabilities();

        //Assert
        assertNotNull(availabilities.size());
    }

    @Test
    public void availability_Manager_should_retrieve_lastHour_average_by_an_application(){
        //Assign
        String applicationId = "e071193b8376e06554eb2344173cb66d";
        //Act
        AvailabilityBusinessApplicationAvg applicationAvg = availabilityManager.findLastHourAverageByApplication(applicationId);

        //Assert
        assertEquals(applicationId, applicationAvg.getBusinessApplication().getId());
        assertEquals("BancoRipley - HomeBanking", applicationAvg.getBusinessApplication().getName());
        assertEquals(90, applicationAvg.getBusinessApplication().getAvailabilityThreshold());
        assertTrue(applicationAvg.getStatus().toString().equals("Success")
                    || applicationAvg.getStatus().toString().equals("Warning")
                    || applicationAvg.getStatus().toString().equals("Critical")
                    || applicationAvg.getStatus().toString().equals("Outlier")
                    || applicationAvg.getStatus().toString().equals("Error")
                    || applicationAvg.getStatus().toString().equals("No_Data")
        );
        assertEquals("1", applicationAvg.getBusinessApplication().getTimeZoneId());

        assertTrue("Error, wrong average, it is over 100%", applicationAvg.getAverage() <= 100);
        assertTrue("Error, wrong average, it's value is less than 0%", applicationAvg.getAverage() >= -1);
        //assertEquals(22, applicationAvg.getAverage());
        //assertEquals(2, applicationAvg.getBusinessApplication().getTransactionIds().size());
    }

    @Test
    public void availability_Manager_should_retrieve_lastHour_average_by_an_application_without_transactions(){
        //Assign
        String applicationId = "e3ccaa4d89e4f05650c6a371923e8796";
        //Act
        AvailabilityBusinessApplicationAvg applicationAvg = availabilityManager.findLastHourAverageByApplication(applicationId);

        //Assert
        assertEquals(applicationId, applicationAvg.getBusinessApplication().getId());
        assertEquals("BancoRipley - Plataforma Post Venta", applicationAvg.getBusinessApplication().getName());
        assertEquals(90, applicationAvg.getBusinessApplication().getAvailabilityThreshold());
        assertTrue(applicationAvg.getStatus().toString().equals("Success")
                    || applicationAvg.getStatus().toString().equals("Warning")
                    || applicationAvg.getStatus().toString().equals("Critical")
                    || applicationAvg.getStatus().toString().equals("Outlier")
                    || applicationAvg.getStatus().toString().equals("Error")
                    || applicationAvg.getStatus().toString().equals("No_Data")
        );
        assertEquals("1", applicationAvg.getBusinessApplication().getTimeZoneId());

        assertTrue("Error, wrong average, it is over 100%", applicationAvg.getAverage() <= 100);
        assertTrue("Error, wrong average, it's value is less than 0%", applicationAvg.getAverage() >= -1);
        //assertEquals(22, applicationAvg.getAverage());
        //assertEquals(2, applicationAvg.getBusinessApplication().getTransactionIds().size());
    }

    //e3ccaa4d89e4f05650c6a371923e8796

    @Test
    public void availability_Manager_should_return_all_aapplications(){
        //Act
        List<BusinessApplication> applications = availabilityManager.findAllApplications();

        //Assert
        assertEquals(3, applications.size());
    }
}
