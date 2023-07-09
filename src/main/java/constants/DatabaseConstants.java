package constants;

import java.io.File;

public class DatabaseConstants {

    public static final String DATABASE_NAME_URL = "jdbc:sqlite:pokemon.db";
    public static final String INSERT_REPLACE = "INSERT_REPLACE";
    public static final String UPDATE = "UPDATE";
    public static final String POKE_BIOLOGY_TABLE = "poke_biology";
    public static final String POKEMON_TABLE = "pokemon";
    public static final String COLORS = "colors";
    public static final String COLORS_DARK = "colors_dark";
    public static final String POKE_ETYMOLOGY_TABLE = "poke_etymology";
    public static final String ITEM_TABLE = "items";
    public static final String ITEM_LOCATIONS_TABLE = "item_game_locations";
    public static final String ITEM_GAME_DESCRIPTIONS_TABLE = "item_game_descriptions";
    public static final String MACHINES_TABLE = "machines";
    public static final String ITEM_CATEGORIES_TABLE = "item_categories";
    public static final String SMOGON_CATEGORIES_TABLE = "smogon_categories";
    public static final String MOVE_CONTEST_TYPES_TABLE = "move_contest_types";
    public static final String POKEDEX_GAMES_TABLE = "pokedex_games";
    public static final String MOVE_CATEGORIES_TABLE = "move_categories";
    public static final String EFFECTIVENESS_TABLE = "effectiveness";
    public static final String EGG_GROUPS_TABLE = "egg_groups";
    public static final String GAMES_TABLE = "games";
    public static final String GAME_GROUPS_TABLE = "game_groups";
    public static final String POKE_LOCATIONS_TABLE = "poke_locations";
    public static final String POKE_DESCRIPTIONS_TABLE = "poke_descriptions";
    public static final String POKE_ABILITIES_TABLE = "poke_abilities";
    public static final String POKE_LEARNSET_MOVES_TABLE = "poke_learnset_moves";
    public static final String POKE_TM_HM_TR_MOVES_TABLE = "poke_tm_hm_tr_moves";
    public static final String POKE_TUTOR_MOVES_TABLE = "poke_tutor_moves";
    public static final String POKE_EVENT_MOVES_TABLE = "poke_event_moves";
    public static final String POKE_EGG_MOVES_TABLE = "poke_egg_moves";
    public static final String POKE_TRANSFER_ONLY_MOVES_TABLE = "poke_transfer_only_moves";
    public static final String BASESTATS_TABLE = "basestats";
    public static final String POKE_EVOLUTIONS_TABLE = "poke_evolutions";
    public static final String POKE_CHANGES_TABLE = "poke_changes";
    public static final String MOVES_TABLE = "moves";
    public static final String BUILD_PRESETS = "build_presets";
    public static final String LOCATIONS_TABLE = "locations";
    public static final String CONNECTED_LOCATIONS_TABLE = "connected_locations";
    public static final String ITEM_GAME_DETAILED_LOCATIONS_TABLE = "item_game_detailed_locations";
    public static final String AREAS_TABLE = "areas";
    public static final String LOCATION_METHODS_TABLE = "location_methods";
    public static final String POKE_GAME_DETAILED_LOCATIONS_TABLE = "poke_game_detailed_locations";
    public static final String NATURES_TABLE = "natures";
    public static final String POKE_GROUPS_TABLE = "poke_groups";
    public static final String EVOLUTION_CONDITIONS_TABLE = "evolution_conditions";
    public static final String GENDERS_TABLE = "genders";
    public static final String TIMES_OF_DAY_TABLE = "times_of_day";
    public static final String SEASONS_TABLE = "seasons";
    public static final String REGIONS_TABLE = "regions";
    public static final String REGION_AREAS_TABLE = "region_areas";
    public static final String GAME_REGIONS_TABLE = "game_regions";
    public static final String POKEDEX_NUMBERS_TABLE = "poke_numbers_dex";
    public static final String TYPES_TABLE = "types";
    public static final String POKEDEX_EVS_TABLE = "poke_evs_yield";
    public static final String POKEDEX_EGG_GROUPS_TABLE = "poke_egg_groups";
    public static final String POKEDEX_TABLE = "pokedex";
    public static final String SPECIES_TABLE = "species";
    public static final String POKE_TYPES_TABLE = "poke_types";
    public static final String ABILITIES_TABLE = "abilities";
    public static final String ES_LANG = "es";
    public static final String EN_LANG = "en";

