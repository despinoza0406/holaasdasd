package hubble.backend.business.services.implementations.services;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.interfaces.operations.averages.IssueOperations;
import hubble.backend.business.services.interfaces.operations.kpis.IssuesKpiOperations;
import hubble.backend.business.services.interfaces.services.IssueService;
import hubble.backend.business.services.models.Issue;
import hubble.backend.business.services.models.measures.IssuesQuantity;
import hubble.backend.business.services.models.measures.kpis.IssuesKpi;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.storage.models.IssueStorage;
import hubble.backend.storage.repositories.IssueRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public IssuesKpi calculateLastMonthKpiByApplication(String applicationId) {
        return issueKpiOperation.calculateLastMonthKeyPerformanceIndicatorByApplication(applicationId);
    }

    @Override
    public IssuesQuantity calculateIssuesQuantityLastDay(String applicationId) {
        return issueOperation.calculateIssuesQuantityLastDay(applicationId);
    }

}
