package utils;

import database.Database;
import enums.GameVersion;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.testng.collections.CollectionUtils;
import pojos.PokedexGame;
import pojos.PokemonGeneration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

import static utils.PokemonUtils.*;
import static utils.Sanitizer.*;

public class PokemonSeleniumUtils {

    private static final String POKEMON_ID_TEMPLATE = "%s" + POKEMON_ID_DELIMITER + "%s" + POKEMON_ID_DELIMITER + "%s";

    public static final String PALDEAN = "Paldea";
    public static final String KANTONIAN = "Kanto";
    public static final String ALOLAN = "Alola";
    public static final String GALARIAN = "Galar";
    public static final String MEGA = "Mega";
    public static final String GIGANTAMAX = "Gigantamax";

    private static final Map<Integer, Pair<Integer, Integer>> rangeOfNationalPokemonForGeneration = new HashMap<>() {{
        put(1, new ImmutablePair<>(GENERATION_1_START, GENERATION_1_END));
        put(2, new ImmutablePair<>(GENERATION_1_START, GENERATION_2_END));
        put(3, new ImmutablePair<>(GENERATION_1_START, GENERATION_3_END));
        put(4, new ImmutablePair<>(GENERATION_1_START, GENERATION_4_END));
        put(5, new ImmutablePair<>(GENERATION_1_START, GENERATION_5_END));
        put(6, new ImmutablePair<>(GENERATION_1_START, GENERATION_6_END));
        put(7, new ImmutablePair<>(GENERATION_1_START, GENERATION_7_END));
        put(8, new ImmutablePair<>(GENERATION_1_START, GENERATION_8_END));
        put(9, new ImmutablePair<>(GENERATION_1_START, GENERATION_9_END));
    }};

    private static final Map<String, String> nationalDexForGame = new HashMap<>() {{
        List<PokedexGame> pokedexGames = Database.getAllPokedexGames().stream().filter(PokedexGame::isNational).collect(Collectors.toList());
        putAll(pokedexGames.stream().collect(Collectors.toMap(PokedexGame::getGameIndex, PokedexGame::getIndexNumber)));
    }};


    private static final List<Integer> generations = new ArrayList<>() {{
        add(1);
        add(2);
        add(3);
        add(4);
        add(5);
        add(6);
        add(7);
        add(8);
        add(9);
    }};


    private static final Map<String, String> translationMap = new HashMap<>() {{
        put("Rojo", "Red");
        put("Azul", "Blue");
        put("Amarillo", "Yellow");
        put("Oro", "Gold");
        put("Plata", "Silver");
        put("Cristal", "Crystal");
        put("Rubí", "Ruby");
        put("Zafiro", "Sapphire");
        put("Esmeralda", "Emerald");
        put("Rojo Fuego", "FireRed");
        put("Verde Hoja", "LeafGreen");
        put("Diamante", "Diamond");
        put("Perla", "Pearl");
        put("Platino", "Platinum");
        put("Oro HeartGold", "HeartGold");
        put("Plata SoulSilver", "SoulSilver");
        put("Blanco", "White");
        put("Blanco 2", "White 2");
        put("Negro", "Black");
        put("Negro 2", "Black 2");
        put("Pokémon X", "X");
        put("Pokémon Y", "Y");
        put("Rubí Omega", "Omega Ruby");
        put("Zafiro Alfa", "Alpha Sapphire");
        put("Sol", "Sun");
        put("Luna", "Moon");
        put("Ultrasol", "Ultra Sun");
        put("Ultraluna", "Ultra Moon");
        put("Let's Go Pikachu!", "Let's Go Pikachu");
        put("Let's Go Eevee!", "Let's Go Eevee");
        put("Espada", "Sword");
        put("Escudo", "Shield");
        put("Diamante Brillante", "Brilliant Diamond");
        put("Perla Reluciente", "Shining Pearl");
        put("Leyendas: Arceus", "Legends: Arceus");
        put("Escarlata", "Scarlet");
        put("Púrpura", "Violet");
        put("Purpura", "Violet");
    }};

    private static final Map<String, Integer> generationByGameMap = new HashMap<>() {{
        put("Red", 1);
        put("Blue", 1);
        put("Yellow", 1);
        put("Gold", 2);
        put("Silver", 2);
        put("Crystal", 2);
        put("Ruby", 3);
        put("Sapphire", 3);
        put("Emerald", 3);
        put("FireRed", 3);
        put("LeafGreen", 3);
        put("Diamond", 4);
        put("Pearl", 4);
        put("Platinum", 4);
        put("Pal Park", 4);
        put("Pokéwalker", 4);
        put("HeartGold", 4);
        put("SoulSilver", 4);
        put("White", 5);
        put("White 2", 5);
        put("Black", 5);
        put("Black 2", 5);
        put("Black & White", 5);
        put("Dream World", 5);
        put("X", 6);
        put("Y", 6);
        put("Omega Ruby", 6);
        put("Alpha Sapphire", 6);
        put("Sun", 7);
        put("Moon", 7);
        put("Sun & Moon", 7);
        put("Ultra Sun", 7);
        put("Ultra Moon", 7);
        put("Let's Go Pikachu", 7);
        put("Let's Go Eevee", 7);
        put("Sword", 8);
        put("Shield", 8);
        put("Scarlet", 9);
        put("Violet", 9);
        put("Expansion Pass", 8);
    }};

