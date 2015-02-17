package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Created by Shtutman on 17.02.2015.
 */
public class Comment {
    @Expose
    private UserBasic user;
    @Expose
    private String text;

    public Comment(UserBasic user, String text) {
        this.user = user;
        this.text = text;
    }

    public UserBasic getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public void setUser(UserBasic user) {
        this.user = user;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static String toJson(String comment) {
        return "{ \"text\": \""+ comment+"\"}";
//        return (new Gson()).toJson(c, Comment.class);
    }
}
