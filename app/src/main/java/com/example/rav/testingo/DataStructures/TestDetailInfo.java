package com.example.rav.testingo.DataStructures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class TestDetailInfo extends TestInfoTemplate {

	@Expose
	private Integer questionsCount;
	@Expose
	private Integer time;
	@Expose
	private String hint;
    @Expose
    private Boolean showResult;
    @Expose
    private String date;

	/**
	* 
	* @return
	* The questionsCount
	*/
	public Integer getQuestionsCount() {
		return questionsCount;
	}

	/**
	* 
	* @param questionsCount
	* The questionsCount
	*/
	public void setQuestionsCount(Integer questionsCount) {
		this.questionsCount = questionsCount;
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

	/**
	* 
	* @return
	* The hint
	*/
	public String getHint() {
		return hint;
	}

    /**
     *
     * @param hint
     * The hint
     */
    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     *
     * @param date
     * The hint
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @return
     * The showResult
     */
    public Boolean isShowResult() {
        return showResult;
    }

    /**
     *
     * @param showResult
     * The hint
     */
    public void setShowResult(Boolean showResult) {
        this.showResult = showResult;
    }

    public static TestDetailCard fromJson(String data) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.fromJson(data, TestDetailCard.class);
    }
}