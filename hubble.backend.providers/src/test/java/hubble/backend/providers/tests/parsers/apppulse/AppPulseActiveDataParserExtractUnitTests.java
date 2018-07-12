package hubble.backend.providers.tests.parsers.apppulse;

import hubble.backend.providers.configurations.environments.AppPulseProviderEnvironmentMongoImpl;
import hubble.backend.providers.configurations.environments.ProviderEnvironment;
import hubble.backend.providers.configurations.mappers.apppulse.AppPulseMapperConfiguration;
import hubble.backend.providers.models.apppulse.AvailabilityProviderModel;
import hubble.backend.providers.parsers.implementations.apppulse.AppPulseActiveDataParserImpl;
import hubble.backend.providers.tests.AppPulseBaseUnitTests;
import hubble.backend.providers.transports.implementations.apppulse.AppPulseActiveTransportImpl;
import hubble.backend.storage.repositories.AvailabilityRepository;
import java.io.InputStream;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AppPulseActiveDataParserExtractUnitTests extends AppPulseBaseUnitTests {

    @Spy
    ProviderEnvironment environment = new AppPulseProviderEnvironmentMongoImpl();
    @Spy
    AppPulseActiveTransportImpl appPulseActiveTransport = new AppPulseActiveTransportImpl(environment);
    @Spy
    AppPulseMapperConfiguration mapperConfifuration = new AppPulseMapperConfiguration();
    @Mock
    private AvailabilityRepository availabilityRepository;

    //TODO: Mover a cada prueba unitaria.
    private AppPulseActiveDataParserImpl appPulseActiveParser
            = new AppPulseActiveDataParserImpl(appPulseActiveTransport, mapperConfifuration, availabilityRepository);

    @Test
    public void AppPulseActiveParserImpl_should_be_instantiated() {

        //Assert
        assertNotNull(appPulseActiveParser);
    }

    @Test
    public void extract_get_json_should_return_applicationName() {

        //Assign
        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("fake-app-example".equals(appPulseActivities.getData().get(0).getApplicationName()));
    }

    @Test
    public void extract_get_json_should_return_applicationId() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("1".equals(appPulseActivities.getData().get(0).getApplicationId()));
    }

    @Test
    public void extract_get_json_should_return_AvailabilityFailIfAbove() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue(appPulseActivities.getData().get(0).getAvailabilityFailIfAbove() == 45);
    }

    @Test
    public void extract_get_json_should_return_AvailabilityStatus() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("disponibilidad-ok-fake".equals(appPulseActivities.getData().get(0).getAvailabilityStatus()));
    }

    @Test
    public void extract_get_json_should_return_CriticalThreshold() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue(appPulseActivities.getData().get(0).getCriticalThreshold() == 12);
    }

    @Test
    public void extract_get_json_should_return_LocationId() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("1".equals(appPulseActivities.getData().get(0).getLocationId()));
    }

    @Test
    public void extract_get_json_should_return_LocationName() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("La Pampa".equals(appPulseActivities.getData().get(0).getLocationName()));
    }

    @Test
    public void extract_get_json_should_return_NumberOfErrors() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue(appPulseActivities.getData().get(0).getNumberOfErrors() == 1);
    }

    @Test
    public void extract_get_json_should_return_PerformanceStatus() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("performance-ok-fake".equals(appPulseActivities.getData().get(0).getPerformanceStatus()));
    }

    @Test
    public void extract_get_json_should_return_PoorThreshold() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue(appPulseActivities.getData().get(0).getPoorThreshold() == 8);
    }

    @Test
    public void extract_get_json_should_return_ScriptName() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("proceso-negocio-fake-name".equals(appPulseActivities.getData().get(0).getScriptName()));
    }

    @Test
    public void extract_get_json_should_return_ServerName() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();
        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("tecnosoftware.com".equals(appPulseActivities.getData().get(0).getServerName()));
    }

    @Test
    public void extract_get_json_should_return_ResponseTime() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue(appPulseActivities.getData().get(0).getResponseTime() == 0);
    }

    @Test
    public void extract_get_json_should_return_TimeStamp() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue(appPulseActivities.getData().get(0).getTimeStamp() == 1301090400);
    }

    @Test
    public void extract_get_json_should_return_one_Error() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue(appPulseActivities.getData().get(0).getErrors().size() == 1);
    }

    @Test
    public void extract_get_json_should_return_ErrorMessage() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("error-time-out".equals(appPulseActivities.getData().get(0).getErrors().get(0).getErrorMessage()));
    }

    @Test
    public void extract_get_json_should_return_ErrorFileName() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("fake-filename.txt".equals(appPulseActivities.getData().get(0).getErrors().get(0).getFileName()));
    }

    @Test
    public void extract_get_json_should_return_ErrorLineNumer() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue(appPulseActivities.getData().get(0).getErrors().get(0).getLineNumber() == 1);
    }

    @Test
    public void extract_get_json_should_return_transactionName() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("fake-transaction".equals(appPulseActivities.getData().get(0).getTransactionName()));
    }

    @Test
    public void extract_get_json_should_return_transactionId() {

        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("1".equals(appPulseActivities.getData().get(0).getTransactionId()));
    }

    @Test
    public void extract_when_data_has_one_correct_record_extract_should_return_a_list_with_one_record() {

        //Assign
        InputStream appPulseDataRaw = LoadAvailabilityFakeDataUnique();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertTrue(appPulseActivities.getData().size() == 1);
        assertNotNull(appPulseActivities);
        assertNotNull(appPulseActiveParser);
    }

    @Test
    public void extract_when_data_has_corrects_record_extract_should_return_a_list_of_records() {

        //Assign
        InputStream appPulseDataRaw = LoadAvailabilityFakeDataList1();

        //Act
        AvailabilityProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue(appPulseActivities.getData().size() == 4);
    }
}