    private static final Map<String, String> idByGameMap = new HashMap<>() {{
        put("Red", GameVersion.RED.getIndex());
        put("Blue", GameVersion.BLUE.getIndex());
        put("Yellow", GameVersion.YELLOW.getIndex());
        put("Gold", GameVersion.GOLD.getIndex());
        put("Silver", GameVersion.SILVER.getIndex());
        put("Crystal", GameVersion.CRYSTAL.getIndex());
        put("Ruby", GameVersion.RUBY.getIndex());
        put("Sapphire", GameVersion.SAPPHIRE.getIndex());
        put("Emerald", GameVersion.EMERALD.getIndex());
        put("FireRed", GameVersion.FIRERED.getIndex());
        put("LeafGreen", GameVersion.LEAFGREEN.getIndex());
        put("Diamond", GameVersion.DIAMOND.getIndex());
        put("Pearl", GameVersion.PEARL.getIndex());
        put("Platinum", GameVersion.PLATINUM.getIndex());
        put("HeartGold", GameVersion.HEARTGOLD.getIndex());
        put("SoulSilver", GameVersion.SOULSILVER.getIndex());
        put("Black", GameVersion.BLACK.getIndex());
        put("White", GameVersion.WHITE.getIndex());
        put("Black 2", GameVersion.BLACK_2.getIndex());
        put("White 2", GameVersion.WHITE_2.getIndex());
        put("X", GameVersion.X.getIndex());
        put("Y", GameVersion.Y.getIndex());
        put("Omega Ruby", GameVersion.OMEGA_RUBY.getIndex());
        put("Alpha Sapphire", GameVersion.ALPHA_SAPPHIRE.getIndex());
        put("Sun", GameVersion.SUN.getIndex());
        put("Moon", GameVersion.MOON.getIndex());
        put("Ultra Sun", GameVersion.ULTRA_SUN.getIndex());
        put("Ultra Moon", GameVersion.ULTRA_MOON.getIndex());
        put("Let's Go Pikachu", GameVersion.LETS_GO_PIKACHU.getIndex());
        put("Let's Go Eevee", GameVersion.LETS_GO_EEVEE.getIndex());
        put("Sword", GameVersion.SWORD.getIndex());
        put("Shield", GameVersion.SHIELD.getIndex());
        put("Expansion Pass", GameVersion.EXPANSION_PASS.getIndex());
        put("Brilliant Diamond", GameVersion.BRILLIANT_DIAMOND.getIndex());
        put("Shining Pearl", GameVersion.SHINING_PEARL.getIndex());
        put("Legends: Arceus", GameVersion.LEGENDS_ARCEUS.getIndex());
        put("Scarlet", GameVersion.SCARLET.getIndex());
        put("Violet", GameVersion.VIOLET.getIndex());
    }};

    private static final Map<String, String> gameEnByIdMap = new HashMap<>() {{
        put(GameVersion.RED.getIndex(), "Red");
        put(GameVersion.BLUE.getIndex(), "Blue");
        put(GameVersion.YELLOW.getIndex(), "Yellow");
        put(GameVersion.GOLD.getIndex(), "Gold");
        put(GameVersion.SILVER.getIndex(), "Silver");
        put(GameVersion.CRYSTAL.getIndex(), "Crystal");
        put(GameVersion.RUBY.getIndex(), "Ruby");
        put(GameVersion.SAPPHIRE.getIndex(), "Sapphire");
        put(GameVersion.EMERALD.getIndex(), "Emerald");
        put(GameVersion.FIRERED.getIndex(), "FireRed");
        put(GameVersion.LEAFGREEN.getIndex(), "LeafGreen");
        put(GameVersion.DIAMOND.getIndex(), "Diamond");
        put(GameVersion.PEARL.getIndex(), "Pearl");
        put(GameVersion.PLATINUM.getIndex(), "Platinum");
        put(GameVersion.HEARTGOLD.getIndex(), "HeartGold");
        put(GameVersion.SOULSILVER.getIndex(), "SoulSilver");
        put(GameVersion.BLACK.getIndex(), "Black");
        put(GameVersion.WHITE.getIndex(), "White");
        put(GameVersion.BLACK_2.getIndex(), "Black 2");
        put(GameVersion.WHITE_2.getIndex(), "White 2");
        put(GameVersion.X.getIndex(), "X");
        put(GameVersion.Y.getIndex(), "Y");
        put(GameVersion.OMEGA_RUBY.getIndex(), "Omega Ruby");
        put(GameVersion.ALPHA_SAPPHIRE.getIndex(), "Alpha Sapphire");
        put(GameVersion.SUN.getIndex(), "Sun");
        put(GameVersion.MOON.getIndex(), "Moon");
        put(GameVersion.ULTRA_SUN.getIndex(), "Ultra Sun");
        put(GameVersion.ULTRA_MOON.getIndex(), "Ultra Moon");
        put(GameVersion.LETS_GO_PIKACHU.getIndex(), "Let's Go Pikachu");
        put(GameVersion.LETS_GO_EEVEE.getIndex(), "Let's Go Eevee");
        put(GameVersion.SWORD.getIndex(), "Sword");
        put(GameVersion.SHIELD.getIndex(), "Shield");
        put(GameVersion.BRILLIANT_DIAMOND.getIndex(), "Brilliant Diamond");
        put(GameVersion.SHINING_PEARL.getIndex(), "Shining Pearl");
        put(GameVersion.LEGENDS_ARCEUS.getIndex(), "Legends: Arceus");
        put(GameVersion.SCARLET.getIndex(), "Scarlet");
        put(GameVersion.VIOLET.getIndex(), "Violet");
    }};

