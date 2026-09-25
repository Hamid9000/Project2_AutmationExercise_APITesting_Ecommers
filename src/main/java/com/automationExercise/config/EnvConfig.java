package com.automationExercise.config;

import java.io.InputStream;
import java.util.Properties;

public class EnvConfig {

    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {

        String environment = System.getProperty("env");

        String fileName;

        // Default configuration when environment is not provided
        if (environment == null || environment.isBlank()) {
            fileName = "config/config.properties";
        } else {
            fileName = "config/config-" + environment + ".properties";
        }

        try (InputStream input =
                     EnvConfig.class
                             .getClassLoader()
                             .getResourceAsStream(fileName)) {

            if (input == null) {
                throw new RuntimeException(
                        fileName + " not found"
                );
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to load " + fileName,
                    e
            );
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public static String getApiLogging() {
        return properties.getProperty("api.logging");
    }

    public static String getTimeout() {
        return properties.getProperty("timeout");
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}