    public static final String SEPARATOR = File.separator;
    public static final String ROOT_FOLDER = ".";
    public static final String SRC_FOLDER = "src";
    public static final String MAIN_FOLDER = "main";
    public static final String RESOURCES_FOLDER = "resources";
    public static final String COREDATA_FOLDER = "coredata";
    public static final String SAMPLEDATA_FOLDER = "sampledata";
    public static final String MIGRATION_FOLDER = "migration";
    public static final String CONTRIBUTION_TRANSLATIONS_FOLDER = "contribution_translations";
    public static final String MANUAL_FOLDER = "manual";
    public static final String IMAGES_FOLDER = "images";
    public static final String SUGIMORI_FOLDER = "sugimori";
    public static final String SHINY_FOLDER = "shiny";
    public static final String NORMAL_FOLDER = "normal";
    public static final String ICONS_FOLDER = "icons";
    public static final String ICONS_SPRITE_FOLDER = "icons_sprite";
    public static final String POKEMON_FOLDER = "pokemon";
    public static final String MINI_FOLDER = "mini";
    public static final String MINI_HD_FOLDER = "mini-hd";
    public static final String TRAINER_FOLDER = "trainer";
    public static final String SPRITES_FOLDER = "sprites";
    public static final String ANIMATED_FOLDER = "animated";

    public static final String COREDATA = COREDATA_FOLDER + SEPARATOR;
    public static final String SAMPLEDATA = SAMPLEDATA_FOLDER + SEPARATOR;
    public static final String MIGRATION = MIGRATION_FOLDER + SEPARATOR;
    public static final String CONTRIBUTION_TRANSLATIONS = CONTRIBUTION_TRANSLATIONS_FOLDER + SEPARATOR;
    public static final String MANUAL = MANUAL_FOLDER + SEPARATOR;

