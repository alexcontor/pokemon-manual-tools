package enums;

public enum EncounterRates {

    COMMON("1"),
    UNCOMMON("2"),
    RARE("3"),
    LIMITED("4");

    private String index;

    EncounterRates(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }
}
