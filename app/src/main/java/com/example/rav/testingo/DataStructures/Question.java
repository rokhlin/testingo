package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Question {

    @SerializedName("_id")
    @Expose
    private String Id;
    @Expose
    private String text;
    @Expose
    private String type;
    @Expose
    private String hint;
    @Expose
    private List<String> data = new ArrayList<String>();

    /**
     *
     * @return
     * The Id
     */
    public String getId() {
        return Id;
    }

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

    public Question withText(String text) {
        this.text = text;
        return this;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public Question withType(String type) {
        this.type = type;
        return this;
    }

    /**
     *
     * @return
     * The hint
     */
    public String getHint() {
        return hint;
    }

    /**
     *
     * @param hint
     * The hint
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    public Question withHint(String hint) {
        this.hint = hint;
        return this;
    }

    /**
     *
     * @return
     * The data
     */
    public List<String> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<String> data) {
        this.data = data;
    }

    public Question withData(List<String> data) {
        this.data = data;
        return this;
    }

    public static Question fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, Question.class);
    }
}