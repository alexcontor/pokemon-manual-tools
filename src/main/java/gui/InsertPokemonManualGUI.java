package gui;

import database.Database;
import enums.Language;
import gui.custom.AbstactCustomApplication;
import gui.custom.AutoCompleteBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import pojos.*;
import utils.GenericUtils;
import utils.PokemonSeleniumUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static constants.DatabaseConstants.*;

public class InsertPokemonManualGUI extends AbstactCustomApplication {

    public static final int DEFAULT_WIDTH = 150;

    private static Pokemon selectedPokemon;
    private static Move move;
    private static Game game;
    private static String selectedMoveGroup;

    private static List<Type> types = Database.getAllTypes();

    private List<InsertPokemonBasicDataRow> pokemonRows = new ArrayList<>();

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
        for (InsertPokemonBasicDataRow insertPokemonRow : pokemonRows) {
            boolean insertedBaseStats = generateBaseStats(insertPokemonRow);
            boolean insertedPokemon = generatePokemon(insertPokemonRow);
            if (insertedBaseStats && insertedPokemon) {
                errorText.setText("Valid!");
                errorText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            } else {
                errorText.setText("Invalid!");
                errorText.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            }
        }
    }

    private boolean generatePokemon(InsertPokemonBasicDataRow insertPokemonBasicDataRow) {
        boolean inserted = false;
        try {
            String nameEN = insertPokemonBasicDataRow.getTfNameEN().getText();
            String nameES = insertPokemonBasicDataRow.getTfNameES().getText();
            String id = insertPokemonBasicDataRow.getTvID().getText();
            int realId = Integer.parseInt(insertPokemonBasicDataRow.getTvRealID().getText());
            String color = insertPokemonBasicDataRow.getTfColor().getText();
            String colorDark = insertPokemonBasicDataRow.getTfColorDark().getText();
            int type1 = Integer.parseInt(insertPokemonBasicDataRow.getType1().getSelectionModel().getSelectedItem().getIndexNumber());
            int type2 = Integer.parseInt(insertPokemonBasicDataRow.getType2().getSelectionModel().getSelectedItem().getIndexNumber());
            PokemonSeleniumUtils.insertToDatabase(
                    generateInsertStatement(nameEN, nameES, id, type1, type2, realId, color, colorDark),
                    PokemonSeleniumUtils.generateStatement(SAMPLEDATA + POKEMON_TABLE + SEPARATOR + INSERT_REPLACE, POKEMON_TABLE));
            inserted = true;
        } catch (Exception ex) {
        }
        return inserted;
    }

    private boolean generateBaseStats(InsertPokemonBasicDataRow insertPokemonBasicDataRow) {
        boolean inserted = false;
        try {
            String id = insertPokemonBasicDataRow.getTvID().getText();
            String[] parts = insertPokemonBasicDataRow.getTvBaseStats().getText().split(",");
            int hp = Integer.parseInt(parts[0]);
            int atk = Integer.parseInt(parts[1]);
            int def = Integer.parseInt(parts[2]);
            int spatk = Integer.parseInt(parts[3]);
            int spdef = Integer.parseInt(parts[4]);
            int speed = Integer.parseInt(parts[5]);
            PokemonSeleniumUtils.insertToDatabase(
                    generateInsertStatement(id,
                            hp,
                            atk,
                            def,
                            spatk,
                            spdef,
                            speed),
                    PokemonSeleniumUtils.generateStatement(SAMPLEDATA + BASESTATS_TABLE + SEPARATOR + INSERT_REPLACE, BASESTATS_TABLE));
            inserted = true;
        } catch (Exception ex) {
        }
        return inserted;
    }

    @FXML
    protected void clear() {
        pokemonRows.clear();
        pokemonRowsContainer.getChildren().clear();
    }


    @FXML
    protected void refreshImages() throws Exception {
        for (InsertPokemonBasicDataRow insertRow : pokemonRows) {
            String imageUrl = insertRow.getTfImageURL().getText();
            String shinyImageUrl = insertRow.getTfShinyImageURL().getText();
            String miniImageUrl = insertRow.getTfMiniImageURL().getText();
            if (StringUtils.isNotEmpty(imageUrl)) {
                GenericUtils.downloadImage(imageUrl, insertRow.getTvID().getText(), "webp", 300, 300, ROOT_FOLDER, SAMPLEDATA_FOLDER, IMAGES_FOLDER, POKEMON_FOLDER, SUGIMORI_FOLDER, NORMAL_FOLDER);
            }
            if (StringUtils.isNotEmpty(miniImageUrl)) {
                GenericUtils.downloadImage(miniImageUrl, insertRow.getTvID().getText(), "webp", null, null, ROOT_FOLDER, SAMPLEDATA_FOLDER, IMAGES_FOLDER, POKEMON_FOLDER, MINI_FOLDER);
            }
            if (StringUtils.isNotEmpty(shinyImageUrl)) {
                GenericUtils.downloadImage(shinyImageUrl, insertRow.getTvID().getText(), "webp", 500, 500, ROOT_FOLDER, SAMPLEDATA_FOLDER, IMAGES_FOLDER, POKEMON_FOLDER, SUGIMORI_FOLDER, SHINY_FOLDER);
            }
            setImage(imageUrl, insertRow.getIvImagePreview(), true);
            setImage(shinyImageUrl, insertRow.getIvShinyImagePreview(), true);
            setImage(miniImageUrl, insertRow.getIvMiniImagePreview(), true);
        }
    }

    @FXML
    protected void refreshColors() throws IOException {
        for (InsertPokemonBasicDataRow insertRow : pokemonRows) {
            insertRow.getTfColor().setStyle("-fx-background-color: " + insertRow.getTfColor().getText() + ";");
            insertRow.getTfColorDark().setStyle("-fx-background-color: " + insertRow.getTfColorDark().getText() + ";");
        }
    }

    private void setImage(String url, ImageView imageView, boolean external) {
        if (StringUtils.isNotEmpty(url)) {
            Image image = null;
            if (!external) {
                File file = new File(url);
                image = new Image(file.toURI().toString(), 240, // requested width
                        240, // requested height
                        true, // preserve ratio
                        true, // smooth rescaling
                        true // load in background
                );
            } else {
                image = new Image(url);
            }
            imageView.setImage(image);
        }
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
        InsertPokemonBasicDataRow pokemonBasicDataRow = createPokemonBasicDataContainer(variantID, selectedPokemon);
        pokemonRowsContainer.getChildren().addAll(splitPane, pokemonBasicDataRow.getMainContainer());
        pokemonRows.add(pokemonBasicDataRow);
    }

    private InsertPokemonBasicDataRow createPokemonBasicDataContainer(String variantID, Pokemon selectedPokemon) {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(15);

        TextField tvID = (TextField) instantiate(TextField.class);
        tvID.setText(variantID);

        TextField tvRealID = (TextField) instantiate(TextField.class);
        tvRealID.setText(String.valueOf(selectedPokemon.getRealID()));

        TextField tvBaseStats = (TextField) instantiate(TextField.class);
        tvBaseStats.setText(selectedPokemon.getBaseStats());

        ComboBox<Type> cbTypes1 = (ComboBox) instantiate(ComboBox.class);
        cbTypes1.setItems(FXCollections.observableArrayList(types));
        cbTypes1.setValue(selectedPokemon.getType1());

        ComboBox<Type> cbTypes2 = (ComboBox) instantiate(ComboBox.class);
        cbTypes2.setItems(FXCollections.observableArrayList(types));
        cbTypes2.setValue(selectedPokemon.getType2());

        VBox imageContainer = new VBox();
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setSpacing(10);

        ImageView imagePreview = new ImageView();
        imagePreview.setFitHeight(100);
        imagePreview.setFitWidth(DEFAULT_WIDTH);

        TextField imageUrl = new TextField();
        imageUrl.setMinWidth(DEFAULT_WIDTH);

        String imageDirectory = GenericUtils.compoundDirectory(selectedPokemon.getIndexNumber().concat(".webp"), SAMPLEDATA_FOLDER, IMAGES_FOLDER, POKEMON_FOLDER, SUGIMORI_FOLDER, NORMAL_FOLDER);
        setImage(imageDirectory, imagePreview, false);

        imageContainer.getChildren().addAll(imagePreview, imageUrl);

        VBox shinyImageContainer = new VBox();
        shinyImageContainer.setAlignment(Pos.CENTER);
        shinyImageContainer.setSpacing(10);

        ImageView shinyImagePreview = new ImageView();
        shinyImagePreview.setFitHeight(100);
        shinyImagePreview.setFitWidth(DEFAULT_WIDTH);

        TextField shinyImageUrl = (TextField) instantiate(TextField.class);

        String shinyImageDirectory = GenericUtils.compoundDirectory(selectedPokemon.getIndexNumber().concat(".webp"), SAMPLEDATA_FOLDER, IMAGES_FOLDER, POKEMON_FOLDER, SUGIMORI_FOLDER, NORMAL_FOLDER);
        setImage(shinyImageDirectory, shinyImagePreview, false);

        shinyImageContainer.getChildren().addAll(shinyImagePreview, shinyImageUrl);

        VBox miniImageContainer = new VBox();
        miniImageContainer.setAlignment(Pos.CENTER);
        miniImageContainer.setSpacing(10);

        ImageView miniImagePreview = new ImageView();
        miniImagePreview.setFitHeight(100);
        miniImagePreview.setFitWidth(DEFAULT_WIDTH);

        TextField miniImageUrl = (TextField) instantiate(TextField.class);

        String miniImageDirectory = GenericUtils.compoundDirectory(selectedPokemon.getIndexNumber().concat(".webp"), SAMPLEDATA_FOLDER, IMAGES_FOLDER, POKEMON_FOLDER, MINI_FOLDER);
        setImage(miniImageDirectory, miniImagePreview, false);

        miniImageContainer.getChildren().addAll(miniImagePreview, miniImageUrl);

        TextField tfNameEn = (TextField) instantiate(TextField.class);
        tfNameEn.setText(selectedPokemon.getLocalizedName(Language.EN));

        TextField tfNameEs = (TextField) instantiate(TextField.class);
        tfNameEs.setText(selectedPokemon.getLocalizedName(Language.ES));

        TextField color = (TextField) instantiate(TextField.class);
        color.setText(selectedPokemon.getColor());
        color.setStyle("-fx-background-color: " + selectedPokemon.getColor() + ";");

        TextField colorDark = (TextField) instantiate(TextField.class);
        colorDark.setText(selectedPokemon.getColorDark());
        colorDark.setStyle("-fx-background-color: " + selectedPokemon.getColorDark() + ";");

        container.getChildren().addAll(miniImageContainer, imageContainer, shinyImageContainer, tvID, tvRealID, tvBaseStats, cbTypes1, cbTypes2, tfNameEn, tfNameEs, color, colorDark);

        return new InsertPokemonBasicDataRow(container, miniImagePreview, miniImageUrl, imagePreview, imageUrl, shinyImagePreview, shinyImageUrl, tvID, tvRealID, tvBaseStats, cbTypes1, cbTypes2, tfNameEn, tfNameEs, color, colorDark);
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
        Parent root = loadStage("insert-pokemon-gui.fxml", this);
        Scene scene = new Scene(root, 1000, 1000);
        stage.setTitle("Hello!");
        initializePokemonComboBox();
        stage.setScene(scene);
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

    private AutoCompleteBox initializeAutoCompleteComboBox(ComboBox comboBox, List<? extends GenericIndex> list) {
        comboBox.setItems(FXCollections.observableArrayList(list));
        return new AutoCompleteBox(comboBox);
    }

    public static void main(String[] args) {
        System.setProperty("debug", "true");
        launch();
    }

    private String generateInsertStatement(String nameEn, String nameEs, String id, int type1, int type2, int realId, String color, String colorDark) {
        String text = "INSERT OR REPLACE INTO pokemon (name, name_es,_id,type_1,type_2,real_id, color, color_dark) \n" +
                "VALUES (\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"); \n";
        return String.format(text, nameEn, nameEs, id, type1, type2, realId, color, colorDark);
    }

    private String generateInsertStatement(String id, int hp, int atk, int def, int spAtk, int spDef, int vel) {
        String text = "INSERT OR REPLACE INTO poke_basestats (_id,hp,atk,def,spatk,spdef,speed) \n" +
                "VALUES (\"%s\",%d,%d,%d,%d,%d,%d); \n";
        return String.format(text, id, hp, atk, def, spAtk, spDef, vel);
    }

}