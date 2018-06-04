package hubble.backend.storage.models;

import java.util.Set;
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
    private Set<String> roles;
    private Set<ApplicationStorage> applications;

    public UserStorage() {
    }

    public UserStorage(String email, String name, char[] password, Set<String> roles, Set<ApplicationStorage> applications) {
        this.email = email;
        this.name = name;
        this.roles = roles;
        this.applications = applications;
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

}
