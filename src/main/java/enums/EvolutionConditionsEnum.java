package enums;

public enum EvolutionConditionsEnum {

    NONE("0"),
    LEVEL("1"),
    ITEM("2"),
    TRADING("3"),
    FRIENDSHIP("4"),
    TIME_OF_DAY("5"),
    GENDER("6"),
    MOVE("7"),
    ABILITY("8"),
    LOCATION("9"),
    REGION("10"),
    POKEMON("11");

    private String index;

    EvolutionConditionsEnum(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }


}
