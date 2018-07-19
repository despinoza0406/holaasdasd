package hubble.backend.providers.tests.parsers.apppulse;

import hubble.backend.providers.configurations.environments.AppPulseAppPulseProviderEnvironmentMongoImpl;
import hubble.backend.providers.configurations.environments.AppPulseProviderEnvironment;
import hubble.backend.providers.configurations.mappers.apppulse.AppPulseMapperConfiguration;
import hubble.backend.providers.models.apppulse.ApplicationProviderModel;
import hubble.backend.providers.parsers.implementations.apppulse.AppPulseActiveApplicationsParserImpl;
import hubble.backend.providers.tests.AppPulseBaseUnitTests;
import hubble.backend.providers.transports.implementations.apppulse.AppPulseActiveTransportImpl;
import hubble.backend.storage.repositories.ApplicationRepository;
import java.io.InputStream;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AppPulseActiveApplicationParserExtractUnitTests extends AppPulseBaseUnitTests {

    @Spy
    AppPulseProviderEnvironment environment = new AppPulseAppPulseProviderEnvironmentMongoImpl();
    @Spy
    AppPulseActiveTransportImpl appPulseActiveTransport = new AppPulseActiveTransportImpl(environment);
    @Spy
    AppPulseMapperConfiguration mapperConfifuration = new AppPulseMapperConfiguration();
    @Mock
    private ApplicationRepository availabilityRepository;

    //TODO: Mover a cada prueba unitaria.
    private AppPulseActiveApplicationsParserImpl appPulseActiveParser
            = new AppPulseActiveApplicationsParserImpl(appPulseActiveTransport, mapperConfifuration, availabilityRepository);

    @Test
    public void AppPulseActiveApplicationParserImpl_should_be_instantiated() {

        //Assert
        assertNotNull(appPulseActiveParser);
    }

    @Test
    public void extract_get_json_should_return_applicationName() {

        //Assign
        InputStream appPulseApplicationsRaw = LoadConfigurationFakeUnique();

        //Act
        ApplicationProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseApplicationsRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("BancoRipley - Sinacofi".equals(appPulseActivities.getApplications().get(0).getApplicationName()));
    }

    @Test
    public void extract_get_json_should_return_applicationId() {

        InputStream appPulseDataRaw = LoadConfigurationFakeUnique();

        //Act
        ApplicationProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("b566958ec4ff28028672780d15edcf56".equals(appPulseActivities.getApplications().get(0).getApplicationId()));
    }

    @Test
    public void extract_get_json_should_return_applicationIsActive() {

        InputStream appPulseDataRaw = LoadConfigurationFakeUnique();

        //Act
        ApplicationProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue(appPulseActivities.getApplications().get(0).getApplicationIsActive());
    }

    @Test
    public void extract_get_json_should_return_defaultPerformanceOkThreshold() {

        InputStream appPulseDataRaw = LoadConfigurationFakeUnique();

        //Act
        ApplicationProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue(appPulseActivities.getApplications().get(0).getDefaultPerformanceOkThreshold() == 11000);
    }

    @Test
    public void extract_get_json_should_return_defaultPerformanceCriticalThreshold() {

        InputStream appPulseDataRaw = LoadConfigurationFakeUnique();

        //Act
        ApplicationProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue(appPulseActivities.getApplications().get(0).getDefaultPerformanceCriticalThreshold() == 12000);
    }

    @Test
    public void extract_get_json_should_return_statusCriticalIfLessThan() {

        InputStream appPulseDataRaw = LoadConfigurationFakeUnique();

        //Act
        ApplicationProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue("90.0%".equals(appPulseActivities.getApplications().get(0).getStatusCriticalIfLessThan()));
    }

    @Test
    public void extract_get_json_should_return_transactionFailThreshold() {

        InputStream appPulseDataRaw = LoadConfigurationFakeUnique();

        //Act
        ApplicationProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertTrue(appPulseActivities.getApplications().get(0).getTransactionFailThreshold() == 300000);
    }

    @Test
    public void extract_get_json_should_return_locations() {

        InputStream appPulseDataRaw = LoadConfigurationFakeUnique();

        //Act
        ApplicationProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertNotNull(appPulseActivities.getApplications().get(0).getLocations());
        assertNotNull("305114c9930027cad1941f3b5e0694d0".equals(appPulseActivities.getApplications().get(0).getLocations().get(0).getLocationId()));
        assertNotNull("Sonda Interna - Ripley 1".equals(appPulseActivities.getApplications().get(0).getLocations().get(0).getLocationName()));
        assertNotNull("private".equals(appPulseActivities.getApplications().get(0).getLocations().get(0).getLocationType()));
    }

    @Test
    public void extract_get_json_should_return_transactions() {

        InputStream appPulseDataRaw = LoadConfigurationFakeUnique();

        //Act
        ApplicationProviderModel appPulseActivities = appPulseActiveParser.extract(appPulseDataRaw);

        //Assert
        assertNotNull(appPulseActivities);
        assertNotNull(appPulseActivities.getApplications().get(0).getLocations());
        assertNotNull("2eae220e082697be3a0646400e5b54fa".equals(appPulseActivities.getApplications().get(0).getTransactions().get(0).getTransactionId()));
        assertNotNull("Auntenticacion Biometrica".equals(appPulseActivities.getApplications().get(0).getTransactions().get(0).getTransactionName()));
        assertNotNull("script".equals(appPulseActivities.getApplications().get(0).getTransactions().get(0).getTransactionType()));
    }
}