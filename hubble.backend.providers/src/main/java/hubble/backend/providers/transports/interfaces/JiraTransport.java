package hubble.backend.providers.transports.interfaces;

import hubble.backend.core.enums.Results;
import hubble.backend.providers.configurations.JiraConfiguration;
import hubble.backend.providers.configurations.environments.JiraProviderEnvironment;
import org.json.JSONObject;

public interface JiraTransport extends Transport<JSONObject>, Sessions {

    public String getEncodedCredentials();

    public void setEncodedCredentials(String encodedCredentials);

    public JiraProviderEnvironment getEnvironment();

    public JiraConfiguration getConfiguration();

    public JSONObject getIssuesByProject(String project, int startAt);

    public Results.RESULTS getResult();

    public String getError();

    public void setError(String result);

    public void setResult(Results.RESULTS result);

}
