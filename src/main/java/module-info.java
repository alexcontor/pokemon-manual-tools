module gaui {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires testng;
    requires selenium.api;
    requires org.apache.commons.lang3;
    requires selenium.chrome.driver;
    requires selenium.edge.driver;
    requires selenium.firefox.driver;
    requires selenium.remote.driver;
    requires selenium.support;
    requires java.desktop;
    requires com.google.common;
    requires jackson.databind;
    requires com.luciad.imageio.webp;
    requires net.coobird.thumbnailator;
    requires org.apache.commons.text;
    requires org.apache.commons.io;
    requires tinify;
    requires deepl.java;

    opens gui to javafx.fxml;
    exports gui;
    exports pojos;
    opens pojos to javafx.fxml;

}