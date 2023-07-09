package utils.properties;

import constants.PropertiesFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;


public final class PropertiesHolder {

    private static PropertiesHolder propertiesHolder;

    private final Properties properties = new Properties();

    private PropertiesHolder() {
        final URL seleniumPropertiesFile = this.getClass().getClassLoader().getResource(PropertiesFile.GLOBAL_PROPERTIES_FILE);
        if (seleniumPropertiesFile == null) {
            System.out.println("No config.properties file could not be found in the project classpath");
        } else {
            try (InputStream input = new FileInputStream(seleniumPropertiesFile.getPath())) {
                properties.load(input);
            } catch (IOException e) {
                System.out.println("An error occurred while reading selenium properties file");
            }
        }
    }

    public static PropertiesHolder getInstance() {
        if (propertiesHolder == null) {
            propertiesHolder = new PropertiesHolder();
        }
        return propertiesHolder;
    }

    public String getProperty(final String propertyKey) {
        final String systemProperty = System.getProperty(propertyKey);
        return systemProperty != null ? systemProperty : (String) properties.get(propertyKey);
    }

}
