package com.automationExercise.base;

import io.restassured.RestAssured;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.automationExercise.config.EnvConfig;

public class CommonToAllTest {

    @BeforeSuite
    public void setup() {

        RestAssured.baseURI = EnvConfig.getBaseUrl();

        System.out.println("API Test Suite Started");
    }

    @AfterSuite
    public void tearDown() {

        System.out.println("API Test Suite Completed");
    }
}