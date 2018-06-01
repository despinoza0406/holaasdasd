package hubble.backend.providers.parsers.implementations.sitescope;

import hubble.backend.providers.configurations.SiteScopeConfiguration;
import hubble.backend.providers.configurations.mappers.sitescope.SiteScopeMapperConfiguration;
import hubble.backend.providers.models.sitescope.SiteScopeApplicationProviderModel;
import hubble.backend.providers.parsers.interfaces.sitescope.SiteScopeApplicationParser;
import hubble.backend.providers.transports.interfaces.SiteScopeTransport;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SiteScopeApplicationParserImpl implements SiteScopeApplicationParser {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private SiteScopeTransport siteScopeTransport;
    @Autowired
    private SiteScopeConfiguration configuration;
    @Autowired
    private SiteScopeMapperConfiguration mapperConfiguration;

    private final Logger logger = LoggerFactory.getLogger(SiteScopeApplicationParserImpl.class);

    @Override
    public SiteScopeApplicationProviderModel parse(JSONObject snapshot) {
        if (snapshot == null) {
            return null;
        }

        JSONObject configurationSnapshot = snapshot.getJSONObject("configuration_snapshot");
        SiteScopeApplicationProviderModel siteScopeApplication = this.mapToSiteScopeModel(configurationSnapshot);

        return siteScopeApplication;
    }

    @Override
    public void run() {
        List<String> groups = siteScopeTransport.getApplicationNames();
        ApplicationStorage application;
        List<String> groupPaths = siteScopeTransport.getPathsToGroups(groups);
        List<JSONObject> groupsSnapshots = siteScopeTransport.getGroupsSnapshots(groupPaths);
        for(JSONObject snapshot : groupsSnapshots){
            application = this.convert(this.parse(snapshot));
            if (!applicationRepository.exist(application)) {
                applicationRepository.save(application);
            }
        }
    }


    @Override
    public ApplicationStorage convert(SiteScopeApplicationProviderModel application) {
        return mapperConfiguration.mapToApplicationStorage(application);
    }

    private SiteScopeApplicationProviderModel mapToSiteScopeModel(JSONObject sitescopeConfigSnapshot) {
        SiteScopeApplicationProviderModel application = new SiteScopeApplicationProviderModel();
        application.setName(sitescopeConfigSnapshot.getString("name"));
        application.setApplicationId(resolveApplicationIdFromConfiguration(application.getName()));
        return application;
    }


    public String resolveApplicationIdFromConfiguration(String applicationName) {
        String[] applicationsIdMap = configuration.getApplicationValueToIdMap().split(",");
        for (String applicationsIdMap1 : applicationsIdMap) {
            if (applicationName.equals(applicationsIdMap1.split(":")[0])) {
                return applicationsIdMap1.split(":")[1];
            }
        }
        logger.debug("SiteScope: field for applications and ids map not correctly configured in properties file");
        return null;
    }



}
