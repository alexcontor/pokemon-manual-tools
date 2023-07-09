package constants;

public class ConfigurationProperty {

    private ConfigurationProperty() {
        //Private constructor to avoid instantiation of constants class
    }

    public static final String LOAD_EXTENSIONS = "load.extensions";
    public static final String OPERATIVE_SYSTEM = "current.os";
    public static final String DRIVER = "current.driver";
    public static final String DRIVER_VERSION = "current.driver.version";
    public static final String IMPLICIT_TIMEOUT_SECONDS = "driver.implicit.timeout.seconds";
    public static final String MAXIMIZED_SCREEN_ENABLED = "driver.window.mode.maximized";
    public static final String CAPTURE_NON_VISIBLE_ELEMENTS = "capture.non.visible.elements";

    public static final String DELAY_BETWEEN_STEPS = "delay.between.steps.seconds";

}
