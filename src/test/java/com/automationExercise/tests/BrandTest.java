package com.automationExercise.tests;

import com.automationExercise.assertions.APIAssertions;
import com.automationExercise.constants.HTTPConstants;
import com.automationExercise.services.BrandService;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class BrandTest {

    BrandService brandService = new BrandService();
    @Test(
            description = "TC_BRAND_004 - Verify PUT do not support all brand list",
            groups = {"smaoke"}
    )
    public void postAllBrandsList(){

        Response response =brandService.putALLBrandList();
        APIAssertions.assertStatusCode(response, HTTPConstants.METHOD_NOT_ALLOWED);

    }
}
