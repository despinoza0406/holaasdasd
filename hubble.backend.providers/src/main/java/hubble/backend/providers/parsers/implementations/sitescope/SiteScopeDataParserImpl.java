package hubble.backend.providers.parsers.implementations.sitescope;

import hubble.backend.providers.configurations.SiteScopeConfiguration;
import hubble.backend.providers.configurations.mappers.sitescope.SiteScopeMapperConfiguration;
import hubble.backend.providers.parsers.interfaces.sitescope.SiteScopeDataParser;
import hubble.backend.providers.transports.interfaces.SiteScopeTransport;
import hubble.backend.storage.repositories.WorkItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SiteScopeDataParserImpl implements SiteScopeDataParser {

    @Autowired
    SiteScopeConfiguration configuration;
    @Autowired
    SiteScopeTransport siteScopeTransport;
    @Autowired
    SiteScopeMapperConfiguration mapper;
    @Autowired
    WorkItemRepository workItemRepository;

    private final Logger logger = LoggerFactory.getLogger(SiteScopeDataParserImpl.class);



}
