package hubble.backend.storage.operations;

import hubble.backend.storage.models.ALM;
import hubble.backend.storage.models.AppPulse;
import hubble.backend.storage.models.BSM;
import hubble.backend.storage.models.Jira;
import hubble.backend.storage.models.PPM;
import hubble.backend.storage.models.SiteScope;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public interface ProvidersOperations {

    ALM alm();

    AppPulse appPulse();

    BSM bsm();

    Jira jira();

    PPM ppm();

    SiteScope siteScope();
}
