package enums;

public enum GendersEnum {

    MALE("1"),
    FEMALE("2");

    private String index;

    GendersEnum(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }
}
