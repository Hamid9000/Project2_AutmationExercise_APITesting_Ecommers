package com.automationExercise.tests;

import com.automationExercise.assertions.APIAssertions;
import com.automationExercise.constants.HTTPConstants;
import com.automationExercise.dataprovider.ExcelDataProvider;
import com.automationExercise.models.response.ProductResponse;
import com.automationExercise.services.ProductService;
import com.automationExercise.utils.GsonUtils;
import com.automationExercise.utils.RetryAnalyzer;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class ProductTest {

    private static final Logger log =
            LoggerFactory.getLogger(ProductTest.class);

    private final ProductService productService =
            new ProductService();

    @Test(
            groups = "smoke",
            description = "TC01 - Verify that the customer can retrieve the complete list of available products",
            retryAnalyzer = RetryAnalyzer.class
    )
    public void getAllProductsList() {

        log.info("Starting TC01 - Retrieve all products");

        // Send GET request
        Response response = productService.getAllProducts();

        log.info(
                "GET All Products API response status: {}",
                response.statusCode()
        );

        // Verify HTTP status code
        APIAssertions.assertStatusCode(
                response,
                HTTPConstants.OK
        );

        // Deserialize response into ProductResponse POJO
        ProductResponse productResponse =
                GsonUtils.fromJson(
                        response,
                        ProductResponse.class
                );

        // Verify products list is present
        APIAssertions.assertNotNull(
                productResponse.getProducts(),
                "Products list should be present in the response"
        );

        // Verify products list is not empty
        APIAssertions.assertNotEmpty(
                productResponse.getProducts(),
                "Products list should not be empty"
        );

        log.info(
                "Total products retrieved: {}",
                productResponse.getProducts().size()
        );

        // Verify first product ID
        APIAssertions.assertTrue(
                productResponse.getProducts().get(0).getId() > 0,
                "Product ID should be greater than 0"
        );

        // Verify first product name
        APIAssertions.assertNotNull(
                productResponse.getProducts().get(0).getName(),
                "Product name should be present"
        );

        log.info("TC01 completed successfully");
    }
    @Test(
            groups = "smoke",
            description = "TC_API_003 - Verify that the customer can search for a product using a valid product name",
            dataProvider = "excelProductData",
            dataProviderClass = ExcelDataProvider.class,
            retryAnalyzer = RetryAnalyzer.class
    )
    public void searchProduct(String productName) {

        log.info(
                "Starting TC_API_003 - Search product: {}",
                productName
        );

        Response response =
                productService.searchProduct(productName);

        log.info(
                "Search Product API response status: {}",
                response.statusCode()
        );

        APIAssertions.assertStatusCode(
                response,
                HTTPConstants.OK
        );

        ProductResponse productResponse =
                GsonUtils.fromJson(
                        response,
                        ProductResponse.class
                );

        APIAssertions.assertNotNull(
                productResponse.getProducts(),
                "Products list should be present in the search response"
        );

        APIAssertions.assertNotEmpty(
                productResponse.getProducts(),
                "Search results should not be empty"
        );

        APIAssertions.assertTrue(
                productResponse.getProducts().get(0).getId() > 0,
                "Product ID should be greater than 0"
        );

        APIAssertions.assertNotNull(
                productResponse.getProducts().get(0).getName(),
                "Product name should be present"
        );

        log.info(
                "Search completed successfully for product: {}",
                productName
        );

        log.info("TC_API_003 completed successfully");
    }

}