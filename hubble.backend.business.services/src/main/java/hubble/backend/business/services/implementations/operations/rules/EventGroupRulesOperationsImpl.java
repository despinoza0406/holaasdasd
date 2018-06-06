package hubble.backend.business.services.implementations.operations.rules;

import hubble.backend.business.services.configurations.mappers.MapperConfiguration;
import hubble.backend.business.services.interfaces.operations.rules.EventGroupRulesOperations;
import hubble.backend.business.services.models.business.ApplicationIndicators;
import hubble.backend.business.services.models.measures.rules.EventsGroupRule;
import hubble.backend.core.enums.MonitoringFields;
import hubble.backend.core.utils.CalendarHelper;
import hubble.backend.core.utils.Threshold;
import hubble.backend.storage.models.ApplicationStorage;
import hubble.backend.storage.models.EventStorage;
import hubble.backend.storage.repositories.ApplicationRepository;
import hubble.backend.storage.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EventGroupRulesOperationsImpl implements EventGroupRulesOperations {


    @Autowired
    EventRepository eventsRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    MapperConfiguration mapper;

    private double warningKpiThreshold;
    private double criticalKpiThreshold;
    private double warningIdxThreshold;
    private double criticalIdxThreshold;


    @Override
    public Double calculateGroupRule(List<EventStorage> events) {
        double totalDays = 0;

        for (int i = 0; i < events.size(); i++) {
            totalDays += events.get(i).getSeverityPoints();
        }

        return (Double) totalDays;
    }

    @Override
    public EventsGroupRule calculateLastDayGroupRuleByApplication(String applicationId) {

        ApplicationStorage applicationStorage = applicationRepository.findApplicationById(applicationId);

        ApplicationIndicators applicationAvailabilityIndicators = mapper.mapToApplicationIndicatorsDto(applicationStorage);

        List<EventStorage> workItems = eventsRepository.findEventsByApplicationIdAndDurationMinutes(CalendarHelper.ONE_DAY, applicationId);

        Double workItemsKpi = calculateGroupRule(workItems);

        EventsGroupRule workItemsGroupRule = new EventsGroupRule();

        setRangeKpiLastDay();
        workItemsGroupRule.setStatus(calculateGroupRuleStatus(applicationAvailabilityIndicators, workItemsKpi));
        workItemsGroupRule.set(workItemsKpi.intValue());

        return workItemsGroupRule;

    }

    @Override
    public EventsGroupRule calculateLastWeekGroupRuleByApplication(String applicationId) {
        ApplicationStorage applicationStorage = applicationRepository.findApplicationById(applicationId);

        ApplicationIndicators applicationAvailabilityIndicators = mapper.mapToApplicationIndicatorsDto(applicationStorage);

        List<EventStorage> workItems = eventsRepository.findEventsByApplicationIdAndDurationMinutes(CalendarHelper.ONE_WEEK, applicationId);

        Double workItemsKpi = calculateGroupRule(workItems);

        EventsGroupRule workItemsGroupRule = new EventsGroupRule();

        setRangeKpiLastWeek();
        workItemsGroupRule.setStatus(calculateGroupRuleStatus(applicationAvailabilityIndicators, workItemsKpi));
        workItemsGroupRule.set(workItemsKpi.intValue());

        return workItemsGroupRule;
    }

    @Override
    public EventsGroupRule calculateLastMonthGroupRuleByApplication(String applicationId) {

        List<EventStorage> workItems = eventsRepository.findEventsByApplicationIdAndDurationMonths(1, applicationId);

        Double issuesGroupRule = calculateGroupRule(workItems);

        EventsGroupRule workItemsGroupRule = new EventsGroupRule();

        setRangeKpiLastMonth();
        workItemsGroupRule.setStatus(calculateGroupRuleStatus(null, issuesGroupRule));
        workItemsGroupRule.set(issuesGroupRule.intValue());

        return workItemsGroupRule;
    }

    @Override
    public MonitoringFields.STATUS calculateGroupRuleStatus(ApplicationIndicators appIndicator, Double measure) {

        if (measure <= this.warningKpiThreshold) {
            return MonitoringFields.STATUS.SUCCESS;
        } else if (measure > this.warningKpiThreshold
                && measure < this.criticalKpiThreshold) {
            return MonitoringFields.STATUS.WARNING;
        } else if (measure > this.criticalKpiThreshold) {
            return MonitoringFields.STATUS.CRITICAL;
        }

        return MonitoringFields.STATUS.NO_DATA;
    }


    private void setRangeKpiLastDay() {

        this.warningKpiThreshold = Threshold.WorkItems.WARNING_WORKITEMS_WEEK_DEFAULT;
        this.criticalKpiThreshold = Threshold.WorkItems.CRITICAL_WORKITEMS_WEEK_DEFAULT;
    }

    private void setRangeKpiLastWeek() {

        this.warningKpiThreshold = Threshold.WorkItems.WARNING_WORKITEMS_WEEK_DEFAULT;
        this.criticalKpiThreshold = Threshold.WorkItems.CRITICAL_WORKITEMS_WEEK_DEFAULT;
    }

    private void setRangeKpiLastMonth() {

        this.warningKpiThreshold = Threshold.WorkItems.WARNING_WORKITEMS_MONTH_DEFAULT;
        this.criticalKpiThreshold = Threshold.WorkItems.CRITICAL_WORKITEMS_MONTH_DEFAULT;
    }

    @Override
    public double getWarningKpiThreshold() {
        return this.warningKpiThreshold;
    }

    @Override
    public void setWarningKpiThreshold(double threshold) {
        this.warningKpiThreshold = threshold;
    }

    @Override
    public double getCriticalKpiThreshold() {
        return this.criticalKpiThreshold;
    }

    @Override
    public void setCriticalKpiThreshold(double threshold) {
        this.criticalKpiThreshold = threshold;
    }

    @Override
    public double getWarningIdxThreshold() {
        return this.warningIdxThreshold;
    }

    @Override
    public void setWarningIdxThreshold(double threshold) {
        this.warningIdxThreshold = threshold;
    }

    @Override
    public double getCriticalIdxThreshold() {
        return this.criticalIdxThreshold;
    }

    @Override
    public void setCriticalIdxThreshold(double threshold) {
        this.criticalIdxThreshold = threshold;
    }

}
