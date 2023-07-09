package gui;

import database.Database;
import enums.Language;
import gui.custom.AbstactCustomApplication;
import gui.custom.AutoCompleteBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import pojos.Game;
import pojos.Pokemon;
import utils.PokemonSeleniumUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static constants.DatabaseConstants.SAMPLEDATA;

public class DeleteNumberDexManualGUI extends AbstactCustomApplication {


    private static Integer generation = 1;
    private static final List<Pokemon> pokemons = new ArrayList<>();
    private static Pokemon pokemon;
    private static List<Game> gamesStack = new ArrayList<>();
    private static Game game;

    private static List<Game> games;

    private static final List<String> dlcs = new ArrayList<>(List.of("isle_of_armor", "crown_tundra", "expansion_pass", "dream_world", "pokewalker", "pal_park"));

    @FXML
    private Label errorText;

    @FXML
    private Button bAddPokemonToList;

    @FXML
    private ComboBox<Pokemon> cbPokemon;
    private AutoCompleteBox acbPokemon;

    @FXML
    private ComboBox<Game> cbGame;
    private AutoCompleteBox acbGame;


    @FXML
    private ComboBox<Integer> cbGeneration;
    private AutoCompleteBox acbGeneration;


    @FXML
    private TextArea taInsert;

    @FXML
    private TextArea stackPoke;

    @FXML
    private TextArea stackGames;


    @FXML
    protected void insert() {
        if (isValid()) {
            StringJoiner joiner = new StringJoiner(",");
            for (Pokemon pokemon : pokemons) {
                for (Game game : gamesStack) {
                    String insertStatement = generateRemoveStatementForPokedexNumbers(pokemon.getIndexNumber(), game.getIndexNumber());
                    joiner.add(pokemon.getLocalizedName(Language.EN));
                    PokemonSeleniumUtils.insertToDatabase(insertStatement, PokemonSeleniumUtils.generateStatement(SAMPLEDATA + "poke_numbers_dex/" + "REMOVE" + "-manual-poke-numbers-dex"));
                }
            }
            errorText.setText("Valid!");
            errorText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            taInsert.setText("Inserted" + joiner + " In Game " + game.getIndexNumber());
        } else {
            errorText.setText("Invalid!");
            errorText.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        }
    }

    @FXML
    protected void addGame() {
        try {
            if (Objects.nonNull(game) && !gamesStack.contains(game)) {
                gamesStack.add(game);
            }
        } catch (Exception ex) {
        }
        stackGames.setText(gamesStack.stream().map(game -> game.getLocalizedName(Language.EN)).collect(Collectors.joining("\n")) + "\n Total= " + gamesStack.size());
    }

    @FXML
    protected void addPoke() {
        try {
            if (Objects.nonNull(pokemon) && !pokemons.contains(pokemon)) {
                pokemons.add(pokemon);
            }
        } catch (Exception ex) {
        }
        stackPoke.setText(pokemons.stream().map(pokemon -> pokemon.getLocalizedName(Language.EN)).collect(Collectors.joining("\n")) + "\n Total= " + pokemons.size());
    }

    @FXML
    protected void clearStack() {
        pokemons.clear();
        stackPoke.setText("");
    }

    @FXML
    protected void clearGamesStack() {
        gamesStack.clear();
        stackGames.setText("");
    }

    private boolean isValid() {
        boolean valid = true;
        if ((Objects.isNull(pokemons) || pokemons.isEmpty()) || (Objects.isNull(games) || games.isEmpty())) {
            valid = false;
        }
        return valid;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadStage("delete-numbers-dex-gui.fxml", this);
        Scene scene = new Scene(root, 620, 640);
        stage.setTitle("Hello!");

        refreshGames();

        initializeRadioButtonListeners();

        initializePokemonComboBox();
        initializeGameComboBox();
        initializeGenerationComboBox();

        stage.setScene(scene);
        stage.show();

    }

    private void refreshGames() {
        games = Database.getAllGames()
                .stream()
                .filter(g -> (g.getGeneration() == generation || g.getIndexNumber().equals("null")) && (!dlcs.contains(g.getIndexNumber())))
                .collect(Collectors.toList());
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
                acbGame = new AutoCompleteBox(cbGame);
                cbGame.setValue(null);
                game = null;
            } catch (Exception ex) {

            }
        });
    }

    private void initializePokemonComboBox() {
        cbPokemon.setItems(FXCollections.observableArrayList(Database.getAllPokemon(new ArrayList<>())));
        acbPokemon = new AutoCompleteBox(cbPokemon);
        cbPokemon.setOnAction(event -> {
            try {
                pokemon = cbPokemon.getSelectionModel().getSelectedItem();
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
            } catch (Exception ex) {

            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

    private String generateRemoveStatementForPokedexNumbers(String id, String game) {
        String text = "DELETE FROM poke_numbers_dex WHERE _id = \"%s\" AND _pokedex_game = \"%s\"; \n";
        return (String.format(text, id, game) + "\n").trim();
    }


}