package com.example.rav.testingo.DataFlow;

/**
 * Created by Shtutman on 16.01.2015.
 */
public class JsonResponseEvent {
    private String data;
    private int id;

    public JsonResponseEvent(String data, int id) {
        this.data = data;
        this.id = id;
    }

    public String getData() {
        return data;
    }
    public int getId() {
        return id;
    }
}
