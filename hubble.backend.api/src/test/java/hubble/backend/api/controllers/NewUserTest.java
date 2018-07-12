package hubble.backend.api.controllers;

import static java.util.Collections.EMPTY_SET;
import org.junit.Test;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class NewUserTest {

    @Test
    public void validPasswordMatches() {
        new NewUser("martin.straus@fit.com.ar", "Martin Straus", "pepe123456", EMPTY_SET, EMPTY_SET).validate(Boolean.TRUE);
    }

    @Test(expected = RuntimeException.class)
    public void shortPasswordFails() {
        new NewUser("martin.straus@fit.com.ar", "Martin Straus", "a", EMPTY_SET, EMPTY_SET).validate(Boolean.TRUE);
    }

    @Test(expected = RuntimeException.class)
    public void passwordWithInvalidCharacterFails() {
        new NewUser("martin.straus@fit.com.ar", "Martin Straus", "pepe1234567|", EMPTY_SET, EMPTY_SET).validate(Boolean.TRUE);
    }

}
