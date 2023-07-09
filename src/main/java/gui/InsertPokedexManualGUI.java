package gui;

import database.Database;
import gui.custom.AbstactCustomApplication;
import gui.custom.AutoCompleteBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import pojos.*;
import utils.PokemonSeleniumUtils;
import utils.Sanitizer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static constants.DatabaseConstants.*;
import static utils.Sanitizer.sanitizeDefault;

public class InsertPokedexManualGUI extends AbstactCustomApplication {

    public static final int DEFAULT_WIDTH = 75;

    private static Pokemon selectedPokemon;
    private static Move move;
    private static Game game;
    private static String selectedMoveGroup;

    private static List<Type> types = Database.getAllTypes();
    private static List<Specie> species = Database.getAllSpecies();
    private static List<Ability> abilities = Database.getAllAbilities();
    private static List<EggGroup> eggGroups = Database.getAllEggGroups();

    private List<InsertPokedexRow> pokemonRows = new ArrayList<>();

    @FXML
    private Label errorText;

    @FXML
    private ComboBox<Pokemon> cbPokemon;
    private AutoCompleteBox acbPokemon;

    @FXML
    private TextField tfNumberVariants, tfID;

    @FXML
    private VBox pokemonRowsContainer;

    @FXML
    protected void generateInsertStatement() {
        for (InsertPokedexRow insertPokemonRow : pokemonRows) {
            boolean insertedEvs = generateEVS(insertPokemonRow);
            boolean insertedPokemon = generatePokemon(insertPokemonRow);
            boolean insertedAbilities = generateAbilities(insertPokemonRow);
            boolean insertedEggGroups = generateEggGroups(insertPokemonRow);
            boolean insertedBiologies = generateBiologies(insertPokemonRow);
            boolean insertedEtymologies = generateEtymologies(insertPokemonRow);
            if (insertedEvs && insertedPokemon && insertedAbilities && insertedEggGroups && insertedBiologies && insertedEtymologies) {
                errorText.setText("Valid!");
                errorText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            } else {
                errorText.setText("Invalid!");
                errorText.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            }
        }
    }

