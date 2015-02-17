package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class TestDetailCard {

	@Expose
	private UserBasic user;
	@Expose
	private TestDetailInfo test;
    @Expose
    private List<Comment> comments = new ArrayList<Comment>();

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

    public List<Comment> getComments() {
        return comments;
    }

    public static TestDetailCard fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                                     .create();
        return gson.fromJson(data, TestDetailCard.class);
    }
}