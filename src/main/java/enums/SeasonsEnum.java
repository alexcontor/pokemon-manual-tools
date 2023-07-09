package enums;

public enum SeasonsEnum {

    ANY("0"),
    SPRING("1"),
    SUMMER("2"),
    AUTUMN("3"),
    WINTER("4");

    private String index;

    SeasonsEnum(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }
}
