package hubble.backend.storage.integrationtests.repositories;


import hubble.backend.storage.configurations.StorageComponentConfiguration;
import hubble.backend.storage.helper.StorageTestsHelper;
import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.repositories.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StorageComponentConfiguration.class)
public class EventRepositoryTests {

    @Autowired
    EventRepository eventRepository;
    StorageTestsHelper helper = new StorageTestsHelper();

    @Test
    public void event_repository_should_be_instantiated(){
        assertNotNull(eventRepository);
    }

    @Test
    public void AvailabilityRepository_should_be_able_to_save_records() {

        EventStorage eventStorage = helper.getFakeEventStorage();

        //Act
        eventStorage = eventRepository.insert(eventStorage);

        //Assert
        assertNotNull(eventStorage.getId());
        eventRepository.delete(eventStorage.getId());
    }

}
