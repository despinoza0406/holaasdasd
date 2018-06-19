package hubble.backend.storage.models;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Cada Location es una computadora/robot o lo que sea que simula un usuario en la integración de BSM.
 */
@Document
public class LocationStorage {

    private String id;
    private String locationId;
    private String locationName;
    private boolean isInternal;

    public LocationStorage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public boolean isIsInternal() {
        return isInternal;
    }

    public void setIsInternal(boolean isInternal) {
        this.isInternal = isInternal;
    }

}
