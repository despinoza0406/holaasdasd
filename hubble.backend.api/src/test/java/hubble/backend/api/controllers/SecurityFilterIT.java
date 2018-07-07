package hubble.backend.api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Pruebas de integración para el filtro de seguridad.
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class SecurityFilterIT {

    @Test
    public void apiFailsIfRequestDoesntHaveAccessToken() throws IOException {
        assertThat(
            "status code", 
            getApplications(new HttpClient(), null), 
            is(HttpServletResponse.SC_BAD_REQUEST)
        );
    }

    @Test
    public void apiFailsIfRequestHasInvalidAccessToken() throws IOException {
        assertThat(
            "status code", 
            getApplications(new HttpClient(), UUID.randomUUID()), 
            is(HttpServletResponse.SC_UNAUTHORIZED)
        );
    }

    @Test
    public void authenticationDoesntRequireAccessToken() throws IOException {
        String email = "admin@tsoftlatam.com";
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url(String.format("/users/%s/auth", email)));
        post.setRequestEntity(authentication(email, "administrator"));
        int statusCode = client.executeMethod(post);
        assertThat("status code", statusCode, is(HttpServletResponse.SC_OK));
    }

    @Test
    public void apiWithValidAccessTokenDoesntFail() throws IOException {
        HttpClient client = new HttpClient();
        assertThat("status code", getApplications(client, authenticate(client)), is(HttpServletResponse.SC_OK));
    }

    private int getApplications(HttpClient client, UUID accessToken) throws IOException {
        GetMethod get = new GetMethod(url("/applications?page=1&limit=1"));
        if (accessToken != null) {
            get.addRequestHeader("access-token", accessToken.toString());
        }
        return client.executeMethod(get);
    }

    private UUID authenticate(HttpClient client) throws UnsupportedEncodingException, IOException {
        String email = "admin@tsoftlatam.com";
        PostMethod post = new PostMethod(url(String.format("/users/%s/auth", email)));
        post.setRequestEntity(authentication(email, "administrator"));
        assertThat("status code", client.executeMethod(post), is(HttpServletResponse.SC_OK));
        Map<String, Object> json = json(post.getResponseBodyAsString());
        assertThat("json in response body", json, hasTokenAndExpiration());
        return UUID.fromString(json.get("token").toString());
    }

    private Map<String, Object> json(String string) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        HashMap<String, Object> json = mapper.readValue(string, typeRef);
        return json;
    }

    private Matcher<Map<String, Object>> hasTokenAndExpiration() {
        return new TypeSafeMatcher<Map<String, Object>>() {
            @Override
            protected boolean matchesSafely(Map<String, Object> t) {
                return t.containsKey("token") && t.containsKey("expiration");
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has token and expiration");
            }

            @Override
            protected void describeMismatchSafely(Map<String, Object> item, Description mismatchDescription) {
                if (!item.containsKey("token")) {
                    mismatchDescription.appendText(" doesnt have token");
                }
                if (!item.containsKey("expiration")) {
                    mismatchDescription.appendText(" doesnt have expiration");
                }
            }

        };
    }

    private String url(String path) {
        return String.format(
            "http://localhost:%d%s",
            Integer.parseInt(System.getProperty("test.server.port")),
            path
        );
    }

    private StringRequestEntity authentication(String email, String password) throws UnsupportedEncodingException {
        return jsonRequestBody(String.format("{\"email\":\"%s\", \"password\":\"%s\"}", email, password));
    }

    private StringRequestEntity jsonRequestBody(String json) throws UnsupportedEncodingException {
        return new StringRequestEntity(json, "application/json", "utf-8");
    }

}
