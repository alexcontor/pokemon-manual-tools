package pojos;

import enums.Language;

public class RegionArea extends GenericIndex {

    public RegionArea(String indexNumber) {
        super(indexNumber);
    }

    @Override
    public String toString() {
        return getLocalizedName(Language.EN);
    }
}
