package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultInfo {

	@SerializedName("_id")
	@Expose
	private String Id;
	@Expose
	private String img;
	@Expose
	private String text;
	@Expose
	private String description;
	@Expose
	private Boolean done;
	@Expose
	private Integer time;

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
	* The img
	*/
	public String getImg() {
		return img;
	}

	/**
	* 
	* @param img
	* The img
	*/
	public void setImg(String img) {
		this.img = img;
	}

	/**
	* 
	* @return
	* The text
	*/
	public String getText() {
		return text;
	}

	/**
	* 
	* @param text
	* The text
	*/
	public void setText(String text) {
		this.text = text;
	}

	/**
	* 
	* @return
	* The description
	*/
	public String getDescription() {
		return description;
	}

	/**
	* 
	* @param description
	* The description
	*/
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	* 
	* @return
	* The done
	*/
	public Boolean getDone() {
		return done;
	}

	/**
	* 
	* @param done
	* The done
	*/
	public void setDone(Boolean done) {
		this.done = done;
	}


	/**
	* 
	* @return
	* The time
	*/
	public Integer getTime() {
	return time;
	}

	/**
	* 
	* @param time
	* The time
	*/
	public void setTime(Integer time) {
	this.time = time;
	}


    public static ResultInfo fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, ResultInfo.class);
    }
}