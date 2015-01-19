package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBasic {

	@SerializedName("_id")
	@Expose
	private String Id;
	@Expose
	private String name;
	@Expose
	private String avatar;

	/**
	* 
	* @return
	* The Id
	*/
	public String getId() {
		return Id;
	}

	/**
	* 
	* @return
	* The name
	*/
	public String getName() {
		return name;
	}

	/**
	* 
	* @param name
	* The name
	*/
	public void setName(String name) {
		this.name = name;
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

    public static UserBasic fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, UserBasic.class);
    }
}