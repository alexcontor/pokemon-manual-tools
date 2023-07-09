package constants;

import java.io.File;
import java.util.StringJoiner;

public class Extensions {

    private static final String ROOT_FOLDER = ".";
    private static final String EXTENSIONS_FOLDER = "extensions";
    private static final String CRX_FILE_SUFFIX = ".crx";

    public static File getExtensionByCode(String extension) {
        StringJoiner joiner = new StringJoiner(File.separator);
        joiner.add(ROOT_FOLDER);
        joiner.add(EXTENSIONS_FOLDER);
        joiner.add(extension.concat(CRX_FILE_SUFFIX));
        return new File(joiner.toString());
    }

}
