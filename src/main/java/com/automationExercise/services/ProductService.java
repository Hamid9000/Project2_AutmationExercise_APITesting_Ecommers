package com.automationExercise.services;

import com.automationExercise.builders.RequestBuilder;
import com.automationExercise.endpoints.Endpoints;
import io.restassured.response.Response;

public class ProductService {

    public Response getAllProducts() {

        return RequestBuilder.build()
                .when()
                .get(Endpoints.PRODUCTS_LIST);
    }

    public Response postAllProducts() {

        return RequestBuilder.build()
                .when()
                .post(Endpoints.PRODUCTS_LIST);
    }

    public Response searchProduct(String product) {

        return RequestBuilder.build()
                .contentType("application/x-www-form-urlencoded")
                .formParam("search_product", product)
                .when()
                .post(Endpoints.SEARCH_PRODUCT);
    }
}