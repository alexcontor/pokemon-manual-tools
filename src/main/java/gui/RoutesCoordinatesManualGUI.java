package gui;

import database.Database;
import enums.Language;
import enums.RouteShape;
import gui.custom.AbstactCustomApplication;
import gui.custom.AutoCompleteBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.testng.collections.CollectionUtils;
import pojos.Region;
import pojos.RegionArea;
import pojos.Route;
import utils.PokemonSeleniumUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static constants.DatabaseConstants.MANUAL;

public class RoutesCoordinatesManualGUI extends AbstactCustomApplication {

    private static Route selectedRoute;
    private static RegionArea selectedRegionArea;
    private static Region selectedRegion;

    private static List<Region> selectedRegions;
    private static List<RegionArea> selectedRegionAreas;
    private static List<Route> selectedRoutes;

    @FXML
    private TextArea taInsert;

    @FXML
    private Label errorText, posX, posY;

    @FXML
    private Button registerPosition;

    @FXML
    private StackPane imageStackPane;

    @FXML
    private ImageView imageMap;

    @FXML
    private ScrollPane imageContainer;

    @FXML
    private CheckBox cbAllRoutes;

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
    private RadioButton rbRectangle, rbPolygon;

    private List<Double> xPoints = new ArrayList<>();
    private List<Double> yPoints = new ArrayList<>();

    boolean registering;

    private double imageX;
    private double imageY;

    @FXML
    protected void generateInsertStatement() {
        selectedRoute.setCoords(parseCoords(xPoints, yPoints));
        selectedRoute.setRouteShape(getRouteShape());
        updateRouteCoords(selectedRoute);
        errorText.setText("Valid!");
        errorText.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
        taInsert.setText(String.format("Last inserted route is %s", selectedRoute.getLocalizedName(Language.EN)));
        clearPoints();
        refreshRoutes(cbAllRoutes.isSelected());
    }

    private RouteShape getRouteShape() {
        if (rbPolygon.isSelected()) {
            return RouteShape.POLYGON;
        } else {
            return RouteShape.RECT;
        }
    }

    private String parseCoords(List<Double> xPoints, List<Double> yPoints) {
        StringJoiner joiner = new StringJoiner(",");
        for (int i = 0; i < xPoints.size(); i++) {
            joiner.add(String.valueOf(xPoints.get(i).intValue()));
            joiner.add(String.valueOf(yPoints.get(i).intValue()));
        }
        return joiner.toString();
    }

    @FXML
    protected void register() {
        if (registering) {
            registerPosition.setText("Register");
            registering = false;
            draw();
            rbRectangle.setDisable(false);
            rbPolygon.setDisable(false);
        } else {
            registering = true;
            registerPosition.setText("Stop drawing");
            rbRectangle.setDisable(true);
            rbPolygon.setDisable(true);
            clearPoints();
        }
    }

    @FXML
    protected void removeRouteCoords() {
        selectedRoute.setCoords("null");
        selectedRoute.setRouteShape(null);
        updateRouteCoords(selectedRoute);
        imageStackPane.getChildren().removeIf(node -> node.getId().equals(selectedRoute.getIndexNumber()));
    }

    @FXML
    protected void removeDrawCoords() {
        List<Node> children = imageStackPane.getChildren();
        children.remove(children.size() - 1);
        clearPoints();

    }

    private void updateRouteCoords(Route selectedRoute) {
        RouteShape routeShape = selectedRoute.getRouteShape();
        String insertStatement = generateUpdateStatement(selectedRoute.getCoords(), Objects.nonNull(routeShape) ? String.valueOf(routeShape.getIndex()) : "", selectedRoute.getIndexNumber());
        PokemonSeleniumUtils.insertToDatabase(insertStatement, PokemonSeleniumUtils.generateStatement(MANUAL + "routes_coords"));
    }

