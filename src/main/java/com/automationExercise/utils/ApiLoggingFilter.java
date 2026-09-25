package com.automationExercise.utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiLoggingFilter implements Filter {

    private static final Logger logger =
            LogManager.getLogger(ApiLoggingFilter.class);

    @Override
    public Response filter(
            FilterableRequestSpecification requestSpec,
            FilterableResponseSpecification responseSpec,
            FilterContext context) {

        Response response = context.next(requestSpec, responseSpec);

        int statusCode = response.statusCode();

        if (statusCode >= 400) {

            // API Failure
            logger.error("========== API FAILURE ==========");
            logger.error("Method: {}", requestSpec.getMethod());
            logger.error("URI: {}", requestSpec.getURI());
            logger.error("Status Code: {}", statusCode);
            logger.error(
                    "Response Body: {}",
                    GsonUtils.prettyPrint(response)
            );

        } else {

            // API Success
            logger.info("========== API SUCCESS ==========");
            logger.info("Method: {}", requestSpec.getMethod());
            logger.info("URI: {}", requestSpec.getURI());
            logger.info("Status Code: {}", statusCode);

            // Response body only in DEBUG mode
            logger.debug(
                    "Response Body: {}",
                    GsonUtils.prettyPrint(response)
            );
        }

        return response;
    }
}