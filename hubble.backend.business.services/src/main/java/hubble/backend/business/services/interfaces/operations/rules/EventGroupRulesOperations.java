package hubble.backend.business.services.interfaces.operations.rules;

import hubble.backend.business.services.interfaces.operations.kpis.KpiThresholdSetup;
import hubble.backend.business.services.models.business.ApplicationIndicators;
import hubble.backend.business.services.models.measures.rules.EventsGroupRule;
import hubble.backend.storage.models.EventStorage;

public interface EventGroupRulesOperations extends
        GroupRuleOperations<EventStorage, ApplicationIndicators>,
        KpiThresholdSetup {

    public EventsGroupRule calculateLastDayGroupRuleByApplication(String applicationId);

    public EventsGroupRule calculateLastWeekGroupRuleByApplication(String applicationId);

    public EventsGroupRule calculateLastMonthGroupRuleByApplication(String applicationId);
}
