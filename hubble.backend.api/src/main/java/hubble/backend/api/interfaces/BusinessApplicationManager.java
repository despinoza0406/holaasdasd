package hubble.backend.api.interfaces;

import hubble.backend.api.models.ApplicationUptime;
import hubble.backend.api.models.BusinessApplication;
import hubble.backend.api.models.BusinessApplicationFrontend;
import hubble.backend.api.models.BusinessApplicationProfile;
import hubble.backend.business.services.models.Availability;
import java.util.List;

public interface BusinessApplicationManager {

    public BusinessApplicationProfile getBusinessApplicationView(String id);

    public List<BusinessApplication> getAllApplications();

    public List<ApplicationUptime> getUptimeLastMonth(String applicationId);

    public List<Availability> getAvailabilityLast10Minutes(String applicationId);

    public List<Availability> getAvailabilityLastHour(String applicationId);

    public List<BusinessApplicationProfile> getBusinessApplicationsPageLimit(int page, int limit);

    public BusinessApplicationFrontend getBusinessApplicationFrontend(String id);

    public List<BusinessApplicationFrontend> getBusinessApplicationsFrontend();

    public BusinessApplicationFrontend getBusinessApplicationFrontendDistValues(String id);
}