    private static final Map<String, String> gameEsByIdMap = new HashMap<>() {{
        put(GameVersion.RED.getIndex(), "Rojo");
        put(GameVersion.BLUE.getIndex(), "Azul");
        put(GameVersion.YELLOW.getIndex(), "Amarillo");
        put(GameVersion.GOLD.getIndex(), "Oro");
        put(GameVersion.SILVER.getIndex(), "Plata");
        put(GameVersion.CRYSTAL.getIndex(), "Cristal");
        put(GameVersion.RUBY.getIndex(), "Rubí");
        put(GameVersion.SAPPHIRE.getIndex(), "Zafiro");
        put(GameVersion.EMERALD.getIndex(), "Esmeralda");
        put(GameVersion.FIRERED.getIndex(), "Rojo Fuego");
        put(GameVersion.LEAFGREEN.getIndex(), "Verde Hoja");
        put(GameVersion.DIAMOND.getIndex(), "Diamante");
        put(GameVersion.PEARL.getIndex(), "Perla");
        put(GameVersion.PLATINUM.getIndex(), "Platino");
        put(GameVersion.HEARTGOLD.getIndex(), "Oro HeartGold");
        put(GameVersion.SOULSILVER.getIndex(), "Plata SoulSilver");
        put(GameVersion.BLACK.getIndex(), "Negro");
        put(GameVersion.WHITE.getIndex(), "Blanco");
        put(GameVersion.BLACK_2.getIndex(), "Negro 2");
        put(GameVersion.WHITE_2.getIndex(), "Blanco 2");
        put(GameVersion.X.getIndex(), "X");
        put(GameVersion.Y.getIndex(), "Y");
        put(GameVersion.OMEGA_RUBY.getIndex(), "Rubí Omega");
        put(GameVersion.ALPHA_SAPPHIRE.getIndex(), "Zafiro Alfa");
        put(GameVersion.SUN.getIndex(), "Sol");
        put(GameVersion.MOON.getIndex(), "Luna");
        put(GameVersion.ULTRA_SUN.getIndex(), "Ultra Sol");
        put(GameVersion.ULTRA_MOON.getIndex(), "Ultra Luna");
        put(GameVersion.LETS_GO_PIKACHU.getIndex(), "Let's Go Pikachu");
        put(GameVersion.LETS_GO_EEVEE.getIndex(), "Let's Go Eevee");
        put(GameVersion.SWORD.getIndex(), "Espada");
        put(GameVersion.SHIELD.getIndex(), "Escudo");
        put(GameVersion.BRILLIANT_DIAMOND.getIndex(), "Diamante Brillante");
        put(GameVersion.SHINING_PEARL.getIndex(), "Perla Reluciente");
        put(GameVersion.LEGENDS_ARCEUS.getIndex(), "Leyendas: Arceus");
        put(GameVersion.SCARLET.getIndex(), "Escarlata");
        put(GameVersion.VIOLET.getIndex(), "Púrpura");
    }};

