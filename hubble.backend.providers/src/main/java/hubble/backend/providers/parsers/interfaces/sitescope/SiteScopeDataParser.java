package hubble.backend.providers.parsers.interfaces.sitescope;

import hubble.backend.providers.models.sitescope.SiteScopeEventProviderModel;
import hubble.backend.providers.parsers.interfaces.Parser;
import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.models.WorkItemStorage;
import org.json.JSONObject;

import java.util.List;

public interface SiteScopeDataParser extends Parser {
    public List<SiteScopeEventProviderModel> parse(JSONObject data);
    public List<EventStorage> convert(List<SiteScopeEventProviderModel> siteScopeEventProviderModel);
}
