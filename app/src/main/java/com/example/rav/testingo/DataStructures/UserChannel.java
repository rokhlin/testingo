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
	private List<TestInfoTemplate> channel = new ArrayList<TestInfoTemplate>();

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

	public UserChannel withUser(UserBasic user) {
		this.user = user;
		return this;
	}

	/**
	* 
	* @return
	* The channel
	*/
	public List<TestInfoTemplate> getChannel() {
		return channel;
	}

	/**
	* 
	* @param channel
	* The channel
	*/
	public void setChannel(List<TestInfoTemplate> channel) {
		this.channel = channel;
	}

	public UserChannel withChannel(List<TestInfoTemplate> channel) {
		this.channel = channel;
		return this;
	}

    public static UserChannel fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, UserChannel.class);
    }
}