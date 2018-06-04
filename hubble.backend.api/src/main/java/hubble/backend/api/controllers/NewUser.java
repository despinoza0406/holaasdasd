package hubble.backend.api.controllers;

import hubble.backend.business.services.models.Roles;
import java.util.Set;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class NewUser {

    private String email;
    private String name;
    private String password;
    private Set<String> roles;
    private Set<String> applications;

    public NewUser() {
    }

    public NewUser(String email, String name, String password, Set<String> roles, Set<String> applications) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.applications = applications;
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

    public Set<String> getApplications() {
        return applications;
    }

    public void setApplications(Set<String> applications) {
        this.applications = applications;
    }

    public void validate() {
        if (!hasName()) {
            throw new RuntimeException("Debe indicarse el nombre.");
        }
        if (!hasEmail()) {
            throw new RuntimeException("Debe indicarse el correo electrónico.");
        }
        if (!hasPassword()) {
            throw new RuntimeException("Debe indicarse la contraseña.");
        }
        if (!passwordIsValid()) {
            throw new RuntimeException("La contraseña debe tener al menos 10 caracteres, letras, y números.");
        }
        validateRoles();
    }

    public boolean hasName() {
        return hasValue(name);
    }

    public boolean hasEmail() {
        return hasValue(email);
    }

    public boolean hasPassword() {
        return hasValue(name);
    }

    public boolean passwordIsValid() {
        return password.matches("([\\d\\w]){10,20}");
    }

    public boolean hasValue(String str) {
        return str != null && !str.trim().isEmpty();
    }

    private void validateRoles() {
        roles.forEach(this::validateRole);
    }

    private void validateRole(String role) {
        Roles.valueOf(role);
    }

}
