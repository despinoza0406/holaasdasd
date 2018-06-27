package hubble.backend.storage.models;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class UserStorageTest {

    @Test
    public void checkPasswordEncryptsPassword() {
        UserStorage user = userWithPassword("pepe1234");
        assertNotEquals(user.getPassword(), "pepe1234");
    }

    @Test
    public void checkPasswordWithSamePasswordReturnsTrue() {
        assertTrue(userWithPassword("pepe1234").verifyPassword("pepe1234".toCharArray()));
    }

    @Test
    public void checkPasswordWithDifferentPasswordReturnsFalse() {
        assertFalse(userWithPassword("pepe1234").verifyPassword("1234pepe".toCharArray()));
    }

    @Test(expected = RuntimeException.class)
    public void authenticateDisaabledUserThrowsException() {
        UserStorage disabledUser = new UserStorage();
        disabledUser.setEnabled(false);
        disabledUser.authenticate("pepe".toCharArray());
    }

    private UserStorage userWithPassword(String password) {
        final UserStorage user = new UserStorage();
        user.setEnabled(true);
        user.changePassword(password.toCharArray());
        return user;
    }

    @Test
    public void refreshTokenSuccessIfNotExpired() {
        String password = "pass123456";
        UserStorage user = user(password);
        AuthToken token = user.authenticate(password.toCharArray());
        assertThat("new token", user.refreshToken(token.getToken()), is(not(nullValue())));
    }

    @Test(expected = RuntimeException.class)
    public void refreshTokenFailsIfIncorrectToken() {
        String password = "pass123456";
        UserStorage user = user(password);
        user.authenticate(password.toCharArray());
        user.refreshToken(UUID.randomUUID());
    }

    @Test(expected = RuntimeException.class)
    public void refreshTokenFailsIfExpired() {
        String password = "pass123456";
        UserStorage user = user(password);
        AuthToken token = user.authenticate(password.toCharArray());
        token.setExpiration(LocalDateTime.now().minusDays(1)); // Horrible, pero es la única forma de simular esto.
        user.refreshToken(token.getToken());
    }

    private UserStorage user(String password) {
        return new UserStorage("a@tsoftlatam.com", "A", password.toCharArray(), Collections.EMPTY_SET, Collections.EMPTY_SET);
    }

    @Test
    public void validateTokenSucceedsIfTokenIsNotExpired() {
        String password = "pass123456";
        UserStorage user = user(password);
        AuthToken token = user.authenticate(password.toCharArray());
        assertThat("token is valid", user.validateToken(token.getToken()), is(true));
    }

    @Test
    public void validateTokenFailsIfTokenIsExpired() {
        String password = "pass123456";
        UserStorage user = user(password);
        AuthToken token = user.authenticate(password.toCharArray());
        token.setExpiration(token.getExpiration().minusDays(2)); // Horrible, no se puede simular de otra forma.
        assertThat("token is not valid", user.validateToken(token.getToken()), is(false));
    }

    @Test
    public void validateTokenFailsIfTokenDifferent() {
        String password = "pass123456";
        UserStorage user = user(password);
        AuthToken token = user.authenticate(password.toCharArray());
        token.setExpiration(token.getExpiration().minusDays(2)); // Horrible, no se puede simular de otra forma.
        assertThat("token is not valid", user.validateToken(UUID.randomUUID()), is(false));
    }

}
