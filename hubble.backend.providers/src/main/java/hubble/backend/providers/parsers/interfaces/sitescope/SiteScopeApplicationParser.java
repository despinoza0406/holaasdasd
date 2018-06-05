package hubble.backend.providers.parsers.interfaces.sitescope;

import hubble.backend.providers.models.sitescope.SiteScopeApplicationProviderModel;
import hubble.backend.providers.parsers.interfaces.Parser;
import hubble.backend.storage.models.ApplicationStorage;
import org.json.JSONObject;

import java.util.List;

public interface SiteScopeApplicationParser extends Parser {

    public SiteScopeApplicationProviderModel parse(JSONObject data);
    public ApplicationStorage convert(SiteScopeApplicationProviderModel almApplicationProviderModel);
}
