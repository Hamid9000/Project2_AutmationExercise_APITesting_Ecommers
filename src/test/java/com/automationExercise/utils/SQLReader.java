package com.automationExercise.utils;

import com.automationExercise.config.DBConnection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SQLReader {

    private static final Properties properties = new Properties();

    static {
        loadQueries();
    }

    private SQLReader() {
        // Utility class
    }

    private static void loadQueries() {

        try (InputStream input =
                     SQLReader.class
                             .getClassLoader()
                             .getResourceAsStream(
                                     "db/sql-queries.properties"
                             )) {

            if (input == null) {
                throw new RuntimeException(
                        "db/sql-queries.properties not found"
                );
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to load db/sql-queries.properties",
                    e
            );
        }
    }

    public static Object[][] getProductData() {

        String query =
                properties.getProperty("product.search");

        if (query == null || query.isBlank()) {
            throw new RuntimeException(
                    "SQL query 'product.search' is not configured"
            );
        }

        query = query.replace(
                "${table}",
                DBConnection.getTableName()
        );

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            List<Object[]> data = new ArrayList<>();

            while (resultSet.next()) {

                String productName =
                        resultSet.getString("product_name");

                data.add(new Object[]{productName});
            }

            if (data.isEmpty()) {
                throw new RuntimeException(
                        "No product data found in database"
                );
            }

            return data.toArray(new Object[0][]);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to read product data from database",
                    e
            );
        }
    }
}