    private static final Map<String, String> localizationsByGameMap = new HashMap<>() {{
        put("red", GameVersion.RED.getIndex());
        put("rojo", GameVersion.RED.getIndex());
        put("blue", GameVersion.BLUE.getIndex());
        put("azul", GameVersion.BLUE.getIndex());
        put("yellow", GameVersion.YELLOW.getIndex());
        put("amarillo", GameVersion.YELLOW.getIndex());
        put("gold", GameVersion.GOLD.getIndex());
        put("oro", GameVersion.GOLD.getIndex());
        put("silver", GameVersion.SILVER.getIndex());
        put("plata", GameVersion.SILVER.getIndex());
        put("crystal", GameVersion.CRYSTAL.getIndex());
        put("cristal", GameVersion.CRYSTAL.getIndex());
        put("ruby", GameVersion.RUBY.getIndex());
        put("rubí", GameVersion.RUBY.getIndex());
        put("sapphire", GameVersion.SAPPHIRE.getIndex());
        put("zafiro", GameVersion.SAPPHIRE.getIndex());
        put("emerald", GameVersion.EMERALD.getIndex());
        put("esmeralda", GameVersion.EMERALD.getIndex());
        put("firered", GameVersion.FIRERED.getIndex());
        put("rojofuego", GameVersion.FIRERED.getIndex());
        put("leafgreen", GameVersion.LEAFGREEN.getIndex());
        put("verdehoja", GameVersion.LEAFGREEN.getIndex());
        put("diamond", GameVersion.DIAMOND.getIndex());
        put("diamante", GameVersion.DIAMOND.getIndex());
        put("pearl", GameVersion.PEARL.getIndex());
        put("perla", GameVersion.PEARL.getIndex());
        put("platinum", GameVersion.PLATINUM.getIndex());
        put("platino", GameVersion.PLATINUM.getIndex());
        put("palpark", GameVersion.PAL_PARK.getIndex());
        put("parquecompi", GameVersion.PAL_PARK.getIndex());
        put("heartgold", GameVersion.HEARTGOLD.getIndex());
        put("oroheartgold", GameVersion.HEARTGOLD.getIndex());
        put("soulsilver", GameVersion.SOULSILVER.getIndex());
        put("platasoulsilver", GameVersion.SOULSILVER.getIndex());
        put("pokéwalker", GameVersion.POKEWALKER.getIndex());
        put("pokewalker", GameVersion.POKEWALKER.getIndex());
        put("black", GameVersion.BLACK.getIndex());
        put("negro", GameVersion.BLACK.getIndex());
        put("white", GameVersion.WHITE.getIndex());
        put("blanco", GameVersion.WHITE.getIndex());
        put("black2", GameVersion.BLACK_2.getIndex());
        put("negro2", GameVersion.BLACK_2.getIndex());
        put("white2", GameVersion.WHITE_2.getIndex());
        put("blanco2", GameVersion.WHITE_2.getIndex());
        put("dreamworld", GameVersion.DREAM_WORLD.getIndex());
        put("x", GameVersion.X.getIndex());
        put("y", GameVersion.Y.getIndex());
        put("omegaruby", GameVersion.OMEGA_RUBY.getIndex());
        put("rubíomega", GameVersion.OMEGA_RUBY.getIndex());
        put("alphasapphire", GameVersion.ALPHA_SAPPHIRE.getIndex());
        put("zafiroalfa", GameVersion.ALPHA_SAPPHIRE.getIndex());
        put("sun", GameVersion.SUN.getIndex());
        put("sol", GameVersion.SUN.getIndex());
        put("moon", GameVersion.MOON.getIndex());
        put("luna", GameVersion.MOON.getIndex());
        put("ultrasun", GameVersion.ULTRA_SUN.getIndex());
        put("ultrasol", GameVersion.ULTRA_SUN.getIndex());
        put("ultramoon", GameVersion.ULTRA_MOON.getIndex());
        put("ultraluna", GameVersion.ULTRA_MOON.getIndex());
        put("let'sgopikachu", GameVersion.LETS_GO_PIKACHU.getIndex());
        put("letsgopikachu", GameVersion.LETS_GO_PIKACHU.getIndex());
        put("let'sgoeevee", GameVersion.LETS_GO_EEVEE.getIndex());
        put("letsgoeevee", GameVersion.LETS_GO_EEVEE.getIndex());
        put("sword", GameVersion.SWORD.getIndex());
        put("espada", GameVersion.SWORD.getIndex());
        put("shield", GameVersion.SHIELD.getIndex());
        put("escudo", GameVersion.SHIELD.getIndex());
        put("brilliantdiamond", GameVersion.BRILLIANT_DIAMOND.getIndex());
        put("diamantebrillante", GameVersion.BRILLIANT_DIAMOND.getIndex());
        put("perlareluciente", GameVersion.SHINING_PEARL.getIndex());
        put("shiningpearl", GameVersion.SHINING_PEARL.getIndex());
        put("leyendas:arceus", GameVersion.LEGENDS_ARCEUS.getIndex());
        put("legendsarceus", GameVersion.LEGENDS_ARCEUS.getIndex());
        put("legends:arceus", GameVersion.LEGENDS_ARCEUS.getIndex());
        put("scarlet", GameVersion.SCARLET.getIndex());
        put("escarlata", GameVersion.SCARLET.getIndex());
        put("Escarlata", GameVersion.SCARLET.getIndex());
        put("púrpura", GameVersion.VIOLET.getIndex());
        put("purpura", GameVersion.VIOLET.getIndex());
        put("Púrpura", GameVersion.VIOLET.getIndex());
        put("violet", GameVersion.VIOLET.getIndex());
        put("expansionpass", GameVersion.EXPANSION_PASS.getIndex());
        put("swordexpansionpass", GameVersion.EXPANSION_PASS.getIndex());
        put("shieldexpansionpass", GameVersion.EXPANSION_PASS.getIndex());
        put("isleofarmor", GameVersion.ISLE_OF_ARMOR.getIndex());
        put("thecrowntundra", GameVersion.CROWN_OF_TUNDRA.getIndex());

    }};

    private static final Map<String, Integer> idTypeByTypeName = new HashMap<>();

    static {
        idTypeByTypeName.put("normal", 1);
        idTypeByTypeName.put("fire", 2);
        idTypeByTypeName.put("fighting", 3);
        idTypeByTypeName.put("water", 4);
        idTypeByTypeName.put("flying", 5);
        idTypeByTypeName.put("grass", 6);
        idTypeByTypeName.put("poison", 7);
        idTypeByTypeName.put("electric", 8);
        idTypeByTypeName.put("ground", 9);
        idTypeByTypeName.put("psychic", 10);
        idTypeByTypeName.put("rock", 11);
        idTypeByTypeName.put("ice", 12);
        idTypeByTypeName.put("bug", 13);
        idTypeByTypeName.put("dragon", 14);
        idTypeByTypeName.put("ghost", 15);
        idTypeByTypeName.put("dark", 16);
        idTypeByTypeName.put("steel", 17);
        idTypeByTypeName.put("fairy", 18);
    }

