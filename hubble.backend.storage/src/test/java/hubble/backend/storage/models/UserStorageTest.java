package hubble.backend.storage.models;

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

}
