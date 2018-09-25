package hubble.backend.business.services.configurations.mappers;

import hubble.backend.business.services.models.tables.AvailabilityTable;
import hubble.backend.business.services.models.Availability;

public class AvailabilityMapper {

    public AvailabilityMapper() {}

    public AvailabilityTable mapAvailabilityToAvailabilityTable(Availability availability) {
        AvailabilityTable availabilityTable = new AvailabilityTable();
        availabilityTable.setOrigen(availability.getProviderOrigin());
        availabilityTable.setTransaccion(availability.getTransactionName());
        availabilityTable.setLocacion(availability.getLocationName());
        availabilityTable.setEstado(availability.getAvailabilityStatus());
        availabilityTable.setTimestamp(availability.getTimeStamp().toString());
        availabilityTable.setSumario(String.format("Script: %s Threshold: %s", availability.getScriptName(), availability.getAvailabilityThreshold()));
        return availabilityTable;
    }
}
