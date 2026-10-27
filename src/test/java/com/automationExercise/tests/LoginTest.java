package com.automationExercise.tests;

import com.automationExercise.assertions.APIAssertions;
import com.automationExercise.base.CommonToAllTest;
import com.automationExercise.constants.HTTPConstants;
import com.automationExercise.endpoints.Endpoints;
import com.automationExercise.services.LoginService;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class LoginTest extends CommonToAllTest {
    LoginService loginService = new LoginService();

    private static final Logger logger =
            LogManager.getLogger(LoginTest.class);


    @Test(
            description = "Test Case No 7:  - Verify User Login With Valid Credentials",
            groups = {"smoke"},
            priority = 1
    )
    public void verifyUserLoginWithValidCredentials(ITestContext context) {

        String email = "vimuzebyku@mailinator.com";
        String password = "Pa$$w0rd!";

        // Store credentials in ITestContext
        context.setAttribute("email", email);
        context.setAttribute("password", password);

        logger.info("========== LOGIN TEST STARTED ==========");
        logger.info("Test Case: TC USER 007");
        logger.info("Request: POST {}", Endpoints.VERIFY_LOGIN);
        logger.info("Submitting login request for email: {}", email);
        Response response = loginService.loginUser(email, password);

        logger.info("Login API response status code: {}",
                response.getStatusCode());

        logger.info("Login API response body: {}",
                response.asString());

        response.then().statusCode(200);


        logger.info("Login test completed successfully");
        logger.info("========== LOGIN TEST FINISHED ==========");
        System.out.println();
        System.out.println();


    }

    @Test(
            description = "TC USER 008 -Verify that login without email parameter handle corrrectly",
            groups = {"smoke"},
            priority = 3
    )
    public void postLoginWithoutEmailParameter() {
        String password = "svfsvgsv";

        logger.info("========== LOGIN TEST STARTED ==========");
        logger.info("Test Case: TC USER 007");
        logger.info("Request: POST {}", Endpoints.VERIFY_LOGIN);
        logger.info("Submitting login request for password: {}", password);
        Response response = loginService.postLoginWithoutEmailParameter(password);

        APIAssertions.assertStatusCode(response, HTTPConstants.OK);

        // Error code inside JSON response body
        APIAssertions.assertEquals(
                response.jsonPath().getInt("responseCode"),
                400,
                "Response code does not match",
                response
        );

        // Validate error message
        APIAssertions.assertEquals(
                response.jsonPath().getString("message"),
                "Bad request, email or password parameter is missing in POST request.",
                "Response message does not match",
                response
        );

        logger.info("Login test completed successfully");
        logger.info("========== LOGIN TEST FINISHED ==========");
        System.out.println();
        System.out.println();

    }

    @Test( description = "TC USER 009 -Verify that registered email id delete successfully",
    groups = {"smoke"},
    dependsOnMethods ={"verifyUserLoginWithValidCredentials"},
            priority = 2
    )
    public void deleteRegisteredEmailId(ITestContext context){

        String email = (String) context.getAttribute("email");
        String password =(String) context.getAttribute("password");

        logger.info("========== LOGIN TEST STARTED ==========");
        logger.info("Test Case: TC USER 007");
        logger.info("Request: POST {}", Endpoints.VERIFY_LOGIN);
        logger.info("Submitting login request for email: {}", email);
        Response response = loginService.deleteToVerifyLogin(email,password);

        APIAssertions.assertStatusCode(response,HTTPConstants.OK);

        APIAssertions.assertEquals(response.jsonPath().getInt("responseCode"),405,"Response code dose not match",response);
        APIAssertions.assertEquals(response.jsonPath().getString("message"),"This request method is not supported.","Responce message does not match",response);

        logger.info("Login test completed successfully");
        logger.info("========== LOGIN TEST FINISHED ==========");
        System.out.println();
        System.out.println();

    }
}