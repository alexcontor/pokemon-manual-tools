package enums;

import utils.PokemonUtils;

public enum PokemonType {

    NORMAL_FORM(PokemonUtils.NORMAL_FORMS_INTERFIX),
    VARIANT_FORM(PokemonUtils.VARIANT_FORMS_INTERFIX),
    ALTERNATE_FORM(PokemonUtils.ALTERNATE_FORMS_INTERFIX),
    MEGA_EVOLUTION(PokemonUtils.MEGA_EVOLUTIONS_INTERFIX),
    REGIONAL_FORM(""),
    ALOLAN_FORM(PokemonUtils.ALOLA_FORMS_INTERFIX),
    GALARIAN_FORM(PokemonUtils.GALAR_FORMS_INTERFIX),
    HISUIAN_FORM(PokemonUtils.HISUI_FORMS_INTERFIX),
    PALDEAN_FORM(PokemonUtils.PALDEA_FORMS_INTERFIX),
    GIGANTAMAX(PokemonUtils.GIGANTAMAX_INTERFIX);

    private String id;

    PokemonType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
