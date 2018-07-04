package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.interfaces.services.ApplicationService;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Guelmy Díaz <guelmy.diaz.blanco@fit.com.ar>
 */
@Service
public class ApplicationsServiceImpl implements ApplicationService{

    private final ApplicationRepository applicationRepository;

    public ApplicationsServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<ApplicationStorage> getAll() {
        return applicationRepository.findAll();
    }


}
