package com.example.rav.testingo.DataFlow;

/**
 * Created by Shtutman on 16.01.2015.
 */
public class ErrorResponseEvent {
    private String message;
    private int id;

    public ErrorResponseEvent(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }
}