    private static final Map<String, Integer> moveCategoryIdTypeByMoveCategoryName = new HashMap<>();

    static {
        moveCategoryIdTypeByMoveCategoryName.put("physical", 1);
        moveCategoryIdTypeByMoveCategoryName.put("físico", 1);
        moveCategoryIdTypeByMoveCategoryName.put("special", 2);
        moveCategoryIdTypeByMoveCategoryName.put("especial", 2);
        moveCategoryIdTypeByMoveCategoryName.put("status", 3);
        moveCategoryIdTypeByMoveCategoryName.put("estado", 3);
    }

    private static final Map<String, Integer> contestCategoryIdTypeByContestCategoryName = new HashMap<>();

    static {
        contestCategoryIdTypeByContestCategoryName.put("cool", 1);
        contestCategoryIdTypeByContestCategoryName.put("beautiful", 2);
        contestCategoryIdTypeByContestCategoryName.put("cute", 3);
        contestCategoryIdTypeByContestCategoryName.put("clever", 4);
        contestCategoryIdTypeByContestCategoryName.put("tough", 5);
    }

    private static final List<String> indexHashMapNames = new ArrayList<>(Arrays.asList("IndexNumberByMoveName", "IndexNumberByPokemonName", "PokemonNameByIndexNumber", "IndexNumberByAbilityName", "IndexNumberByEggGroupName"));


    public static void insertToDatabase(String text, String txtFileName) {
        String nullifiedText = text.replace("\"\"", "null").replace("\"null\"", "null");
        if (System.getProperty("debug") == null) {
            Database.insert(nullifiedText);
        }
        addTextToFile(nullifiedText, txtFileName);
    }

