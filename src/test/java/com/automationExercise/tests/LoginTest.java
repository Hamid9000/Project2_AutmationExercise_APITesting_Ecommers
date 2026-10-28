package com.automationExercise.tests;

import com.automationExercise.assertions.APIAssertions;
import com.automationExercise.base.CommonToAllTest;
import com.automationExercise.constants.HTTPConstants;
import com.automationExercise.endpoints.Endpoints;
import com.automationExercise.services.LoginService;
import com.automationExercise.specifications.ResponseSpec;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class LoginTest extends CommonToAllTest {
    LoginService loginService = new LoginService();

    private static final Logger logger =
            LogManager.getLogger(LoginTest.class);


    @Test(
            description = "Test Case No 7:  - Verify User Login With Valid Credentials",
            groups = {"smoke"}

    )
    public void userLoginWithValidCredentials(ITestContext context) {

        String email = "vimuzebyku@mailinator.com";
        String password = "Pa$$w0rd!";

        // Store credentials in ITestContext
        context.setAttribute("email", email);
        context.setAttribute("password", password);

        logger.info("========== LOGIN TEST STARTED ==========");
        logger.info("Test Case: TC USER 007");
        logger.info("Request: POST {}", Endpoints.VERIFY_LOGIN);
        logger.info("Submitting login request for email: {}", email);
        Response response = loginService.postLoginUser(email, password);

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
            groups = {"smoke"}
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

    @Test(description = "TC USER 009 -Verify that delete method login with  email and password handle properly",
            groups = {"smoke"}
    )
    public void deleteRegisteredEmailId(ITestContext context) {

        String email = (String) context.getAttribute("email");
        String password = (String) context.getAttribute("password");

        logger.info("========== LOGIN TEST STARTED ==========");
        logger.info("Test Case: TC USER 007");
        logger.info("Request: POST {}", Endpoints.VERIFY_LOGIN);
        logger.info("Submitting login request for email: {}", email);
        Response response = loginService.deleteToVerifyLogin(email, password);

        APIAssertions.assertStatusCode(response, HTTPConstants.OK);

        APIAssertions.assertEquals(response.jsonPath().getInt("responseCode"), 405, "Response code dose not match", response);
        APIAssertions.assertEquals(response.jsonPath().getString("message"), "This request method is not supported.", "Responce message does not match", response);

        logger.info("Login test completed successfully");
        logger.info("========== LOGIN TEST FINISHED ==========");
        System.out.println();
        System.out.println();

    }

    @Test(
            description = "TC_LOGIN_010 -Verify that invalid user name and password show proper Error",
            groups = {"smoke"}
    )
    public void postLoginWithInvalidcredential() {

        String email = "sfsfsdfsd";
        String password = "fsdfsf";

        Response response =
                loginService.postLoginUser(email, password);

        response.then()
                .spec(ResponseSpec.successResponse200());

        String body = response.getBody().asString();

        APIAssertions.assertEquals(response.jsonPath().getInt("responseCode"), HTTPConstants.NOT_FOUND, "Responce code does not match", response);

        APIAssertions.assertEquals(response.jsonPath().getString("message"), "User not found!", "Expected error message was not found", response);

    }

    @Test(
            description = "TC USER 11 - Verify that user can register successfully",
            groups = {"smoke", "CRUD"},
            priority = 1
    )
    public void postUserCreateAccount(ITestContext context) {

        String name = "Hamid";
        String email = "hamid" + System.currentTimeMillis() + "@example.com";
        String password = "Test@123";
        String title = "Mr";
        String birth_date = "7";
        String birth_month = "7";
        String birth_year = "1997";
        String firstname = "Hamid";
        String lastname = "Shaikh";
        String company = "TestCompany";
        String address1 = "Pune";
        String address2 = "";
        String country = "India";
        String zipcode = "411014";
        String state = "Maharashtra";
        String city = "Pune";
        String mobile_number = "9876543210";

        // Store data in ITestContext
        context.setAttribute("name", name);
        context.setAttribute("email", email);
        context.setAttribute("password", password);
        context.setAttribute("title", title);
        context.setAttribute("birth_date", birth_date);
        context.setAttribute("birth_month", birth_month);
        context.setAttribute("birth_year", birth_year);
        context.setAttribute("firstname", firstname);
        context.setAttribute("lastname", lastname);
        context.setAttribute("company", company);
        context.setAttribute("address1", address1);
        context.setAttribute("address2", address2);
        context.setAttribute("country", country);
        context.setAttribute("zipcode", zipcode);
        context.setAttribute("state", state);
        context.setAttribute("city", city);
        context.setAttribute("mobile_number", mobile_number);


        Response response = loginService.postCreateAccount(
                name, email, password, title,
                birth_date, birth_month, birth_year,
                firstname, lastname, company, address1,
                address2, country, zipcode, state, city, mobile_number
        );

        System.out.println(response.prettyPrint());
        response.then().statusCode(200);
        System.out.println(response.asString());

        response.then().statusCode(200);


        String body = response.getBody().asString();

        JsonPath jsonPath = new JsonPath(body);

        Assert.assertEquals(jsonPath.getInt("responseCode"), 201);
        Assert.assertEquals(jsonPath.getString("message"), "User created!");
    }

    @Test(description = "Verify that user delete account successfull",
            groups = {"smoke", "CRUD"},
            dependsOnMethods = {"postUserCreateAccount"},
            priority = 4)

    public void deleteUserAccount(ITestContext context) {
        String email = (String) context.getAttribute("email");
        String password = (String) context.getAttribute("password");

        Response response = loginService.deleteAccount(email, password);

        response.then().statusCode(200);
        String body = response.asString();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println(gson.toJson(JsonParser.parseString(response.asString())));
        JsonPath jsonPath = new JsonPath(body);

        Assert.assertEquals(jsonPath.getInt("responseCode"), 200);
        Assert.assertEquals(jsonPath.getString("message"), "Account deleted!");


    }

    @Test(
            description = "TC LOGIN 013- Verify that user can update data successfully",
            groups = {"smoke", "CRUD"},
            priority = 2,
            dependsOnMethods = {"postUserCreateAccount"}
    )
    public void putUpdateUserAccount(ITestContext context) {
        String name = "Hamid";
        String email = (String) context.getAttribute("email");
        String password = (String) context.getAttribute("password");
        String title = "Mr";
        String birth_date = "7";
        String birth_month = "7";
        String birth_year = "1997";
        String firstname = "Hamid";
        String lastname = "Shaikh";
        String company = "fake ompany";
        String address1 = "Pune";
        String address2 = "";
        String country = "India";
        String zipcode = "411014";
        String state = "Maharashtra";
        String city = "Pune";
        String mobile_number = "9876543210";
        Response response = loginService.putUpdateUserAccount(name, email, password, title,
                birth_date, birth_month, birth_year,
                firstname, lastname, company, address1,
                address2, country, zipcode, state, city, mobile_number);

        response.then().statusCode(200);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(JsonParser.parseString(response.asString())));

        String body = response.asString();

        JsonPath jsonPath = new JsonPath(body);

        Assert.assertEquals(jsonPath.getInt("responseCode"), 200);
        Assert.assertEquals(jsonPath.getString("message"), "User updated!");

    }

    @Test(
            description = "TC LOGIN 014 - Verify that user gets account details by email",
            groups = {"smoke", "CRUD"},
            dependsOnMethods = {"postUserCreateAccount"},
            priority = 3
    )
    public void getUserAccountDetailByUser(ITestContext context) {

        String email = (String) context.getAttribute("email");

        Response response = loginService.getUserAccountDetailByEmail(email);

        // 1. HTTP status validation
        response.then()
                .statusCode(200);

        // 2. JSON Schema validation
        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/UserSchema.json"));

        // 3. Response data validation
        Assert.assertEquals(
                response.jsonPath().getInt("responseCode"),
                200
        );

        Assert.assertEquals(
                response.jsonPath().getString("user.name"),
                "Hamid"
        );

        Assert.assertEquals(
                response.jsonPath().getString("user.email"),
                email
        );
    }
}