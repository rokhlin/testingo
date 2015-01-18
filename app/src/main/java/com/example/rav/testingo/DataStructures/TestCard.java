package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class TestCard {

	@Expose
	private UserBasic user;
	@Expose
	private TestInfoTemplate test;

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

	public TestCard withUser(UserBasic user) {
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

	public TestCard withTest(TestInfoTemplate test) {
		this.test = test;
		return this;
	}

    public static TestCard fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, TestCard.class);
    }

    public static TestCard[] arrayFromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, TestCard[].class);
    }
}