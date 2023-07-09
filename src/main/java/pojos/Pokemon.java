package pojos;

import enums.GendersEnum;
import enums.Language;
import enums.PokemonType;

public class Pokemon extends GenericIndex {

    private String color;
    private String colorDark;
    private Type type1;
    private Type type2;
    private String baseStats;
    private int realID;
    private Pokedex pokedex;
    private String evs;
    private GendersEnum gendersEnum;
    private PokemonType pokemonType;

    public Pokemon(String indexNumber) {
        super(indexNumber);
    }

    public Pokemon(String indexNumber, int realID) {
        super(indexNumber);
        this.realID = realID;
    }

    public int getRealID() {
        return realID;
    }

    public void setRealID(int realID) {
        this.realID = realID;
    }

    public String getNormalizedName(Language language) {
        return getLocalizedName(language).replaceAll("\\s+", "-")
                .replace(".", "")
                .replace(":", "")
                .replace("'", "")
                .replace("(", "")
                .replace(")", "")
                .replace("%", "")
                .replace("♂", "-m")
                .replace("♀", "-f")
                .toLowerCase();
    }


    public Pokedex getPokedex() {
        return pokedex;
    }

    public void setPokedex(Pokedex pokedex) {
        this.pokedex = pokedex;
    }

    public String getEvs() {
        return evs;
    }

    public void setEvs(String evs) {
        this.evs = evs;
    }

    @Override
    public String toString() {
        return "Nº" + realID + " - " + getLocalizedName(Language.EN);
    }

    public String getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(String baseStats) {
        this.baseStats = baseStats;
    }

    public Type getType1() {
        return type1;
    }

    public void setType1(Type type1) {
        this.type1 = type1;
    }

    public Type getType2() {
        return type2;
    }

    public void setType2(Type type2) {
        this.type2 = type2;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorDark() {
        return colorDark;
    }

    public void setColorDark(String colorDark) {
        this.colorDark = colorDark;
    }

    public GendersEnum getGendersEnum() {
        return gendersEnum;
    }

    public void setGendersEnum(GendersEnum gendersEnum) {
        this.gendersEnum = gendersEnum;
    }

    public PokemonType getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(PokemonType pokemonType) {
        this.pokemonType = pokemonType;
    }
}
