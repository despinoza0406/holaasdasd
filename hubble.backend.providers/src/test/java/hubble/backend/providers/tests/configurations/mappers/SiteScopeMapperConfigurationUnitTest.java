package hubble.backend.providers.tests.configurations.mappers;


import hubble.backend.providers.configurations.mappers.sitescope.SiteScopeMapperConfiguration;
import hubble.backend.storage.models.WorkItemStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SiteScopeMapperConfigurationUnitTest {

    SiteScopeMapperConfiguration siteScopeMapperConfiguration = new SiteScopeMapperConfiguration();


    @Test
    public void _mapper_should_map_ppmprovidermodel_to_workitemstorage() {
 /*       //Assign
        PpmProgramIssueProviderModel ppmProviderModel = mock(PpmProgramIssueProviderModel.class);

        //Act
        when(ppmProviderModel.getAssignee()).thenReturn("fake-assignee");
        when(ppmProviderModel.getDetailedDescription()).thenReturn("description");
        when(ppmProviderModel.getCreatedBy()).thenReturn("fake-user");
        when(ppmProviderModel.getId()).thenReturn(12);
        when(ppmProviderModel.getLastUpdateDate()).thenReturn("2017-02-18T00:09:37.000-03:00 ");
        when(ppmProviderModel.getPriority()).thenReturn("Alto/a");
        when(ppmProviderModel.getPercentComplete()).thenReturn(35);
        when(ppmProviderModel.getCreatedDate()).thenReturn("2017-02-17T00:10:35.000-03:00");
        when(ppmProviderModel.getStatusCode()).thenReturn("Corrected");
        when(ppmProviderModel.getDescription()).thenReturn("fake-title");
        when(ppmProviderModel.getProviderName()).thenReturn("Ppm installed on fake place");
        when(ppmProviderModel.getProviderOrigin()).thenReturn("Ppm");
        when(ppmProviderModel.getBusinessApplication()).thenReturn("fake business application");
        when(ppmProviderModel.getApplicationId()).thenReturn("fake application id");
        when(ppmProviderModel.getTransaction()).thenReturn("fake transaction");
        when(ppmProviderModel.getTransactionId()).thenReturn("fake transaction id");

        WorkItemStorage workItemStorage = SiteScopeMapperConfiguration.mapToWorkItemStorage(ppmProviderModel);

        //Assert
        assertEquals("fake-assignee", workItemStorage.getAssignee());
        assertEquals("description", workItemStorage.getDescription());
        assertEquals("fake-user", workItemStorage.getRegisteredBy());
        assertEquals(12, workItemStorage.getExternalId());
        assertTrue(workItemStorage.getModifiedDate().toString().startsWith("Sat Feb 18"));
        assertEquals(2, workItemStorage.getPriority());
        assertEquals(35, workItemStorage.getPercentCompleted());
        assertTrue(workItemStorage.getRegisteredDate().toString().startsWith("Fri Feb 17"));
        assertEquals("Corrected", workItemStorage.getStatus());
        assertEquals("fake-title", workItemStorage.getTitle());
        assertEquals("Ppm installed on fake place", workItemStorage.getProviderName());
        assertEquals("Ppm", workItemStorage.getProviderOrigin());
        assertEquals("fake business application", workItemStorage.getBusinessApplication());
        assertEquals("fake application id", workItemStorage.getBusinessApplicationId());
        assertEquals("fake transaction", workItemStorage.getTransaction());
        assertEquals("fake transaction id", workItemStorage.getTransactionId());
*/
    }
}
