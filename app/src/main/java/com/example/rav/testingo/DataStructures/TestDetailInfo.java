package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class TestDetailInfo extends TestInfoTemplate {

	@Expose
	private Integer questionsCount;
	@Expose
	private Integer time;
	@Expose
	private String hint;

	/**
	* 
	* @return
	* The questionsCount
	*/
	public Integer getQuestionsCount() {
		return questionsCount;
	}

	/**
	* 
	* @param questionsCount
	* The questionsCount
	*/
	public void setQuestionsCount(Integer questionsCount) {
		this.questionsCount = questionsCount;
	}

	public TestDetailInfo withQuestionsCount(Integer questionsCount) {
		this.questionsCount = questionsCount;
		return this;
	}

	/**
	* 
	* @return
	* The time
	*/
	public Integer getTime() {
		return time;
	}

	/**
	* 
	* @param time
	* The time
	*/
	public void setTime(Integer time) {
		this.time = time;
	}

	public TestDetailInfo withTime(Integer time) {
		this.time = time;
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

	public TestDetailInfo withHint(String hint) {
		this.hint = hint;
		return this;
	}

    public static TestDetailCard fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, TestDetailCard.class);
    }
}