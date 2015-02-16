package com.example.rav.testingo.DataStructures;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class UserChannel {

	@Expose
	private UserBasic user;
	@Expose
	private List<TestInfo> tests = new ArrayList<TestInfo>();

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
	* The channel
	*/
	public List<TestInfo> getChannel() {
		return tests;
	}

	/**
	* 
	* @param channel
	* The channel
	*/
	public void setChannel(List<TestInfo> channel) {
		this.tests = channel;
	}

    public static UserChannel fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, UserChannel.class);
    }
}