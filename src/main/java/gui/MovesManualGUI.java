package gui;

import database.Database;
import enums.Language;
import gui.custom.AbstactCustomApplication;
import gui.custom.AutoCompleteBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pojos.Game;
import pojos.Move;
import pojos.Pokemon;
import utils.PokemonSeleniumUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static constants.DatabaseConstants.*;

public class MovesManualGUI extends AbstactCustomApplication implements ChangeListener<Toggle> {

    private static final String LEARNSET = "rbLearnset";
    private static final String TUTOR = "rbTutor";
    private static final String MT = "rbMt";
    private static final String EGG = "rbEggmove";
    private static final String TRANSFER = "rbTransfer";
    private static final String EVENT = "rbEvent";

    private static Integer generation = 1;
    private static Pokemon pokemon;
    private static Move move;
    private static Game game;
    private static String selectedMoveGroup;

    private static List<Game> games;
    private static final List<String> dlcs = new ArrayList<>(List.of("isle_of_armor", "crown_tundra", "expansion_pass", "dream_world", "pokewalker", "pal_park"));

    @FXML
    private Label errorText;
    @FXML
    private ToggleGroup movesGroup;
    @FXML
    private ComboBox<Pokemon> cbPokemon;
    private AutoCompleteBox acbPokemon;

    @FXML
    private ComboBox<Move> cbMove;
    private AutoCompleteBox acbMove;

    @FXML
    private ComboBox<Game> cbGame;
    private AutoCompleteBox acbGame;
    @FXML
    private ComboBox<Integer> cbGeneration;
    private AutoCompleteBox acbGeneration;

    @FXML
    private TextField tfLevel;
    @FXML
    private TextField tfMethod;
    @FXML
    private TextField tfTM;

    @FXML
    private TextArea taInsert;

    @FXML
    protected void generateInsertStatement() {

        String insertStatement = doGenerateInsertStatement();
        if (insertStatement != null && !insertStatement.isEmpty()) {
            errorText.setText("Valid!");
            errorText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            taInsert.setText(selectedMoveGroup + "---" + pokemon.getLocalizedName(Language.EN) + "---" + move.getLocalizedName(Language.EN) + "---Generation:" + generation);
            PokemonSeleniumUtils.insertToDatabase(insertStatement, PokemonSeleniumUtils.generateStatement(MANUAL + "moves/" + INSERT_REPLACE + "-" + pokemon.getLocalizedName(Language.EN), selectedMoveGroup));
            nullify();
        }
    }

    private void nullify() {
        move = null;
        cbMove.setValue(null);
    }

    private String doGenerateInsertStatement() {
        String insertStatement = null;
        boolean isValid = validate(selectedMoveGroup);
        if (isValid) {
            switch (selectedMoveGroup) {
                case LEARNSET:
                    insertStatement = generateInsertStatementForLearnsetMoves(pokemon.getIndexNumber(), move.getIndexNumber(), generation, Integer.parseInt(tfLevel.getText()), game.getIndexNumber());
                    break;
                case TUTOR:
                    insertStatement = generateInsertStatementForTutorMoves(pokemon.getIndexNumber(), move.getIndexNumber(), generation, game.getIndexNumber());
                    break;
                case MT:
                    insertStatement = generateInsertStatementForTmHmTrMoves(pokemon.getIndexNumber(), move.getIndexNumber(), generation, tfTM.getText(), game.getIndexNumber());
                    break;
                case EGG:
                    insertStatement = generateInsertStatementForEggMoves(pokemon.getIndexNumber(), move.getIndexNumber(), generation, game.getIndexNumber());
                    break;
                case TRANSFER:
                    insertStatement = generateInsertStatementForTransferOnlyMoves(pokemon.getIndexNumber(), move.getIndexNumber(), generation, tfMethod.getText());
                    break;
                case EVENT:
                    insertStatement = generateInsertStatementForEventMoves(pokemon.getIndexNumber(), move.getIndexNumber(), generation, game.getIndexNumber(), tfMethod.getText());
                    break;
            }
        } else {
            errorText.setText("Please fill all the required fields");
            errorText.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        }
        return insertStatement;
    }

    private boolean validate(String selectedMoveGroup) {
        boolean isValid = validateCommonData(selectedMoveGroup);
        if (isValid) {
            switch (selectedMoveGroup) {
                case LEARNSET:
                    isValid = tfLevel.getText() != null && !tfLevel.getText().isEmpty() && tfLevel.getText().matches("\\d*");
                    break;
                case TUTOR:
                    break;
                case MT:
                    isValid = tfTM.getText() != null && !tfTM.getText().isEmpty();
                    break;
                case EGG:
                    break;
                case TRANSFER:
                    isValid = tfMethod.getText() != null && !tfMethod.getText().isEmpty();
                    break;
                case EVENT:
                    isValid = tfMethod.getText() != null && !tfMethod.getText().isEmpty();
                    break;
            }
        }
        return isValid;
    }

