package gui.custom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;


public abstract class AbstactCustomApplication extends Application {


    protected Parent loadStage(String file, AbstactCustomApplication abstactCustomApplication) throws IOException {
        String resourcePath = String.format("/gui/%s", file);
        URL location = getClass().getResource(resourcePath);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        fxmlLoader.setController(abstactCustomApplication);
        Parent root = fxmlLoader.load();
        root.setStyle(".root { \n" +
                "    -fx-accent: #1e74c6;\n" +
                "    -fx-focus-color: -fx-accent;\n" +
                "    -fx-base: #373e43;\n" +
                "    -fx-control-inner-background: derive(-fx-base, 35%);\n" +
                "    -fx-control-inner-background-alt: -fx-control-inner-background ;\n" +
                "}\n" +
                "\n" +
                ".label{\n" +
                "    -fx-text-fill: lightgray;\n" +
                "}\n" +
                "\n" +
                ".text-field {\n" +
                "    -fx-prompt-text-fill: gray;\n" +
                "}\n" +
                "\n" +
                ".titulo{\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-size: 18px;\n" +
                "}\n" +
                "\n" +
                ".button{\n" +
                "    -fx-focus-traversable: false;\n" +
                "}\n" +
                "\n" +
                ".button:hover{\n" +
                "    -fx-text-fill: white;\n" +
                "}\n" +
                "\n" +
                ".separator *.line { \n" +
                "    -fx-background-color: #3C3C3C;\n" +
                "    -fx-border-style: solid;\n" +
                "    -fx-border-width: 1px;\n" +
                "}\n" +
                "\n" +
                ".scroll-bar{\n" +
                "    -fx-background-color: derive(-fx-base,45%)\n" +
                "}\n" +
                "\n" +
                ".button:default {\n" +
                "    -fx-base: -fx-accent ;\n" +
                "} \n" +
                "\n" +
                ".table-view{\n" +
                "    /*-fx-background-color: derive(-fx-base, 10%);*/\n" +
                "    -fx-selection-bar-non-focused: derive(-fx-base, 50%);\n" +
                "}\n" +
                "\n" +
                ".table-view .column-header .label{\n" +
                "    -fx-alignment: CENTER_LEFT;\n" +
                "    -fx-font-weight: none;\n" +
                "}\n" +
                "\n" +
                ".list-cell:even,\n" +
                ".list-cell:odd,\n" +
                ".table-row-cell:even,\n" +
                ".table-row-cell:odd{    \n" +
                "    -fx-control-inner-background: derive(-fx-base, 15%);\n" +
                "}\n" +
                "\n" +
                ".list-cell:empty,\n" +
                ".table-row-cell:empty {\n" +
                "    -fx-background-color: transparent;\n" +
                "}\n" +
                "\n" +
                ".list-cell,\n" +
                ".table-row-cell{\n" +
                "    -fx-border-color: transparent;\n" +
                "    -fx-table-cell-border-color:transparent;\n" +
                "}");
        return root;
    }
}
