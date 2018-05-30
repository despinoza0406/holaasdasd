package hubble.backend.providers.tests.transports;


import hubble.backend.providers.configurations.ProvidersConfiguration;
import hubble.backend.providers.transports.interfaces.BsmTransport;
import hubble.backend.providers.transports.interfaces.SiteScopeTransport;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.soap.SOAPBody;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProvidersConfiguration.class)
public class SiteScopeTransportUnitTest {

    @Autowired
    private SiteScopeTransport siteScopeTransport;

    @Test
    public void SiteScopeTransport_should_be_instantiated() {
        assertNotNull(siteScopeTransport);
    }

    @Test
    public void SiteScopeTransport_getData_should_get_all_transactions() {
        //Assign
        List<String> pathsToGroups = new ArrayList<String>();
        pathsToGroups.add("VM_sis_path_delimiter_Home%20Banking");
        pathsToGroups.add("VM_sis_path_delimiter_CRM");
        //Act
        List<JSONObject> objects = null;
        objects = siteScopeTransport.getGroupsSnapshots(pathsToGroups);

        //Assert
        assertNotNull(objects);
//        assertNotNull(body.getFirstChild().getFirstChild().getChildNodes().item(0).getNodeValue());
//        assertNotNull(bsmTransport.getMessage());
    }

}