    protected void draw() {
        if (CollectionUtils.hasElements(xPoints) && CollectionUtils.hasElements(yPoints)) {
            errorText.setVisible(false);
            Canvas canvas = new Canvas(imageX, imageY);
            if (rbRectangle.isSelected()) {
                drawRectangle(canvas.getGraphicsContext2D(), xPoints, yPoints, Color.RED, selectedRoute.getLocalizedName(Language.EN));
            } else {
                drawPolygon(canvas.getGraphicsContext2D(), xPoints, yPoints, Color.RED, selectedRoute.getLocalizedName(Language.EN));
            }
            refreshCoords();
            imageStackPane.getChildren().add(canvas);
        } else {
            errorText.setVisible(true);
            errorText.setText("Please add coordinates");
            errorText.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        }

    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadStage("routes-coordinates-gui.fxml", this);
        Scene scene = new Scene(root, 620, 640);
        stage.setTitle("Hello!");

        initializeRegionComboBox();
        initializeRegionAreasComboBox();
        initializeRouteComboBox();
        initializeImage();
        initializeChecbox();

        stage.setScene(scene);
        stage.show();

    }

    private void initializeChecbox() {
        cbAllRoutes.selectedProperty().addListener((observable, oldValue, isSelected) -> {
            refreshRoutes(isSelected);
        });
    }

    private void initializeImage() {

        refreshMap();

        refreshRoutesCoords();

        refreshListeners();

    }

