package enums;

public enum PokedexGameVersion {

    ALL_GAMES("all"),
    RED("red"),
    BLUE("blue"),
    YELLOW("yellow"),
    RED_BLUE("red¬blue"),
    RED_BLUE_YELLOW("red¬blue¬yellow"),

    GOLD("gold"),
    SILVER("silver"),
    CRYSTAL("crystal"),
    GOLD_SILVER("gold¬silver"),
    GOLD_SILVER_CRYSTAL("gold¬silver¬crystal"),

    RUBY("ruby"),
    SAPPHIRE("sapphire"),
    EMERALD("emerald"),
    RUBY_SAPPHIRE("ruby¬sapphire"),
    RUBY_SAPPHIRE_EMERALD("ruby¬sapphire¬emerald"),

    FIRERED("firered"),
    LEAFGREEN("leafgreen"),
    FIRERED_LEAFGREEN("firered¬leafgreen"),

    DIAMOND("diamond"),
    PEARL("pearl"),
    PLATINUM("platinum"),
    DIAMOND_PEARL("diamond¬pearl"),
    DIAMOND_PEARL_PLATINUM("diamond¬pearl¬platinum"),

    HEARTGOLD("heartgold"),
    SOULSILVER("soulsilver"),
    HEARTGOLD_SOULSILVER("heartgold¬soulsilver"),

    BLACK("black"),
    WHITE("white"),
    BLACK_WHITE("black¬white"),

    BLACK_2("black_2"),
    WHITE_2("white_2"),
    BLACK_2_WHITE_2("black_2¬white_2"),

    X("x"),
    Y("y"),
    X_Y("x¬y"),

    OMEGA_RUBY("omega_ruby"),
    ALPHA_SAPPHIRE("alpha_sapphire"),
    OMEGA_RUBY_ALPHA_SAPPHIRE("omega_ruby¬alpha_sapphire"),

    SUN("sun"),
    MOON("moon"),
    SUN_MOON("sun¬moon"),

    ULTRA_SUN("ultra_sun"),
    ULTRA_MOON("ultra_moon"),
    ULTRA_SUN_ULTRA_MOON("ultra_sun¬ultra_moon"),

    LETS_GO_PIKACHU("lets_go_pikachu"),
    LETS_GO_EEVEE("lets_go_eevee"),
    LETS_GO_PIKACHU_LETS_GO_EEVEE("lets_go_pikachu¬lets_go_eevee"),

    SWORD("sword"),
    SHIELD("shield"),
    SWORD_SHIELD("sword¬shield"),

    BRILLIANT_DIAMOND("brilliant_diamond"),
    SHINING_PEARL("shining_pearl"),
    BRILLIANT_DIAMOND_SHINING_PEARL("brilliant_diamond¬shining_pearl"),

    LEGENDS_ARCEUS("arceus_legends"),

    SCARLET("scarlet"),
    VIOLET("violet"),
    SCARLET_VIOLET("scarlet_violet"),

    PAL_PARK("pal_park"),
    POKEWALKER("pokewalker"),
    DREAM_WORLD("dream_world"),
    EXPANSION_PASS("expansion_pass"),
    ISLE_OF_ARMOR("isle_of_armor"),
    CROWN_OF_TUNDRA("crown_tundra");

    private final String index;

    private PokedexGameVersion(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }
}