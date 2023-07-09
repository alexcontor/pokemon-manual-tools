package pojos;

import enums.Language;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericIndex {

    private Integer rowID;
    private String indexNumber;
    private Map<Language, String> localizedNameMap;
    private List<LocalizedColumn> localizedColumnList = new ArrayList<>();

    public GenericIndex(String indexNumber) {
        this.indexNumber = indexNumber;
        localizedNameMap = new HashMap<>();
    }

    public GenericIndex(String localizedEN, String localizedES) {
        localizedNameMap = new HashMap<>();
        localizedNameMap.put(Language.EN, localizedEN);
        localizedNameMap.put(Language.ES, localizedES);
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    public String getLocalizedName(Language language) {
        return StringUtils.firstNonEmpty(localizedNameMap.get(language), localizedNameMap.get(Language.EN));
    }

    public void setLocalizedName(Language language, String localizedName) {
        localizedNameMap.put(language, localizedName);
    }

    public boolean addLocalizedColumn(String columnName, String englishValue, String localizedValue) {
        return localizedColumnList.add(new LocalizedColumn(columnName, englishValue, localizedValue));
    }

    public List<LocalizedColumn> getLocalizedColumnList() {
        return localizedColumnList;
    }

    public Integer getRowID() {
        return rowID;
    }

    public void setRowID(Integer rowID) {
        this.rowID = rowID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericIndex genericIndex = (GenericIndex) o;
        return indexNumber.equals(genericIndex.getIndexNumber());
    }

    @Override
    public int hashCode() {
        int result = indexNumber.hashCode();
        result = 31 * result;
        return result;
    }

    @Override
    public String toString() {
        return getLocalizedName(Language.EN);
    }
}
