package hubble.backend.providers.transports.interfaces;

import hubble.backend.core.enums.Results;
import hubble.backend.providers.configurations.environments.AlmProviderEnvironment;
import java.util.Map;
import org.json.JSONObject;

public interface AlmTransport {

    public void login();

    public void logout();

    public boolean isAuthenticated();

    public AlmProviderEnvironment getEnvironment();

    public Map<String, String> getSessionCookies();

    public JSONObject getOpenDefects(Map<String, String> sessionCookies, int startInd);

    public JSONObject getClosedTodayDefects(Map<String, String> sessionCookies, int startInd);

    public Results.RESULTS getResult();

    public String getError();

    public void setResult(Results.RESULTS result);

    public void setError(String error);
}
