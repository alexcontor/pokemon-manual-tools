package gui;

import database.Database;
import enums.Language;
import gui.custom.AbstactCustomApplication;
import gui.custom.AutoCompleteBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import pojos.Region;
import pojos.RegionArea;
import pojos.Route;
import utils.PokemonSeleniumUtils;
import utils.PokemonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static constants.DatabaseConstants.MANUAL;

public class RegionRoutesAssignManualGUI extends AbstactCustomApplication {

    private static Route selectedRoute;
    private static RegionArea selectedRegionArea;
    private static Region selectedRegion;

    private static List<Region> regions;
    private static List<RegionArea> regionAreas;
    private static List<Route> routes;

    private static List<Route> routesStack = new ArrayList<>();

    @FXML
    private Label errorText;

    @FXML
    private ComboBox<Route> cbRoute;
    private AutoCompleteBox acbRoute;

    @FXML
    private ComboBox<Region> cbRegion;
    private AutoCompleteBox acbRegion;

    @FXML
    private ComboBox<RegionArea> cbRegionAreas;
    private AutoCompleteBox acbRegionAreas;

    @FXML
    private TextArea taInsert, taAddFromText;

    @FXML
    private TextArea taStackRoute;


    private List<Double> xPoints = new ArrayList<>();
    private List<Double> yPoints = new ArrayList<>();

    @FXML
    protected void addFromText() {
        try {
            String text = taAddFromText.getText();
            List<Route> sanitizedRoutes = sanitizeRoute(text);
            for (Route sanitizedRoute : sanitizedRoutes) {
                if (Objects.nonNull(sanitizedRoute) && !routesStack.contains(sanitizedRoute)) {
                    routesStack.add(sanitizedRoute);
                }
            }
            if (routesStack.isEmpty()) {
                taStackRoute.setText("");
            } else {
                taStackRoute.setText(routesStack.stream().map(Route::toString).collect(Collectors.joining("\n")));
            }
        } catch (Exception ex) {
        }
    }

    @FXML
    protected void clearStack() {
        routesStack.clear();
        taStackRoute.setText("");
    }

    private List<Route> sanitizeRoute(String text) {
        List<Route> allRoutesForRegion = Database.getAllLocationsForRegion(selectedRegionArea.getIndexNumber());
        List<Route> routes = new ArrayList<>();
        String[] lines = text.split("\n");
        for (String line : lines) {
            try {
                Integer.parseInt(line.trim());
            } catch (Exception ex) {
                routes.add(PokemonUtils.calculatePossibleObjectByName(line, allRoutesForRegion, Language.EN));
            }
        }
        return routes;
    }

    @FXML
    protected void generateInsertStatement() {
        for (Route route : routesStack) {
            String insertStatement = generateUpdateStatement(selectedRegionArea.getIndexNumber(), route.getIndexNumber());
            PokemonSeleniumUtils.insertToDatabase(insertStatement, PokemonSeleniumUtils.generateStatement(MANUAL + "routes_coords"));
        }
        errorText.setText("Valid!");
        errorText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = loadStage("region-routes-assign-gui.fxml", this);
        Scene scene = new Scene(root, 1820, 1020);
        stage.setTitle("Hello!");
        initializeRegionComboBox();
        initializeRegionAreasComboBox();
        initializeRouteComboBox();

        stage.setScene(scene);
        stage.show();

    }

    private void initializeRegionComboBox() {
        regions = Database.getAllRegions();
        selectedRegion = regions.iterator().next();
        cbRegion.setItems(FXCollections.observableArrayList(regions));
        acbRegion = new AutoCompleteBox(cbRegion);
        cbRegion.setValue(selectedRegion);
        cbRegion.setOnAction(event -> {
            try {
                selectedRegion = cbRegion.getSelectionModel().getSelectedItem();
                refreshRegionAreas();
                refreshRoutes();
            } catch (Exception ex) {

            }
        });

    }

    private void initializeRegionAreasComboBox() {
        regionAreas = Database.getAllRegionsAreasForRegion(regions.iterator().next().getIndexNumber());
        selectedRegionArea = regionAreas.iterator().next();
        cbRegionAreas.setItems(FXCollections.observableArrayList(regionAreas));
        acbRegionAreas = new AutoCompleteBox(cbRegionAreas);
        cbRegionAreas.setValue(selectedRegionArea);
        cbRegionAreas.setOnAction(event -> {
            try {
                selectedRegionArea = cbRegionAreas.getSelectionModel().getSelectedItem();
                refreshRoutes();
            } catch (Exception ex) {

            }
        });
    }

    private void refreshRegionAreas() {
        List<RegionArea> regionAreas = Database.getAllRegionsAreasForRegion(selectedRegion.getIndexNumber());
        cbRegionAreas.setItems(FXCollections.observableArrayList(regionAreas));
        selectedRegionArea = regionAreas.iterator().next();
        cbRegionAreas.setValue(selectedRegionArea);
    }

    private void refreshRoutes() {
        routes = Database.getAllLocationsForRegion(selectedRegionArea.getIndexNumber());
        selectedRoute = routes.iterator().next();
        cbRoute.setItems(FXCollections.observableArrayList(routes));
        cbRoute.setValue(selectedRoute);
    }

    private void initializeRouteComboBox() {
        routes = Database.getAllLocationsForRegion(regions.iterator().next().getIndexNumber());
        selectedRoute = routes.iterator().next();
        cbRoute.setItems(FXCollections.observableArrayList(routes));
        acbRoute = new AutoCompleteBox(cbRoute);
        cbRoute.setValue(selectedRoute);
        cbRoute.setOnAction(event -> {
            try {
                selectedRoute = cbRoute.getSelectionModel().getSelectedItem();
            } catch (Exception ex) {

            }
        });
    }

    private String generateUpdateStatement(String regionAreaIndex, String routeIndex) {
        String text = "UPDATE routes " + "SET _region_area = \"%s\"  \n" + "WHERE _id =\"%s\"; \n";
        return String.format(text, regionAreaIndex, routeIndex);
    }


}