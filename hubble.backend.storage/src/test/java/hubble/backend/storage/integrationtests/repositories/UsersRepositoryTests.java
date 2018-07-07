package hubble.backend.storage.integrationtests.repositories;

import hubble.backend.storage.configurations.StorageComponentConfiguration;
import hubble.backend.storage.models.AuthToken;
import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.repositories.UsersRepository;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StorageComponentConfiguration.class)
public class UsersRepositoryTests {

    @Autowired
    UsersRepository users;

    @Test
    public void springInjectsImplementation() {
        assertThat("UsersRepository was injected", users, is(not(nullValue())));
    }

    @Test
    public void findByEmailWithExistingEmailReturnsUser() {
        String email = randomEmail();
        users.save(new UserStorage(email, "test", randomPassword(), Collections.EMPTY_SET, Collections.EMPTY_SET));
        assertThat("is present", users.findByEmail(email).isPresent(), is(true));
    }

    @Test
    public void existsRetursTrueIfEmailExists() {
        final String email = randomEmail();
        users.save(new UserStorage(email, "test", randomPassword(), Collections.EMPTY_SET, Collections.EMPTY_SET));
        assertThat("email exists", users.emailExists(email), is(true));
    }

    @Test
    public void existsRetursFalseIfEmailDoesntExist() {
        final String email = randomEmail();
        assertThat("email doesn't exist", users.emailExists(email), is(false));
    }
    
    @Test
    public void findByTokenReturnsCorrectUser(){
        final String email = randomEmail();
        final String password = "abcde12345";
        final UserStorage user = new UserStorage(email, "test", randomPassword(), Collections.EMPTY_SET, Collections.EMPTY_SET);
        user.changePassword(password.toCharArray());
        users.insert(user);
        AuthToken token = user.authenticate(password.toCharArray());
        users.save(user);
        assertThat(
            "user for token", 
            users.findByAccessToken(token.getToken()), 
            is(present())
        );
    }
    
    private Matcher<Optional> present() {
        return new TypeSafeMatcher<Optional>() {
            @Override
            protected boolean matchesSafely(Optional t) {
                return t.isPresent();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("present");
            }

            @Override
            protected void describeMismatchSafely(Optional item, Description mismatchDescription) {
                mismatchDescription.appendText("not present");
            }
            
            
        };
    }

    private String randomEmail() {
        return String.format("%d@fit.com.ar", new Random().nextLong());
    }

    private char[] randomPassword() {
        return new char[]{};
    }

}
