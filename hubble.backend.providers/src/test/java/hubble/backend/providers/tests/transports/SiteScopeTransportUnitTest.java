package hubble.backend.providers.tests.transports;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
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
    public void SiteScopeTransport_getSnapShots_should_get_all_data_from_groups() {
        //Assign
        List<String> pathsToGroups = new ArrayList<String>();
        pathsToGroups.add("VM_sis_path_delimiter_Home Banking");
        pathsToGroups.add("VM_sis_path_delimiter_CRM");
        //Act
        List<JSONObject> objects = null;
        objects = siteScopeTransport.getGroupsSnapshots(pathsToGroups);

        //Assert
        assertNotNull(objects);

    }

    @Test
    public void SiteScopeTransport_getPaths_should_get_paths() {
        //Assign
        List<String> groups = new ArrayList<>();
        groups.add("Home Banking");
        groups.add("CRM");
        groups.add("AmoALean");

        //Act
        List<String> paths = siteScopeTransport.getPathsToGroups(groups);


        //Assert
        assertNotNull(paths);

    }

}



