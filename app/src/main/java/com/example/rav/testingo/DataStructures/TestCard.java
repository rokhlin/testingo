package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class TestCard {

	@Expose
	private UserBasic user;
	@Expose
	private TestInfo test;

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