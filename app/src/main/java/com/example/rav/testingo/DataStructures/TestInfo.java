package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Shtutman on 16.01.2015.
 */
public class TestInfo extends TestInfoTemplate {
    public static TestInfoTemplate fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, TestInfoTemplate.class);
    }
}
