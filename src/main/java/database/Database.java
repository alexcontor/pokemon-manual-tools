package database;

import com.google.common.base.MoreObjects;
import enums.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.testng.collections.CollectionUtils;
import pojos.*;
import utils.GenericUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

import static constants.DatabaseConstants.*;
import static utils.PokemonUtils.*;


public class Database {

    private static final String nestedMainGameQuery = "(SELECT g_nested._id FROM games as g_nested, game_groups as gg WHERE g_nested._id = gg._game_group AND g_nested.is_main = 1 AND gg._game = g._id) main_game";

    private static final String DATABASE_URL = "jdbc:sqlite:pokemon.db";

    public static void insert(String sentence) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            connection.setAutoCommit(true);
            statement = connection.createStatement();
            statement.executeUpdate(sentence);
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
    }


    /**
     * Instantiate the database with core data
     */
    public static void createNewDatabase() {

        List<String> coredata = new ArrayList<>();
        System.out.println("Loading coredata into database , this process may take a while...");
        try {
            coredata.add(GenericUtils.readFile(SCHEMA_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(EFFECTIVENESS_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(NATURES_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(EGG_GROUPS_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(GAMES_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(GAME_GROUPS_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(MOVE_CATEGORIES_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(ITEM_CATEGORIES_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(SMOGON_CATEGORIES_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(MOVE_CONTEST_TYPES_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(POKEDEX_GAMES_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(TYPES_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(POKE_GROUPS_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(EVOLUTION_CONDITIONS_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(GENDERS_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(TIMES_OF_DAY_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(SEASONS_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(REGIONS_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(REGION_AREAS_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(GAME_REGIONS_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(POKEMON_GIGANTAMAX_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(POKEMON_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(POKE_BASE_STATS_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(COLORS_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(COLORS_DARK_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(MOVES_URL, StandardCharsets.UTF_8));
            coredata.add(GenericUtils.readFile(BUILD_PRESETS_URL, StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("There was an error reading the file");
            e.printStackTrace();
        }
        for (String data : coredata) {
            insert(data);
        }
        System.out.println("Coredata loaded , a new pokemon database has been created.");
    }

    public static List<String> getAllTables() {
        List<String> tableNames = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            ResultSet rs = conn.getMetaData().getTables(null, null, null, null);
            while (rs.next()) {
                tableNames.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return tableNames;
    }

    public static List<GenericIndex> getAllLocalizationsForTableForLanguage(String table, Language language) {
        List<GenericIndex> localizedElements = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT *,rowid as rowid FROM " + table);
            while (rs.next()) {
                GenericIndex localizedElement = new GenericIndex(null);
                localizedElement.setRowID(rs.getInt("rowid"));
                List<String> filteredColumns = extractLocalizedTableColumns(rs, language);
                for (String filteredColumn : filteredColumns) {
                    String englishValue = rs.getString(filteredColumn.replace(language.getIsocode(), ""));
                    String localizedValue = rs.getString(filteredColumn.concat(language.getIsocode()));
                    localizedElement.addLocalizedColumn(filteredColumn, englishValue, localizedValue);
                }
                localizedElements.add(localizedElement);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return localizedElements;
    }

    public static List<GenericIndex> getAllLocalizationsForTableForLanguageExternal(String table, Language language) {
        List<GenericIndex> localizedElements = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(determineExternalDatabaseURL(language));
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT *,rowid as rowid FROM " + table);
            while (rs.next()) {
                GenericIndex localizedElement = new GenericIndex(null);
                localizedElement.setRowID(rs.getInt("rowid"));
                List<String> filteredColumns = extractLocalizedTableColumns(rs, language);
                for (String filteredColumn : filteredColumns) {
                    String englishValue = rs.getString(filteredColumn.replace(language.getIsocode(), ""));
                    String localizedValue = rs.getString(filteredColumn.concat(language.getIsocode()));
                    localizedElement.addLocalizedColumn(filteredColumn, englishValue, localizedValue);
                }
                localizedElements.add(localizedElement);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return localizedElements;
    }

    private static String determineExternalDatabaseURL(Language language) {
        return "jdbc:sqlite:pokemon" + language.getIsocode() + ".db";
    }

    public static Set<String> getAllLocalizedColumns(String table) {
        Set<String> localizedColumns = new HashSet<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            while (rs.next()) {
                List<String> filteredColumns = extractLocalizedTableColumns(rs, Language.ES);
                localizedColumns.addAll(filteredColumns);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return localizedColumns;
    }

    private static List<String> extractTableColumns(ResultSet rs) {
        List<String> allTableColumns = new ArrayList<>();
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int count = 0; //number of column
            count = metaData.getColumnCount();
            for (int i = 1; i <= count; i++) {
                allTableColumns.add(metaData.getColumnLabel(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allTableColumns;
    }

    private static List<String> extractLocalizedTableColumns(ResultSet rs, Language language) {
        List<String> allTableColumns = new ArrayList<>();
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int count = 0; //number of column
            count = metaData.getColumnCount();
            for (int i = 1; i <= count; i++) {
                String localizedColumn = metaData.getColumnLabel(i);
                if (localizedColumn.endsWith(language.getIsocode())) {
                    allTableColumns.add(localizedColumn.replace(language.getIsocode(), StringUtils.EMPTY));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allTableColumns;
    }

    public static List<Pokemon> getAllPokemon(List<PokemonType> exclusions) {
        List<Pokemon> pokemons = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pokemon as p ORDER BY real_id");
            while (rs.next()) {
                Pokemon pokemon = new Pokemon(rs.getString("_id"), rs.getInt("real_id"));
                if (isNotExcluded(pokemon.getIndexNumber(), exclusions)) {
                    String color = rs.getString("color");
                    String colorDark = rs.getString("color_dark");
                    for (Language supportedLanguage : Language.values()) {
                        pokemon.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                    }
                    pokemon.setColor(color);
                    pokemon.setColorDark(colorDark);
                    pokemons.add(pokemon);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemons;
    }

    public static List<Pokemon> getAllPokemonForGame(GameVersion gameVersion) {
        List<Pokemon> pokemons = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT p._id, p.real_id FROM pokemon as p, poke_numbers_dex as pnd WHERE p._id = pnd._id AND pnd._pokedex_game='" + gameVersion.getIndex() + "' ORDER BY real_id");
            while (rs.next()) {
                Pokemon pokemon = new Pokemon(rs.getString("_id"), rs.getInt("real_id"));
                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemons;
    }

    public static Location getLocationForCode(String locationCode) {
        Location location = null;
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from locations where _id='" + locationCode + "'");
            while (rs.next()) {
                location = new Location(rs.getString("_id"));
                location.setRegionIndex(rs.getString("_region_area"));
                for (Language supportedLanguage : Language.values()) {
                    location.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return location;
    }

    public static List<Stats> getBaseStatsForPokemon(String pokemonID) {
        List<Stats> baseStats = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM poke_basestats WHERE _id ='" + pokemonID + "' ORDER BY _start_gen");
            while (rs.next()) {
                baseStats.add(new Stats(
                        rs.getInt("hp"),
                        rs.getInt("atk"),
                        rs.getInt("def"),
                        rs.getInt("special"),
                        rs.getInt("spatk"),
                        rs.getInt("spdef"),
                        rs.getInt("speed"),
                        rs.getInt("_start_gen"),
                        rs.getInt("_end_gen"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return baseStats;
    }

    private static boolean isNotExcluded(String pokemonID, List<PokemonType> exclusions) {
        if (CollectionUtils.hasElements(exclusions)) {
            if (exclusions.contains(PokemonType.ALOLAN_FORM)) {
                if (isAlolanForm(pokemonID)) return false;
            }
            if (exclusions.contains(PokemonType.GALARIAN_FORM)) {
                if (isGalarianForm(pokemonID)) return false;
            }
            if (exclusions.contains(PokemonType.REGIONAL_FORM)) {
                if (isRegionalForm(pokemonID)) return false;
            }
            if (exclusions.contains(PokemonType.ALTERNATE_FORM)) {
                if (isAlternateForm(pokemonID)) return false;
            }
            if (exclusions.contains(PokemonType.MEGA_EVOLUTION)) {
                if (isMegaEvolution(pokemonID)) return false;
            }
            if (exclusions.contains(PokemonType.GIGANTAMAX)) {
                if (isGigantamaxForm(pokemonID)) return false;
            }
            if (exclusions.contains(PokemonType.VARIANT_FORM)) {
                if (isVariantForm(pokemonID)) return false;
            }
            if (exclusions.contains(PokemonType.NORMAL_FORM)) {
                return !isNormalForm(pokemonID);
            }
        }
        return true;
    }

    public static List<Pokemon> getPokemonsForId(int id) {
        List<Pokemon> pokemons = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pokemon where _id LIKE '" + id + "¬%'");
            while (rs.next()) {
                Pokemon pokemon = new Pokemon(rs.getString("_id"), rs.getInt("real_id"));
                for (Language supportedLanguage : Language.values()) {
                    pokemon.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemons;
    }

    public static Pokemon getPokemonForId(String id) {
        Pokemon pokemon = null;
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM pokemon where _id='%s'", id));
            while (rs.next()) {
                pokemon = new Pokemon(rs.getString("_id"), rs.getInt("real_id"));
                for (Language supportedLanguage : Language.values()) {
                    pokemon.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemon;
    }

    public static List<Pokemon> getAllPokemonWithRange(int start, int end) {
        List<Pokemon> pokemons = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM pokemon WHERE real_id >= %d AND real_id <= %d", start, end));
            while (rs.next()) {
                Pokemon pokemon = new Pokemon(rs.getString("_id"), rs.getInt("real_id"));
                for (Language supportedLanguage : Language.values()) {
                    pokemon.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemons;
    }

    public static List<Ability> getAllAbilities() {
        List<Ability> abilities = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM abilities");
            while (rs.next()) {
                Ability ability = new Ability(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    ability.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                abilities.add(ability);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return abilities;
    }

    public static List<Type> getAllTypes() {
        List<Type> types = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM types");
            while (rs.next()) {
                Type type = new Type(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    type.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                types.add(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }

    public static List<Specie> getAllSpecies() {
        List<Specie> species = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM species");
            while (rs.next()) {
                Specie specie = new Specie(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    specie.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                species.add(specie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return species;
    }

    public static List<Move> getAllMoves() {
        List<Move> moves = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM moves group by name");
            while (rs.next()) {
                Move move = new Move(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    move.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                moves.add(move);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moves;
    }

    public static List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM games");
            while (rs.next()) {
                int generation = rs.getInt("gen");
                Game game = new Game(rs.getString("_id"), generation);
                for (Language supportedLanguage : Language.values()) {
                    game.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public static List<LocationMethod> getAllLocationMethods() {
        List<LocationMethod> locationMethods = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM location_methods");
            while (rs.next()) {
                String mainLocationIndex = rs.getString("_main");
                String secondaryLocationIndex = rs.getString("_secondary");
                LocationMethod locationMethod = new LocationMethod(mainLocationIndex + "¬" + secondaryLocationIndex);
                for (Language supportedLanguage : Language.values()) {
                    StringJoiner joiner = new StringJoiner("¬");
                    joiner.add(rs.getString("main_name" + supportedLanguage.getIsocode()));
                    joiner.add(rs.getString("secondary_name" + supportedLanguage.getIsocode()));
                    locationMethod.setLocalizedName(supportedLanguage, joiner.toString());
                }
                locationMethod.setMainIndex(mainLocationIndex);
                locationMethod.setSecondaryIndex(secondaryLocationIndex);
                locationMethods.add(locationMethod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locationMethods;
    }

    public static List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM items");
            while (rs.next()) {
                Item item = new Item(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    item.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM locations");
            while (rs.next()) {
                Location location = new Location(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    location.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    public static List<Machine> getAllMachines() {
        List<Machine> machines = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT " +
                    "ma._id as ma_id, " +
                    "ma._machine_type as ma_machine_type," +
                    "mo._id as mo_id," +
                    "ma._move as ma_move," +
                    "mo.name as moname, " +
                    "ma._number as ma_number " +
                    "FROM machines as ma, moves as mo where ma._move = mo._id");
            while (rs.next()) {
                Machine machine = new Machine(rs.getString("ma_id"));
                machine.setNumber(rs.getInt("ma_number"));
                MachineTypesEnum machineType = MachineTypesEnum.get(rs.getString("ma_machine_type"));
                Move move = new Move(rs.getString("mo_id"));
                move.setLocalizedName(Language.EN, rs.getString("moname"));
                machine.setMove(move);
                machine.setMachineType(machineType);
                machines.add(machine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return machines;
    }

    public static List<EggGroup> getAllEggGroups() {
        List<EggGroup> eggGroups = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM egg_groups");
            while (rs.next()) {
                EggGroup eggGroup = new EggGroup(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    eggGroup.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                eggGroups.add(eggGroup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eggGroups;
    }

    public static List<Region> getAllRegions() {
        List<Region> regions = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM regions");
            while (rs.next()) {
                Region region = new Region(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    region.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                regions.add(region);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regions;
    }

    public static List<RegionArea> getAllRegionsAreasForRegion(String regionIndex) {
        List<RegionArea> regions = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM region_areas where _region='" + regionIndex + "'");
            while (rs.next()) {
                RegionArea region = new RegionArea(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    region.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                regions.add(region);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regions;
    }

    public static List<PokedexGame> getAllPokedexGames() {
        List<PokedexGame> pokedexGames = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pokedex_games");
            while (rs.next()) {
                PokedexGame pokedexGame = new PokedexGame(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    pokedexGame.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                pokedexGame.setGameIndex(rs.getString("_game"));
                pokedexGame.setNational(rs.getBoolean("national"));
                pokedexGames.add(pokedexGame);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokedexGames;
    }

    public static List<GameVersion> getGameGroupsForGameIndex(String gameIndex) {
        List<GameVersion> gameVersions = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM game_groups where _game = '%s' ORDER BY LENGTH(_game_group) DESC", gameIndex));
            while (rs.next()) {
                gameVersions.add(GameVersion.get(rs.getString("_game_group")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameVersions;
    }

    public static List<GameVersion> getGameIndexesForGameGroup(String gameIndex) {
        List<GameVersion> gameVersions = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM game_groups where _game_group = '%s'", gameIndex));
            while (rs.next()) {
                gameVersions.add(GameVersion.get(rs.getString("_game")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameVersions;
    }

    public static List<PokedexNumber> getNumbersForPokemonForGame(int realID, String gameIndex) {
        List<PokedexNumber> numbers = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT " +
                    "p._id as p_id, p.real_id as preal_id, p.name as pname, " +
                    "pnd._id as pnd_id, pnd._number as pnd_number, pnd._pokedex_game as pnd_pokedex_game, pnd._pokedex as pnd_pokedex" +
                    " FROM poke_numbers_dex as pnd, pokemon as p WHERE p_id= pnd_id AND pnd_id LIKE '" + realID + "¬%' AND pnd_pokedex_game = '" + gameIndex + "'");
            while (rs.next()) {
                Pokemon pokemon = new Pokemon(rs.getString("p_id"), rs.getInt("preal_id"));
                pokemon.setLocalizedName(Language.EN, rs.getString("pname"));
                numbers.add(new PokedexNumber(
                        pokemon,
                        rs.getString("pnd_number"),
                        rs.getString("pnd_pokedex_game"),
                        rs.getString("pnd_pokedex")
                ));
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return numbers;
    }

    public static PokedexNumber getNumberForPokemonForGameForDex(int realID, String gameIndex, String pokedexIndex) {
        PokedexNumber number = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT " +
                    "p._id as p_id, p.real_id as preal_id, p.name as pname, " +
                    "pnd._id as pnd_id, pnd._number as pnd_number, pnd._pokedex_game as pnd_pokedex_game, pnd._pokedex as pnd_pokedex" +
                    " FROM poke_numbers_dex as pnd, pokemon as p WHERE p_id= pnd_id AND pnd_id LIKE '" + realID + "¬%' AND pnd_pokedex_game = '" + gameIndex + "' AND pnd_pokedex = '" + pokedexIndex + "'");
            if (rs.next()) {
                Pokemon pokemon = new Pokemon(rs.getString("p_id"), rs.getInt("preal_id"));
                pokemon.setLocalizedName(Language.EN, rs.getString("pname"));
                number = new PokedexNumber(
                        pokemon,
                        rs.getString("pnd_number"),
                        rs.getString("pnd_pokedex_game"),
                        rs.getString("pnd_pokedex")
                );
            }
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return number;
    }

    public static List<Pokemon> getAllPokemonForTypes(List<PokemonType> types, String selectedBulkPokemonId) {
        String queryFilter = resolveTypes(types, selectedBulkPokemonId);
        List<Pokemon> pokemons = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pokemon WHERE (" + queryFilter + ")");
            while (rs.next()) {
                Pokemon pokemon = new Pokemon(rs.getString("_id"), rs.getInt("real_id"));
                for (Language supportedLanguage : Language.values()) {
                    pokemon.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemons;
    }

    private static String resolveTypes(List<PokemonType> typeOfPokemon, String realID) {
        StringJoiner joiner = new StringJoiner(" OR ");
        for (PokemonType pokemonType : typeOfPokemon) {
            switch (pokemonType) {
                case MEGA_EVOLUTION:
                    joiner.add("_id LIKE '" + realID + "_mega%'");
                    break;
                case GIGANTAMAX:
                    joiner.add("_id LIKE '" + realID + "_gigantamax%'");
                    break;
                case ALOLAN_FORM:
                    joiner.add("_id LIKE '" + realID + "_alola%'");
                    break;
                case GALARIAN_FORM:
                    joiner.add("_id LIKE '" + realID + "_galar%'");
                    break;
                case HISUIAN_FORM:
                    joiner.add("_id LIKE '" + realID + "_hisui%'");
                    break;
                case PALDEAN_FORM:
                    joiner.add("_id LIKE '" + realID + "_paldea%'");
                    break;
                case ALTERNATE_FORM:
                    joiner.add("_id LIKE '" + realID + "_alternate%'");
                    break;
                case NORMAL_FORM:
                    joiner.add("_id LIKE '" + realID + "_normal%'");
                    break;
                case VARIANT_FORM:
                    joiner.add("_id LIKE '" + realID + "_variant%'");
                    break;
            }
        }
        return joiner.toString();
    }

    public static List<Route> getAllRoutesWithCoords() {
        List<Route> routes = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM routes where coordinates NOT NULL");
            while (rs.next()) {
                Route route = new Route(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    route.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                String coords = rs.getString("coordinates");
                String shape = rs.getString("shape");
                if (StringUtils.isNotEmpty(coords) && StringUtils.isNotEmpty(shape)) {
                    route.setCoords(coords);
                    route.setRouteShape(RouteShape.get(Integer.parseInt(shape)));
                }
                routes.add(route);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    public static List<Route> getAllLocationsForRegion(String region) {
        List<Route> routes = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM locations where _region_area='" + region + "'");
            while (rs.next()) {
                Route route = new Route(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    route.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }
                String coords = rs.getString("coordinates");
                String shape = rs.getString("shape");
                if (StringUtils.isNotEmpty(coords) && StringUtils.isNotEmpty(shape)) {
                    route.setCoords(coords);
                    route.setRouteShape(RouteShape.get(Integer.parseInt(shape)));
                }
                routes.add(route);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    public static GenericIndex getGameDescriptionForPoke(String id, String game) {
        GenericIndex gameDescription = null;
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM poke_game_descriptions where _id='" + id + "' AND _game='" + game + "'");
            while (rs.next()) {
                gameDescription = new GenericIndex(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    gameDescription.setLocalizedName(supportedLanguage, rs.getString("description" + supportedLanguage.getIsocode()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameDescription;
    }

    public static GenericIndex getGameLocationForPoke(String id, String game) {
        GenericIndex gameDescription = null;
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM poke_game_locations where _id='" + id + "' AND _game_location='" + game + "'");
            while (rs.next()) {
                gameDescription = new GenericIndex(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    gameDescription.setLocalizedName(supportedLanguage, rs.getString("locations" + supportedLanguage.getIsocode()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameDescription;
    }

    public static GenericIndex getBiologyForPoke(String id) {
        GenericIndex biology = null;
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM poke_biologies where _id='" + id + "'");
            while (rs.next()) {
                biology = new GenericIndex(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    biology.setLocalizedName(supportedLanguage, rs.getString("biology" + supportedLanguage.getIsocode()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return biology;
    }

    public static GenericIndex getEtymologyForPoke(String id) {
        GenericIndex biology = null;
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM poke_etymologies where _id='" + id + "'");
            while (rs.next()) {
                biology = new GenericIndex(rs.getString("_id"));
                for (Language supportedLanguage : Language.values()) {
                    biology.setLocalizedName(supportedLanguage, rs.getString("etymology" + supportedLanguage.getIsocode()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return biology;
    }

    public static Specie getSpeciesForPokemon(String id) {
        Specie specie = null;
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT species FROM pokedex as p where _id='" + id + "'");
            while (rs.next()) {
                specie = new Specie(rs.getString("species"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specie;
    }

    public static List<Pokemon> getAllPokemonFullData() {
        List<Pokemon> pokemons = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pokemon as p, pokedex as dex, poke_evs_yield as evs, poke_egg_groups as peg WHERE p._id = dex._id AND p._id = evs._id AND p._id = peg._id");
            while (rs.next()) {

                Pokemon pokemon = new Pokemon(rs.getString("_id"), rs.getInt("real_id"));
                for (Language supportedLanguage : Language.values()) {
                    pokemon.setLocalizedName(supportedLanguage, rs.getString("name" + supportedLanguage.getIsocode()));
                }

                Pokedex pokedex = new Pokedex();

                PokedexData pokedexData = new PokedexData();
                pokedexData.setId(rs.getString("_id"));
                pokedexData.setHeightMeters(rs.getDouble("height_meters"));
                pokedexData.setWeightKilograms(rs.getDouble("weight_kilograms"));
                pokedexData.setSpecies(rs.getString("species"));

                Breeding breeding = new Breeding();
                breeding.setEggGroups(new ImmutablePair<>(rs.getString("group_1"), rs.getString("group_2")));
                breeding.setGenders(new ImmutablePair<>(rs.getString("male_rate"), rs.getString("female_rate")));
                breeding.setEggCycles(rs.getString("egg_steps"));

                Training training = new Training();
                training.setGrowthRate(rs.getString("growth"));
                training.setCatchRate(rs.getString("catch_rate"));
                training.setBaseFriendship(rs.getString("base_friendship"));
                training.setBaseExp(rs.getString("base_exp"));

                StringJoiner joiner = new StringJoiner(",");
                joiner.add(MoreObjects.firstNonNull(rs.getString("hp"), "0"));
                joiner.add(MoreObjects.firstNonNull(rs.getString("atk"), "0"));
                joiner.add(MoreObjects.firstNonNull(rs.getString("def"), "0"));
                joiner.add(MoreObjects.firstNonNull(rs.getString("spatk"), "0"));
                joiner.add(MoreObjects.firstNonNull(rs.getString("spdef"), "0"));
                joiner.add(MoreObjects.firstNonNull(rs.getString("speed"), "0"));

                pokedex.setPokedexData(pokedexData);
                pokedex.setBreeding(breeding);
                pokedex.setTraining(training);

                pokemon.setPokedex(pokedex);
                pokemon.setEvs(joiner.toString());

                pokemons.add(pokemon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pokemons;
    }

    public Integer getCountForMovesForPokemonForGen(String pokemonId, Game game, MovesClassification movesClassification) throws SQLException {
        Integer count = null;
        String generation = String.valueOf(game.getGeneration());
        String gameIndex = game.getIndexNumber();
        switch (movesClassification) {
            case BY_LEVEL:
                count = getAllLearnSetMovesForPokemonForGenForGame(pokemonId, generation, gameIndex);
                break;
            case BY_TUTOR:
                count = getAllTutorsMovesForPokemonForGenForGame(pokemonId, generation, gameIndex);
                break;
            case BY_TM_HM_TR:
                count = getAllTechnicalAndHiddenMachinesIdForPokemonForGenForGame(pokemonId, generation, gameIndex);
                break;
            case BY_EGGMOVE:
                count = getAllEggMovesForPokemonForGenForGame(pokemonId, generation, gameIndex);
                break;
            case BY_TRANSFER:
                count = getAllTransferMovesForPokemonForGen(pokemonId, generation);
                break;
            case BY_EVENT:
                count = getAllEventMovesForPokemonForGen(pokemonId, generation);
                break;
        }
        return count;
    }

    public static int getAllLearnSetMovesForPokemonForGenForGame(String pokemonId, String generation, String gameIndex) throws SQLException {
        Connection conn = DriverManager.getConnection(DATABASE_URL);
        Statement stmt = conn.createStatement();
        String query = "SELECT COUNT(*) AS recordCount FROM poke_learnset_moves WHERE " +
                "_gen =" + generation + " AND \n " +
                "(_game_exclusive IS NULL OR _game_exclusive LIKE '" + gameIndex + "¬%' OR _game_exclusive LIKE '%¬" + gameIndex + "¬%' OR _game_exclusive LIKE '%¬" + gameIndex + "%' OR _game_exclusive ='" + gameIndex + "') AND \n" +
                "_id ='" + pokemonId + "'";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int count = rs.getInt("recordCount");
        rs.close();
        return count;
    }

    public static int getAllTutorsMovesForPokemonForGenForGame(String pokemonId, String generation, String gameIndex) throws SQLException {
        Connection conn = DriverManager.getConnection(DATABASE_URL);
        Statement stmt = conn.createStatement();
        String query = "SELECT COUNT(*) AS recordCount FROM poke_tutor_moves WHERE " +
                "_gen =" + generation + " AND \n " +
                "(_game_exclusive IS NULL OR _game_exclusive LIKE '" + gameIndex + "¬%' OR _game_exclusive LIKE '%¬" + gameIndex + "¬%' OR _game_exclusive LIKE '%¬" + gameIndex + "%' OR _game_exclusive ='" + gameIndex + "') AND \n" +
                "_id ='" + pokemonId + "'";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int count = rs.getInt("recordCount");
        rs.close();
        return count;
    }

    public static int getAllTechnicalAndHiddenMachinesIdForPokemonForGenForGame(String pokemonId, String generation, String gameIndex) throws SQLException {
        Connection conn = DriverManager.getConnection(DATABASE_URL);
        Statement stmt = conn.createStatement();
        String query = "SELECT COUNT(*) AS recordCount FROM poke_tm_hm_tr_moves WHERE " +
                "_gen =" + generation + " AND \n " +
                "(_game_exclusive IS NULL OR _game_exclusive LIKE '" + gameIndex + "¬%' OR _game_exclusive LIKE '%¬" + gameIndex + "¬%' OR _game_exclusive LIKE '%¬" + gameIndex + "%' OR _game_exclusive ='" + gameIndex + "') AND \n" +
                "_id ='" + pokemonId + "'";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int count = rs.getInt("recordCount");
        rs.close();
        return count;
    }

    public static int getAllEggMovesForPokemonForGenForGame(String pokemonId, String generation, String gameIndex) throws SQLException {
        Connection conn = DriverManager.getConnection(DATABASE_URL);
        Statement stmt = conn.createStatement();
        String query = "SELECT COUNT(*) AS recordCount FROM poke_egg_moves WHERE " +
                "_gen =" + generation + " AND \n " +
                "(_game_exclusive IS NULL OR _game_exclusive LIKE '" + gameIndex + "¬%' OR _game_exclusive LIKE '%¬" + gameIndex + "¬%' OR _game_exclusive LIKE '%¬" + gameIndex + "%' OR _game_exclusive ='" + gameIndex + "') AND \n" +
                "_id ='" + pokemonId + "'";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int count = rs.getInt("recordCount");
        rs.close();
        return count;
    }

    public static int getAllTransferMovesForPokemonForGen(String pokemonId, String generation) throws SQLException {
        Connection conn = DriverManager.getConnection(DATABASE_URL);
        Statement stmt = conn.createStatement();
        String query = "SELECT COUNT(*) AS recordCount FROM poke_transfer_only_moves WHERE " +
                "_gen =" + generation + " AND \n " +
                "_id ='" + pokemonId + "'";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int count = rs.getInt("recordCount");
        rs.close();
        return count;
    }

    public static int getAllEventMovesForPokemonForGen(String pokemonId, String generation) throws SQLException {
        Connection conn = DriverManager.getConnection(DATABASE_URL);
        Statement stmt = conn.createStatement();
        String query = "SELECT COUNT(*) AS recordCount FROM poke_event_moves WHERE " +
                "_gen =" + generation + " AND \n " +
                "_id ='" + pokemonId + "'";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int count = rs.getInt("recordCount");
        rs.close();
        return count;
    }

    public static List<Game> getAllGamesAvailableForPokemon(String id) throws SQLException {
        Connection conn = DriverManager.getConnection(DATABASE_URL);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT DISTINCT gg._game as g_id ,pnd._pokedex as pnd_pokedex, g.name as gname , g.gen as ggen, " +
                nestedMainGameQuery +
                " FROM games as g ,poke_numbers_dex as pnd, game_groups as gg \n" +
                " WHERE gg._game_group = pnd._pokedex_game  " +
                " AND  gg._game = g._id " +
                " AND pnd._id ='" + id + "' " +
                " ORDER BY g.gen");

        List<Game> games = new ArrayList<>();
        while (rs.next()) {
            Game game = new Game(
                    rs.getString("g_id"),
                    rs.getString("gname"),
                    rs.getInt("ggen"),
                    rs.getString("main_game"));
            PokedexGame pokedexGame = new PokedexGame(rs.getString("pnd_pokedex"));
            game.setSubGames(Collections.singletonList(pokedexGame));
            games.add(game);
        }
        rs.close();

        return games;
    }

}


