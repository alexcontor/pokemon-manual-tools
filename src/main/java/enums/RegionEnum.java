package enums;

public enum RegionEnum {

    KANTO("kanto"),
    JOHTO("johto"),
    HOENN("hoenn"),
    SINNOH("sinnoh"),
    UNOVA("unova"),
    KALOS("kalos"),
    ALOLA("alola"),
    GALAR("galar"),
    HISUI("hisui"),
    PALDEA("paldea");

    private String index;

    RegionEnum(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }
}
