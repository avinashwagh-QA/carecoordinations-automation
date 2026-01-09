package com.carecoordination.healthcare.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties prop = new Properties();

    static {
        // load config file
        try {
            loadProperties("Config.properties");
            loadProperties("Roles.properties");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties files", e);
        }
    }

    private static void loadProperties(String fileName) throws IOException {
        InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream(fileName);
        if (input == null) {
            throw new RuntimeException("Properties file not found:" + fileName);
        }
        prop.load(input);
    }

    public static String getProperty(String key) {
        /*
        if (value == null) {
            throw new RuntimeException("Properties not found key" + key);
        }

 */
        return prop.getProperty(key);
    }
}
