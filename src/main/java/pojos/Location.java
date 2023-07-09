package pojos;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Location extends GenericIndex {

    private String regionIndex;
    private String mapDescription;
    private String locationType;

    private Set<Location> connectedLocations;

    public Location(String indexNumber) {
        super(indexNumber);
    }

    public String getRegionIndex() {
        return regionIndex;
    }

    public void setRegionIndex(String regionIndex) {
        this.regionIndex = regionIndex;
    }

    public String getMapDescription() {
        return mapDescription;
    }

    public void setMapDescription(String mapDescription) {
        this.mapDescription = mapDescription;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Set<Location> getConnectedLocations() {
        if (Objects.isNull(connectedLocations)) {
            connectedLocations = new HashSet<>();
        }
        return connectedLocations;
    }

    public void setConnectedLocations(Set<Location> connectedLocations) {
        this.connectedLocations = connectedLocations;
    }
}
