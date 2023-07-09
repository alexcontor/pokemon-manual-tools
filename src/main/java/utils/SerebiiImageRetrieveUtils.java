package utils;

import enums.GameVersion;
import enums.Language;
import org.testng.internal.collections.Pair;
import pojos.Pokemon;

import java.util.*;

public class SerebiiImageRetrieveUtils {

    private static final List<GameVersion> bridgedGames = Arrays.asList(
            GameVersion.RUBY,
            GameVersion.SAPPHIRE,
            GameVersion.EMERALD,
            GameVersion.FIRERED,
            GameVersion.LEAFGREEN,
            GameVersion.BLACK_2,
            GameVersion.WHITE_2,
            GameVersion.SWORD,
            GameVersion.LEGENDS_ARCEUS);

    private static final Map<GameVersion, Pair<String, String>> imageGameCodeForGameGeneration = new HashMap<GameVersion, Pair<String, String>>() {
        {
            put(GameVersion.RED, new Pair<>(SEREBII_BASE_URL + "pokearth/sprites/rb", PNG));
            put(GameVersion.BLUE, new Pair<>(SEREBII_BASE_URL + "pokearth/sprites/rb", PNG));
            put(GameVersion.YELLOW, new Pair<>(SEREBII_BASE_URL + "pokearth/sprites/yellow", PNG));
            put(GameVersion.GOLD, new Pair<>(SEREBII_BASE_URL + "pokearth/sprites/gold", PNG));
            put(GameVersion.SILVER, new Pair<>(SEREBII_BASE_URL + "pokearth/sprites/silver", PNG));
            put(GameVersion.CRYSTAL, new Pair<>(SEREBII_BASE_URL + "pokearth/sprites/crystal", PNG));
            put(GameVersion.RUBY, new Pair<>(SEREBII_BASE_URL + "ruby-sapphire", GIF));
            put(GameVersion.SAPPHIRE, new Pair<>(SEREBII_BASE_URL + "pokemon_advance", GIF));
            put(GameVersion.EMERALD, new Pair<>(SEREBII_BASE_URL + "emerald", PNG));
            put(GameVersion.LEAFGREEN, new Pair<>(SEREBII_BASE_URL + "red_green", GIF));
            put(GameVersion.FIRERED, new Pair<>(SEREBII_BASE_URL + "red_green", GIF));
            put(GameVersion.DIAMOND, new Pair<>(SEREBII_BASE_URL + "pokearth/sprites/dp", PNG));
            put(GameVersion.PEARL, new Pair<>(SEREBII_BASE_URL + "pokearth/sprites/dp", PNG));
            put(GameVersion.PLATINUM, new Pair<>(SEREBII_BASE_URL + "pokearth/sprites/dp", PNG));
            put(GameVersion.HEARTGOLD, new Pair<>(SEREBII_BASE_URL + "pokearth/sprites/hgss", PNG));
            put(GameVersion.SOULSILVER, new Pair<>(SEREBII_BASE_URL + "pokearth/sprites/hgss", PNG));
            put(GameVersion.WHITE, new Pair<>(SEREBII_BASE_URL + "blackwhite/pokemon", PNG));
            put(GameVersion.BLACK, new Pair<>(SEREBII_BASE_URL + "blackwhite/pokemon", PNG));
            put(GameVersion.WHITE_2, new Pair<>(SEREBII_BASE_URL + "blackwhite/pokemon", PNG));
            put(GameVersion.BLACK_2, new Pair<>(SEREBII_BASE_URL + "blackwhite/pokemon", PNG));
            put(GameVersion.X, new Pair<>(SEREBII_BASE_URL + "xy/pokemon", PNG));
            put(GameVersion.Y, new Pair<>(SEREBII_BASE_URL + "xy/pokemon", PNG));
            put(GameVersion.OMEGA_RUBY, new Pair<>(SEREBII_BASE_URL + "xy/pokemon", PNG));
            put(GameVersion.ALPHA_SAPPHIRE, new Pair<>(SEREBII_BASE_URL + "xy/pokemon", PNG));
            put(GameVersion.SUN, new Pair<>(SEREBII_BASE_URL + "sunmoon/pokemon", PNG));
            put(GameVersion.MOON, new Pair<>(SEREBII_BASE_URL + "sunmoon/pokemon", PNG));
            put(GameVersion.ULTRA_SUN, new Pair<>(SEREBII_BASE_URL + "sunmoon/pokemon", PNG));
            put(GameVersion.ULTRA_MOON, new Pair<>(SEREBII_BASE_URL + "sunmoon/pokemon", PNG));
            put(GameVersion.LETS_GO_PIKACHU, new Pair<>(SEREBII_BASE_URL + "swordshield/pokemon", PNG));
            put(GameVersion.LETS_GO_EEVEE, new Pair<>(SEREBII_BASE_URL + "swordshield/pokemon", PNG));
            put(GameVersion.SWORD, new Pair<>(SEREBII_BASE_URL + "swordshield/pokemon", PNG));
            put(GameVersion.SHIELD, new Pair<>(SEREBII_BASE_URL + "swordshield/pokemon", PNG));
            put(GameVersion.BRILLIANT_DIAMOND, new Pair<>(SEREBII_BASE_URL + "swordshield/pokemon", PNG));
            put(GameVersion.SHINING_PEARL, new Pair<>(SEREBII_BASE_URL + "swordshield/pokemon", PNG));
            put(GameVersion.LEGENDS_ARCEUS, new Pair<>(SEREBII_BASE_URL + "swordshield/pokemon", PNG));
            put(GameVersion.SCARLET, new Pair<>(SEREBII_BASE_URL + "scarletviolet/pokemon", PNG));
            put(GameVersion.VIOLET, new Pair<>(SEREBII_BASE_URL + "scarletviolet/pokemon", PNG));
        }
    };

