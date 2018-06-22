package hubble.backend.storage.models;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class SiteScope extends ProviderStorage<NoConfig, NoConfig> {

    public SiteScope() {
    }

    public SiteScope(boolean enabled, TaskRunner taskRunner) {
        super("sitescope", "SiteScope", enabled, taskRunner, new NoConfig(), new NoConfig());
    }

    @Override
    public ProviderStorage fromJson(JsonNode jsonNode) {
        
        
        JsonNode configuration = jsonNode.get("configuration");
        this.getConfiguration().fromJson(configuration);

        JsonNode enviroment = jsonNode.get("environment");
        this.getEnvironment().fromJson(enviroment);

        this.setName(jsonNode.get("name").asText());
        this.setEnabled(jsonNode.get("enabled").asBoolean());

        JsonNode taskRunner = jsonNode.get("taskRunner");
        this.getTaskRunner().fromJson(taskRunner);

        return this;
    }

}
