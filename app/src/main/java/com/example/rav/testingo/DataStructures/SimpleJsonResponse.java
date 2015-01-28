package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * Created by Shtutman on 28.01.2015.
 */
public class SimpleJsonResponse {

    @Expose
    private String data;

    /**
     *
     * @return
     * The data
     */
    public String getData() {
        return data;
    }

    public static SimpleJsonResponse fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, SimpleJsonResponse.class);
    }
}