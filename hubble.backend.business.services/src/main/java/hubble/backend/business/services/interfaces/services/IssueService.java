package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.interfaces.services.kpis.InstantOperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.interfaces.services.kpis.OperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.models.Issue;
import hubble.backend.business.services.models.measures.quantities.IssuesQuantity;
import hubble.backend.business.services.models.measures.kpis.IssuesKpi;
import hubble.backend.storage.models.ApplicationStorage;

import java.util.List;

public interface IssueService extends ServiceBase<Issue>,
        OperationsKeyPerformanceIndicatorServiceBase<IssuesKpi>,
        InstantOperationsKeyPerformanceIndicatorServiceBase<IssuesKpi> {

    public IssuesQuantity calculateIssuesQuantityLastDay(String applicationId);

    List<Integer> getDistValuesLastDay(String id);

    double calculateHistoryLastDayKpiByApplication(ApplicationStorage application);

    double calculateHistoryKPIByApplication(ApplicationStorage application,String periodo);

    double calculateHistoryDayBeforeKpiByApplication(ApplicationStorage application);
}
