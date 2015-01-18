package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class TestDetailCard {

	@Expose
	private UserBasic user;
	@Expose
	private TestDetailInfo test;

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

	public TestDetailCard withUser(UserBasic user) {
	this.user = user;
		return this;
	}

	/**
	* 
	* @return
	* The test
	*/
	public TestDetailInfo getTest() {
		return test;
	}

	/**
	* 
	* @param test
	* The test
	*/
	public void setTest(TestDetailInfo test) {
		this.test = test;
	}

	public TestDetailCard withTest(TestDetailInfo test) {
		this.test = test;
		return this;
	}

    public static TestDetailCard fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                                     .create();
        return gson.fromJson(data, TestDetailCard.class);
    }
}