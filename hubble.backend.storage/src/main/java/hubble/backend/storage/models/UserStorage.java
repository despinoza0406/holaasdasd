package hubble.backend.storage.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
@Document
public class UserStorage {

    private static final Argon2Encrypter encrypter = new Argon2Encrypter();

    private String id;
    @Indexed(unique = true)
    private String email;
    private String name;
    private String password;
    private boolean enabled;
    private AuthToken token;
    private Set<String> roles;
    private Set<ApplicationStorage> applications;

    public UserStorage() {
    }

    public UserStorage(String email, String name, char[] password, Set<String> roles, Set<ApplicationStorage> applications) {
        this.email = email;
        this.name = name;
        this.roles = roles;
        this.applications = applications;
        this.enabled = true;
        changePassword(password);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<ApplicationStorage> getApplications() {
        return applications;
    }

    public void setApplications(Set<ApplicationStorage> applications) {
        this.applications = applications;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void changePassword(char[] newPassword) {
        this.password = new String(encrypter.encrypt(newPassword));
    }

    public boolean verifyPassword(char[] passwordToCheck) {
        return encrypter.verify(password, passwordToCheck);
    }

    @Override
    public String toString() {
        return "UserStorage{" + "id=" + id + ", email=" + email + ", name=" + name + ", password=" + password + '}';
    }

    public UserStorage edit(String email, String name, Set<String> roles, Set<ApplicationStorage> applications, Optional<String> password) {
        
        this.email = email;
        this.name = name;

        if (password.isPresent()) {
            changePassword(password.get().toCharArray());
        }

        this.roles = roles;
        this.applications = applications;

        return this;
    }

    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("id", id)
                .put("email", email)
                .put("name", name)
                .put("enabled", enabled);
        json.set("roles", rolesToJson());
        json.set("applications", applicationsToJson());
        return json;
    }

    private ArrayNode rolesToJson() {
        ArrayNode array = new ObjectMapper().createArrayNode();
        if (!roles.isEmpty()) {
            roles.forEach(array::add);
        }
        return array;
    }

    private ArrayNode applicationsToJson() {
        ArrayNode array = new ObjectMapper().createArrayNode();
        if (!applications.isEmpty()) {
            applications.forEach((application) -> array.add(application.getId()));
        }
        return array;
    }

    public AuthToken authenticate(char[] password) {
        if (!enabled || !verifyPassword(password)) {
            throw new RuntimeException("Usuario o clave inválidos.");
        }
        this.token = newToken();
        return token;
    }

    public AuthToken refreshToken(UUID token) {
        if (!this.token.isSameToken(token) || this.token.isExpired()) {
            throw new RuntimeException();
        }
        this.token = newToken();
        return this.token;
    }

    private AuthToken newToken() {
        return new AuthToken(UUID.randomUUID(), LocalDateTime.now().plusDays(1));
    }

    public boolean validateToken(UUID token) {
        return this.token.isSameToken(token) && !this.token.isExpired();
    }

    public boolean isAdministrator()
    {
       return roles.stream().anyMatch(r -> r.equalsIgnoreCase("ADMINISTRATOR"));
    }
    
    public boolean isUser()
    {
       return roles.stream().anyMatch(r -> r.equalsIgnoreCase("USER"));
    }
        
        
    public boolean hasAccess(String idApp)
    {
       return applications.stream().anyMatch(app -> app.getId().equalsIgnoreCase(idApp));
    }
           

}
