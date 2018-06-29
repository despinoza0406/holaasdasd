package hubble.backend.providers.tests.configurations.mappers;

import hubble.backend.providers.configurations.mappers.alm.AlmMapperConfiguration;
import hubble.backend.providers.models.alm.AlmDefectProviderModel;
import hubble.backend.storage.models.IssueStorage;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class AlmMapperConfigurationUnitTest {

    AlmMapperConfiguration almMapperConfiguration = new AlmMapperConfiguration();

    @Test
    public void alm_mapper_should_map_almprovidermodel_to_issuestorage() {
        //Assign
        AlmDefectProviderModel almProviderModel = mock(AlmDefectProviderModel.class);

        //Act
        when(almProviderModel.getAssignee()).thenReturn("fake-assignee");
        when(almProviderModel.getClosedDate()).thenReturn("2017-09-22 00:00:00");
        when(almProviderModel.getCorrectedOnRelease()).thenReturn("R5");
        when(almProviderModel.getDescription()).thenReturn("description");
        when(almProviderModel.getDetectedBy()).thenReturn("fake-user");
        when(almProviderModel.getDetectedOnRelease()).thenReturn("R2");
        when(almProviderModel.getId()).thenReturn(12);
        when(almProviderModel.getModifiedDate()).thenReturn("2017-08-30 10:39:02");
        when(almProviderModel.getPriority()).thenReturn("1 - High");
        when(almProviderModel.getProject()).thenReturn("fake-project");
        when(almProviderModel.getRegisteredDate()).thenReturn("2017-08-22");
        when(almProviderModel.getReproducible()).thenReturn("Y");
        when(almProviderModel.getSeverity()).thenReturn("2 - Medium");
        when(almProviderModel.getStatus()).thenReturn("Corrected");
        when(almProviderModel.getTitle()).thenReturn("fake-title");
        when(almProviderModel.getProviderName()).thenReturn("Alm installed on fake place");
        when(almProviderModel.getProviderOrigin()).thenReturn("Alm");
        when(almProviderModel.getBusinessApplication()).thenReturn("fake business application");

        IssueStorage issueStorage = almMapperConfiguration.maptoIssueStorage(almProviderModel);

        //Assert
        assertEquals("fake-assignee", issueStorage.getAssignee());
        String hole= issueStorage.getClosedDate().toString();
        assertTrue(issueStorage.getClosedDate().toString().startsWith("Fri Sep 22 00:00:00"));
        assertEquals("R5", issueStorage.getCorrectedOnRelease());
        assertEquals("description", issueStorage.getDescription());
        assertEquals("fake-user", issueStorage.getDetectedBy());
        assertEquals("R2", issueStorage.getDetectedOnRelease());
        assertEquals(12, issueStorage.getExternalId());
        assertTrue(issueStorage.getModifiedDate().toString().startsWith("Wed Aug 30"));
        assertEquals(1, issueStorage.getPriority());
        assertEquals("fake-user", issueStorage.getDetectedBy());
        assertTrue(issueStorage.isReproducible());
        assertEquals(2, issueStorage.getSeverity());
        assertEquals("Corrected", issueStorage.getStatus());
        assertEquals("fake-title", issueStorage.getTitle());
        assertEquals("Alm installed on fake place", issueStorage.getProviderName());
        assertEquals("Alm", issueStorage.getProviderOrigin());
        assertEquals("fake business application", issueStorage.getBusinessApplication());
    }

}
