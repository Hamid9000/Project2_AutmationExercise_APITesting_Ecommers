
package com.automationExercise.assertions;

import com.automationExercise.utils.GsonUtils;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.Collection;

public class APIAssertions {

    private static final Logger log =
            LoggerFactory.getLogger(APIAssertions.class);

    private APIAssertions() {
        // Utility class
    }

    public static void assertStatusCode(
            Response response,
            int expectedStatusCode) {

        int actualStatusCode = response.statusCode();

        if (actualStatusCode != expectedStatusCode) {

            String message = "Status code does not match";

            log.error("Assertion Failed: {}", message);
            log.error("Expected Status Code: {}", expectedStatusCode);
            log.error("Actual Status Code: {}", actualStatusCode);
            log.error("Response Body: {}",
                    GsonUtils.prettyPrint(response));

        } else {
            log.info("Assertion Passed: Status Code");
            log.info("Status Code: {}", actualStatusCode);
        }


        Assert.assertEquals(
                actualStatusCode,
                expectedStatusCode,
                "Unexpected HTTP status code: " + actualStatusCode
        );
    }

    public static void assertNotNull(
            Object actual,
            String message,
            Response response) {

        if (actual == null) {
            log.error("Assertion Failed: {}", message);
            log.error("Response Body: {}",
                    GsonUtils.prettyPrint(response));
        }

        Assert.assertNotNull(actual, message);
    }

    public static void assertNotEmpty(
            Collection<?> collection,
            String message,
            Response response) {

        if (collection == null || collection.isEmpty()) {
            log.error("Assertion Failed: {}", message);
            log.error("Response Body: {}",
                    GsonUtils.prettyPrint(response));
        }

        Assert.assertFalse(
                collection == null || collection.isEmpty(),
                message
        );
    }

    public static void assertTrue(
            boolean condition,
            String message,
            Response response) {

        if (!condition) {
            log.error("Assertion Failed: {}", message);
            log.error("Response Body: {}",
                    GsonUtils.prettyPrint(response));
        }

        Assert.assertTrue(condition, message);
    }
    public static void assertEquals(
            Object actual,
            Object expected,
            String message,
            Response response) {

        if (!java.util.Objects.equals(actual, expected)) {
            log.error("Assertion Failed: {}", message);
            log.error("Expected Value: {}", expected);
            log.error("Actual Value: {}", actual);
            log.error("Response Body: {}",
                    GsonUtils.prettyPrint(response));
        }

        Assert.assertEquals(actual, expected, message);
    }
}
