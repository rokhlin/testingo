package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class NotificationsInfo {

	@Expose
	private Integer notifications;

	/**
	* 
	* @return
	* The notifications
	*/
	public Integer getNotifications() {
		return notifications;
	}

    public static NotificationsInfo fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, NotificationsInfo.class);
    }
}