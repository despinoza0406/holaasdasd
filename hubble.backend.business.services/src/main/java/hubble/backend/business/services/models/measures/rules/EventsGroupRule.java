package hubble.backend.business.services.models.measures.rules;

import hubble.backend.business.services.models.measures.Unit;

public class EventsGroupRule extends Rule<Integer> {

    public EventsGroupRule() {
        unitMeasure = Unit.MEASURES.QUANTITY;
        this.set(0);
    }
}
