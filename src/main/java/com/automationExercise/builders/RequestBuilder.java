package com.automationExercise.builders;

import com.automationExercise.config.EnvConfig;
import com.automationExercise.utils.ApiLoggingFilter;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class RequestBuilder {

    public static RequestSpecification build() {

        return given()
                .baseUri(EnvConfig.getBaseUrl())
                .contentType("application/json")
                .filter(new ApiLoggingFilter());
    }
}