    public static void addTextToFile(String text, String txtFileName) {
        try {
            final Path path = Paths.get(txtFileName);
            Files.write(path, Collections.singletonList(text), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
        } catch (final IOException ioe) {

        }
    }

    public static String getCurrentPokemonFromUrl(WebDriver driver, String baseUrl) {
        return driver.getCurrentUrl().replace(baseUrl, "");
    }

    public static List<PokemonGeneration> getPokemonGenerations() {
        PokemonGeneration generation1 = new PokemonGeneration(GENERATION_1_END, "Bulbasaur", GEN_1, GENERATION_1_START, GENERATION_1_END);
        PokemonGeneration generation2 = new PokemonGeneration(GENERATION_2_END - GENERATION_1_END, "Chikorita", GEN_2, GENERATION_2_START, GENERATION_2_END);
        PokemonGeneration generation3 = new PokemonGeneration(GENERATION_3_END - GENERATION_2_END, "Treecko", GEN_3, GENERATION_3_START, GENERATION_3_END);
        PokemonGeneration generation4 = new PokemonGeneration(GENERATION_4_END - GENERATION_3_END, "Turtwig", GEN_4, GENERATION_4_START, GENERATION_4_END);
        PokemonGeneration generation5 = new PokemonGeneration(GENERATION_5_END - GENERATION_4_END, "Victini", GEN_5, GENERATION_5_START, GENERATION_5_END);
        PokemonGeneration generation6 = new PokemonGeneration(GENERATION_6_END - GENERATION_5_END, "Chespin", GEN_6, GENERATION_6_START, GENERATION_6_END);
        PokemonGeneration generation7 = new PokemonGeneration(GENERATION_7_END - GENERATION_6_END, "Rowlet", GEN_7, GENERATION_7_START, GENERATION_7_END);
        PokemonGeneration generation8 = new PokemonGeneration(GENERATION_8_END - GENERATION_7_END, "Grookey", GEN_8, GENERATION_8_START, GENERATION_8_END);
        PokemonGeneration generation9 = new PokemonGeneration(GENERATION_9_END - GENERATION_8_END, "Sprigatito", GEN_9, GENERATION_9_START, GENERATION_9_END);
        return List.of(generation1, generation2, generation3, generation4, generation5, generation6, generation7, generation8, generation9);
    }

    public static String generateStatement(String... strings) {
        return String.join("_", strings).concat(".txt");
    }

    public static boolean isMega(String name) {
        return ((name.toLowerCase().contains("mega") || name.contains("primal") || name.contains("primigen")) && hasMultipleWords(name));
    }

    public static boolean isGigantamax(String name) {
        return ((name.toLowerCase().contains("gigantamax") || name.toLowerCase().contains("gigamax")) && hasMultipleWords(name));
    }

    public static boolean isAlolan(String name) {
        return name.toLowerCase().contains("alola") && hasMultipleWords(name);
    }

    public static boolean isGalarian(String name) {
        return name.toLowerCase().contains("galar") && hasMultipleWords(name);
    }

    public static boolean isHisuian(String name) {
        return name.toLowerCase().contains("hisui") && hasMultipleWords(name);
    }

    public static boolean isPaldean(String name) {
        return name.toLowerCase().contains("paldea") && hasMultipleWords(name);
    }

    public static boolean isAlternateForm(String name) {
        return name.toLowerCase().contains("form") && hasMultipleWords(name);
    }

    public static boolean isNormalForm(String name) {
        return name.toLowerCase().contains("normal") && hasMultipleWords(name);
    }

    private static boolean hasMultipleWords(String name) {
        return name.split(" ").length > 1;
    }

    public static String calculateId(String realId, String name, HashMap<String, String> pokemonAvailabilityMap) {
        int i = 0;
        String id = String.format(POKEMON_ID_TEMPLATE, realId, NORMAL_FORMS_INTERFIX, i);
        while (pokemonAvailabilityMap.containsKey(id)) {
            id = calculatePrefix(realId, name, i);
            i++;
        }
        pokemonAvailabilityMap.put(id, name);
        return id;
    }

    public static String calculatePrefix(String realId, String pokemonName, int i) {

        String prefix = ALTERNATE_FORMS_INTERFIX;

        if (isAlternateForm(pokemonName)) {
            prefix = ALTERNATE_FORMS_INTERFIX;
        }
        if (isHisuian(pokemonName)) {
            prefix = HISUI_FORMS_INTERFIX;
        }
        if (isAlolan(pokemonName)) {
            prefix = ALOLA_FORMS_INTERFIX;
        }
        if (isPaldean(pokemonName)) {
            prefix = PALDEA_FORMS_INTERFIX;
        }
        if (isGalarian(pokemonName)) {
            prefix = GALAR_FORMS_INTERFIX;
        }
        if (isMega(pokemonName)) {
            prefix = MEGA_EVOLUTIONS_INTERFIX;
        }
        if (isGigantamax(pokemonName)) {
            prefix = GIGANTAMAX_INTERFIX;
        }
        if (isNormalForm(pokemonName)) {
            prefix = NORMAL_FORMS_INTERFIX;
        }

        return String.format(POKEMON_ID_TEMPLATE, realId, prefix, i);

    }


    public static HashMap<PokedexGame, Integer> calculateGamesIds(String gamesIds, String pokemonName) {
        HashMap<PokedexGame, Integer> pokedexNumbersByRegion = new HashMap<>();
        if (gamesIds.equals("—")) return new HashMap<>();
        String[] lines = gamesIds.split("\\r?\\n");
        for (String currentLine : lines) {
            String game = sanitizeGame(StringUtils.substringBetween(currentLine, "(", ")"));
            int id = Integer.parseInt(currentLine.substring(0, currentLine.indexOf("(")).trim());

            switch (game) {
                case "red_blue_yellow", "yellow_red_blue":
                    if (!isAnyMegaOrRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("kanto_regional", "red¬blue¬yellow", false), id);
                    }
                    break;

                case "gold_silver_crystal":
                    if (!isAnyMegaOrRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("johto_regional", "gold¬silver¬crystal", false), id);
                    }
                    break;

                case "ruby_sapphire_emerald":
                    if (!isAnyMegaOrRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("hoenn_regional", "ruby¬sapphire¬emerald", false), id);
                    }
                    break;

                case "firered_leafgreen":
                    if (!isAnyMegaOrRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("kanto_regional", "firered¬leafgreen", false), id);

                    }
                    break;
                case "diamond_pearl":
                    if (!isAnyMegaOrRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("sinnoh_regional", "diamond¬pearl", false), id);

                    }
                    break;
                case "platinum":
                    if (!isAnyMegaOrRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("sinnoh_regional", "platinum", false), id);
                    }
                    break;
                case "diamond_pearl_platinum":
                    if (!isAnyMegaOrRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("sinnoh_regional", "diamond¬pearl¬platinum", false), id);

                    }
                    break;
                case "heartgold_soulsilver":
                    if (!isAnyMegaOrRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("johto_regional", "heartgold¬soulsilver", false), id);

                    }
                    break;
                case "black_white":
                    if (!isAnyMegaOrRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("unova_regional", "black¬white", false), id);

                    }
                    break;
                case "black_2_white_2":
                    if (!isAnyMegaOrRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("unova_regional", "black_2¬white_2", false), id);
                    }
                    break;
                case "x_y_coastal":
                    if (!isAnyRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("kalos_coastal", "x¬y", false), id);
                    }
                    break;
                case "x_y_central":
                    if (!isAnyRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("kalos_central", "x¬y", false), id);
                    }
                    break;
                case "x_y_mountain":
                    if (!isAnyRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("kalos_mountain", "x¬y", false), id);
                    }
                    break;
                case "omega_ruby_alpha_sapphire":
                    if (!isAnyRegionalForm(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("hoenn_regional", "omega_ruby¬alpha_sapphire", false), id);

                    }
                    break;
                case "sun_moon":
                    if (!isGalarian(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("alola_regional", "sun¬moon", false), id);
                    }
                    break;
                case "ultra_sun_ultra_moon":
                    if (!isGalarian(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("alola_regional", "ultra_sun¬ultra_moon", false), id);

                    }
                    break;
                case "lets_go_pikachu_lets_go_eevee":
                    if (!isGalarian(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("kanto_regional", "lets_go_pikachu¬lets_go_eevee", false), id);

                    }
                    break;
                case "sword_shield":
                    if (!isMega(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("galar_regional", "sword¬shield", false), id);

                    }
                    break;
                case "isle_of_armor":
                    if (!isMega(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("isle_of_armor", "sword¬shield", false), id);

                    }
                    break;
                case "crown_tundra":
                    if (!isMega(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("crown_tundra", "sword¬shield", false), id);

                    }
                    break;
                case "brilliant_diamond_shining_pearl":
                    if (!isMega(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("sinnoh_regional", "brilliant_diamond¬shining_pearl", false), id);
                    }
                    break;

                case "legends:_arceus":
                    if (!isMega(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("hisui_regional", "arceus_legends", false), id);

                    }
                    break;

                case "scarlet_violet":
                    if (!isMega(pokemonName)) {
                        pokedexNumbersByRegion.put(new PokedexGame("paldea_regional", "scarlet¬violet", false), id);

                    }
                    break;

                default:
                    System.out.println(String.format("ERROR POKEDEX_GAME WITH NAME %s NOT FOUND", game));
                    break;
            }

        }
        return pokedexNumbersByRegion;
    }

    public static HashMap<String, Integer> calculateEvYieldByStats(String evYield) {
        HashMap<String, Integer> evYieldByStats = new HashMap<>();
        evYieldByStats.put("hp", null);
        evYieldByStats.put("atk", null);
        evYieldByStats.put("def", null);
        evYieldByStats.put("spatk", null);
        evYieldByStats.put("spdef", null);
        evYieldByStats.put("speed", null);
        String[] lines = evYield.split(",");
        for (String line : lines) {
            if (line.contains("—")) break;
            String evValue = line.substring(0, 2).replaceAll("\\s+", "");
            String evStat = sanitizeEvStat(line.replace(evValue, ""));
            evYieldByStats.put(evStat, Integer.parseInt(evValue.trim()));
        }
        return evYieldByStats;
    }

    public static Pair<String, String> extractEggGroups(String eggGroups) {
        if (eggGroups.contains("—")) return new ImmutablePair<>(null, null);

        if (eggGroups.contains(",")) {
            String[] lines = eggGroups.split(",");
            return new ImmutablePair<>(lines[0], lines[1]);
        }
        return new ImmutablePair<>(eggGroups, eggGroups);
    }

    public static Pair<String, String> extractGenders(String genders) {
        if (genders.contains("—") || genders.contains("Genderless")) return new ImmutablePair<>(null, null);

        String[] lines = sanitizeGenders(genders).split(",");
        return new ImmutablePair<>(lines[0].replace(".", ","), lines[1].replace(".", ","));
    }

    public static boolean isAnySpecialForm(String string) {
        return isAlternateForm(string) || isAnyMegaOrRegionalForm(string);
    }

    public static boolean isAnyMegaOrRegionalForm(String string) {
        return isMega(string) || isAnyRegionalForm(string);
    }

    public static boolean isAnyRegionalForm(String name) {
        return isGalarian(name) || isAlolan(name) || isHisuian(name) || isPaldean(name);
    }

    public static String extractPokemonNameFromString(String text, String pokemonName) {
        String parsedPokemonName = GenericUtils.removeParenthesisFromString(pokemonName);
        if (text.contains(parsedPokemonName)) {
            text = text.substring(text.indexOf(parsedPokemonName)).replace(parsedPokemonName, "").trim();
        }
        return text;
    }

    public static Map<String, String> getTranslationMap() {
        return translationMap;
    }

    public static Integer getGenerationByGameMap(String game) {
        int generation = 0;
        try {
            generation = generationByGameMap.get(game);
        } catch (NullPointerException npe) {
            System.out.printf("ERROR trying to add game %s%n", game);
        }
        return generation;
    }

    public static int getIdTypeByTypeName(String type) {
        return idTypeByTypeName.get(type.toLowerCase());
    }

    public static Integer getMoveCategoryIdTypeByMoveCategoryName(String categoryName) {
        return StringUtils.isEmpty(categoryName) ? null : moveCategoryIdTypeByMoveCategoryName.get(sanitizeMoveCategory(categoryName));
    }

    public static Integer getContestCategoryIdTypeByContestCategoryName(String contestName) {
        return StringUtils.isEmpty(contestName) || contestName.contains("?") ? null : contestCategoryIdTypeByContestCategoryName.get(contestName.toLowerCase());
    }

    public static String getIdByGameMap(String game) {
        return idByGameMap.get(game);
    }

    public static String getGameEsByIdMap(String game) {
        return gameEsByIdMap.get(game);
    }

    public static List<String> getGameEsByIdMap(Set<String> ids) {
        List<String> translatedGames = new ArrayList<>();
        for (String gameId : ids) {
            translatedGames.add(getGameEsByIdMap(gameId));
        }
        return translatedGames;
    }

    public static String getGameEnByIdMap(String game) {
        return gameEnByIdMap.get(game);
    }

    public static List<String> getGameEnByIdMap(Set<String> ids) {
        List<String> translatedGames = new ArrayList<>();
        for (String gameId : ids) {
            translatedGames.add(getGameEnByIdMap(gameId));
        }
        return translatedGames;
    }

    public static String getLocalizationsByGameMap(String localization) {
        String localizationByGameMap = localizationsByGameMap.get(localization);
        if (Objects.isNull(localizationByGameMap)) {
            //throw new RuntimeException(String.format("Game %s is not added to 'localizationsByGameMap', please add it and execute the test again.", localization));
        }
        return localizationByGameMap;
    }


    public static String getNationalDexForGame(String game) {
        return nationalDexForGame.get(game);
    }


    public static Pair<Integer, Integer> getRangeOfNationalPokemonForGeneration(int gen) {
        return rangeOfNationalPokemonForGeneration.get(gen);
    }

    public static String getPokemonResourceID(String pokemonId) {
        String[] parts = pokemonId.split("¬");
        String realID = parts[0];
        String type = parts[1];
        String version = parts[2];
        return realID + (type.equals("normal") ? "" : type) + (version.equals("0") ? "" : version);
    }

    public static Set<GameVersion> groupGameVersionsIds(List<String> allNonGroupedGamesIds) {
        List<GameVersion> allNonGroupedGames = new ArrayList<>();
        for (String gameId : allNonGroupedGamesIds) {
            allNonGroupedGames.add(GameVersion.get(gameId));
        }
        return groupGameVersions(allNonGroupedGames);
    }

    public static Set<GameVersion> groupGameVersions(List<GameVersion> allNonGroupedGames) {
        Set<GameVersion> groupedVersions = new HashSet<>();
        List<GameVersion> flattenedGroupedGames = flattenGames(allNonGroupedGames);
        for (GameVersion nonGroupedGame : flattenedGroupedGames) {
            List<GameVersion> gameGroupsForGame = PokemonIndexUtils.getGroupedGamesForGameIndex(nonGroupedGame.getIndex());
            if (CollectionUtils.hasElements(gameGroupsForGame)) {
                for (GameVersion gameGroupForGame : gameGroupsForGame) {
                    List<GameVersion> gamesForGameGroup = PokemonIndexUtils.getGameIndexesForGameGroup(gameGroupForGame.getIndex()).stream().filter(flattenedGroupedGames::contains).collect(Collectors.toList());
                    if (CollectionUtils.hasElements(gamesForGameGroup) && gamesForGameGroup.size() == 1) {
                        groupedVersions.add(gamesForGameGroup.iterator().next());
                    } else {
                        groupedVersions.addAll(resolveMatchingGroups(
                                gamesForGameGroup.stream().map(GameVersion::getIndex).collect(Collectors.toList()), gamesForGameGroup.size()));
                    }
                }
            }
        }
        return filterMatroskaGroups(groupedVersions);
    }

    private static List<GameVersion> flattenGames(List<GameVersion> allNonGroupedGames) {
        List<GameVersion> flattened = new ArrayList<>();
        for (GameVersion game : allNonGroupedGames) {
            String[] subgames = game.getIndex().split("¬");
            for (String subgame : subgames) {
                flattened.add(GameVersion.get(subgame));
            }
        }
        return flattened;
    }

    /**
     * We use this in order to avoid repetition in game groups, for example :
     * If a group of games has matched 2 game groups that are matroska ("RUBY_SAPPHIRE_EMERALD" contains group "RUBY_SAPPHIRE))
     * we get rid of the nested ones and we preserve the most large group.
     *
     * @param gameGroupsForGame gameGroupsForGame
     */
    private static Set<GameVersion> filterMatroskaGroups(Set<GameVersion> gameGroupsForGame) {
        List<String> gameVersionIndexes = gameGroupsForGame.stream().map(GameVersion::getIndex).collect(Collectors.toList());
        for (GameVersion gameVersion : gameGroupsForGame) {
            String gameVersionIndex = gameVersion.getIndex();
            if (gameVersionIndexes.stream()
                    .filter(s -> !s.equals(gameVersionIndex))
                    .anyMatch(s -> s.contains(gameVersionIndex))) {
                gameVersionIndexes.remove(gameVersionIndex);
            }
        }
        return gameVersionIndexes.stream().map(GameVersion::get).collect(Collectors.toSet());
    }

    private static Set<GameVersion> resolveMatchingGroups(List<String> gameGroupsForGame, int depthSize) {
        Set<GameVersion> resolvedGames = new HashSet<>();
        Set<String> allPosibleCombinations = gameGroupsForGame.stream()
                .flatMap(s1 -> gameGroupsForGame.stream()
                        .flatMap(s2 -> gameGroupsForGame.stream()
                                .map(s3 -> s1 + "¬" + s2 + (depthSize == 3 ? "¬" + s3 : StringUtils.EMPTY)) //we support max 3 levels of depth
                        ))
                .collect(Collectors.toSet());

        for (String result : allPosibleCombinations) {
            GameVersion gameVersion = GameVersion.get(result);
            if (Objects.nonNull(gameVersion)) {
                resolvedGames.add(gameVersion);
            }
        }

        return resolvedGames;
    }


}
