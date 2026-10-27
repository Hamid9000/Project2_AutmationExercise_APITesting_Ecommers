package com.automationExercise.tests;

import com.automationExercise.assertions.APIAssertions;
import com.automationExercise.constants.HTTPConstants;
import com.automationExercise.dataprovider.ExcelDataProvider;
import com.automationExercise.models.response.ProductResponse;
import com.automationExercise.services.ProductService;
import com.automationExercise.utils.GsonUtils;
import com.automationExercise.utils.RetryAnalyzer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import io.qameta.allure.internal.shadowed.jackson.databind.JsonNode;
import io.restassured.mapper.ObjectMapper;
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
            groups = {"smoke"},
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
                "Products list should be present in the response" ,response
        );

        // Verify products list is not empty
        APIAssertions.assertNotEmpty(
                productResponse.getProducts(),
                "Products list should not be empty" ,response
        );

        log.info(
                "Total products retrieved: {}",
                productResponse.getProducts().size()
        );

        // Verify first product ID
        APIAssertions.assertTrue(
                productResponse.getProducts().get(0).getId() > 0,
                "Product ID should be greater than 0" ,response
        );

        // Verify first product name
        APIAssertions.assertNotNull(
                productResponse.getProducts().get(0).getName(),
                "Product name should be present" ,response
        );

        log.info("TC01 completed successfully");
    }

    @Test(
            groups = {"smoke"},
            description = "TC_PROD_002 - Verify POST method is not supported"
    )
    public void postToAllProductList() {

        Response response = productService.postAllProducts();

        APIAssertions.assertStatusCode(
                response,
                HTTPConstants.METHOD_NOT_ALLOWED
        );
    }
    @Test(
            groups = {"smaoke"},
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
                productService.postSearchProduct(productName);

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
                "Products list should be present in the search response",response
        );

        APIAssertions.assertNotEmpty(
                productResponse.getProducts(),
                "Search results should not be empty",response
        );

        APIAssertions.assertTrue(
                productResponse.getProducts().get(0).getId() > 0,
                "Product ID should be greater than 0",response
        );

        APIAssertions.assertNotNull(
                productResponse.getProducts().get(0).getName(),
                "Product name should be present",response
        );

        log.info(
                "Search completed successfully for product: {}",
                productName
        );

        log.info("TC_API_003 completed successfully");
    }

    @Test(
            description = "TC_PROD_005 - Verify POST search product successfully",
            groups = {"smoke"}
    )
    public void postToSearchProduct() {

        String productName = "tshirt";

        // Send POST request
        Response response =
                productService.postSearchProduct(productName);

        // Verify HTTP status code
        APIAssertions.assertStatusCode(
                response,
                HTTPConstants.OK
        );

        // Deserialize JSON response into POJO
        ProductResponse productResponse =
                GsonUtils.fromJson(
                        response,
                        ProductResponse.class
                );

        // Verify products list is present
        APIAssertions.assertNotNull(
                productResponse.getProducts(),
                "Products list should be present", response
        );

        // Verify products list is not empty
        APIAssertions.assertNotEmpty(
                productResponse.getProducts(),
                "Search results should not be empty", response
        );

        // Verify that returned products match the search term
        APIAssertions.assertTrue(
                productResponse.getProducts()
                        .stream()
                        .anyMatch(product ->
                                product.getName() != null
                                        && product.getName()
                                        .toLowerCase()
                                        .contains(productName.toLowerCase())
                        ),
                "At least one product name should contain: "
                        + productName, response
        );


        //  System.out.println(response.asPrettyString());
        // System.out.println(response.then().log().all());
        //  System.out.println(response());
        //System.out.println(response.prettyPeek());
        //System.out.println(response.prettyPrint());
        String json = response.asString();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        System.out.println(
                gson.toJson(JsonParser.parseString(json)));
    }
    @Test(
            description = "TC_PROD_006 - Verify that search without product parameter is handled correctly",
            groups = {"smoke"}
    )
    public void postTosSearchWithoutProductParameter() {

        Response response = productService.postSearchProductWithoutParameter();

        // 1. Verify HTTP status code
        APIAssertions.assertStatusCode(
                response,
                HTTPConstants.OK
        );

        // 2. Verify response body status code
        APIAssertions.assertEquals(
                response.jsonPath().getInt("responseCode"),
                400,
                "Response body responseCode does not match",
                response
        );

        // 3. Verify response body message
        APIAssertions.assertEquals(
                response.jsonPath().getString("message"),
                "Bad request, search_product parameter is missing in POST request.",
                "Response body message does not match",
                response
        );

    }
}