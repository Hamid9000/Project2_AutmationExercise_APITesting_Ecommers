package com.automationExercise.config;

import java.io.InputStream;
import java.util.Properties;

public class DBConfig {

    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {

        String environment = System.getProperty("env");

        String fileName;

        if (environment == null || environment.isBlank()) {
            fileName = "db.properties";
        } else {
            fileName = "db-" + environment + ".properties";
        }

        try (InputStream input =
                     DBConfig.class
                             .getClassLoader()
                             .getResourceAsStream("db/" + fileName)) {

            if (input == null) {
                throw new RuntimeException(
                        "db/" + fileName + " not found"
                );
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to load db/" + fileName,
                    e
            );
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }

    public static String getTableName() {
        return properties.getProperty("db.table");
    }
}