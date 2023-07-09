package gui;

import database.Database;
import enums.Language;
import enums.PokemonType;
import gui.custom.AbstactCustomApplication;
import gui.custom.AutoCompleteBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import pojos.*;
import utils.PokemonIndexUtils;
import utils.PokemonSeleniumUtils;
import utils.PokemonUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static constants.DatabaseConstants.INSERT_REPLACE;
import static constants.DatabaseConstants.SAMPLEDATA;

public class InsertNumberDexManualGUI extends AbstactCustomApplication {


    private static Integer generation = 1;
    private static final List<Pokemon> pokemons = new ArrayList<>();
    private static Pokemon selectedPokemon;
    private static Pokemon selectedBulkPokemon;
    private static Game game;
    private static PokedexGame pokedexGame;

    private static List<Game> games;
    private static List<PokedexGame> pokedexGames;
    private static final List<String> dlcs = new ArrayList<>(List.of("isle_of_armor", "crown_tundra", "expansion_pass", "dream_world", "pokewalker", "pal_park"));

    @FXML
    private Label errorText;

    @FXML
    private Button bAddPokemonToList;

    @FXML
    private ComboBox<Pokemon> cbPokemon, cbBulkPokemon;
    private AutoCompleteBox acbPokemon, acbBulkPokemon;

    @FXML
    private ComboBox<Game> cbGame;
    private AutoCompleteBox acbGame;

    @FXML
    private ComboBox<PokedexGame> cbPokedexGame;


    @FXML
    private ComboBox<Integer> cbGeneration;
    private AutoCompleteBox acbGeneration;

    @FXML
    private TextField tfNumber;

    @FXML
    private TextArea taInsert, taAddFromText;

    @FXML
    private TextArea taNumbersDex;

    @FXML
    private TextArea stackPoke;

    @FXML
    private CheckBox cbOwnNumber, cbMega, cbVariants, cbGigantamax, cbAlternate, cbAlola, cbGalar, cbHisui, cbPaldea;

    @FXML
    protected void insert() throws SQLException {
        if (isValid()) {
            StringJoiner joiner = new StringJoiner(" , ");
            for (Pokemon pokemon : pokemons) {
                if ((Objects.isNull(game) || Objects.isNull(pokedexGame))) {
                    String basePokemonID = PokemonIndexUtils.getPokemonBaseId(pokemon.getIndexNumber());
                    List<Game> allGames = Database.getAllGamesAvailableForPokemon(basePokemonID);
                    for (Game game : allGames) {
                        String insertStatement = generateInsertStatementForPokedexNumbers(pokemon.getIndexNumber(), getOwnNumber(pokemon.getIndexNumber(), game.getMainGameID(), game.getSubGames().get(0).getIndexNumber()), game.getSubGames().get(0).getIndexNumber(), game.getMainGameID());
                        joiner.add(pokemon.getLocalizedName(Language.EN));
                        PokemonSeleniumUtils.insertToDatabase(insertStatement, PokemonSeleniumUtils.generateStatement(SAMPLEDATA + "poke_numbers_dex/" + INSERT_REPLACE + "-manual-poke-numbers-dex"));
                    }
                } else {
                    Integer number = cbOwnNumber.isSelected() ?
                            getOwnNumber(pokemon.getIndexNumber(), game.getIndexNumber(), pokedexGame.getIndexNumber()) :
                            Integer.valueOf(Integer.parseInt(tfNumber.getText().trim()));
                    String insertStatement = generateInsertStatementForPokedexNumbers(pokemon.getIndexNumber(), number, pokedexGame.getIndexNumber(), game.getIndexNumber());
                    joiner.add(pokemon.getLocalizedName(Language.EN));
                    PokemonSeleniumUtils.insertToDatabase(insertStatement, PokemonSeleniumUtils.generateStatement(SAMPLEDATA + "poke_numbers_dex/" + INSERT_REPLACE + "-manual-poke-numbers-dex"));
                }
            }
            errorText.setText("Valid!");
            errorText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            taInsert.setText("Inserted" + joiner);
        } else {
            errorText.setText("Invalid!");
            errorText.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        }
    }

