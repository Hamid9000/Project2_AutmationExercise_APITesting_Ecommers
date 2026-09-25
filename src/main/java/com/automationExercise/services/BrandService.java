package com.automationExercise.services;

import com.automationExercise.builders.RequestBuilder;
import com.automationExercise.endpoints.Endpoints;
import io.restassured.response.Response;

public class BrandService {
    public Response getAllBrandList(){

        return RequestBuilder.build()
                .when()
                .get(Endpoints.BRANDS_LIST);

    }

    public Response putALLBrandList(){

        return RequestBuilder.build()
                .when()
                .put(Endpoints.BRANDS_LIST);
    }
}