    private boolean generateEggGroups(InsertPokedexRow insertPokemonRow) {
        boolean inserted = false;
        try {
            String id = insertPokemonRow.getTvID().getText();
            String eggGroup1 = Optional.ofNullable(insertPokemonRow.getEggGroup1().getSelectionModel().getSelectedItem().getIndexNumber()).orElseGet(null);
            String eggGroup2 = Optional.ofNullable(insertPokemonRow.getEggGroup1().getSelectionModel().getSelectedItem().getIndexNumber()).orElseGet(null);
            PokemonSeleniumUtils.insertToDatabase(
                    generateInsertStatementForPokemonEggGroups(id, new ImmutablePair<>(eggGroup1, eggGroup2)),
                    PokemonSeleniumUtils.generateStatement(MANUAL + id));
            inserted = true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return inserted;
    }

    private boolean generateBiologies(InsertPokedexRow insertPokemonRow) {
        boolean inserted = false;
        try {
            String id = insertPokemonRow.getTvID().getText();
            String bioES = insertPokemonRow.getTvBiologyEs().getText();
            String bioEN = insertPokemonRow.getTvBiologyEn().getText();
            if (StringUtils.isNotEmpty(bioES) && StringUtils.isNotEmpty(bioEN)) {
                PokemonSeleniumUtils.insertToDatabase(
                        generateStatementForBiologies(id, bioEN, bioES),
                        PokemonSeleniumUtils.generateStatement(MANUAL + id));
            }
            inserted = true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return inserted;
    }

    private boolean generateEtymologies(InsertPokedexRow insertPokemonRow) {
        boolean inserted = false;
        try {
            String id = insertPokemonRow.getTvID().getText();
            String etyES = insertPokemonRow.getTvEtymologyEs().getText();
            String etyEN = insertPokemonRow.getTvEtymologyEn().getText();
            if (StringUtils.isNotEmpty(etyES) && StringUtils.isNotEmpty(etyEN)) {
                PokemonSeleniumUtils.insertToDatabase(
                        generateStatementForEtymologies(id, etyEN, etyES),
                        PokemonSeleniumUtils.generateStatement(MANUAL + id));
            }
            inserted = true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return inserted;
    }

    private boolean generateAbilities(InsertPokedexRow insertPokemonRow) {
        boolean inserted = false;
        try {
            String id = insertPokemonRow.getTvID().getText();

            Ability ability1 = insertPokemonRow.getAbility1().getValue();
            String firstAbility = null;
            if (Objects.nonNull(ability1)) {
                firstAbility = ability1.getIndexNumber();
            }

            Ability ability2 = insertPokemonRow.getAbility2().getValue();
            String secondAbility = null;
            if (Objects.nonNull(ability2)) {
                secondAbility = ability2.getIndexNumber();
            }


            Ability hidden = insertPokemonRow.getHiddenAbility().getValue();
            String hiddenAbility = null;
            if (Objects.nonNull(hidden)) {
                hiddenAbility = hidden.getIndexNumber();
            }

            PokemonSeleniumUtils.insertToDatabase(
                    generateInsertStatement(id, firstAbility, secondAbility, hiddenAbility),
                    PokemonSeleniumUtils.generateStatement(MANUAL + id));
            inserted = true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return inserted;
    }

    private boolean generatePokemon(InsertPokedexRow insertPokemonBasicDataRow) {
        boolean inserted = false;
        try {
            String id = insertPokemonBasicDataRow.getTvID().getText();
            String species = Optional.ofNullable(insertPokemonBasicDataRow.getSpecies().getSelectionModel().getSelectedItem().getIndexNumber()).orElseGet(null);
            String heightMeters = insertPokemonBasicDataRow.getTvHeightMeters().getText();
            String heightFeet = insertPokemonBasicDataRow.getTvHeightFeet().getText();
            String weightKg = insertPokemonBasicDataRow.getTvWeightKg().getText();
            String weightPounds = insertPokemonBasicDataRow.getTvWeightPounds().getText();

            String maleRate = insertPokemonBasicDataRow.getTvMaleRate().getText();
            String femaleRate = insertPokemonBasicDataRow.getTvFemaleRate().getText();

            String eggCycles = insertPokemonBasicDataRow.getTvEggSteps().getText();
            String eggGroup1 = Optional.ofNullable(insertPokemonBasicDataRow.getEggGroup1().getSelectionModel().getSelectedItem().getIndexNumber()).orElseGet(null);
            String eggGroup2 = Optional.ofNullable(insertPokemonBasicDataRow.getEggGroup2().getSelectionModel().getSelectedItem().getIndexNumber()).orElseGet(null);

            String baseXP = insertPokemonBasicDataRow.getTvBaseExp().getText();
            String baseFriendship = insertPokemonBasicDataRow.getTvBaseFriendship().getText();
            String catchRate = insertPokemonBasicDataRow.getTvCatchRate().getText();
            String growthRate = insertPokemonBasicDataRow.getTvGrowth().getText();

            Pokedex pokedex = new Pokedex();

            PokedexData pokedexData = new PokedexData();
            pokedexData.setId(id);
            pokedexData.setSpecies(species);
            pokedexData.setHeightMeters(Double.valueOf(heightMeters));
            pokedexData.setHeightFeet(Double.valueOf(heightFeet));
            pokedexData.setWeightKilograms(Double.valueOf(weightKg));
            pokedexData.setWeightPounds(Double.valueOf(weightPounds));

            Breeding breeding = new Breeding();
            breeding.setGenders(new ImmutablePair<>(maleRate, femaleRate));
            breeding.setEggCycles(eggCycles);
            breeding.setEggGroups(new ImmutablePair<>(eggGroup1, eggGroup2));

            Training training = new Training();
            training.setBaseExp(baseXP);
            training.setBaseFriendship(baseFriendship);
            training.setCatchRate(catchRate);
            training.setGrowthRate(growthRate);

            pokedex.setPokedexData(pokedexData);
            pokedex.setBreeding(breeding);
            pokedex.setTraining(training);

            PokemonSeleniumUtils.insertToDatabase(
                    generateInsertStatementForPokedex(pokedex),
                    PokemonSeleniumUtils.generateStatement(MANUAL + id));
            inserted = true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return inserted;
    }

    private boolean generateEVS(InsertPokedexRow insertPokedexRow) {
        boolean inserted = false;
        try {
            String id = insertPokedexRow.getTvID().getText();
            String[] parts = insertPokedexRow.getTvEVS().getText().split(",");
            Integer hp = Integer.parseInt(parts[0]) == 0 ? null : Integer.parseInt(parts[0]);
            Integer atk = Integer.parseInt(parts[1]) == 0 ? null : Integer.parseInt(parts[1]);
            Integer def = Integer.parseInt(parts[2]) == 0 ? null : Integer.parseInt(parts[2]);
            Integer spatk = Integer.parseInt(parts[3]) == 0 ? null : Integer.parseInt(parts[3]);
            Integer spdef = Integer.parseInt(parts[4]) == 0 ? null : Integer.parseInt(parts[4]);
            Integer speed = Integer.parseInt(parts[5]) == 0 ? null : Integer.parseInt(parts[5]);
            PokemonSeleniumUtils.insertToDatabase(
                    generateInsertStatement(id,
                            hp,
                            atk,
                            def,
                            spatk,
                            spdef,
                            speed),
                    PokemonSeleniumUtils.generateStatement(MANUAL + id));
            inserted = true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return inserted;
    }

    @FXML
    protected void clear() {
        pokemonRows.clear();
        pokemonRowsContainer.getChildren().clear();
    }


    @FXML
    protected void create() {
        try {
            Integer inputID = getIDFromInput();
            if (Objects.nonNull(inputID)) {
                selectedPokemon = new Pokemon(inputID + "¬normal¬0", inputID);
            }
            if (Objects.nonNull(selectedPokemon)) {
                Integer id = selectedPokemon.getRealID();
                int numberOfVariants = Integer.parseInt(tfNumberVariants.getText());
                for (int i = 0; i < numberOfVariants; i++) {
                    String variantID = id + "¬variant¬" + i;
                    createInsertionRow(variantID);
                }
            }
        } catch (NumberFormatException ex) {
            errorText.setText("Invalid!");
            errorText.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        }
    }

    private Integer getIDFromInput() {
        Integer id = null;
        try {
            id = Integer.parseInt(tfID.getText());
        } catch (Exception ex) {
        }
        return id;
    }

    public void createInsertionRow(String variantID) {
        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPosition(1, 0.1);
        InsertPokedexRow pokedexRow = createPokedexContainer(variantID, selectedPokemon);
        pokemonRowsContainer.getChildren().addAll(splitPane, pokedexRow.getMainContainer());
        pokemonRows.add(pokedexRow);
    }


    private InsertPokedexRow createPokedexContainer(String pokeID, Pokemon selectedPokemon) {

        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(15);

        TextField tvID = (TextField) instantiate(TextField.class);
        tvID.setText(pokeID);

        TextField tvBiologyEn = (TextField) instantiate(TextField.class);
        TextField tvBiologyEs = (TextField) instantiate(TextField.class);
        TextField tvEtymologyEn = (TextField) instantiate(TextField.class);
        TextField tvEtymologyEs = (TextField) instantiate(TextField.class);

        TextField tvHeightMeters = (TextField) instantiate(TextField.class);
        //tvHeightMeters.setText(String.valueOf(selectedPokemon.getPokedex().getPokedexData().getHeightMeters()));

        TextField tvHeightFeet = (TextField) instantiate(TextField.class);
        //tvHeightFeet.setText(String.valueOf(selectedPokemon.getPokedex().getPokedexData().getHeightFeet()));

        TextField tvWeightKg = (TextField) instantiate(TextField.class);
        //tvWeightKg.setText(String.valueOf(selectedPokemon.getPokedex().getPokedexData().getWeightKilograms()));

        TextField tvWeightPounds = (TextField) instantiate(TextField.class);
        //tvWeightPounds.setText(String.valueOf(selectedPokemon.getPokedex().getPokedexData().getWeightPounds()));

        TextField tvCatchRate = (TextField) instantiate(TextField.class);
        //tvCatchRate.setText(String.valueOf(selectedPokemon.getPokedex().getTraining().getCatchRate()));

        TextField tvBaseFriendship = (TextField) instantiate(TextField.class);
        //tvBaseFriendship.setText(String.valueOf(selectedPokemon.getPokedex().getTraining().getBaseFriendship()));

        TextField tvBaseExp = (TextField) instantiate(TextField.class);
        //tvBaseExp.setText(String.valueOf(selectedPokemon.getPokedex().getTraining().getBaseExp()));

        TextField tvGrowth = (TextField) instantiate(TextField.class);
        //tvGrowth.setText(String.valueOf(selectedPokemon.getPokedex().getTraining().getGrowthRate()));

        TextField tvEggSteps = (TextField) instantiate(TextField.class);
        //tvEggSteps.setText(String.valueOf(selectedPokemon.getPokedex().getBreeding().getEggCycles()));

        TextField tvMaleRate = (TextField) instantiate(TextField.class);
        //tvMaleRate.setText(String.valueOf(selectedPokemon.getPokedex().getBreeding().getGenders().getLeft()));

        TextField tvFemaleRate = (TextField) instantiate(TextField.class);
        //tvFemaleRate.setText(String.valueOf(selectedPokemon.getPokedex().getBreeding().getGenders().getRight()));


        ComboBox<Specie> cbSpecies = (ComboBox) instantiate(ComboBox.class);
        cbSpecies.setItems(FXCollections.observableArrayList(species));
        //Specie matchingSpecie = species.stream().filter(specie -> specie.getIndexNumber().equals(selectedPokemon.getPokedex().getPokedexData().getSpecies())).findAny().orElse(null);
        //if (Objects.nonNull(matchingSpecie)) {
        //    cbSpecies.setValue(matchingSpecie);
        //}

        ComboBox<EggGroup> cbEggGroup1 = (ComboBox) instantiate(ComboBox.class);
        cbEggGroup1.setItems(FXCollections.observableArrayList(eggGroups));
        //EggGroup matchingEgg1 = eggGroups.stream().filter(eggGroup -> eggGroup.getIndexNumber().equals(selectedPokemon.getPokedex().getBreeding().getEggGroups().getLeft())).findAny().orElse(null);
        //if (Objects.nonNull(matchingEgg1)) {
        //    cbEggGroup1.setValue(matchingEgg1);
        //}

        ComboBox<EggGroup> cbEggGroup2 = (ComboBox) instantiate(ComboBox.class);
        cbEggGroup2.setItems(FXCollections.observableArrayList(eggGroups));
        //EggGroup matchingEgg2 = eggGroups.stream().filter(eggGroup -> eggGroup.getIndexNumber().equals(selectedPokemon.getPokedex().getBreeding().getEggGroups().getRight())).findAny().orElse(null);
        //if (Objects.nonNull(matchingEgg2)) {
        //    cbEggGroup2.setValue(matchingEgg2);
        //}

        ComboBox<Ability> cbAbility1 = (ComboBox) instantiate(ComboBox.class);
        cbAbility1.setItems(FXCollections.observableArrayList(abilities));

        ComboBox<Ability> cbAbility2 = (ComboBox) instantiate(ComboBox.class);
        cbAbility2.setItems(FXCollections.observableArrayList(abilities));

        ComboBox<Ability> cbHiddenAbility = (ComboBox) instantiate(ComboBox.class);
        cbHiddenAbility.setItems(FXCollections.observableArrayList(abilities));

        TextField tvEVS = (TextField) instantiate(TextField.class);
        tvEVS.setText(selectedPokemon.getEvs());

        container.getChildren().addAll(tvID, tvBiologyEn, tvBiologyEs, tvEtymologyEn, tvEtymologyEs, tvHeightMeters, tvHeightFeet, tvWeightKg, tvWeightPounds, tvCatchRate, tvBaseFriendship, tvBaseExp, tvGrowth, tvEggSteps, tvMaleRate, tvFemaleRate, cbSpecies, cbEggGroup1, cbEggGroup2, cbAbility1, cbAbility2, cbHiddenAbility, tvEVS);

        return new InsertPokedexRow(tvID, container, tvBiologyEn, tvBiologyEs, tvEtymologyEn, tvEtymologyEs, tvHeightMeters, tvHeightFeet, tvWeightKg, tvWeightPounds, tvCatchRate, tvBaseFriendship, tvBaseExp, tvGrowth, tvEggSteps, tvMaleRate, tvFemaleRate, cbSpecies, cbEggGroup1, cbEggGroup2, cbAbility1, cbAbility2, cbHiddenAbility, tvEVS);
    }

    private javafx.scene.layout.Region instantiate(Class clazz) {
        javafx.scene.layout.Region region = null;
        try {
            region = (javafx.scene.layout.Region) clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        region.setMinWidth(DEFAULT_WIDTH);
        return region;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadStage("insert-pokedex-gui.fxml", this);
        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("Hello!");
        initializePokemonComboBox();
        stage.setScene(scene);
        pokemonRowsContainer.setSpacing(60);
        VBox.setMargin(pokemonRowsContainer, new Insets(0, 0, 0, 50));

        stage.show();
    }


    private void initializePokemonComboBox() {
        cbPokemon.setItems(FXCollections.observableArrayList(Database.getAllPokemon(new ArrayList<>())));
        acbPokemon = new AutoCompleteBox(cbPokemon);
        cbPokemon.setOnAction(event -> {
            try {
                selectedPokemon = cbPokemon.getSelectionModel().getSelectedItem();
            } catch (Exception ex) {

            }
        });
    }


    public static void main(String[] args) {
        System.setProperty("debug", "true");
        launch();
    }


    private String generateInsertStatement(String id, Integer hp, Integer atk, Integer def, Integer spAtk, Integer spDef, Integer vel) {
        String text = "INSERT OR REPLACE INTO poke_evs_yield (_id,hp,atk,def,spatk,spdef,speed) \n" +
                "VALUES (\"%s\",%d,%d,%d,%d,%d,%d); \n";
        return String.format(text, id, hp, atk, def, spAtk, spDef, vel);
    }

    private String generateInsertStatementForPokemonEggGroups(String id, Pair<String, String> parsedEggGroups) {
        String text = "INSERT OR REPLACE INTO poke_egg_groups (_id,group_1,group_2) \n" +
                "VALUES (\"%s\",\"%s\",\"%s\"); \n";
        return String.format(text, id,
                sanitizeDefault(parsedEggGroups.getLeft()),
                sanitizeDefault(parsedEggGroups.getRight()));
    }

    private String generateInsertStatementForPokedex(Pokedex pokedex) {
        String text = "INSERT OR REPLACE INTO pokedex (_id,species,height_meters,height_feet,weight_kilograms,weight_pounds,catch_rate,base_friendship,base_exp,growth,egg_steps,male_rate,female_rate) \n" +
                "VALUES (\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"); \n";
        return String.format(text,
                pokedex.getPokedexData().getId(),
                Sanitizer.sanitizeDefault(pokedex.getPokedexData().getSpecies()),
                pokedex.getPokedexData().getHeightMeters(),
                pokedex.getPokedexData().getHeightFeet(),
                pokedex.getPokedexData().getWeightKilograms(),
                pokedex.getPokedexData().getWeightPounds(),
                pokedex.getTraining().getCatchRate(),
                pokedex.getTraining().getBaseFriendship(),
                pokedex.getTraining().getBaseExp(),
                pokedex.getTraining().getGrowthRate(),
                pokedex.getBreeding().getEggCycles(),
                pokedex.getBreeding().getGenders().getLeft(),
                pokedex.getBreeding().getGenders().getRight()
        );

    }

    private String generateInsertStatement(String id, String first, String second, String hidden) {
        String text = "INSERT OR REPLACE INTO poke_abilities (_id,first,second,hidden) \n" +
                "VALUES (\"%s\",\"%s\",\"%s\",\"%s\"); \n";
        return String.format(text, id, first, second, hidden);
    }

    private String generateStatementForBiologies(String id, String bioEN, String bioES) {
        String insert = "INSERT OR REPLACE INTO poke_biologies (_id,biology, biology_es) \n" +
                "VALUES (\"%s\",\"%s\",\"%s\"); \n";
        return String.format(insert, id, bioEN, bioES);
    }

    private String generateStatementForEtymologies(String id, String etyEN, String etyES) {
        String insert = "INSERT OR REPLACE INTO poke_etymologies (_id,etymology, etymology_es) \n" +
                "VALUES (\"%s\",\"%s\",\"%s\"); \n";
        return String.format(insert, id, etyEN, etyES);
    }
}