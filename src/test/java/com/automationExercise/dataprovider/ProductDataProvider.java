package com.automationExercise.dataprovider;

import com.automationExercise.utils.SQLReader;
import org.testng.annotations.DataProvider;

public class ProductDataProvider {

    @DataProvider(name = "productData")
    public Object[][] getProductData() {

        return SQLReader.getProductData();
    }
}