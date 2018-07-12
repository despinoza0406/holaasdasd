package hubble.backend.providers.parsers.implementations.sitescope;

import hubble.backend.core.enums.Providers;
import hubble.backend.providers.configurations.SiteScopeConfiguration;
import hubble.backend.providers.configurations.mappers.sitescope.SiteScopeMapperConfiguration;
import hubble.backend.providers.configurations.mappers.sitescope.SiteScopeMonitorMapper;
import hubble.backend.providers.models.sitescope.SiteScopeEventProviderModel;
import hubble.backend.providers.parsers.interfaces.sitescope.SiteScopeDataParser;
import hubble.backend.providers.transports.interfaces.SiteScopeTransport;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.models.Monitor;
import hubble.backend.storage.repositories.EventRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class SiteScopeDataParserImpl implements SiteScopeDataParser {

    @Autowired
    SiteScopeConfiguration configuration;
    @Autowired
    SiteScopeTransport siteScopeTransport;
    @Autowired
    SiteScopeMapperConfiguration mapper;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    SiteScopeMonitorMapper monitorMapper;

    private final Logger logger = LoggerFactory.getLogger(SiteScopeDataParserImpl.class);

    @Override
    public void run() {
        if(configuration.taskEnabled()) {
            List<String> groups = siteScopeTransport.getApplicationNames();
            ApplicationStorage application;
            List<String> groupPaths = siteScopeTransport.getPathsToGroups(groups);
            List<JSONObject> groupsSnapshots = siteScopeTransport.getGroupsSnapshots(groupPaths);

            for (JSONObject snapshot : groupsSnapshots) {
                EventStorage event = this.convert(this.parse(snapshot));
                if (!eventRepository.exist(event)) {
                    eventRepository.save(event);
                }
            }
        }
    }

    @Override
    public SiteScopeEventProviderModel parse(JSONObject data) {
        String groupName;
        List<Monitor> monitors;

        if (data == null) {
            return null;
        }

        JSONObject siteScopeRuntimeSnapshot = data.getJSONObject("runtime_snapshot");
        JSONObject siteScopeConfigSnapshot = data.getJSONObject("configuration_snapshot");
        groupName = siteScopeConfigSnapshot.getString("name");
        monitors = this.getMonitors(groupName);

        SiteScopeEventProviderModel siteScopeEvent= this.mapToSiteScopeEvent(siteScopeConfigSnapshot,siteScopeRuntimeSnapshot,monitors);

        return siteScopeEvent;
    }

    private List<Monitor> getMonitors(String groupName){
        List<String> paths = new ArrayList<>();
        List<JSONObject> monitorsSnapshots = null;
        List<Monitor> monitors = new ArrayList<>();
        paths = siteScopeTransport.getMonitorPaths(groupName);


        monitorsSnapshots = siteScopeTransport.getMonitorSnapshots(paths);

        for(JSONObject monitor : monitorsSnapshots){

            monitors.add(monitorMapper.mapToSimpleMonitor(monitor));
        }
        return monitors;
    }


    @Override
    public EventStorage convert(SiteScopeEventProviderModel siteScopeEventProviderModel) {
        return mapper.mapToEventStorage(siteScopeEventProviderModel);
    }

    public SiteScopeEventProviderModel mapToSiteScopeEvent(JSONObject config, JSONObject runtime,List<Monitor> monitors){
        SiteScopeEventProviderModel model = new SiteScopeEventProviderModel();

        model.setSummary(runtime.getString("summary"));
        model.setStatus(runtime.getString("status"));
        model.setName(config.getString("name"));
        model.setDescription(config.getString(("description")));
        model.setUpdatedDate(this.getDate(config.getString("updated_date")));
        model.setMonitors(monitors);
        model.setBusinessApplication(config.getString("name"));
        model.setApplicationId(resolveApplicationIdFromConfiguration(model.getBusinessApplication()));
        model.setProviderName(Providers.PROVIDERS_NAME.SITE_SCOPE.toString());
        model.setProviderOrigin(Providers.PROVIDERS_NAME.SITE_SCOPE.toString());

        return model;
    }

    private Date getDate(String dateInMiliseconds){
        long miliseconds = Long.parseLong(dateInMiliseconds);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date updatedDate = new Date(miliseconds);
        return updatedDate;
    }

    private String resolveApplicationIdFromConfiguration(String applicationName) {
        HashMap<String,String> applicationsIdMap = configuration.getApplicationValueToIdMap();
        Set<String> keySet = applicationsIdMap.keySet();
        for (String key : keySet) {
            if (applicationName.equals(applicationsIdMap.get(key))) {
                return key;
            }
        }

        logger.warn("SiteScope field for applications and ids map not correctly"
                + " configured in properties file for specified app name: "
                + applicationName);
        return null;
    }


}
