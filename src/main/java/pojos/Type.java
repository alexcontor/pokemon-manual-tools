package pojos;

import enums.Language;

public class Type extends GenericIndex {
    public Type(String indexNumber) {
        super(indexNumber);
    }

    public Type(String localizedEN, String localizedES) {
        super(localizedEN, localizedES);
    }

}
