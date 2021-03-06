package hubble.backend.storage.integrationtests.repositories;

import hubble.backend.storage.configurations.StorageComponentConfiguration;
import hubble.backend.storage.models.ALM;
import hubble.backend.storage.models.Days;
import hubble.backend.storage.models.Frecuency;
import hubble.backend.storage.models.HourRange;
import hubble.backend.storage.models.Schedule;
import hubble.backend.storage.models.TaskRunner;
import hubble.backend.storage.repositories.ProvidersRepository;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
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
public class ProvidersRepositoryTest {

    @Autowired
    ProvidersRepository providers;

    @Test
    public void almRetrivesObjectOfCorrectType() {
            if(!providers.exists("alm")) {
                providers.save(alm());
                assertThat("ALM returned by ProvidersRepository", providers.alm(), is(not(nullValue())));
                providers.delete("alm");
            }


    }

    private ALM alm() {
        return new ALM(
            true,
            new TaskRunner(true, new Schedule(Days.EVERY_DAY, HourRange._24hs, Frecuency.HOURLY)),
            new ALM.Environment(),
            new ALM.Configuration()
        );
    }
}
