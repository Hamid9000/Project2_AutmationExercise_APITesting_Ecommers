package com.automationExercise.tests.crud;

import com.automationExercise.assertions.APIAssertions;
import com.automationExercise.services.UserService;
import com.automationExercise.utils.RetryAnalyzer;
import com.mysql.cj.x.protobuf.Mysqlx;
import io.restassured.response.Response;
import org.testng.annotations.Test;


public class UserCRUDTest {

    private final UserService userService = new UserService();

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void verifyUserCRUDFlow() {
        // =========================
        // TEST DATA
        // =========================

        String name = "Hamid";
        String email = "hamid" + System.currentTimeMillis() + "@gmail.com";
        String password = "Test@123";

        String updatedName = "Hamid Updated";
        String updatedCompany = "Updated Company";
        String updatedMobile = "9876543211";


        // =========================
        // 1. CREATE USER
        // =========================

        Response createResponse = userService.createAccount(
                name,
                email,
                password,
                "Mr",
                "07",
                "07",
                "1997",
                "Hamid",
                "Sheikh",
                "Test Company",
                "Test Address 1",
                "Test Address 2",
                "India",
                "442401",
                "Maharashtra",
                "Chandrapur",
                "9876543210"
        );

        APIAssertions.assertStatusCode(createResponse, 200);

        APIAssertions.assertTrue(
                createResponse.jsonPath().getInt("responseCode") == 201,
                "User account was not created successfully"
        );


        // =========================
        // 2. READ USER
        // =========================

        Response getResponse =
                userService.getUserByEmail(email);

        APIAssertions.assertStatusCode(getResponse, 200);

        APIAssertions.assertTrue(
                getResponse.jsonPath().getInt("responseCode") == 200,
                "User details could not be retrieved"
        );

        APIAssertions.assertTrue(
                getResponse.jsonPath()
                        .getString("user.email")
                        .equals(email),
                "Retrieved email does not match created email"
        );


        // =========================
        // 3. UPDATE USER
        // =========================

        Response updateResponse = userService.updateAccount(
                name,
                email,
                password,
                "Mr",
                "07",
                "07",
                "1997",
                updatedName,
                "Sheikh",
                updatedCompany,
                "Updated Address 1",
                "Updated Address 2",
                "India",
                "442402",
                "Maharashtra",
                "Chandrapur",
                updatedMobile
        );

        APIAssertions.assertStatusCode(updateResponse, 200);

        APIAssertions.assertTrue(
                updateResponse.jsonPath().getInt("responseCode") == 200,
                "User account was not updated successfully"
        );


        // =========================
        // 4. READ USER AFTER UPDATE
        // =========================

        Response updatedResponse =
                userService.getUserByEmail(email);

        APIAssertions.assertStatusCode(updatedResponse, 200);

        APIAssertions.assertTrue(
                updatedResponse.jsonPath().getInt("responseCode") == 200,
                "Updated user details could not be retrieved"
        );

        APIAssertions.assertTrue(
                updatedResponse.jsonPath()
                        .getString("user.first_name")
                        .equals(updatedName),
                "Updated first name was not saved"
        );

        APIAssertions.assertTrue(
                updatedResponse.jsonPath()
                        .getString("user.company")
                        .equals(updatedCompany),
                "Updated company was not saved"
        );




        // =========================
        // 5. DELETE USER
        // =========================

        Response deleteResponse =
                userService.deleteAccount(email, password);

        APIAssertions.assertStatusCode(deleteResponse, 200);

        APIAssertions.assertTrue(
                deleteResponse.jsonPath().getInt("responseCode") == 200,
                "User account was not deleted successfully"
        );

        APIAssertions.assertTrue(
                deleteResponse.jsonPath()
                        .getString("message")
                        .contains("Account deleted"),
                "Account deletion success message was not found"
        );
    }
}