    private static final Map<GameVersion, Pair<String, String>> shinyImageGameCodeForGameGeneration = new HashMap<GameVersion, Pair<String, String>>() {
        {
            put(GameVersion.GOLD, new Pair<>(SEREBII_BASE_URL + SHINY + "Gold", PNG));
            put(GameVersion.SILVER, new Pair<>(SEREBII_BASE_URL + SHINY + "Silver", PNG));
            put(GameVersion.CRYSTAL, new Pair<>(SEREBII_BASE_URL + SHINY + "Crystal", PNG));
            put(GameVersion.RUBY, new Pair<>(SEREBII_BASE_URL + SHINY + "RuSa", GIF));
            put(GameVersion.SAPPHIRE, new Pair<>(SEREBII_BASE_URL + SHINY + "RuSa", GIF));
            put(GameVersion.EMERALD, new Pair<>(SEREBII_BASE_URL + SHINY + "Em", PNG));
            put(GameVersion.LEAFGREEN, new Pair<>(SEREBII_BASE_URL + SHINY + "FRLG", PNG));
            put(GameVersion.FIRERED, new Pair<>(SEREBII_BASE_URL + SHINY + "FRLG", PNG));
            put(GameVersion.DIAMOND, new Pair<>(SEREBII_BASE_URL + SHINY + "DP", PNG));
            put(GameVersion.PEARL, new Pair<>(SEREBII_BASE_URL + SHINY + "DP", PNG));
            put(GameVersion.PLATINUM, new Pair<>(SEREBII_BASE_URL + SHINY + "DP", PNG));
            put(GameVersion.HEARTGOLD, new Pair<>(SEREBII_BASE_URL + SHINY + "HGSS", PNG));
            put(GameVersion.SOULSILVER, new Pair<>(SEREBII_BASE_URL + SHINY + "HGSS", PNG));
            put(GameVersion.WHITE, new Pair<>(SEREBII_BASE_URL + SHINY + "BW", PNG));
            put(GameVersion.BLACK, new Pair<>(SEREBII_BASE_URL + SHINY + "BW", PNG));
            put(GameVersion.WHITE_2, new Pair<>(SEREBII_BASE_URL + SHINY + "BW", PNG));
            put(GameVersion.BLACK_2, new Pair<>(SEREBII_BASE_URL + SHINY + "BW", PNG));
            put(GameVersion.X, new Pair<>(SEREBII_BASE_URL + SHINY + "XY", PNG));
            put(GameVersion.Y, new Pair<>(SEREBII_BASE_URL + SHINY + "XY", PNG));
            put(GameVersion.OMEGA_RUBY, new Pair<>(SEREBII_BASE_URL + SHINY + "XY", PNG));
            put(GameVersion.ALPHA_SAPPHIRE, new Pair<>(SEREBII_BASE_URL + SHINY + "XY", PNG));
            put(GameVersion.SUN, new Pair<>(SEREBII_BASE_URL + SHINY + "SM", PNG));
            put(GameVersion.MOON, new Pair<>(SEREBII_BASE_URL + SHINY + "SM", PNG));
            put(GameVersion.ULTRA_SUN, new Pair<>(SEREBII_BASE_URL + SHINY + "SM", PNG));
            put(GameVersion.ULTRA_MOON, new Pair<>(SEREBII_BASE_URL + SHINY + "SM", PNG));
            put(GameVersion.LETS_GO_PIKACHU, new Pair<>(SEREBII_BASE_URL + SHINY + "SWSH", PNG));
            put(GameVersion.LETS_GO_EEVEE, new Pair<>(SEREBII_BASE_URL + SHINY + "SWSH", PNG));
            put(GameVersion.SWORD, new Pair<>(SEREBII_BASE_URL + SHINY + "SWSH", PNG));
            put(GameVersion.SHIELD, new Pair<>(SEREBII_BASE_URL + SHINY + "SWSH", PNG));
            put(GameVersion.BRILLIANT_DIAMOND, new Pair<>(SEREBII_BASE_URL + SHINY + "SWSH", PNG));
            put(GameVersion.SHINING_PEARL, new Pair<>(SEREBII_BASE_URL + SHINY + "SWSH", PNG));
            put(GameVersion.LEGENDS_ARCEUS, new Pair<>(SEREBII_BASE_URL + SHINY + "SWSH", PNG));
            put(GameVersion.SCARLET, new Pair<>(SEREBII_BASE_URL + "SV", PNG));
            put(GameVersion.VIOLET, new Pair<>(SEREBII_BASE_URL + "SV", PNG));
        }
    };

