package hubble.backend.api.controllers;

import hubble.backend.business.services.interfaces.services.UsersService;
import hubble.backend.storage.models.AuthToken;
import hubble.backend.storage.repositories.UsersRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class UsersControllerTest {

    @Test
    public void autenticateWithNonExistentEmailReturnsUNAUTHORIZED() {
        UsersRepository repository = Mockito.mock(UsersRepository.class);
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        UsersService service = Mockito.mock(UsersService.class);
        UsersController controller = new UsersController(service, repository);
        String nonExistentEmail = "test@tsoftlatam.com";
        assertThat(
            "autentication",
            controller.authenticate(nonExistentEmail, new Auth()),
            is(unauthorized())
        );
    }

    @Test
    public void autenticateWithWrongPasswordReturnsUNAUTHORIZED() {
        UsersService service = mock(UsersService.class);
        Auth auth = new Auth("123456pepe");
        when(
            service.authenticate(anyString(), eq(auth.getPassword().toCharArray()))
        ).thenThrow(new RuntimeException());
        UsersController controller = new UsersController(service, mock(UsersRepository.class));
        assertThat(
            "autentication",
            controller.authenticate("test@tsoftlatam.com", auth),
            is(unauthorized())
        );
    }

    @Test
    public void autenticateWithRightPasswordReturnsOK() {
        UsersService service = mock(UsersService.class);
        Auth auth = new Auth("pepe123456");
        when(
            service.authenticate(anyString(), eq(auth.getPassword().toCharArray()))
        ).thenReturn(new AuthToken(UUID.randomUUID(), LocalDateTime.now()));
        UsersController controller = new UsersController(service, mock(UsersRepository.class));
        assertThat(
            "autentication",
            controller.authenticate("test@tsoftlatam.com", auth),
            is(ok())
        );
    }

    @Test
    public void refreshTokenWithValidTokenReturnsOK() {
        UsersService service = mock(UsersService.class);
        UUID token = UUID.randomUUID();
        when(service.refreshToken("test@tsoftlatam.com", token)).thenReturn(randomToken());
        UsersController controller = new UsersController(service, mock(UsersRepository.class));
        assertThat(
            "new token",
            controller.refreshToken("test@tsoftlatam.com", token),
            is(ok())
        );
    }

    private AuthToken randomToken() {
        return new AuthToken(UUID.randomUUID(), LocalDateTime.now().plusDays(1));
    }

    @Test
    public void refreshTokenWithInvalidTokenReturnsUNAUTHORIZED() {
        UsersService service = mock(UsersService.class);
        when(
            service.refreshToken(Mockito.any(String.class), Mockito.any(UUID.class))
        ).thenThrow(new RuntimeException());
        UsersController controller = new UsersController(service, mock(UsersRepository.class));
        assertThat(
            "new token",
            controller.refreshToken("test@tsoftlatam.com", UUID.randomUUID()),
            is(unauthorized())
        );
    }

    private Matcher<ResponseEntity> unauthorized() {
        return hasStatus(HttpStatus.UNAUTHORIZED);
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