    public static final String SCHEMA_URL = COREDATA + "schema" + SEPARATOR + "Schema-Modification.txt";
    public static final String EFFECTIVENESS_URL = COREDATA + EFFECTIVENESS_TABLE + SEPARATOR + "INSERT-REPLACE-Effectiveness.txt";
    public static final String EGG_GROUPS_URL = COREDATA + EGG_GROUPS_TABLE + SEPARATOR + "INSERT-REPLACE-EggGroups.txt";
    public static final String NATURES_URL = COREDATA + NATURES_TABLE + SEPARATOR + "INSERT-REPLACE-Natures.txt";
    public static final String POKE_GROUPS_URL = COREDATA + POKE_GROUPS_TABLE + SEPARATOR + "INSERT-REPLACE-Poke_Groups.txt";
    public static final String EVOLUTION_CONDITIONS_URL = COREDATA + EVOLUTION_CONDITIONS_TABLE + SEPARATOR + "INSERT-REPLACE-Evolution-Conditions.txt";
    public static final String GENDERS_URL = COREDATA + GENDERS_TABLE + SEPARATOR + "INSERT-REPLACE-Genders.txt";
    public static final String TIMES_OF_DAY_URL = COREDATA + TIMES_OF_DAY_TABLE + SEPARATOR + "INSERT-REPLACE-Times-Of-Day.txt";
    public static final String SEASONS_URL = COREDATA + SEASONS_TABLE + SEPARATOR + "INSERT-REPLACE-Seasons.txt";
    public static final String REGIONS_URL = COREDATA + REGIONS_TABLE + SEPARATOR + "INSERT-REPLACE-Regions.txt";
    public static final String REGION_AREAS_URL = COREDATA + REGION_AREAS_TABLE + SEPARATOR + "INSERT-REPLACE-Region-Areas.txt";
    public static final String GAME_REGIONS_URL = COREDATA + GAME_REGIONS_TABLE + SEPARATOR + "INSERT-REPLACE-Game-Regions.txt";
    public static final String GAMES_URL = COREDATA + GAMES_TABLE + SEPARATOR + "INSERT-REPLACE-Games.txt";
    public static final String GAME_GROUPS_URL = COREDATA + GAME_GROUPS_TABLE + SEPARATOR + "INSERT-REPLACE-Game-Groups.txt";
    public static final String MOVE_CATEGORIES_URL = COREDATA + MOVE_CATEGORIES_TABLE + SEPARATOR + "INSERT-REPLACE-MoveCategories.txt";
    public static final String ITEM_CATEGORIES_URL = COREDATA + ITEM_CATEGORIES_TABLE + SEPARATOR + "INSERT-REPLACE-ItemCategories.txt";
    public static final String SMOGON_CATEGORIES_URL = COREDATA + SMOGON_CATEGORIES_TABLE + SEPARATOR + "INSERT-REPLACE-SmogonCategories.txt";
    public static final String MOVE_CONTEST_TYPES_URL = COREDATA + MOVE_CONTEST_TYPES_TABLE + SEPARATOR + "INSERT-REPLACE-MoveContestTypes.txt";
    public static final String POKEDEX_GAMES_URL = COREDATA + POKEDEX_GAMES_TABLE + SEPARATOR + "INSERT-REPLACE-Pokedex_Games.txt";
    public static final String POKEMON_GIGANTAMAX_URL = COREDATA + POKEMON_TABLE + SEPARATOR + "I-Gigantamax-Into-Pokemon-Table.txt";
    public static final String POKEMON_URL = COREDATA + POKEMON_TABLE + SEPARATOR + "INSERT_REPLACE_pokemon.txt";
    public static final String POKE_BASE_STATS_URL = COREDATA + POKEMON_TABLE + SEPARATOR + "INSERT_REPLACE_basestats.txt";
    public static final String COLORS_URL = COREDATA + POKEMON_TABLE + SEPARATOR + "UPDATE_colors.txt";
    public static final String COLORS_DARK_URL = COREDATA + POKEMON_TABLE + SEPARATOR + "UPDATE_colors_dark.txt";
    public static final String MOVES_URL = COREDATA + MOVES_TABLE + SEPARATOR + "INSERT_REPLACE_moves_en.txt";
    public static final String BUILD_PRESETS_URL = COREDATA + BUILD_PRESETS + SEPARATOR + "INSERT-REPLACE-Build-Presets.txt";
    public static final String TYPES_URL = COREDATA + TYPES_TABLE + SEPARATOR + "INSERT-REPLACE-Types.txt";

    public static final String[] TRAINER_ICONS_FOLDER = new String[]{ROOT_FOLDER, SAMPLEDATA_FOLDER, IMAGES_FOLDER, TRAINER_FOLDER};
    public static final String[] ITEM_ICONS_FOLDER = new String[]{ROOT_FOLDER, SAMPLEDATA_FOLDER, IMAGES_FOLDER, ICONS_FOLDER};
    public static final String[] ITEM_SPRITE_ICONS_FOLDER = new String[]{ROOT_FOLDER, SAMPLEDATA_FOLDER, IMAGES_FOLDER, ICONS_SPRITE_FOLDER};
    public static final String[] MINI_POKE_IMAGES_FOLDER = new String[]{ROOT_FOLDER, SAMPLEDATA_FOLDER, IMAGES_FOLDER, POKEMON_FOLDER, MINI_FOLDER};
    public static final String[] MINI_HD_POKE_IMAGES_FOLDER = new String[]{ROOT_FOLDER, SAMPLEDATA_FOLDER, IMAGES_FOLDER, POKEMON_FOLDER, MINI_HD_FOLDER};
    public static final String[] NORMAL_POKE_IMAGES_FOLDER = new String[]{ROOT_FOLDER, SAMPLEDATA_FOLDER, IMAGES_FOLDER, POKEMON_FOLDER, SUGIMORI_FOLDER, NORMAL_FOLDER};
    public static final String[] SHINY_POKE_IMAGES_FOLDER = new String[]{ROOT_FOLDER, SAMPLEDATA_FOLDER, IMAGES_FOLDER, POKEMON_FOLDER, SUGIMORI_FOLDER, SHINY_FOLDER};

    private DatabaseConstants() {
        //private constructor for constants class
    }
}