    private boolean validateCommonData(String selectedMoveGroup) {
        boolean isValid = selectedMoveGroup != null && !selectedMoveGroup.isEmpty();
        if (isValid) {
            if (pokemon == null || pokemon.getIndexNumber().equals("null")) {
                isValid = false;
            }
            if (move == null || move.getIndexNumber().equals("null")) {
                isValid = false;
            }
            if (!TRANSFER.equals(selectedMoveGroup)) {
                if (game == null) {
                    isValid = false;
                }
            }
            if (generation == null) {
                isValid = false;
            }
        }
        return isValid;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadStage("moves-gui.fxml", this);
        Scene scene = new Scene(root, 620, 640);
        stage.setTitle("Hello!");

        refreshGames();

        initializeRadioButtonListeners();

        initializePokemonComboBox();
        initializeMoveComboBox();
        initializeGameComboBox();
        initializeGenerationComboBox();


        movesGroup.selectedToggleProperty().addListener(this);

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

    //Then you handle all in a centralized place
    @Override
    public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
        selectedMoveGroup = ((RadioButton) newValue).getId();
        switch (selectedMoveGroup) {
            case LEARNSET:
                setVisible(true, tfLevel, cbGame);
                setVisible(false, tfMethod, tfTM);
                break;
            case TUTOR:
                setVisible(true, cbGame);
                setVisible(false, tfLevel, tfMethod, tfTM);
                break;
            case MT:
                setVisible(true, tfTM, cbGame);
                setVisible(false, tfMethod, tfLevel);
                break;
            case EGG:
                setVisible(true, cbGame);
                setVisible(false, tfLevel, tfMethod, tfTM);
                break;
            case TRANSFER:
                setVisible(true, tfMethod);
                setVisible(false, tfLevel, tfTM, cbGame);
                break;
            case EVENT:
                setVisible(true, tfMethod, cbGame);
                setVisible(false, tfLevel, tfTM);
                break;
        }
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

    private void initializeMoveComboBox() {
        cbMove.setItems(FXCollections.observableArrayList(Database.getAllMoves()));
        acbMove = new AutoCompleteBox(cbMove);
        cbMove.setOnAction(event -> {
            try {
                move = cbMove.getSelectionModel().getSelectedItem();
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
        System.setProperty("debug", "true");
        launch();
    }

    protected String generateInsertStatementForLearnsetMoves(String id, String moveIndexNumber, int generation,
                                                             int level, String gameExclusive) {
        String text = "INSERT OR REPLACE INTO " + POKE_LEARNSET_MOVES_TABLE + " (_id,_move,_gen,level,_game_exclusive) \n" + "VALUES (\"%s\",\"%s\",%d,%d,\"%s\"); \n";
        return String.format(text, id, moveIndexNumber, generation, level, gameExclusive);
    }

    protected String generateInsertStatementForTutorMoves(String id, String moveIndexNumber, int generation, String
            gameExclusive) {
        String text = "INSERT OR REPLACE INTO " + POKE_TUTOR_MOVES_TABLE + " (_id,_move,_gen,_game_exclusive) \n" + "VALUES (\"%s\",\"%s\",%d,\"%s\"); \n";
        return String.format(text, id, moveIndexNumber, generation, gameExclusive);
    }

    protected String generateInsertStatementForTmHmTrMoves(String id, String moveIndexNumber, int generation, String
            tmHmTrName, String gameExclusive) {
        String text = "INSERT OR REPLACE INTO " + POKE_TM_HM_TR_MOVES_TABLE + " (_id,_move,_gen,_name,_game_exclusive) \n" + "VALUES (\"%s\",\"%s\",%d,\"%s\",\"%s\"); \n";
        return String.format(text, id, moveIndexNumber, generation, tmHmTrName, gameExclusive);
    }

    protected String generateInsertStatementForEggMoves(String id, String moveIndexNumber, int generation, String
            gameExclusive) {
        String text = "INSERT OR REPLACE INTO " + POKE_EGG_MOVES_TABLE + " (_id,_move,_gen,_game_exclusive) \n" + "VALUES (\"%s\",\"%s\",%d,\"%s\"); \n";
        return String.format(text, id, moveIndexNumber, generation, gameExclusive);
    }

    protected String generateInsertStatementForTransferOnlyMoves(String id, String moveIndexNumber,
                                                                 int generation, String methodText) {
        String text = "INSERT OR REPLACE INTO " + POKE_TRANSFER_ONLY_MOVES_TABLE + " (_id,_move,_gen,method) \n" + "VALUES (\"%s\",\"%s\",%d,\"%s\"); \n";
        return String.format(text, id, moveIndexNumber, generation, methodText);
    }


    protected String generateInsertStatementForEventMoves(String id, String moveIndexNumber, int generation, String
            gameExclusive, String method) {
        String text = "INSERT OR REPLACE INTO " + POKE_EVENT_MOVES_TABLE + " (_id,_move,_gen,_game_exclusive,method) \n" + "VALUES (\"%s\",\"%s\",%d,\"%s\",\"%s\"); \n";
        return String.format(text, id, moveIndexNumber, generation, gameExclusive, method);
    }

}