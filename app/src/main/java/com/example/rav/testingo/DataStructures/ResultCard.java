package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class ResultCard {

	@Expose
	private TestInfoTemplate test;
	@Expose
	private ResultInfo result;

	/**
	* 
	* @return
	* The test
	*/
	public TestInfoTemplate getTest() {
		return test;
	}

	/**
	* 
	* @param test
	* The test
	*/
	public void setTest(TestInfoTemplate test) {
		this.test = test;
	}

	public ResultCard withTest(TestInfoTemplate test) {
		this.test = test;
		return this;
	}

	/**
	* 
	* @return
	* The result
	*/
	public ResultInfo getResult() {
		return result;
	}

	/**
	* 
	* @param result
	* The result
	*/
	public void setResult(ResultInfo result) {
		this.result = result;
	}

	public ResultCard withResult(ResultInfo result) {
		this.result = result;
		return this;
	}

    public static ResultCard fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, ResultCard.class);
    }

    public static ResultCard[] arrayFromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, ResultCard[].class);
    }
}