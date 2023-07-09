package pojos;

import enums.Language;

public class Region extends GenericIndex {

    public Region(String indexNumber) {
        super(indexNumber);
    }

    @Override
    public String toString() {
        return getLocalizedName(Language.EN);
    }
}
