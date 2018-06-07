package hubble.backend.storage.repositories;

import hubble.backend.storage.models.ALM;
import hubble.backend.storage.models.AppPulse;
import hubble.backend.storage.models.BSM;
import hubble.backend.storage.models.Jira;
import hubble.backend.storage.models.PPM;
import hubble.backend.storage.models.ProviderStorage;
import hubble.backend.storage.models.SiteScope;
import hubble.backend.storage.operations.ProvidersOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class ProvidersRepositoryImpl implements ProvidersOperations {

    private final MongoOperations mongo;

    @Autowired
    public ProvidersRepositoryImpl(MongoOperations mongo) {
        this.mongo = mongo;
    }

    @Override
    public ALM alm() {
        return find(ALM.class, "alm");
    }

    @Override
    public AppPulse appPulse() {
        return find(AppPulse.class, "apppulse");
    }

    @Override
    public BSM bsm() {
        return find(BSM.class, "bsm");
    }

    @Override
    public Jira jira() {
        return find(Jira.class, "jira");
    }

    @Override
    public PPM ppm() {
        return find(PPM.class, "ppm");
    }

    @Override
    public SiteScope siteScope() {
        return find(SiteScope.class, "sitescope");
    }

    private <T extends ProviderStorage> T find(Class<T> type, String id) {
        return (T) mongo.findById(id, ProviderStorage.class);
    }
}