    private Integer getOwnNumber(String pokemonID, String gameID, String pokedexGameID) {
        try {
            int realID = Integer.parseInt(PokemonIndexUtils.getPokemonRealId(pokemonID));
            if (!pokedexGameID.contains("national")) {
                PokedexNumber number = Database.getNumberForPokemonForGameForDex(realID, gameID, pokedexGameID);
                return Integer.parseInt(number.getNumber());
            } else {
                return realID;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    @FXML
    protected void addFromText() {
        try {
            String text = taAddFromText.getText();
            List<Pokemon> sanitizedPokemons = sanitizePokemon(text);
            for (Pokemon sanitizedPokemon : sanitizedPokemons) {
                if (Objects.nonNull(sanitizedPokemon) && !pokemons.contains(sanitizedPokemon)) {
                    pokemons.add(sanitizedPokemon);
                }
            }
            refreshNumbers();
        } catch (Exception ex) {
        }
        stackPoke.setText(pokemons.stream().map(pokemon -> pokemon.getLocalizedName(Language.EN)).collect(Collectors.joining("\n")) + "\n Total= " + pokemons.size());
    }

    private List<Pokemon> sanitizePokemon(String text) {
        List<Pokemon> pokes = new ArrayList<>();
        String[] pokeLines = text.split("\n");
        for (String pokeLine : pokeLines) {
            try {
                Integer.parseInt(pokeLine.trim());
            } catch (Exception ex) {
                pokes.add(PokemonUtils.calculatePossibleObjectByName(pokeLine, Database.getAllPokemon(new ArrayList<>()), Language.EN));
            }
        }
        return pokes;
    }

    @FXML
    protected void addPoke() {
        try {
            if (Objects.nonNull(selectedPokemon) && !pokemons.contains(selectedPokemon)) {
                pokemons.add(selectedPokemon);
                refreshNumbers();
            }
        } catch (Exception ex) {
        }
        stackPoke.setText(pokemons.stream().map(pokemon -> pokemon.getLocalizedName(Language.EN)).collect(Collectors.joining("\n")) + "\n Total= " + pokemons.size());
    }

    @FXML
    protected void addAll() {
        try {
            pokemons.clear();
            List<PokemonType> types = getTypesOfPokemon();
            List<Pokemon> matchingPokes = Database.getAllPokemonForTypes(types,
                    Objects.isNull(selectedBulkPokemon) ? "%" : String.valueOf(selectedBulkPokemon.getRealID()));
            pokemons.addAll(matchingPokes);
            refreshNumbers();
        } catch (Exception ex) {
        }
        stackPoke.setText(pokemons.stream().map(pokemon -> pokemon.getLocalizedName(Language.EN)).collect(Collectors.joining("\n")) + "\n Total= " + pokemons.size());
    }

    private List<PokemonType> getTypesOfPokemon() {
        List<PokemonType> types = new ArrayList<>();
        if (cbMega.isSelected()) {
            types.add(PokemonType.MEGA_EVOLUTION);
        }
        if (cbVariants.isSelected()) {
            types.add(PokemonType.VARIANT_FORM);
        }
        if (cbGigantamax.isSelected()) {
            types.add(PokemonType.GIGANTAMAX);
        }
        if (cbAlternate.isSelected()) {
            types.add(PokemonType.ALTERNATE_FORM);
        }
        if (cbAlola.isSelected()) {
            types.add(PokemonType.ALOLAN_FORM);
        }
        if (cbGalar.isSelected()) {
            types.add(PokemonType.GALARIAN_FORM);
        }
        if (cbHisui.isSelected()) {
            types.add(PokemonType.HISUIAN_FORM);
        }
        if (cbPaldea.isSelected()) {
            types.add(PokemonType.PALDEAN_FORM);
        }
        return types;
    }

    private void refreshNumbers() {
        List<PokedexNumber> pokedexNumbers = new ArrayList<>();
        for (Pokemon poke : pokemons) {
            pokedexNumbers.addAll(Database.getNumbersForPokemonForGame(poke.getRealID(), game.getIndexNumber()));
        }
        if (pokedexNumbers.isEmpty()) {
            taNumbersDex.setText("");
        } else {
            taNumbersDex.setText(pokedexNumbers.stream().map(PokedexNumber::toString).collect(Collectors.joining("\n")));
        }
    }

    @FXML
    protected void clearStack() {
        pokemons.clear();
        refreshNumbers();
        stackPoke.setText("");
    }

    private boolean isValid() {
        if (pokemons.isEmpty()) {
            return false;
        }
        if (!cbOwnNumber.isSelected()) {
            return StringUtils.isNotEmpty(tfNumber.getText());
        }
        return true;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadStage("insert-numbers-dex-gui.fxml", this);
        Scene scene = new Scene(root, 1300, 800);
        stage.setTitle("Hello!");

        refreshGames();

        initializeRadioButtonListeners();

        initializePokemonComboBox();
        initializeGameComboBox();
        initializePokedexGameComboBox();
        initializeGenerationComboBox();

        stage.setScene(scene);
        stage.show();

    }

    private void refreshGames() {
        games = Database.getAllGames()
                .stream()
                .filter(g -> (g.getGeneration() == generation || g.getIndexNumber().equals("null")) && (!dlcs.contains(g.getIndexNumber())))
                .collect(Collectors.toList());
        List<String> gameIds = games.stream().map(GenericIndex::getIndexNumber).collect(Collectors.toList());
        pokedexGames = Database.getAllPokedexGames().stream().filter(pg -> gameIds.contains(pg.getGameIndex())).collect(Collectors.toList());
    }

    private void initializeRadioButtonListeners() {

    }


    private void setVisible(boolean visible, Parent... parents) {
        for (Parent parent : parents) {
            parent.getParent().setVisible(visible);
        }
    }

    private void initializeGenerationComboBox() {
        cbGeneration.setItems(FXCollections.observableArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
        cbGeneration.setOnAction(event -> {
            try {
                generation = cbGeneration.getSelectionModel().getSelectedItem();
                acbGeneration = new AutoCompleteBox(cbGeneration);
                refreshGames();
                cbGame.setItems(FXCollections.observableArrayList(games));
                cbPokedexGame.setItems(FXCollections.observableArrayList(pokedexGames));
                acbGame = new AutoCompleteBox(cbGame);
                cbGame.setValue(null);
                game = null;
            } catch (Exception ex) {

            }
        });
    }

    private void initializePokemonComboBox() {
        List<Pokemon> allPokemon = Database.getAllPokemon(new ArrayList<>());
        cbBulkPokemon.setItems(FXCollections.observableArrayList(allPokemon));
        cbPokemon.setItems(FXCollections.observableArrayList(allPokemon));
        acbBulkPokemon = new AutoCompleteBox(cbBulkPokemon);
        acbPokemon = new AutoCompleteBox(cbPokemon);
        cbPokemon.setOnAction(event -> {
            try {
                selectedPokemon = cbPokemon.getSelectionModel().getSelectedItem();
            } catch (Exception ex) {

            }
        });
        cbBulkPokemon.setOnAction(event -> {
            try {
                selectedBulkPokemon = cbBulkPokemon.getSelectionModel().getSelectedItem();
            } catch (Exception ex) {

            }
        });
    }

    private void initializeGameComboBox() {
        cbGame.setItems(FXCollections.observableArrayList(games));
        acbGame = new AutoCompleteBox(cbGame);
        cbGame.setOnAction(event -> {
            try {
                game = cbGame.getSelectionModel().getSelectedItem();
                cbPokedexGame.setItems(FXCollections.observableArrayList(pokedexGames.stream().filter(pg -> pg.getGameIndex().equals(game.getIndexNumber())).distinct().collect(Collectors.toList())));
                refreshNumbers();
            } catch (Exception ex) {

            }
        });
    }

    private void initializePokedexGameComboBox() {
        cbPokedexGame.setItems(FXCollections.observableArrayList(pokedexGames));
        cbPokedexGame.setOnAction(event -> {
            try {
                pokedexGame = cbPokedexGame.getSelectionModel().getSelectedItem();
            } catch (Exception ex) {

            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

    private String generateInsertStatementForPokedexNumbers(String id, Integer number, String pokedexGame, String game) {
        String text = "INSERT OR REPLACE INTO poke_numbers_dex (_id,_number,_pokedex,_pokedex_game) " +
                "VALUES (\"%s\",%d,\"%s\",\"%s\");  \n";
        return (String.format(text, id, number, pokedexGame, game) + "\n").trim();
    }


}