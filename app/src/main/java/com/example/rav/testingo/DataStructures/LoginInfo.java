package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class LoginInfo {

	@Expose
	private String token;

	/**
	* 
	* @return
	* The token
	*/
	public String getToken() {
	return token;
	}

    public static LoginInfo fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, LoginInfo.class);
    }
}