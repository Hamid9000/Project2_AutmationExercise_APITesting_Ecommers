package com.automationExercise.config;

public class AuthConfig {

    public static String getUsername() {
        return EnvConfig.getProperty("auth.username");
    }

    public static String getPassword() {
        return EnvConfig.getProperty("auth.password");
    }

    public static String getToken() {
        return EnvConfig.getProperty("auth.token");
    }
}