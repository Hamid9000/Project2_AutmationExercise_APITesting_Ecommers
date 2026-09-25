package com.automationExercise.dataprovider;

import com.automationExercise.utils.CSVReader;
import org.testng.annotations.DataProvider;

public class CSVDataProvider {

    @DataProvider(name = "csvProductData")
    public Object[][] getProductData() {

        return CSVReader.getProductData();
    }
}