package hubble.backend.api.controllers;

import hubble.backend.business.services.interfaces.services.UsersService;
import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.repositories.UsersRepository;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class UsersControllerTest {

    @Test
    public void autenticateWithNonExistentEmailReturnsFORBIDDEN() {
        UsersRepository repository = Mockito.mock(UsersRepository.class);
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        UsersService service = Mockito.mock(UsersService.class);
        UsersController controller = new UsersController(service, repository);
        String nonExistentEmail = "test@tsoftlatam.com";
        assertThat(
            "autentication",
            controller.authenticate(nonExistentEmail, new Auth()),
            is(forbidden())
        );
    }

    @Test
    public void autenticateWithWrongPasswordReturnsFORBIDDEN() {
        UsersRepository repository = Mockito.mock(UsersRepository.class);
        UserStorage user = new UserStorage();
        user.changePassword("pepe123456".toCharArray());
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        UsersService service = Mockito.mock(UsersService.class);
        UsersController controller = new UsersController(service, repository);
        String wrongPassword = "123456pepe";
        assertThat(
            "autentication",
            controller.authenticate("test@tsoftlatam.com", new Auth(wrongPassword)),
            is(forbidden())
        );
    }

    @Test
    public void autenticateWithWightPasswordReturnsOK() {
        UsersRepository repository = Mockito.mock(UsersRepository.class);
        UserStorage user = new UserStorage();
        String password = "pepe123456";
        user.changePassword(password.toCharArray());
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        UsersService service = Mockito.mock(UsersService.class);
        UsersController controller = new UsersController(service, repository);
        assertThat(
            "autentication",
            controller.authenticate("test@tsoftlatam.com", new Auth(password)),
            is(ok())
        );
    }

    private Matcher<ResponseEntity> forbidden() {
        return hasStatus(HttpStatus.FORBIDDEN);
    }

    private Matcher<ResponseEntity> ok() {
        return hasStatus(HttpStatus.OK);
    }

    private Matcher<ResponseEntity> hasStatus(HttpStatus status) {
        return new TypeSafeMatcher<ResponseEntity>() {
            @Override
            protected boolean matchesSafely(ResponseEntity t) {
                return t.getStatusCode().equals(status);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(status.toString());
            }

            @Override
            protected void describeMismatchSafely(ResponseEntity item, Description mismatchDescription) {
                mismatchDescription.appendValue(item.getStatusCode());
            }

        };
    }
}
