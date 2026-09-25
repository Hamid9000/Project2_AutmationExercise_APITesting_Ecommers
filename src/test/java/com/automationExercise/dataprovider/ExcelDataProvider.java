package com.automationExercise.dataprovider;

import com.automationExercise.utils.ExcelReader;
import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    @DataProvider(name = "excelProductData")
    public Object[][] getProductData() {

        return ExcelReader.getProductData();
    }
}