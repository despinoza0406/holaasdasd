package hubble.backend.business.services.configurations.mappers;

import hubble.backend.business.services.models.Event;
import hubble.backend.business.services.models.tables.EventsTable;

public class EventsMapper {

    public EventsMapper(){}

    public EventsTable mapEventToEventsTable(Event event){
        EventsTable eventsTable = new EventsTable();
        eventsTable.setOrigen(event.getProviderOrigin());
        eventsTable.setTipoDeMonitor(event.getType());
        eventsTable.setUpdatedDate(event.getUpdatedDate().toString());
        eventsTable.setEstado(event.getStatus());
        eventsTable.setSumario(String.format("Summary: %s",event.getSummary()));

        return eventsTable;
    }

}
