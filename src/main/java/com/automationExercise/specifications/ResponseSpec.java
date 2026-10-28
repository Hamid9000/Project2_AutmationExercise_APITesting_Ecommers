package com.automationExercise.specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public final class ResponseSpec {

    private ResponseSpec() {
    }

    public static ResponseSpecification successResponse200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification createdResponse201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }

    public static ResponseSpecification unauthorizedResponse401() {
        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .build();
    }

    public static ResponseSpecification notFoundResponse404() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();

    }
}