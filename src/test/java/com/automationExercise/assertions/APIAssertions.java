package com.automationExercise.assertions;

import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Collection;

public class APIAssertions {

    private APIAssertions() {
        // Utility class
    }

    public static void assertStatusCode(
            Response response,
            int expectedStatusCode) {

        Assert.assertEquals(
                response.statusCode(),
                expectedStatusCode,
                "Unexpected HTTP status code: "
                        + response.statusCode()
        );
    }

    public static void assertNotNull(
            Object actual,
            String message) {

        Assert.assertNotNull(actual, message);
    }

    public static void assertNotEmpty(
            Collection<?> collection,
            String message) {

        Assert.assertFalse(
                collection == null || collection.isEmpty(),
                message
        );
    }

    public static void assertTrue(
            boolean condition,
            String message) {

        Assert.assertTrue(condition, message);
    }
}