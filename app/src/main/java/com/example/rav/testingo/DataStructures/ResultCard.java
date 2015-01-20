package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class ResultCard {

	@Expose
	private TestInfo test;
	@Expose
	private ResultInfo result;

	/**
	* 
	* @return
	* The test
	*/
	public TestInfo getTest() {
		return test;
	}

	/**
	* 
	* @param test
	* The test
	*/
	public void setTest(TestInfo test) {
		this.test = test;
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