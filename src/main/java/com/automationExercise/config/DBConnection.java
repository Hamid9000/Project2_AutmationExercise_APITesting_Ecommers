package com.automationExercise.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private DBConnection() {
        // Utility class
    }

    public static Connection getConnection() {

        String dbUrl = DBConfig.getUrl();
        String username = DBConfig.getUsername();
        String password = DBConfig.getPassword();

        if (dbUrl == null || dbUrl.isBlank()) {
            throw new RuntimeException(
                    "Database URL is not configured"
            );
        }

        if (username == null || username.isBlank()) {
            throw new RuntimeException(
                    "Database username is not configured"
            );
        }

        if (password == null || password.isBlank()) {
            throw new RuntimeException(
                    "Database password is not configured"
            );
        }

        try {

            return DriverManager.getConnection(
                    dbUrl,
                    username,
                    password
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to connect to database",
                    e
            );
        }
    }

    public static String getTableName() {

        String tableName = DBConfig.getTableName();

        if (tableName == null || tableName.isBlank()) {
            throw new RuntimeException(
                    "Database table name is not configured"
            );
        }

        return tableName;
    }
}