package enums;

public enum TimesOfDayEnum {

    ANY("0"),
    MORNING("1"),
    DAY("2"),
    EVENING("3"),
    NIGHT("4");

    private String index;

    TimesOfDayEnum(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }
}
