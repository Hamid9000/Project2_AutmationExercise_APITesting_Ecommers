package com.automationExercise.services;

import com.automationExercise.builders.RequestBuilder;
import com.automationExercise.endpoints.Endpoints;
import io.restassured.response.Response;

public class UserService {

    // CREATE USER ACCOUNT
    public Response createAccount(
            String name,
            String email,
            String password,
            String title,
            String birthDate,
            String birthMonth,
            String birthYear,
            String firstname,
            String lastname,
            String company,
            String address1,
            String address2,
            String country,
            String zipcode,
            String state,
            String city,
            String mobileNumber) {

        return RequestBuilder.build()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", name)
                .formParam("email", email)
                .formParam("password", password)
                .formParam("title", title)
                .formParam("birth_date", birthDate)
                .formParam("birth_month", birthMonth)
                .formParam("birth_year", birthYear)
                .formParam("firstname", firstname)
                .formParam("lastname", lastname)
                .formParam("company", company)
                .formParam("address1", address1)
                .formParam("address2", address2)
                .formParam("country", country)
                .formParam("zipcode", zipcode)
                .formParam("state", state)
                .formParam("city", city)
                .formParam("mobile_number", mobileNumber)
                .when()
                .post(Endpoints.CREATE_ACCOUNT);
    }


    // READ USER ACCOUNT
    public Response getUserByEmail(String email) {

        return RequestBuilder.build()
                .queryParam("email", email)
                .when()
                .get(Endpoints.GET_USER_DETAIL);
    }


    // UPDATE USER ACCOUNT
    public Response updateAccount(
            String name,
            String email,
            String password,
            String title,
            String birthDate,
            String birthMonth,
            String birthYear,
            String firstname,
            String lastname,
            String company,
            String address1,
            String address2,
            String country,
            String zipcode,
            String state,
            String city,
            String mobileNumber) {

        return RequestBuilder.build()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", name)
                .formParam("email", email)
                .formParam("password", password)
                .formParam("title", title)
                .formParam("birth_date", birthDate)
                .formParam("birth_month", birthMonth)
                .formParam("birth_year", birthYear)
                .formParam("firstname", firstname)
                .formParam("lastname", lastname)
                .formParam("company", company)
                .formParam("address1", address1)
                .formParam("address2", address2)
                .formParam("country", country)
                .formParam("zipcode", zipcode)
                .formParam("state", state)
                .formParam("city", city)
                .formParam("mobile_number", mobileNumber)
                .when()
                .put(Endpoints.UPDATE_ACCOUNT);
    }


    // DELETE USER ACCOUNT
    public Response deleteAccount(String email, String password) {

        return RequestBuilder.build()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .delete(Endpoints.DELETE_ACCOUNT);
    }

    // Login user
    public Response loginUser(String email, String password) {

        return RequestBuilder.build()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post(Endpoints.VERIFY_LOGIN);
    }
}
