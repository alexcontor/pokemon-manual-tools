package pojos;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class LocationMethod extends GenericIndex {

    private String mainIndex;
    private String secondaryIndex;

    private String localizedMainName;
    private String localizedSecondaryName;

    public LocationMethod(String indexNumber) {
        super(indexNumber);
    }

    public String getMainIndex() {
        return mainIndex;
    }

    public void setMainIndex(String mainIndex) {
        this.mainIndex = mainIndex;
    }

    public String getSecondaryIndex() {
        return secondaryIndex;
    }

    public void setSecondaryIndex(String secondaryIndex) {
        this.secondaryIndex = secondaryIndex;
    }

    public String getLocalizedMainName() {
        return localizedMainName;
    }

    public void setLocalizedMainName(String localizedMainName) {
        this.localizedMainName = localizedMainName;
    }

    public String getLocalizedSecondaryName() {
        return localizedSecondaryName;
    }

    public void setLocalizedSecondaryName(String localizedSecondaryName) {
        this.localizedSecondaryName = localizedSecondaryName;
    }

    public String getFormattedLocation() {
        return StringUtils.isNotEmpty(localizedSecondaryName) ?
                localizedMainName + " (" + localizedSecondaryName + ")" :
                localizedMainName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationMethod that = (LocationMethod) o;
        return Objects.equals(mainIndex, that.mainIndex) && Objects.equals(secondaryIndex, that.secondaryIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainIndex, secondaryIndex);
    }

    @Override
    public String toString() {
        return "LocationMethod{" +
                "mainIndex='" + mainIndex + '\'' +
                ", secondaryIndex='" + secondaryIndex + '\'' +
                '}';
    }
}
