package utils;


import enums.GameVersion;
import enums.Language;
import pojos.Pokemon;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokemonDBImageRetrieveUtils {

    private static final Map<GameVersion, String> imageGameCodeForGameGeneration = new HashMap<GameVersion, String>() {
        {
            put(GameVersion.RED, "red-blue");
            put(GameVersion.BLUE, "red-blue");
            put(GameVersion.YELLOW, "yellow");
            put(GameVersion.GOLD, "gold");
            put(GameVersion.SILVER, "silver");
            put(GameVersion.CRYSTAL, "crystal");
            put(GameVersion.RUBY, "ruby-sapphire");
            put(GameVersion.SAPPHIRE, "ruby-sapphire");
            put(GameVersion.EMERALD, "emerald");
            put(GameVersion.LEAFGREEN, "firered-leafgreen");
            put(GameVersion.FIRERED, "firered-leafgreen");
            put(GameVersion.DIAMOND, "diamond-pearl");
            put(GameVersion.PEARL, "diamond-pearl");
            put(GameVersion.PLATINUM, "platinum");
            put(GameVersion.HEARTGOLD, "heartgold-soulsilver");
            put(GameVersion.SOULSILVER, "heartgold-soulsilver");
            put(GameVersion.WHITE, "black-white");
            put(GameVersion.BLACK, "black-white");
            put(GameVersion.WHITE_2, "black-white/anim");
            put(GameVersion.BLACK_2, "black-white/anim");
            put(GameVersion.X, "x-y");
            put(GameVersion.Y, "x-y");
            put(GameVersion.OMEGA_RUBY, "omega-ruby-alpha-sapphire/dex");
            put(GameVersion.ALPHA_SAPPHIRE, "omega-ruby-alpha-sapphire/dex");
            put(GameVersion.SUN, "sun-moon");
            put(GameVersion.MOON, "sun-moon");
            put(GameVersion.ULTRA_SUN, "ultra-sun-ultra-moon");
            put(GameVersion.ULTRA_MOON, "ultra-sun-ultra-moon");
            put(GameVersion.LETS_GO_PIKACHU, "home");
            put(GameVersion.LETS_GO_EEVEE, "home");
            put(GameVersion.SWORD, "home");
            put(GameVersion.SHIELD, "home");
            put(GameVersion.LEGENDS_ARCEUS, "legends-arceus");
            put(GameVersion.SCARLET, "scarlet-violet");
            put(GameVersion.VIOLET, "scarlet-violet");
        }
    };

    private static final String MINI_SPRITE_BASE_URL = "https://img.pokemondb.net/sprites/%s/icon/%s.%s";
    private static final String MINI_SPRITE_GIGANTAMAX_BASE_URL = "https://img.pokemondb.net/sprites/%s/normal/%s.%s";
    private static final String MINI_SPRITE_ALOLA_BASE_URL = "https://img.pokemondb.net/sprites/%s/icon/%s.%s";
    private static final String MINI_SPRITE_GALAR_BASE_URL = "https://img.pokemondb.net/sprites/%s/icon/%s.%s";
    private static final String MINI_SPRITE_HISUI_BASE_URL = "https://img.pokemondb.net/sprites/%s/normal/1x/%s.%s";
    private static final String MINI_SPRITE_PALDEA_BASE_URL = "https://img.pokemondb.net/sprites/%s/normal/1x/%s.%s";

    private static final String NORMAL_SPRITE_BASE_URL = "https://img.pokemondb.net/sprites/%s/normal/%s.%s";
    private static final String BASE_BACK_URL = "https://img.pokemondb.net/sprites/%s/back-normal/%s.%s";
    private static final String BASE_SHINY_URL = "https://img.pokemondb.net/sprites/%s/shiny/%s.%s";
    private static final String BASE_SHINY_BACK_URL = "https://img.pokemondb.net/sprites/%s/back-shiny/%s.%s";

    private static final String PNG = "png";
    private static final String GIF = "gif";

    public static List<String> getMiniSprite(Pokemon pokemon) {
        return determineURLs(pokemon);
    }

    private static List<String> determineURLs(Pokemon pokemon) {
        if (PokemonUtils.isGigantamaxForm(pokemon.getIndexNumber())) {
            return Arrays.asList(String.format(MINI_SPRITE_GIGANTAMAX_BASE_URL, "sword-shield", pokemon.getNormalizedName(Language.EN), PNG));
        }
        if (PokemonUtils.isAlolanForm(pokemon.getIndexNumber())) {
            return Arrays.asList(
                    String.format(MINI_SPRITE_ALOLA_BASE_URL, "sword-shield", pokemon.getNormalizedName(Language.EN), PNG),
                    String.format(MINI_SPRITE_ALOLA_BASE_URL, "lets-go-pikachu-eevee", pokemon.getNormalizedName(Language.EN), PNG)
            );
        }
        if (PokemonUtils.isGalarianForm(pokemon.getIndexNumber())) {
            return Arrays.asList(String.format(MINI_SPRITE_GALAR_BASE_URL, "sword-shield", pokemon.getNormalizedName(Language.EN), PNG));
        }
        if (PokemonUtils.isHisuianForm(pokemon.getIndexNumber())) {
            return Arrays.asList(String.format(MINI_SPRITE_HISUI_BASE_URL, "scarlet-violet", pokemon.getNormalizedName(Language.EN), PNG));
        }
        if (PokemonUtils.isPaldeanForm(pokemon.getIndexNumber()) || pokemon.getRealID() >= 899) {
            return Arrays.asList(String.format(MINI_SPRITE_PALDEA_BASE_URL, "scarlet-violet", pokemon.getNormalizedName(Language.EN), PNG));
        }
        return Arrays.asList(String.format(MINI_SPRITE_BASE_URL, "sword-shield", pokemon.getNormalizedName(Language.EN), PNG));
    }

    public static String getNormalSprite(Pokemon pokemon, GameVersion gameVersion) {
        return String.format(NORMAL_SPRITE_BASE_URL, getImageGameCodeForGame(gameVersion), pokemon.getNormalizedName(Language.EN).concat(isFirstGenGame(gameVersion) ? "-color" : ""), isBlackOrWhite2(gameVersion) ? GIF : PNG);
    }

    public static String getNormalSpriteBack(Pokemon pokemon, GameVersion gameVersion) {
        return String.format(BASE_BACK_URL, getImageGameCodeForGame(gameVersion), pokemon.getNormalizedName(Language.EN).concat(isFirstGenGame(gameVersion) ? "-color" : ""), isBlackOrWhite2(gameVersion) ? GIF : PNG);
    }

    public static String getShinySprite(Pokemon pokemon, GameVersion gameVersion) {
        return String.format(BASE_SHINY_URL, getImageGameCodeForGame(gameVersion), pokemon.getNormalizedName(Language.EN), isBlackOrWhite2(gameVersion) ? GIF : PNG);
    }

    public static String getShinySpriteBack(Pokemon pokemon, GameVersion gameVersion) {
        return String.format(BASE_SHINY_BACK_URL, getImageGameCodeForGame(gameVersion), pokemon.getNormalizedName(Language.EN), isBlackOrWhite2(gameVersion) ? GIF : PNG);
    }

    private static boolean isBlackOrWhite2(GameVersion gameVersion) {
        return GameVersion.WHITE_2.equals(gameVersion) || GameVersion.BLACK_2.equals(gameVersion);
    }

    public static String getImageGameCodeForGame(GameVersion game) {
        return imageGameCodeForGameGeneration.getOrDefault(game, "x-y");
    }

    private static boolean isFirstGenGame(GameVersion gameVersion) {
        return GameVersion.RED.equals(gameVersion) || GameVersion.BLUE.equals(gameVersion) || GameVersion.YELLOW.equals(gameVersion);
    }

}
