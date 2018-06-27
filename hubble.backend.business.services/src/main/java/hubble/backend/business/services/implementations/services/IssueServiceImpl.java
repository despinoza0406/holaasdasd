package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.interfaces.operations.averages.IssueOperations;
import hubble.backend.business.services.interfaces.operations.kpis.IssuesKpiOperations;
import hubble.backend.business.services.interfaces.services.IssueService;
import hubble.backend.business.services.models.Issue;
import hubble.backend.business.services.models.measures.quantities.IssuesQuantity;
import hubble.backend.business.services.models.measures.kpis.IssuesKpi;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.DateHelper;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.Defects;
import hubble.backend.storage.models.IssueStorage;
import hubble.backend.storage.models.Threashold;
import hubble.backend.storage.repositories.IssueRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.Math.round;

@Component
public class IssueServiceImpl implements IssueService {

    @Autowired
    IssueRepository issueRepository;
    @Autowired
    MapperConfiguration mapper;
    @Autowired
    IssueOperations issueOperation;
    @Autowired
    IssuesKpiOperations issueKpiOperation;

    private double lWarningKpiThreshold;
    private double lCriticalKpiThreshold;
    private double okKpiThreshold;

    @Override
    public List<Issue> getLastDay(String applicationId) {

        Calendar yesterday = CalendarHelper.getNow();
        yesterday.add(Calendar.HOUR, -24);
        Date today = new GregorianCalendar().getTime();

        List<IssueStorage> issues = issueRepository.findIssuesByApplicationIdBetweenDates(applicationId, yesterday.getTime(), today);

        return mapper.mapToIssueDtoList(issues);
    }

    @Override
    public List<Issue> getLastMonth(String applicationId) {
        Calendar lastmonth = CalendarHelper.getNow();
        lastmonth.add(Calendar.MONTH, -1);
        Date today = new GregorianCalendar().getTime();

        List<IssueStorage> issues = issueRepository.findIssuesByApplicationIdBetweenDates(applicationId, lastmonth.getTime(), today);

        return mapper.mapToIssueDtoList(issues);
    }

    @Override
    public IssuesKpi calculateLast10MinutesKpiByApplication(String applicationId) {
        return issueKpiOperation.calculateLast10MinutesKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public IssuesKpi calculateLastHourKpiByApplication(String applicationId) {
        return issueKpiOperation.calculateLastHourKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public IssuesKpi calculateLastDayKpiByApplication(String applicationId) {
        return issueKpiOperation.calculateLastDayKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public double calculateHistoryDayBeforeKpiByApplication(ApplicationStorage application) {
        Defects issues = application.getKpis().getDefects();
        Threashold lastDayThreshold = issues.getDayThreashold();
        this.lCriticalKpiThreshold = lastDayThreshold.getCritical();
        List<IssueStorage> issuesStorage =
                issueRepository.findIssuesByApplicationIdBetweenTimestampDates(application.getId(), DateHelper.getNDaysBefore(2), DateHelper.getYesterday());
        return calculateKPI(issuesStorage);
    }

    @Override
    public double calculateHistoryLastDayKpiByApplication(ApplicationStorage application) {
        Defects issues = application.getKpis().getDefects();
        Threashold lastDayThreshold = issues.getDayThreashold();
        this.lCriticalKpiThreshold = lastDayThreshold.getCritical();
        List<IssueStorage> issuesStorage =
                issueRepository.findIssuesByApplicationIdBetweenTimestampDates(application.getId(), DateHelper.getYesterday(), DateHelper.getDateNow());
        return calculateKPI(issuesStorage);
    }

    @Override
    public IssuesKpi calculateLastMonthKpiByApplication(String applicationId) {
        return issueKpiOperation.calculateLastMonthKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public IssuesQuantity calculateIssuesQuantityLastDay(String applicationId) {
        return issueOperation.calculateIssuesQuantityLastDay(applicationId);
    }

    @Override
    public List<Integer> getDistValuesLastDay(String id) {
        List<IssueStorage> issuesStorage =
                issueRepository.findIssuesByApplicationIdBetweenDates(id, DateHelper.getYesterday(), DateHelper.getDateNow());
        List<Integer> distValuesInt = new ArrayList<>();
        for (IssueStorage issue : issuesStorage) {
            float criticity = (issue.getPriority() + issue.getSeverity()) / 2;
            distValuesInt.add(calculateCriticityForDashboardTwo(criticity));
        }
        return distValuesInt;
    }

    private Integer calculateCriticityForDashboardTwo(float criticity){
        if(criticity >= 4){
            return 1;
        }
        if(criticity >=3) {
            return 2;
        } else
            return 3;
    }

    private double calculateKPI(List<IssueStorage> issuesStorage){
        double totalCriticity = 0;

        for(IssueStorage issueStorage : issuesStorage) {
            double criticity = round((issueStorage.getSeverity() + issueStorage.getPriority()) / 2);
            totalCriticity = totalCriticity + criticity;
        }



        if(totalCriticity > lCriticalKpiThreshold) {
            return 0;
        }

        if(totalCriticity == 0) {
            return 10;
        }

        return ( (totalCriticity - 1) / (lCriticalKpiThreshold - 1) ) * (10-1) + 1;
    }
}
