package hubble.backend.providers.configurations.environments;

/**
 *
 * @author Ismael J. Tisminetzky
 */
public interface AppPulseProviderEnvironment {

    /**
     * The URL of the provider from which offers
     *
     * @return
     */
    public String getUrl();

    /**
     * Get the user ID of the provider.
     *
     * @return
     */
    public String getClient();

    /**
     * Password to authenticate in the Provider
     *
     * @return
     */
    public String getSecret();

}
