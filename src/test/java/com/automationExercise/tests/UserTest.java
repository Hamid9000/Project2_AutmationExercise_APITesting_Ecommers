
package com.automationExercise.tests;

import com.automationExercise.services.UserService;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

public class UserTest {

    private static final Logger logger =
            LogManager.getLogger(UserTest.class);

    private final UserService userService = new UserService();

    @Test(groups = {"smoke"},
            description = "Test Case No: 8 - Verify User Login With Valid Credentials"
    )
    public void verifyUserLoginWithValidCredentials() {

        String email = "vimuzebyku@mailinator.com";
        String password = "Pa$$w0rd!";

        logger.info("Starting login test for email: {}", email);

        Response response = userService.loginUser(email, password);

        logger.info("Login API response status code: {}",
                response.getStatusCode());

        logger.info("Login API response body: {}",
                response.asString());

        response.then().statusCode(200);

        logger.info("Login test passed successfully");
    }
}
