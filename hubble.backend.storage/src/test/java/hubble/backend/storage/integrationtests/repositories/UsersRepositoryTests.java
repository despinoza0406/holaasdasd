package hubble.backend.storage.integrationtests.repositories;

import hubble.backend.storage.configurations.StorageComponentConfiguration;
import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.repositories.UsersRepository;
import java.util.Collections;
import java.util.Random;
import static org.hamcrest.CoreMatchers.*;
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

    private String randomEmail() {
        return String.format("%d@fit.com.ar", new Random().nextLong());
    }

    private char[] randomPassword() {
        return new char[]{};
    }

}