    private void refreshListeners() {
        imageStackPane.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            if (registering) {
                xPoints.add(x);
                yPoints.add(y);
                refreshCoords();
            }
        });
    }

    private void refreshRoutesCoords() {
        imageStackPane.getChildren().clear();
        imageStackPane.getChildren().add(imageMap);
        for (Route route : Database.getAllLocationsForRegion(selectedRegionArea.getIndexNumber())) {
            String coords = route.getCoords();
            RouteShape shape = route.getRouteShape();
            if (Objects.nonNull(shape) && StringUtils.isNotEmpty(coords)) {
                Canvas canvas = new Canvas(imageX, imageY);
                canvas.setId(route.getIndexNumber());
                List<Double> xPoints = new ArrayList<>();
                List<Double> yPoints = new ArrayList<>();
                parsePoints(coords, xPoints, yPoints);
                switch (shape) {
                    case RECT:
                        drawRectangle(canvas.getGraphicsContext2D(), xPoints, yPoints, getColor(route), route.getLocalizedName(Language.EN));
                        break;
                    case POLYGON:
                        drawPolygon(canvas.getGraphicsContext2D(), xPoints, yPoints, getColor(route), route.getLocalizedName(Language.EN));
                        break;
                }
                imageStackPane.getChildren().add(canvas);
            }
        }
    }

    private Color getColor(Route route) {
        Color color = Color.GREEN;
        if (Objects.nonNull(selectedRoute) && route.equals(selectedRoute)) {
            color = Color.RED;
        }
        return color;
    }

    private void parsePoints(String coords, List<Double> xPoints, List<Double> yPoints) {
        String[] coordsStringArray = coords.split(",");
        for (int i = 0; i < coordsStringArray.length; i++) {
            xPoints.add(Double.valueOf(coordsStringArray[i]));
            yPoints.add(Double.valueOf(coordsStringArray[i + 1]));
            i++;
        }

    }

    private void refreshMap() {
        Image map = new Image(getClass().getResource(String.format("/images/map/%s.png", selectedRegionArea.getIndexNumber())).toString());
        imageMap.setImage(map);
        imageX = imageMap.getImage().getWidth();
        imageY = imageMap.getImage().getHeight();
    }

    private void refreshCoords() {
        posX.setText(xPoints.stream().map(String::valueOf).collect(Collectors.joining(",")));
        posY.setText(yPoints.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }

    /**
     * https://stackoverflow.com/questions/16563440/creating-a-rectangle-from-2-specific-points
     *
     * @param gc
     */
    private void drawRectangle(GraphicsContext gc, List<Double> xPoints, List<Double> yPoints, Color color, String text) {
        gc.setStroke(color);
        gc.setLineWidth(1);
        Rectangle rect = new Rectangle(new Point(xPoints.get(0).intValue(), yPoints.get(0).intValue()));
        rect.add(new Point(xPoints.get(1).intValue(), yPoints.get(1).intValue()));
        gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        gc.strokeText(text, rect.getX(), rect.getY());
    }

    private void drawPolygon(GraphicsContext gc, List<Double> xPoints, List<Double> yPoints, Color color, String text) {
        gc.setStroke(color);
        gc.setLineWidth(1);
        xPoints.add(xPoints.get(0));
        yPoints.add(yPoints.get(0));
        gc.strokePolygon(
                xPoints.stream().mapToDouble(Double::doubleValue).toArray(),
                yPoints.stream().mapToDouble(Double::doubleValue).toArray(), xPoints.size());
        gc.strokeText(text, xPoints.get(0), yPoints.get(0));

    }


    private void initializeRegionComboBox() {
        selectedRegions = Database.getAllRegions();
        selectedRegion = selectedRegions.iterator().next();
        cbRegion.setItems(FXCollections.observableArrayList(selectedRegions));
        acbRegion = new AutoCompleteBox(cbRegion);
        cbRegion.setValue(selectedRegion);
        cbRegion.setOnAction(event -> {
            try {
                selectedRegion = cbRegion.getSelectionModel().getSelectedItem();
                refreshRegionAreas();
                refreshRoutes(cbAllRoutes.isSelected());
                refreshMap();
            } catch (Exception ex) {

            }
        });

    }

    private void initializeRegionAreasComboBox() {
        selectedRegionAreas = Database.getAllRegionsAreasForRegion(selectedRegions.iterator().next().getIndexNumber());
        selectedRegionArea = selectedRegionAreas.iterator().next();
        cbRegionAreas.setItems(FXCollections.observableArrayList(selectedRegionAreas));
        acbRegionAreas = new AutoCompleteBox(cbRegionAreas);
        cbRegionAreas.setValue(selectedRegionArea);
        cbRegionAreas.setOnAction(event -> {
            try {
                selectedRegionArea = cbRegionAreas.getSelectionModel().getSelectedItem();
                refreshRoutes(cbAllRoutes.isSelected());
                refreshMap();
                refreshRoutesCoords();
            } catch (Exception ex) {

            }
        });
    }

    private void refreshRegionAreas() {
        List<RegionArea> regionAreas = Database.getAllRegionsAreasForRegion(selectedRegion.getIndexNumber());
        cbRegionAreas.setItems(FXCollections.observableArrayList(regionAreas));
        selectedRegionArea = regionAreas.iterator().next();
        cbRegionAreas.setValue(selectedRegionArea);
        refreshMap();
    }

    private void refreshRoutes(boolean retrieveAll) {
        selectedRoutes = Database.getAllLocationsForRegion(selectedRegionArea.getIndexNumber())
                .stream()
                .filter(route -> retrieveAll || StringUtils.isBlank(route.getCoords()))
                .collect(Collectors.toList());
        cbRoute.setItems(FXCollections.observableArrayList(selectedRoutes));
        if (CollectionUtils.hasElements(selectedRoutes)) {
            selectedRoute = selectedRoutes.iterator().next();
            cbRoute.setValue(selectedRoute);
        }
    }

    private void clearPoints() {
        xPoints.clear();
        yPoints.clear();
        refreshCoords();
    }

    private void initializeRouteComboBox() {
        selectedRoutes = Database.getAllLocationsForRegion(selectedRegions.iterator().next().getIndexNumber());
        selectedRoute = selectedRoutes.iterator().next();
        cbRoute.setItems(FXCollections.observableArrayList(selectedRoutes));
        acbRoute = new AutoCompleteBox(cbRoute);
        cbRoute.setValue(selectedRoute);
        cbRoute.setOnAction(event -> {
            try {
                selectedRoute = cbRoute.getSelectionModel().getSelectedItem();
                refreshRoutesCoords();
            } catch (Exception ex) {

            }
        });
    }

    private String generateUpdateStatement(String coords, String routeShape, String routeIndex) {
        String text = "UPDATE locations " + "SET coordinates = \"%s\", shape = \"%s\"  \n" + "WHERE _id =\"%s\"; \n";
        return String.format(text, coords, routeShape, routeIndex);
    }


}