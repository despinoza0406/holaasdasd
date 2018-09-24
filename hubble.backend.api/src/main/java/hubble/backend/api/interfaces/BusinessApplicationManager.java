package hubble.backend.api.interfaces;

import hubble.backend.api.models.ApplicationUptime;
import hubble.backend.api.models.BusinessApplication;
import hubble.backend.api.models.BusinessApplicationFrontend;
import hubble.backend.api.models.BusinessApplicationLigth;
import hubble.backend.api.models.BusinessApplicationProfile;
import hubble.backend.business.services.models.Availability;
import hubble.backend.business.services.models.distValues.LineGraphDistValues;
import hubble.backend.storage.models.KPIs;

import java.util.List;

public interface BusinessApplicationManager {

    public BusinessApplicationProfile getBusinessApplicationView(String id);

    public List<BusinessApplication> getAllApplications();

    public List<ApplicationUptime> getUptimeLastMonth(String applicationId);

    public List<BusinessApplicationProfile> getBusinessApplicationsPageLimit(int page, int limit);

    public BusinessApplicationFrontend getBusinessApplicationFrontend(String id,String periodo);

    public List<BusinessApplicationFrontend> getBusinessApplicationsFrontend(boolean includeInactives,String periodo);

    public BusinessApplicationFrontend getBusinessApplicationFrontendDistValues(String id,String period);

    public KPIs getKPIs(String id,String periodo);
    
    public List<BusinessApplicationLigth> getApplicationsLigth(boolean includeInactives);

    public List<LineGraphDistValues> getLineGraphDistValuesOf(String kpiName, String id, String period);
}
