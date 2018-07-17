package hubble.backend.storage.models;

/**
 * Configuración de una aplicación para ALM
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class ApplicationInProvider {

    public static ApplicationInProvider standard(String applicationName) {
        return new ApplicationInProvider(applicationName, true);
    }

    private String applicationName;
    private boolean enabledInTaskRunner;

    public ApplicationInProvider() {
    }

    public ApplicationInProvider(String applicationName, boolean enabledInTaskRunner) {
        this.applicationName = applicationName;
        this.enabledInTaskRunner = enabledInTaskRunner;
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
