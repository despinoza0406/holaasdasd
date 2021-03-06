package hubble.backend.storage.models;

import org.springframework.data.annotation.Transient;

public class ApplicationInProviderJira {

    public static ApplicationInProviderJira standard(String applicationName, String projectKey, boolean enabledInTaskRunner) {
        return new ApplicationInProviderJira(applicationName, enabledInTaskRunner, projectKey);
    }

    @Transient
    private boolean activeProvider;

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

    public boolean isActiveProvider() {
        return activeProvider;
    }

    public void setActiveProvider(boolean activeProvider) {
        this.activeProvider = activeProvider;
    }
    
    
}
