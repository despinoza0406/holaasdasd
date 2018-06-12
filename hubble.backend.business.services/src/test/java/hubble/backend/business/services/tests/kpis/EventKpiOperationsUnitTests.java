package hubble.backend.business.services.tests.kpis;


import hubble.backend.business.services.interfaces.operations.kpis.EventKpiOperations;
import hubble.backend.business.services.tests.StorageTestsHelper;
import hubble.backend.business.services.tests.configurations.ServiceBaseConfigurationTest;
import hubble.backend.storage.configurations.StorageComponentConfiguration;
import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.repositories.EventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceBaseConfigurationTest.class, StorageComponentConfiguration.class})
@Ignore
public class EventKpiOperationsUnitTests {

    @Autowired
    EventKpiOperations kpiOperations;
    @Autowired
    EventRepository eventRepository;
    StorageTestsHelper helper = new StorageTestsHelper();


    @Test
    public void event_repository_should_be_instantiated(){
        assertNotNull(eventRepository);
    }

    @Test
    public void should_be_all_good(){
        List<EventStorage> storages = new ArrayList<>();

        for(int i = 0; i < 1500 ; i++) {
            EventStorage eventStorage = helper.getGoodFakeEventStorage(i);

            //Act
            eventStorage = eventRepository.insert(eventStorage);
            storages.add(eventStorage);

        }

        assert (kpiOperations.calculateLastDayKPI("Benchmark Home Banking") == 10);

        for(EventStorage storage : storages) {

            eventRepository.delete(storage.getId());

        }
    }

    @Test
    public void should_be_all_bad(){
        List<EventStorage> storages = new ArrayList<>();

        for(int i = 0; i < 75 ; i++) {
            EventStorage eventStorage = helper.getErrorFakeEventStorage(i);

            //Act
            eventStorage = eventRepository.insert(eventStorage);
            storages.add(eventStorage);

        }

        assert (kpiOperations.calculateLastDayKPI("Benchmark Home Banking") == 0);

        for(EventStorage storage : storages) {

            eventRepository.delete(storage.getId());

        }
    }




}
