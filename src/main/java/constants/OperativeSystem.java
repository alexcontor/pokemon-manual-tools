package constants;

import java.io.File;

public class OperativeSystem {

    public static final String SEPARATOR = File.separator;

    private OperativeSystem() {
        //Private constructor to avoid instantiation of constants class
    }

    public static final String WINDOWS = "windows";
    public static final String LINUX = "linux";

}
