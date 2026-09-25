package com.automationExercise.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    private CSVReader() {
        // Utility class
    }

    public static Object[][] getProductData() {

        String filePath = "product-search.csv";

        try (
                InputStream input =
                        CSVReader.class
                                .getClassLoader()
                                .getResourceAsStream(filePath)
        ) {

            if (input == null) {
                throw new RuntimeException(
                        filePath + " not found"
                );
            }

            List<Object[]> data = new ArrayList<>();

            try (
                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            input,
                                            StandardCharsets.UTF_8
                                    )
                            )
            ) {

                // Skip CSV header
                reader.readLine();

                String line;

                while ((line = reader.readLine()) != null) {

                    if (line.isBlank()) {
                        continue;
                    }

                    String productName = line.trim();

                    data.add(
                            new Object[]{productName}
                    );
                }
            }

            if (data.isEmpty()) {
                throw new RuntimeException(
                        "No product data found in " + filePath
                );
            }

            return data.toArray(new Object[0][]);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to read product data from CSV",
                    e
            );
        }
    }
}