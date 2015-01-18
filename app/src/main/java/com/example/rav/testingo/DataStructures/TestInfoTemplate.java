package com.example.rav.testingo.DataStructures;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class TestInfoTemplate {

	@SerializedName("_id")
	@Expose
	private String Id;
	@Expose
	private String name;
	@Expose
	private String image;
	@Expose
	private String description;
	@Expose
	private Integer count;
	@Expose
	private List<String> tags = new ArrayList<String>();

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

//	public TestInfo withName(String name) {
//		this.name = name;
//		return this;
//	}

	/**
	* 
	* @return
	* The image
	*/
	public String getImage() {
		return image;
	}

	/**
	* 
	* @param image
	* The image
	*/
	public void setImage(String image) {
		this.image = image;
	}

//	public TestInfo withImage(String image) {
//		this.image = image;
//		return this;
//	}

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

//	public TestInfo withDescription(String description) {
//		this.description = description;
//		return this;
//	}

	/**
	* 
	* @return
	* The count
	*/
	public Integer getCount() {
		return count;
	}

	/**
	* 
	* @param count
	* The count
	*/
	public void setCount(Integer count) {
		this.count = count;
	}

//	public TestInfo withCount(Integer count) {
//		this.count = count;
//		return this;
//	}

	/**
	* 
	* @return
	* The tags
	*/
	public List<String> getTags() {
		return tags;
	}

	/**
	* 
	* @param tags
	* The tags
	*/
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public TestInfoTemplate withTags(List<String> tags) {
		this.tags = tags;
		return this;
	}
}