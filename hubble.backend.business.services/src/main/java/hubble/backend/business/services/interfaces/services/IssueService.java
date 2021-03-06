package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.interfaces.services.kpis.InstantOperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.interfaces.services.kpis.OperationsKeyPerformanceIndicatorServiceBase;
import hubble.backend.business.services.models.distValues.DistValues;
import hubble.backend.business.services.models.Issue;
import hubble.backend.business.services.models.distValues.LineGraphDistValues;
import hubble.backend.business.services.models.measures.quantities.IssuesQuantity;
import hubble.backend.business.services.models.measures.kpis.IssuesKpi;
import hubble.backend.core.enums.Results;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.TaskRunnerExecution;

import java.sql.SQLTransactionRollbackException;
import java.util.List;

public interface IssueService extends ServiceBase<Issue>,
        OperationsKeyPerformanceIndicatorServiceBase<IssuesKpi>,
        InstantOperationsKeyPerformanceIndicatorServiceBase<IssuesKpi> {

    public IssuesQuantity calculateIssuesQuantityLastDay(String applicationId);

    List<Integer> getDistValuesLastDay(String id);

    List<DistValues> getDistValues(String id, String periodo);

    List<LineGraphDistValues> getLineGraphDistValues(String id, String periodo);

    double calculateHistoryLastDayKpiByApplication(ApplicationStorage application);

    double calculateHistoryKPIByApplication(ApplicationStorage application,String periodo);

    double calculateHistoryDayBeforeKpiByApplication(ApplicationStorage application);

    String calculatePeriod(String periodo);

    String calculatePeriodFrontend(String periodo);

    Results.RESULTS calculateKpiResult(String applicationId,String periodo);

    List<TaskRunnerExecution> getTaskRunnerExecutions(String applicationId,String periodo);

    Issue get(String id);

    List<Issue> getIssuesBetweenDates(String appId,String dateFrom, String dateTo);
}
