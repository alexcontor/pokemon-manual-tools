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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pojos.GenericIndex;
import pojos.LocalizedColumn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TranslationGUI extends AbstactCustomApplication {

    public static final int DEFAULT_WIDTH = 150;

    @FXML
    private Label errorText;

    @FXML
    private ComboBox<Language> cbLanguages;
    private AutoCompleteBox acbLanguages;
    private Language selectedLanguage;

    @FXML
    private ComboBox<String> cbTables;
    private AutoCompleteBox acbTables;
    private String selectedTable;


    @FXML
    private VBox rowsContainer;

    private static final List<String> allTables = Database.getAllTables();
    private static final List<Language> allSupportedLanguages = Arrays.asList(Language.values());
    private static List<GenericIndex> localizedRows = new ArrayList<>();


    @FXML
    protected void generateInsertStatement() {
        errorText.setText("Valid!");
        errorText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = loadStage("translation-gui.fxml", this);
        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setMaximized(true);
        initializeLanguagesComboBox();
        initializeTablesComboBox();
        refreshLocalizedRows();
        rowsContainer.setFillWidth(true);
        stage.setScene(scene);
        stage.show();

    }

    private void initializeLanguagesComboBox() {
        selectedLanguage = allSupportedLanguages.iterator().next();
        cbLanguages.setItems(FXCollections.observableArrayList(allSupportedLanguages));
        acbLanguages = new AutoCompleteBox(cbTables);
        cbLanguages.setValue(selectedLanguage);
        cbLanguages.setOnAction(event -> {
            try {
                selectedLanguage = cbLanguages.getSelectionModel().getSelectedItem();
                refreshLocalizedRows();
            } catch (Exception ex) {

            }
        });

    }

    private void initializeTablesComboBox() {
        selectedTable = allTables.iterator().next();
        cbTables.setItems(FXCollections.observableArrayList(allTables));
        acbTables = new AutoCompleteBox(cbTables);
        cbTables.setValue(selectedTable);
        cbTables.setOnAction(event -> {
            try {
                selectedTable = cbTables.getSelectionModel().getSelectedItem();
                refreshLocalizedRows();
            } catch (Exception ex) {

            }
        });
    }

    private void refreshLocalizedRows() {
        localizedRows = Database.getAllLocalizationsForTableForLanguage(selectedTable, selectedLanguage);
        rowsContainer.getChildren().clear();
        for (GenericIndex localizedRow : localizedRows) {
            createRow(localizedRow);
        }
    }

    public void createRow(GenericIndex genericIndex) {

        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(15);
        container.setPrefWidth(1800);

        Label tvID = new Label();
        tvID.setText(genericIndex.getIndexNumber());

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPosition(1, 0.1);

        VBox localizationsContainer = new VBox();
        localizationsContainer.setAlignment(Pos.CENTER);
        localizationsContainer.setSpacing(15);

        for (LocalizedColumn localizedColumn : genericIndex.getLocalizedColumnList()) {

            HBox vBox = new HBox();

            TextField tvColumnName = new TextField();
            tvColumnName.setText(" (" + localizedColumn.getEnglishValue() + ") " + localizedColumn.getColumn() + " = ");

            TextField tvLocalizedValue = new TextField();
            tvLocalizedValue.setText(localizedColumn.getLocalizedValue());

            vBox.getChildren().addAll(tvColumnName, tvLocalizedValue);
            localizationsContainer.getChildren().add(vBox);

        }

        container.getChildren().addAll(tvID, splitPane, localizationsContainer);

        SplitPane verticalSplit = new SplitPane();
        verticalSplit.setDividerPosition(1, 0.1);

        rowsContainer.getChildren().addAll(container, verticalSplit);

    }

}