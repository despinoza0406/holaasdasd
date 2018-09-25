package hubble.backend.business.services.tests;


import hubble.backend.business.services.tests.configurations.ServiceBaseConfigurationTest;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ServiceBaseConfigurationTest.class)
public class WorkItemServiceUnitTests {
}
