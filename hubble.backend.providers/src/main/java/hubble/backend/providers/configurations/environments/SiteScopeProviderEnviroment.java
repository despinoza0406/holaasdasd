package hubble.backend.providers.configurations.environments;

public interface SiteScopeProviderEnviroment {
    public String getHost();
    public String getPort();
    public String getUserName();
    public String getPassword();
    public String getDomain();
    public String getProject();
}
