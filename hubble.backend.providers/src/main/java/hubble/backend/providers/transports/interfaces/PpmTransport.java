package hubble.backend.providers.transports.interfaces;

import hubble.backend.core.enums.Results;
import hubble.backend.providers.configurations.environments.PpmProviderEnvironment;
import java.util.List;
import org.json.JSONObject;

public interface PpmTransport {

    public PpmProviderEnvironment getEnvironment();

    public String encodeToBase64(String userName, String password);

    public String decodeFromBase64(String encodedString);

    public List<JSONObject> getAllRequestTypes(String encodedAuthString);

    public List<JSONObject> getRequestTypes(String encodedAuthString, List<String> requestTypeIds);

    public List<JSONObject> getRequests(String encodedAuthString, String requestTypeId);

    public JSONObject getRequestDetails(String encodedAuthString, String requestId);

    public List<String> getConfiguredRequestTypes(String encodedAuthString);

    public String getError();

    public Results.RESULTS getResult();

    public void setResult(Results.RESULTS result);

    public void setError(String error);

}
