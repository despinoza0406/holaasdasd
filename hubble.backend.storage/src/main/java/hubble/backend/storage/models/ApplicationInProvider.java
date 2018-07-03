package hubble.backend.storage.models;

/**
 * Configuración de una aplicación para ALM
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class ApplicationInProvider {

    public static ApplicationInProvider standard(String applicationName) {
        return new ApplicationInProvider(true, applicationName, true);
    }

    private boolean enabled;
    private String applicationName;
    private boolean enabledInTaskRunner;

    public ApplicationInProvider() {
    }

    public ApplicationInProvider(boolean enabled, String applicationName, boolean enabledInTaskRunner) {
        this.enabled = enabled;
        this.applicationName = applicationName;
        this.enabledInTaskRunner = enabledInTaskRunner;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public boolean isEnabledInTaskRunner() {
        return enabledInTaskRunner;
    }

    public void setEnabledInTaskRunner(boolean enabledInTaskRunner) {
        this.enabledInTaskRunner = enabledInTaskRunner;
    }

}
