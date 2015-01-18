package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class ErrorResponce {

	@Expose
	private String err;

	/**
	* 
	* @return
	* The err
	*/
	public String getErr() {
		return err;
	}

    public static ErrorResponce fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, ErrorResponce.class);
    }
}