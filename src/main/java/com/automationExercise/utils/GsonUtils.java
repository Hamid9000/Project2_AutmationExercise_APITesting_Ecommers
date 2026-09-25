package com.automationExercise.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import io.restassured.response.Response;

public class GsonUtils {

    private static final Gson gson = new Gson();

    private static final Gson prettyGson =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    public static <T> T fromJson(Response response, Class<T> classType) {

        String json = extractJson(response.asString());

        return gson.fromJson(json, classType);
    }

    public static String prettyPrint(Response response) {

        String json = extractJson(response.asString());

        return prettyGson.toJson(
                JsonParser.parseString(json)
        );
    }

    private static String extractJson(String responseBody) {

        return responseBody
                .replace("<html>", "")
                .replace("</html>", "")
                .replace("<body>", "")
                .replace("</body>", "")
                .trim();
    }
}