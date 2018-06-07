package hubble.backend.storage.models;

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

}
