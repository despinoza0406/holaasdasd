package hubble.backend.providers.tests.configurations.mappers;


import hubble.backend.providers.configurations.mappers.sitescope.SiteScopeMapperConfiguration;
import hubble.backend.providers.models.sitescope.SiteScopeEventProviderModel;
import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.models.WorkItemStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SiteScopeMapperConfigurationUnitTest {

    SiteScopeMapperConfiguration siteScopeMapperConfiguration = new SiteScopeMapperConfiguration();


    @Test
    public void _mapper_should_map_sitescope_event_providermodel_to_event_storage() {
        //Assign
        SiteScopeEventProviderModel eventProviderModel = mock(SiteScopeEventProviderModel.class);

        //Act
        when(eventProviderModel.getSummary()).thenReturn("Hubble is so good");
        when(eventProviderModel.getStatus()).thenReturn("Good");
        when(eventProviderModel.getName()).thenReturn("Hubble");
        when(eventProviderModel.getUpdatedDate()).thenReturn(new Date(999999999));
        when(eventProviderModel.getType()).thenReturn("Groovy");
        when(eventProviderModel.getDescription()).thenReturn("fake-title");
        when(eventProviderModel.getProviderName()).thenReturn("Ppm installed on fake place");
        when(eventProviderModel.getProviderOrigin()).thenReturn("Ppm");
        when(eventProviderModel.getBusinessApplication()).thenReturn("fake business application");
        when(eventProviderModel.getApplicationId()).thenReturn("fake application id");

        EventStorage eventStorage = siteScopeMapperConfiguration.mapToEventStorage(eventProviderModel);

        //Assert
        assertEquals("Hubble is so good", eventStorage.getSummary());
        assertEquals("fake-title", eventStorage.getDescription());
        assertEquals("Good", eventStorage.getStatus());
        assertEquals(new Date(999999999), eventStorage.getUpdatedDate());
        assertEquals("Ppm installed on fake place", eventStorage.getProviderName());
        assertEquals("Ppm", eventStorage.getProviderOrigin());
        assertEquals("fake business application", eventStorage.getBusinessApplication());
    }
}
