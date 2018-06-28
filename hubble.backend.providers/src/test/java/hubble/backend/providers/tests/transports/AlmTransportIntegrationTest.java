package hubble.backend.providers.tests.transports;

import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.transports.interfaces.AlmTransport;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class AlmTransportIntegrationTest {
    
    @Autowired
    private AlmTransport almTransport;

    @Test
    public void alm_transport_should_be_instantiated(){
        assertNotNull(almTransport);
    }
    
    @Test
    public void alm_is_authenticated_query_should_return_false(){
        assertFalse(almTransport.isAuthenticated());
    }
    
    @Test
    public void alm_transport_should_login_verify_and_logout() throws Exception{
        almTransport.login();
        assertTrue(almTransport.isAuthenticated()); 
        almTransport.logout();
        assertFalse(almTransport.isAuthenticated());
    }
    
    @Test
    public void alm_transport_should_retrieve_session_cookie() throws Exception{
        //Assign
        Map<String, String> cookies;
        //Act
        almTransport.login();
        cookies = almTransport.getSessionCookies();
        
        //Asser
        assertTrue(3 <= cookies.size());
        assertNotNull(cookies.get("QCSession"));
        assertNotNull(cookies.get("XSRF-TOKEN"));
        assertNotNull(cookies.get("ALM_USER"));
        assertNull(cookies.get("false_token_this_should_return_null"));
        almTransport.logout();
        assertFalse(almTransport.isAuthenticated());
    }
    
    @Test
    public void alm_transport_should_retrieve_all_open_defects() throws Exception{
        almTransport.login();
        Map<String,String> cookies=almTransport.getSessionCookies();
        assertNotNull(almTransport.getOpenDefects(cookies,1));
        assertTrue(almTransport.isAuthenticated());     
        
        almTransport.logout();
        assertFalse(almTransport.isAuthenticated());
    }

    @Test
    public void alm_transport_should_retrieve_all_closed_today_defects() throws Exception{
        almTransport.login();
        Map<String,String> cookies=almTransport.getSessionCookies();
        assertNotNull(almTransport.getClosedTodayDefects(cookies,1));
        assertTrue(almTransport.isAuthenticated());

        almTransport.logout();
        assertFalse(almTransport.isAuthenticated());
    }
}
