package utils;

import enums.GameVersion;
import enums.Language;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.similarity.FuzzyScore;
import pojos.GenericIndex;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class PokemonUtils {

    public static final String POKEMON_ID_DELIMITER = "¬";

    public static final int GEN_1 = 1;
    public static final int GEN_2 = 2;
    public static final int GEN_3 = 3;
    public static final int GEN_4 = 4;
    public static final int GEN_5 = 5;
    public static final int GEN_6 = 6;
    public static final int GEN_7 = 7;
    public static final int GEN_8 = 8;
    public static final int GEN_9 = 9;
    public static final int LASTEST_GENERATION = GEN_9;

    public static final int GENERATION_1_START = 1;
    public static final int GENERATION_1_END = 151;
    public static final int GENERATION_2_START = 152;
    public static final int GENERATION_2_END = 251;
    public static final int GENERATION_3_START = 252;
    public static final int GENERATION_3_END = 386;
    public static final int GENERATION_4_START = 387;
    public static final int GENERATION_4_END = 493;
    public static final int GENERATION_5_START = 494;
    public static final int GENERATION_5_END = 649;
    public static final int GENERATION_6_START = 650;
    public static final int GENERATION_6_END = 721;
    public static final int GENERATION_7_START = 722;
    public static final int GENERATION_7_END = 809;
    public static final int GENERATION_8_START = 810;
    public static final int GENERATION_8_END = 905;
    public static final int GENERATION_9_START = 906;
    public static final int GENERATION_9_END = 1010;

    private static final Map<Integer, Pair<Integer, Integer>> rangeOfPokemonForGenerations = Map.ofEntries(
            entry(GEN_1, new ImmutablePair<>(GENERATION_1_START, GENERATION_1_END)),
            entry(GEN_2, new ImmutablePair<>(GENERATION_2_START, GENERATION_2_END)),
            entry(GEN_3, new ImmutablePair<>(GENERATION_3_START, GENERATION_3_END)),
            entry(GEN_4, new ImmutablePair<>(GENERATION_4_START, GENERATION_4_END)),
            entry(GEN_5, new ImmutablePair<>(GENERATION_5_START, GENERATION_5_END)),
            entry(GEN_6, new ImmutablePair<>(GENERATION_6_START, GENERATION_6_END)),
            entry(GEN_7, new ImmutablePair<>(GENERATION_7_START, GENERATION_7_END)),
            entry(GEN_8, new ImmutablePair<>(GENERATION_8_START, GENERATION_8_END)),
            entry(GEN_9, new ImmutablePair<>(GENERATION_9_START, GENERATION_9_END))
    );

    public static final String ALOLA_FORMS_INTERFIX = "alola";
    public static final String GALAR_FORMS_INTERFIX = "galar";
    public static final String HISUI_FORMS_INTERFIX = "hisui";
    public static final String PALDEA_FORMS_INTERFIX = "paldea";

    public static final String MEGA_EVOLUTIONS_INTERFIX = "mega";
    public static final String GIGANTAMAX_INTERFIX = "gigantamax";
    public static final String ALTERNATE_FORMS_INTERFIX = "alternate";
    public static final String VARIANT_FORMS_INTERFIX = "variant";
    public static final String NORMAL_FORMS_INTERFIX = "normal";

    public static boolean isPresentInGame(String pokeId, GameVersion gameVersion) {

        boolean isValid = true;

        String[] info = pokeId.split(POKEMON_ID_DELIMITER);
        String realId = info[0];
        String type = info[1];
        String version = info[2];

        if (isAlolanForm(type)) {
            isValid = gamesWithAlolanForms.contains(gameVersion);
        } else if (isGalarianForm(type)) {
            isValid = gamesWithGalarianForms.contains(gameVersion);
        } else if (isHisuianForm(type)) {
            isValid = gamesWithHisuianForms.contains(gameVersion);
        } else if (isPaldeanForm(pokeId)) {
            isValid = gamesWithPaldeanForms.contains(gameVersion);
        } else if (isMegaEvolution(type)) {
            isValid = gamesWithMegaEvolutions.contains(gameVersion);
        } else if (isGigantamaxForm(type)) {
            isValid = gamesWithGigantamax.contains(gameVersion);
        }


        return isValid;

    }

    public static boolean isPokemonInRangeForGeneration(Integer realId, Integer generation) {
        Pair<Integer, Integer> rangeOfPokemonForGeneration = rangeOfPokemonForGenerations.get(generation);
        return rangeOfPokemonForGeneration.getLeft() >= realId && realId <= rangeOfPokemonForGeneration.getRight();
    }

    public static final List<GameVersion> gamesWithoutAbilities = List.of(GameVersion.RED, GameVersion.BLUE, GameVersion.YELLOW, GameVersion.GOLD, GameVersion.SILVER, GameVersion.CRYSTAL, GameVersion.LETS_GO_EEVEE, GameVersion.LETS_GO_PIKACHU);

    public static final List<GameVersion> gamesWithoutNatures = List.of(GameVersion.RED, GameVersion.BLUE, GameVersion.YELLOW, GameVersion.GOLD, GameVersion.SILVER, GameVersion.CRYSTAL);

    /**
     * Prior to gen 3, EVs where NOT limited to 510 for the sum of all stats, therefore in Gen 1 and 2 you could
     * fill every stat without EV limitation. Fore more info:
     * <a>https://bulbapedia.bulbagarden.net/wiki/Effort_values</a>
     **/
    public static final List<GameVersion> gamesWithoutLimitedEVSpread = List.of(GameVersion.RED, GameVersion.BLUE, GameVersion.YELLOW, GameVersion.GOLD, GameVersion.SILVER, GameVersion.CRYSTAL, GameVersion.LETS_GO_EEVEE, GameVersion.LETS_GO_PIKACHU);

    /**
     * Prior to gen 3, IVs where limited to 31 for every stat.
     * For more info:
     * <a>https://bulbapedia.bulbagarden.net/wiki/Individual_values</a>
     **/
    public static final List<GameVersion> gamesWithout31IVSpread = List.of(GameVersion.RED, GameVersion.BLUE, GameVersion.YELLOW, GameVersion.GOLD, GameVersion.SILVER, GameVersion.CRYSTAL);

    public static final List<GameVersion> gamesWithoutSpecialStatSplit = List.of(GameVersion.RED, GameVersion.BLUE, GameVersion.YELLOW);

    public static final List<GameVersion> gamesWithoutFriendship = List.of(GameVersion.RED, GameVersion.BLUE, GameVersion.YELLOW);

    public static final List<GameVersion> gamesWithoutGenders = List.of(GameVersion.RED, GameVersion.BLUE, GameVersion.YELLOW);

    public static final List<GameVersion> gamesWithoutHiddenPower = List.of(GameVersion.RED, GameVersion.BLUE, GameVersion.YELLOW);

    public static final List<GameVersion> gamesWithoutItems = List.of(GameVersion.RED, GameVersion.BLUE, GameVersion.YELLOW);

    public static final List<GameVersion> gamesWithMegaEvolutions = List.of(GameVersion.X, GameVersion.Y, GameVersion.OMEGA_RUBY, GameVersion.ALPHA_SAPPHIRE, GameVersion.SUN, GameVersion.MOON, GameVersion.ULTRA_SUN, GameVersion.ULTRA_MOON, GameVersion.LETS_GO_PIKACHU, GameVersion.LETS_GO_EEVEE);

    public static final List<GameVersion> gamesWithGigantamax = List.of(GameVersion.SWORD, GameVersion.SHIELD, GameVersion.EXPANSION_PASS, GameVersion.ISLE_OF_ARMOR, GameVersion.CROWN_OF_TUNDRA);

    public static final List<GameVersion> gamesWithRegionalForms = List.of(GameVersion.SUN, GameVersion.MOON, GameVersion.ULTRA_SUN, GameVersion.ULTRA_MOON, GameVersion.LETS_GO_PIKACHU, GameVersion.LETS_GO_EEVEE, GameVersion.SWORD, GameVersion.SHIELD, GameVersion.EXPANSION_PASS, GameVersion.ISLE_OF_ARMOR, GameVersion.CROWN_OF_TUNDRA);

    public static final List<GameVersion> gamesWithAlolanForms = List.of(GameVersion.SUN, GameVersion.MOON, GameVersion.ULTRA_SUN, GameVersion.ULTRA_MOON, GameVersion.LETS_GO_PIKACHU, GameVersion.LETS_GO_EEVEE, GameVersion.SWORD, GameVersion.SHIELD, GameVersion.EXPANSION_PASS, GameVersion.ISLE_OF_ARMOR, GameVersion.CROWN_OF_TUNDRA);

    public static final List<GameVersion> gamesWithGalarianForms = List.of(GameVersion.SWORD, GameVersion.SHIELD, GameVersion.EXPANSION_PASS, GameVersion.ISLE_OF_ARMOR, GameVersion.CROWN_OF_TUNDRA);

    public static final List<GameVersion> gamesWithHisuianForms = List.of(GameVersion.LEGENDS_ARCEUS);

    public static final List<GameVersion> gamesWithPaldeanForms = List.of(GameVersion.SCARLET, GameVersion.VIOLET);

    public static final List<GameVersion> dlcs = List.of(GameVersion.PAL_PARK, GameVersion.POKEWALKER, GameVersion.DREAM_WORLD, GameVersion.EXPANSION_PASS, GameVersion.ISLE_OF_ARMOR, GameVersion.CROWN_OF_TUNDRA);

    public static final List<Integer> generationsWithAbilities = List.of(GEN_3, GEN_4, GEN_5, GEN_6, GEN_7, GEN_8, GEN_9);

    public static boolean isAlternateForm(String poke_id) {
        return poke_id.contains(ALTERNATE_FORMS_INTERFIX);
    }

    public static boolean isRegionalForm(String poke_id) {
        return isAlolanForm(poke_id) || isGalarianForm(poke_id) || isHisuianForm(poke_id) || isPaldeanForm(poke_id);
    }

    public static boolean isAlolanForm(String poke_id) {
        return poke_id.contains(ALOLA_FORMS_INTERFIX);
    }

    public static boolean isGalarianForm(String poke_id) {
        return poke_id.contains(GALAR_FORMS_INTERFIX);
    }

    public static boolean isHisuianForm(String poke_id) {
        return poke_id.contains(HISUI_FORMS_INTERFIX);
    }

    public static boolean isPaldeanForm(String poke_id) {
        return poke_id.contains(PALDEA_FORMS_INTERFIX);
    }

    public static boolean isMegaEvolution(String poke_id) {
        return poke_id.contains(MEGA_EVOLUTIONS_INTERFIX);
    }

    public static boolean isGigantamaxForm(String poke_id) {
        return poke_id.contains(GIGANTAMAX_INTERFIX);
    }

    public static boolean isVariantForm(String poke_id) {
        return poke_id.contains(VARIANT_FORMS_INTERFIX);
    }


    public static boolean isNormalForm(String poke_id) {
        return poke_id.contains(NORMAL_FORMS_INTERFIX);
    }

    public static final Map<Integer, GameVersion[]> gamesForGeneration = Map.ofEntries(
            entry(GEN_1, new GameVersion[]{GameVersion.RED_BLUE_YELLOW}),
            entry(GEN_2, new GameVersion[]{GameVersion.GOLD_SILVER_CRYSTAL}),
            entry(GEN_3, new GameVersion[]{GameVersion.FIRERED_LEAFGREEN, GameVersion.RUBY_SAPPHIRE_EMERALD}),
            entry(GEN_4, new GameVersion[]{GameVersion.DIAMOND_PEARL, GameVersion.PLATINUM, GameVersion.HEARTGOLD_SOULSILVER}),
            entry(GEN_5, new GameVersion[]{GameVersion.BLACK_WHITE, GameVersion.BLACK_2_WHITE_2}),
            entry(GEN_6, new GameVersion[]{GameVersion.X_Y, GameVersion.OMEGA_RUBY_ALPHA_SAPPHIRE}),
            entry(GEN_7, new GameVersion[]{GameVersion.SUN_MOON, GameVersion.ULTRA_SUN_ULTRA_MOON, GameVersion.LETS_GO_PIKACHU_LETS_GO_EEVEE}),
            entry(GEN_8, new GameVersion[]{GameVersion.SWORD_SHIELD, GameVersion.BRILLIANT_DIAMOND_SHINING_PEARL, GameVersion.LEGENDS_ARCEUS}),
            entry(GEN_9, new GameVersion[]{GameVersion.SCARLET_VIOLET})
    );


    public static Integer getGenerationForGame(GameVersion gameVersion) {
        Integer generation = null;
        for (Map.Entry<Integer, GameVersion[]> entry : gamesForGeneration.entrySet()) {
            if (Arrays.asList(entry.getValue()).contains(gameVersion)) {
                generation = entry.getKey();
                break;
            }
        }
        return generation;
    }

    public static Integer getGenerationForPokemon(String pokemonID) {
        String[] parts = pokemonID.split(POKEMON_ID_DELIMITER);
        if (isMegaEvolution(pokemonID)) {
            return GEN_6;
        }
        if (isAlolanForm(pokemonID)) {
            return GEN_7;
        }
        if (isGalarianForm(pokemonID) || isHisuianForm(pokemonID) || isGigantamaxForm(pokemonID)) {
            return GEN_8;
        }
        if (isPaldeanForm(pokemonID)) {
            return GEN_9;
        }
        int realID = Integer.parseInt(parts[0]);
        for (Map.Entry<Integer, Pair<Integer, Integer>> entry : rangeOfPokemonForGenerations.entrySet()) {
            Pair<Integer, Integer> startEndPair = entry.getValue();
            if (realID >= startEndPair.getLeft() && realID <= startEndPair.getRight()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static List<Integer> getGenerations() {
        return List.of(GEN_1, GEN_2, GEN_3, GEN_4, GEN_5, GEN_6, GEN_7, GEN_8, GEN_9);
    }


    public static <T extends GenericIndex> T calculatePossibleObjectByName(String name, List<? extends GenericIndex> dataset, Language language) {
        int higher = name.length() / 2; //minimum length to prevent random stuff
        GenericIndex mostMatchingElement = null;
        FuzzyScore fs = new FuzzyScore(Locale.ROOT);
        for (GenericIndex genericObject : dataset) {
            String normalizedSourceName = Sanitizer.sanitizeDefault(name.toLowerCase()).replace("_", " ");
            Set<String> allPossibleCombinations = getPossibleCombinations(normalizedSourceName);
            for (String possibleCombination : allPossibleCombinations) {
                String normalizedTargetName = Sanitizer.sanitizeDefault(genericObject.getLocalizedName(language)).replace("_", " ");
                //same length matches should be more boosted
                if (normalizedTargetName.split("\\s+").length == possibleCombination.split("\\s+").length) {
                    if (possibleCombination.equals(normalizedTargetName)) {
                        higher = Integer.MAX_VALUE;
                        mostMatchingElement = genericObject;
                    } else {
                        int fuzzyScore = fs.fuzzyScore(normalizedTargetName, possibleCombination) + name.length();
                        if (fuzzyScore > higher) {
                            higher = fuzzyScore;
                            mostMatchingElement = genericObject;
                        }
                    }
                } else {
                    int fuzzyScore = fs.fuzzyScore(normalizedTargetName, possibleCombination);
                    if (fuzzyScore > higher) {
                        higher = fuzzyScore;
                        mostMatchingElement = genericObject;
                    }
                }
            }
        }
        return (T) mostMatchingElement;
    }

    private static Set<String> getPossibleCombinations(String name) {
        String[] nameParts = name.split("\\s+");
        Set<String> sanitizedCombinations = new HashSet<>();
        List<String> variations = Arrays.stream(nameParts).collect(Collectors.toList());

        Set<String> allPossibleRawCombinations = variations.stream()
                .flatMap(s1 -> variations.stream()
                        .flatMap(s2 -> variations.stream()
                                .flatMap(s3 -> variations.stream()
                                        .flatMap(s4 -> variations.stream()
                                                .flatMap(s5 -> variations.stream() //we support max 5 levels of depth
                                                        .map(s6 -> concatCombinations(nameParts.length, s1, s2, s3, s4, s5, s6)) //we support max 6 levels of depth
                                                )))))
                .collect(Collectors.toSet());

        for (String allPossibleRawCombination : allPossibleRawCombinations) {
            String[] parts = allPossibleRawCombination.split("\\s+");
            //Set<String> set = new LinkedHashSet<>(Arrays.asList(parts));
            if (parts.length == nameParts.length) {
                sanitizedCombinations.add(String.join(" ", parts));
            }
        }
        return sanitizedCombinations;
    }

    private static String concatCombinations(int length, String... parts) {
        StringJoiner joiner = new StringJoiner(StringUtils.SPACE);
        for (int i = 0; i < length; i++) {
            joiner.add(parts[i]);
        }
        return joiner.toString();
    }

    private static boolean pokemonExists(String pokemonName) {
        return StringUtils.isNotEmpty(PokemonIndexUtils.getIndexNumberByPokemonName(pokemonName));
    }

}
