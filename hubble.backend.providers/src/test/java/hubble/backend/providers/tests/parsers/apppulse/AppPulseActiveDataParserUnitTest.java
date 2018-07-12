package hubble.backend.providers.tests.parsers.apppulse;

import hubble.backend.providers.configurations.environments.AppPulseProviderEnvironmentMongoImpl;
import hubble.backend.providers.configurations.mappers.apppulse.AppPulseMapperConfiguration;
import hubble.backend.providers.parsers.implementations.apppulse.AppPulseActiveDataParserImpl;
import hubble.backend.providers.tests.AppPulseBaseUnitTests;
import hubble.backend.providers.transports.implementations.apppulse.AppPulseActiveTransportImpl;
import hubble.backend.storage.models.AvailabilityStorage;
import hubble.backend.storage.repositories.AvailabilityRepository;
import java.util.List;
import static org.apache.commons.lang.StringUtils.EMPTY;
import org.json.JSONObject;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AppPulseActiveDataParserUnitTest extends AppPulseBaseUnitTests {

    @Spy
    private AppPulseProviderEnvironmentMongoImpl environment = new AppPulseProviderEnvironmentMongoImpl();
    @Spy
    private AppPulseMapperConfiguration mapperConfifuration;
    @Spy
    private AppPulseActiveTransportImpl appPulseActiveTransport = new AppPulseActiveTransportImpl(environment);
    @Mock
    private AvailabilityRepository appPulseActiveRepository;

    @InjectMocks
    private AppPulseActiveDataParserImpl appPulseActiveParser;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    public void AppPulseActiveParser_when_it_runs_should_map_records_from_providermodel_to_storage() {

        //Assign
        doReturn("fake-token").when(appPulseActiveTransport).getToken();
        doReturn("fake-token").when(appPulseActiveTransport).getTokenValue();
        doReturn(AppPulseBaseUnitTests.getFakeAppPulseJson()).when(appPulseActiveTransport).getData();

        //Act
        appPulseActiveParser.run();

        //Assert
        verify(mapperConfifuration, times(1)).mapToAvailabilitiesStorage(any());
        verify(appPulseActiveTransport).getData();
    }

    @Test
    public void AppPulseActiveParser_when_it_runs_and_there_are_more_data_should_get_it_all() {

        //Assign
        doReturn("fake-token").when(appPulseActiveTransport).getToken();
        doReturn("fake-token").when(appPulseActiveTransport).getTokenValue();
        doReturn(AppPulseBaseUnitTests.getFakeAppPulseJson())
                .doReturn(AppPulseBaseUnitTests.getFakeAppPulseJson()).when(appPulseActiveTransport).getData();
        doReturn(true).doReturn(false).when(appPulseActiveTransport).hasMoreData();

        //Act
        appPulseActiveParser.run();

        //Assert
        verify(appPulseActiveTransport, times(2)).getData();
    }

    @Test
    public void AppPulseActiveParser_when_it_runs_and_not_get_token_should_not_be_parsed() {

        //Assign
        doReturn(EMPTY).when(appPulseActiveTransport).getToken();
        doReturn(EMPTY).when(appPulseActiveTransport).getTokenValue();
        doReturn(new JSONObject()).when(appPulseActiveTransport).getData();

        //Act
        appPulseActiveParser.run();

        //Assert
        assertTrue(appPulseActiveParser.getAvailabilitiesStorage() == null);
    }

    @Test
    public void AppPulseActiveParser_when_it_runs_and_not_get_data_should_not_be_parsed() {
        //Assign
        doReturn("fake-token").when(appPulseActiveTransport).getToken();
        doReturn("fake-token").when(appPulseActiveTransport).getTokenValue();
        doReturn(new JSONObject()).when(appPulseActiveTransport).getData();

        //Act
        appPulseActiveParser.run();

        //Assert
        verify(appPulseActiveTransport, times(1)).getData();
        assertTrue(appPulseActiveParser.getAvailabilitiesStorage() == null);

    }

    @Test
    public void AppPulseActiveParser_when_it_runs_should_get_data_repetitive_2_times_and_save_it_all() {
        //TODO: Usa un listado grande de registros que llamen iterativamente.
    }

    @Test
    public void ApppulseActiveParser_when_gets_data_should_save_on_mongo() {

        //Assign
        List<AvailabilityStorage> appPulseRecords = this.mapperConfifuration.mapToAvailabilitiesStorage(AppPulseBaseUnitTests.getFakeAppPulseProviderModel());
        doReturn(appPulseRecords.get(0)).when(appPulseActiveRepository).save(any(AvailabilityStorage.class));
        doReturn(true).when(appPulseActiveRepository).exist(any());

        //Act
        appPulseActiveParser.save(appPulseRecords);

        //Assert
        verify(appPulseActiveRepository, never()).save(any(AvailabilityStorage.class));
        verify(appPulseActiveRepository, times(1)).exist(any(AvailabilityStorage.class));
    }
}
