package hubble.backend.storage.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class AuthToken {

    private UUID token;
    private LocalDateTime expiration;

    public AuthToken() {
    }

    public AuthToken(UUID token, LocalDateTime expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public ObjectNode toJson() {
        return new ObjectMapper()
            .createObjectNode()
            .put("token", token.toString())
            .put("expiration", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(expiration));
    }

    public boolean validate(UUID token) {
        return this.token.equals(token);
    }

    public boolean isValid() {
        return LocalDateTime.now().isBefore(this.expiration);
    }

}
