package hubble.backend.providers.tests.transports;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hubble.backend.providers.configurations.environments.ProviderEnvironment;
import hubble.backend.providers.tests.AppPulseBaseUnitTests;
import hubble.backend.providers.transports.implementations.apppulse.AppPulseActiveTransportImpl;
import org.json.JSONObject;
import static org.junit.Assert.assertNull;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.anyString;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@Ignore
@RunWith(PowerMockRunner.class)
@PrepareForTest(Unirest.class)
public class AppPulseActiveTransportUnitTest extends AppPulseBaseUnitTests {

    private ProviderEnvironment environment;
    private AppPulseActiveTransportImpl appPulseActiveTransport;

    @Test
    public void AppPulseActiveParser_when_it_runs_and_not_get_connection_should_not_be_parsed() {

        //Assign
        environment = PowerMockito.mock(ProviderEnvironment.class);
        PowerMockito.doReturn("fake-url").when(environment).getUrl();
        appPulseActiveTransport = new AppPulseActiveTransportImpl(environment);
        appPulseActiveTransport.setTokenValue("fake-token");
        PowerMockito.mockStatic(Unirest.class);
        PowerMockito.when(Unirest.post(anyString())).thenCallRealMethod();
        PowerMockito.when(Unirest.get(anyString())).thenAnswer((invocationOnMock) -> {
            throw new UnirestException("Get unreachable");
        });

        //Act
        JSONObject dataNull = appPulseActiveTransport.getData();

        //Assert
        assertNull(dataNull);
    }
}
