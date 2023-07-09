package utils;

import database.Database;
import enums.GameVersion;
import enums.ItemCategory;
import enums.Language;
import org.apache.commons.lang3.StringUtils;
import pojos.GenericIndex;
import pojos.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static utils.PokemonUtils.*;


public class PokemonIndexUtils {

    private static final List<Move> moves = Database.getAllMoves();

    private PokemonIndexUtils() {

    }

    public static final HashMap<ItemCategory, Integer> indexNumberByItemCategory = new HashMap<>() {{
        put(ItemCategory.POKEBALL, 1);
        put(ItemCategory.RECOVERY, 2);
        put(ItemCategory.HOLD, 3);
        put(ItemCategory.EVOLUTIONARY, 4);
        put(ItemCategory.BERRIES, 5);
        put(ItemCategory.BERRIES_GS, 6);
        put(ItemCategory.BATTLE_EFFECT, 7);
        put(ItemCategory.VITAMINS, 8);
        put(ItemCategory.FOSSILS, 9);
        put(ItemCategory.MAIL, 10);
        put(ItemCategory.MISCELLANEOUS, 11);
        put(ItemCategory.KEY, 12);
        put(ItemCategory.EVENT, 13);
        put(ItemCategory.DECORATIONS, 14);
        put(ItemCategory.MOVES, 15);
        put(ItemCategory.MEGA_STONES, 16);
    }};

    public static final HashMap<String, String> indexNumberByEggGroupName = new HashMap<>() {{
        putAll(Database.getAllEggGroups()
                .stream()
                .collect(Collectors.toMap(move -> move.getLocalizedName(Language.EN), GenericIndex::getIndexNumber)));
    }};

    public static final HashMap<String, String> indexNumberByMoveName = new HashMap<>() {{
        putAll(Database.getAllMoves()
                .stream()
                .collect(Collectors.toMap(move -> Sanitizer.sanitizeMoveForIndex(move.getLocalizedName(Language.EN)), GenericIndex::getIndexNumber)));
    }};

    public static final HashMap<String, List<GameVersion>> groupedRelatedGamesByGameIndex = new HashMap<>() {{
        for (GameVersion gameVersion : GameVersion.values()) {
            put(gameVersion.getIndex(), Database.getGameGroupsForGameIndex(gameVersion.getIndex()));
        }
    }};

    public static final HashMap<String, List<GameVersion>> gameIndexesByGameGroup = new HashMap<>() {{
        for (GameVersion gameVersion : GameVersion.values()) {
            put(gameVersion.getIndex(), Database.getGameIndexesForGameGroup(gameVersion.getIndex()));
        }
    }};

    public static final HashMap<String, String> indexNumberByItemName = new HashMap<>() {{
        putAll(Database.getAllItems()
                .stream()
                .collect(Collectors.toMap(item -> item.getLocalizedName(Language.EN), GenericIndex::getIndexNumber)));
    }};

    public static HashMap<String, String> indexNumberByPokemonName = new HashMap<>() {{
        putAll(Database.getAllPokemon(new ArrayList<>())
                .stream()
                .collect(Collectors.toMap(pokemon -> pokemon.getLocalizedName(Language.EN), GenericIndex::getIndexNumber)));
    }};

    public static HashMap<String, String> pokemonNameByIndexNumber = new HashMap<>() {{
        putAll(Database.getAllPokemon(new ArrayList<>())
                .stream()
                .collect(Collectors.toMap(GenericIndex::getIndexNumber, pokemon -> pokemon.getLocalizedName(Language.EN))));
    }};

    public static final HashMap<String, String> indexNumberByAbilityName = new HashMap<>() {{
        putAll(Database.getAllAbilities()
                .stream()
                .collect(Collectors.toMap(ability -> ability.getLocalizedName(Language.EN), GenericIndex::getIndexNumber)));
    }};

    public static final HashMap<String, String> indexNumberByRegionName = new HashMap<>() {{
        putAll(Database.getAllRegions()
                .stream()
                .collect(Collectors.toMap(move -> move.getLocalizedName(Language.EN), GenericIndex::getIndexNumber)));
    }};


    public static String getIndexNumberByMoveName(String name) {
        String indexNumber = null;
        try {
            indexNumber = indexNumberByMoveName.get(Sanitizer.sanitizeMoveForIndex(name));
        } catch (NullPointerException ex) {
            Move move = calculatePossibleObjectByName(name, moves, Language.EN);
            if (Objects.nonNull(move)) {
                indexNumber = move.getIndexNumber();
            } else {
                System.out.println(String.format("Move %s doesn't exist , please add it to list manually", name));
            }
        }
        if (StringUtils.isEmpty(indexNumber)) {
            Move move = calculatePossibleObjectByName(name, moves, Language.EN);
            if (Objects.nonNull(move)) {
                indexNumber = move.getIndexNumber();
            } else {
                System.out.println(String.format("Move %s doesn't exist , please add it to list manually", name));
            }
        }
        return indexNumber;
    }

    public static String getIndexNumberByPokemonName(String name) {
        String indexNumber = null;
        try {
            indexNumber = indexNumberByPokemonName.get(name);
        } catch (NullPointerException ex) {
            System.out.println(String.format("There is no pokemon ID with name %s", name));
        }
        return indexNumber;
    }

    public static String getPokemonNameByIndexNumber(String indexNumber) {
        return pokemonNameByIndexNumber.get(String.valueOf(indexNumber));
    }

    public static String getIndexNumberByAbilityName(String abilityName) {
        String abilityIndex = null;
        if (!StringUtils.isEmpty(abilityName)) {
            try {
                abilityIndex = indexNumberByAbilityName.get(convert(abilityName));
                if (StringUtils.isEmpty(abilityIndex)) {
                    throw new Exception();
                }
            } catch (Exception ex) {
                System.out.println(String.format("Cannot find ability with name %s", abilityName));
            }
        }
        return abilityIndex;
    }

    private static String convert(String abilityName) {
        String sanitizedName = abilityName;
        switch (abilityName) {
            case "Lightningrod":
                sanitizedName = "Lightning Rod";
                break;
        }
        return sanitizedName;
    }

    public static String getIndexNumberByEggGroupName(String eggGroupName) {
        return indexNumberByEggGroupName.get(eggGroupName);
    }

    public static Integer getItemCategoryIndexByType(ItemCategory category) {
        return indexNumberByItemCategory.get(category);
    }

    public static List<GameVersion> getGroupedGamesForGameIndex(String gameIndex) {
        return groupedRelatedGamesByGameIndex.get(gameIndex);
    }

    public static List<GameVersion> getGameIndexesForGameGroup(String gameIndex) {
        return gameIndexesByGameGroup.get(gameIndex);
    }

    public static String getIndexNumberByItemName(String itemName) {
        return indexNumberByItemName.get(itemName);
    }

    public static String getIndexNumberByRegionName(String regionName) {
        return indexNumberByRegionName.get(regionName);
    }

    public static String getPokemonBaseId(String pokemonId) {
        String[] parts = pokemonId.split(POKEMON_ID_DELIMITER);
        String realID = parts[0];
        return realID.concat(POKEMON_ID_DELIMITER).concat(NORMAL_FORMS_INTERFIX).concat(POKEMON_ID_DELIMITER).concat("0");
    }

    public static String getPokemonRealId(String pokemonId) {
        String[] parts = pokemonId.split(POKEMON_ID_DELIMITER);
        return parts[0];
    }

}
