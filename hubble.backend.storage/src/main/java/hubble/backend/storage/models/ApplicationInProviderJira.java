package hubble.backend.storage.models;

public class ApplicationInProviderJira {

    public static ApplicationInProviderJira standard(String applicationName,String projectKey) {
        return new ApplicationInProviderJira(applicationName, true,projectKey);
    }

    private String applicationName;
    private boolean enabledInTaskRunner;
    private String projectKey;

    public ApplicationInProviderJira() {
    }

    public ApplicationInProviderJira(String applicationName, boolean enabledInTaskRunner, String projectKey) {
        this.applicationName = applicationName;
        this.enabledInTaskRunner = enabledInTaskRunner;
        this.projectKey = projectKey;
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

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }
}
