package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class ResultDetail {

	@Expose
	private UserBasic user;
	@Expose
	private TestInfoTemplate test;
	@Expose
	private ResultInfo result;

	/**
	* 
	* @return
	* The user
	*/
	public UserBasic getUser() {
	return user;
	}

	/**
	* 
	* @param user
	* The user
	*/
	public void setUser(UserBasic user) {
	this.user = user;
	}

	public ResultDetail withUser(UserBasic user) {
	this.user = user;
	return this;
	}

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

	public ResultDetail withTest(TestInfoTemplate test) {
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

	public ResultDetail withResult(ResultInfo result) {
        this.result = result;
        return this;
	}

    public static ResultDetail fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, ResultDetail.class);
    }
}
	