    private static final String SEREBII_BASE_URL = "https://www.serebii.net/";
    private static final String SHINY = "Shiny/";
    private static final String NORMAL_SPRITE_BASE_URL = "%s/%s.%s";
    private static final String SHINY_SPRITE_BASE_URL = "%s/%s.%s";
    private static final String MINI_SPRITE_BASE_URL = SEREBII_BASE_URL + "pokedex-swsh/icon/%s.%s";


    private static final String PNG = "png";
    private static final String GIF = "gif";


    public static String getNormalSprite(Pokemon pokemon, GameVersion gameVersion) {
        if (bridgedGames.contains(gameVersion) || !PokemonUtils.isNormalForm(pokemon.getIndexNumber())) {
            return PokemonDBImageRetrieveUtils.getNormalSprite(pokemon, gameVersion);
        }
        Pair<String, String> imageCodeAndExtension = getImageGameCodeAndExtensionForGame(gameVersion);
        if (Objects.isNull(imageCodeAndExtension)) {
            return null;
        }
        return String.format(NORMAL_SPRITE_BASE_URL, imageCodeAndExtension.first(), getPokemonFormattedRealId(pokemon), imageCodeAndExtension.second());
    }

    public static String getShinySprite(Pokemon pokemon, GameVersion gameVersion) {
        if (bridgedGames.contains(gameVersion) || !PokemonUtils.isNormalForm(pokemon.getIndexNumber())) {
            return PokemonDBImageRetrieveUtils.getShinySprite(pokemon, gameVersion);
        }
        Pair<String, String> imageCodeAndExtension = getShinyImageGameCodeAndExtensionForGame(gameVersion);
        if (Objects.isNull(imageCodeAndExtension)) {
            return null;
        }
        return String.format(SHINY_SPRITE_BASE_URL, imageCodeAndExtension.first(), getPokemonFormattedRealId(pokemon), imageCodeAndExtension.second());
    }

    public static Pair<String, String> getImageGameCodeAndExtensionForGame(GameVersion game) {
        return imageGameCodeForGameGeneration.getOrDefault(game, null);
    }

    public static Pair<String, String> getShinyImageGameCodeAndExtensionForGame(GameVersion game) {
        return shinyImageGameCodeForGameGeneration.getOrDefault(game, null);
    }

    private static String getPokemonFormattedRealId(Pokemon pokemon) {
        String formattedId = "";
        int id = pokemon.getRealID();
        if (id < 1000) {
            formattedId = String.format(new Locale("English"), "%03d", id);
        }
        return formattedId + getSuffix(pokemon);
    }

    private static String getSuffix(Pokemon pokemon) {
        String suffix = "";

        if (PokemonUtils.isAlternateForm(pokemon.getIndexNumber())) {
            suffix = "-" + GenericUtils.toAcronym(pokemon.getLocalizedName(Language.EN)).toLowerCase(Locale.ROOT);
        } else if (PokemonUtils.isGigantamaxForm(pokemon.getIndexNumber())) {
            suffix = "-gi";
        } else if (PokemonUtils.isMegaEvolution(pokemon.getIndexNumber())) {
            suffix = "-m";
        } else if (PokemonUtils.isAlolanForm(pokemon.getIndexNumber())) {
            suffix = "-a";
        } else if (PokemonUtils.isGalarianForm(pokemon.getIndexNumber())) {
            suffix = "-g";
        } else if (PokemonUtils.isHisuianForm(pokemon.getIndexNumber())) {
            suffix = "-h";
        }

        return suffix;
    }

}
