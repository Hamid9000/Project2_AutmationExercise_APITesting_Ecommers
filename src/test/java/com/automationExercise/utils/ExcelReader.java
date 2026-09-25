package com.automationExercise.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    private ExcelReader() {
        // Utility class
    }

    public static Object[][] getProductData() {

        String filePath = "product-search.xlsx";
        String sheetName = "ProductData";

        try (
                InputStream input =
                        ExcelReader.class
                                .getClassLoader()
                                .getResourceAsStream(filePath)
        ) {

            if (input == null) {
                throw new RuntimeException(
                        filePath + " not found"
                );
            }

            Workbook workbook =
                    WorkbookFactory.create(input);

            Sheet sheet =
                    workbook.getSheet(sheetName);

            if (sheet == null) {
                workbook.close();

                throw new RuntimeException(
                        "Sheet '" + sheetName + "' not found"
                );
            }

            List<Object[]> data =
                    new ArrayList<>();

            // Start from row 1 because row 0 is header
            for (int rowIndex = 1;
                 rowIndex <= sheet.getLastRowNum();
                 rowIndex++) {

                Row row =
                        sheet.getRow(rowIndex);

                if (row == null) {
                    continue;
                }

                Cell cell =
                        row.getCell(0);

                if (cell == null) {
                    continue;
                }

                String productName =
                        cell.getStringCellValue().trim();

                if (productName.isBlank()) {
                    continue;
                }

                data.add(
                        new Object[]{productName}
                );
            }

            workbook.close();

            if (data.isEmpty()) {
                throw new RuntimeException(
                        "No product data found in "
                                + filePath
                );
            }

            return data.toArray(
                    new Object[0][]
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to read product data from Excel",
                    e
            );
        }
    }
}