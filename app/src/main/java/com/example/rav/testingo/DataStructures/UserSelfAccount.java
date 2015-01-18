package com.example.rav.testingo.DataStructures;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class UserSelfAccount {

	@Expose
	private String firstName;
	@Expose
	private String lastName;
	@Expose
	private String email;
	@Expose
	private String avatar;
	@Expose
	private List<UserBasic> subscriptions = new ArrayList<UserBasic>();

	/**
	* 
	* @return
	* The firstName
	*/
	public String getFirstName() {
		return firstName;
	}

	/**
	* 
	* @param firstName
	* The firstName
	*/
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public UserSelfAccount withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	* 
	* @return
	* The lastName
	*/
	public String getLastName() {
		return lastName;
	}

	/**
	* 
	* @param lastName
	* The lastName
	*/
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserSelfAccount withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	/**
	* 
	* @return
	* The email
	*/
	public String getEmail() {
		return email;
	}

	/**
	* 
	* @param email
	* The email
	*/
	public void setEmail(String email) {
		this.email = email;
	}

	public UserSelfAccount withEmail(String email) {
		this.email = email;
		return this;
	}

	/**
	* 
	* @return
	* The avatar
	*/
	public String getAvatar() {
		return avatar;
	}

	/**
	* 
	* @param avatar
	* The avatar
	*/
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public UserSelfAccount withAvatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	/**
	* 
	* @return
	* The subscriptions
	*/
	public List<UserBasic> getSubscriptions() {
		return subscriptions;
	}

	/**
	* 
	* @param subscriptions
	* The subscriptions
	*/
	public void setSubscriptions(List<UserBasic> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public UserSelfAccount withSubscriptions(List<UserBasic> subscriptions) {
		this.subscriptions = subscriptions;
		return this;
	}

    public static UserSelfAccount fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, UserSelfAccount.class);
    }
}