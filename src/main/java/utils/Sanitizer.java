package utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Objects;

public class Sanitizer {

    private static final String UNDERSCORE = "_";
    private static final String DOTS = ".";
    private static final String SINGLE_QUOTE = "'";
    private static final String DASH = "-";
    private static final String EMPTY = StringUtils.EMPTY;
    private static final String SPACE = StringUtils.SPACE;
    private static final String OPEN_PARENTHESIS = "(";
    private static final String CLOSE_PARENTHESIS = ")";


    private static final String WHITE_SPACE_REGEX = "\\s+";

    private Sanitizer() {
        //private constructor to avoid instantiation of utility class
    }

    public static String sanitize(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        return string
                .replaceAll(WHITE_SPACE_REGEX, EMPTY)
                .toLowerCase();
    }

    public static String sanitizeMoveForIndex(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        return string
                .replace(DASH, EMPTY)
                .replaceAll(WHITE_SPACE_REGEX, EMPTY)
                .toLowerCase();
    }

    public static String sanitizeEvStat(String evStat) {
        return evStat
                .replace("HP", "hp")
                .replace("Special Attack", "spatk")
                .replace("Special Defense", "spdef")
                .replace("Attack", "atk")
                .replace("Defense", "def")
                .replace("Speed", "speed")
                .replaceAll(WHITE_SPACE_REGEX, EMPTY);
    }

    public static String sanitizeGame(String game) {
        return game
                .replace("/", UNDERSCORE)
                .replace("U.", "ultra_")
                .replace("'", EMPTY)
                .replace("The ", EMPTY)
                .replace(" — ", " ")
                .replace(" Kalos", EMPTY)
                .replace(" Alola dex", EMPTY)
                .replace(" ", UNDERSCORE)
                .trim()
                .toLowerCase();
    }

    public static String sanitizeGenders(String genders) {
        return genders
                .replace("% male", EMPTY)
                .replace("% female", EMPTY);
    }

    public static String sanitizeMoveCategory(String moveCategory) {
        return moveCategory.replaceAll("\\s+", "").toLowerCase();
    }

    public static String sanitizeText(String text) {
        return StringUtils.isEmpty(text) ?
                null :
                text.replace("\"", "'")
                        .replace("(ver más abajo)", "")
                        .trim();
    }

    public static String generifyExplanation(String text) {
        return StringUtils.isEmpty(text) || text.contains("Success") || text.contains("Always") ?
                null :
                text.replace("\"", "'")
                        .replace("-type", "")
                        .replace("move", "")
                        .replace("in", "")
                        .replace("   ", " ")
                        .replace("Generation", "(Gen")
                        .replace("Generation", "(Gen")
                        .concat(")");
    }

    public static String sanitizeAbility(String ability) {
        String capitalizedAbility = WordUtils.capitalizeFully(ability);
        return capitalizedAbility
                .replace("*", "").trim();
    }

    public static String sanitizeMove(String move) {
        return StringUtils.isEmpty(move) ? null :
                GenericUtils.removeParenthesisFromString(WordUtils.capitalizeFully(move)
                        .replace(" (move)", "")
                        .replace("*", "")
                        .trim());
    }

    /**
     * Fix for farfetch'd , nidoran♀♂ , flabébé
     *
     * @param pokemonName
     * @return
     */
    public static String sanitizePokemonName(String pokemonName) {
        return StringUtils.stripAccents(pokemonName
                .replace("%27", EMPTY)
                .replace("%", EMPTY)
                .replace("%E2%99%82", "♂")
                .replace("%E2%99%80", "♀")
                .replace("Paldean", "(Paldean)")
                .replace("Hisuian", "(Hisuian)")
                .replace("Galarian", "(Galarian)")
                .replace("Alolan", "(Alolan)")
                .replace(" Cloak", EMPTY)
                .replace(" Forme", EMPTY)
                .replace(" Form", EMPTY)
                .replace(" Mode", EMPTY)
                .replace(" Size", EMPTY)
                .replace(" Style", EMPTY)
                .replace(" Noice", EMPTY)
                .replace(" Ash-", " Ash")
                .replace(" of Many Battles", EMPTY)
                .replace(" Crowned Sword", " Crowned")
                .replace(" Crowned Shield", " Crowned")
                .replace("MegaX", "Mega (X)")
                .replace("MegaY", "Mega (Y)")
                .replace("\n", SPACE)
                .replaceAll(WHITE_SPACE_REGEX, SPACE)
                .trim());
    }

    public static String sanitizeAccuracy(String accuracy) {
        return StringUtils.isEmpty(accuracy) || accuracy.contains("—") ?
                null :
                accuracy.replace("%", "");
    }

    public static String sanitizePower(String power) {
        return StringUtils.isEmpty(power) || power.contains("—") ?
                null :
                power.replace("*", "").replaceAll("\\s+", "");
    }

    public static String sanitizeSpecies(String species) {
        return species.replace("Pokémon ", "");
    }

    public static String sanitizeItem(String item) {
        return StringUtils.stripAccents(item
                .replaceAll("\\*", "")
                .replaceAll("'", "")
                .replaceAll("-", " ")
                .replaceAll("\\.", " ")
                .replaceAll("\\s+", "_")
                .toLowerCase());
    }

    public static String sanitizeDefault(String string) {
        return Objects.isNull(string) ? null :
                StringUtils.stripAccents(string
                        .replace(DASH, SPACE)
                        .replace(DOTS, SPACE)
                        .replace(SINGLE_QUOTE, EMPTY)
                        .replace(OPEN_PARENTHESIS, SPACE)
                        .replace(CLOSE_PARENTHESIS, SPACE)
                        .trim()
                        .replaceAll(WHITE_SPACE_REGEX, UNDERSCORE)
                        .toLowerCase());
    }

}
