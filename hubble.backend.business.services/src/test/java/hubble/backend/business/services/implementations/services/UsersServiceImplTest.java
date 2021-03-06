package hubble.backend.business.services.implementations.services;

import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.UsersRepository;
import java.util.Collections;
import java.util.Random;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class UsersServiceImplTest {

    @Test(expected = RuntimeException.class)
    public void crearUserWithExistingEmailFails() {
        new UsersServiceImpl(
            repostoryThatReturnsThatEMailExists(),
            mock(ApplicationRepository.class)
        ).create(randomEmail(), "Martin Straus", password(), null, null);
    }

    private UsersRepository repostoryThatReturnsThatEMailExists() {
        final UsersRepository repository = mock(UsersRepository.class);
        when(repository.emailExists(anyString())).thenReturn(true);
        System.out.println(repository.emailExists(randomEmail()));
        return repository;
    }

    @Test
    public void crearUserSavesUserInRepository() {
        final UsersRepository repository = mock(UsersRepository.class);
        final UsersServiceImpl service = new UsersServiceImpl(repository, mock(ApplicationRepository.class));
        String email = randomEmail();
        char[] password = password();
        service.create(email, "Martin Straus", password, Collections.EMPTY_SET, Collections.EMPTY_SET);
        UserStorage expectedUser = new UserStorage(email, "Martin Straus", password, Collections.EMPTY_SET, Collections.EMPTY_SET);
        verify(repository).save(argThat(matches(expectedUser)));
    }

    private Matcher<UserStorage> matches(UserStorage user) {
        return new TypeSafeMatcher<UserStorage>() {
            @Override
            protected boolean matchesSafely(UserStorage t) {
                return t.getEmail().equals(user.getEmail())
                    && t.getName().equals(user.getName());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("user that matches ").appendValue(user);
            }

        };
    }

    private String randomEmail() {
        return String.format("%d@fit.com.ar", new Random().nextLong());
    }

    private char[] password() {
        return new char[]{};
    }
}
