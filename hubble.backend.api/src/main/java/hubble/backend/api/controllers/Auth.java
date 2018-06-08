package hubble.backend.api.controllers;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class Auth {

    private String password;

    public Auth() {
    }

    public Auth(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
