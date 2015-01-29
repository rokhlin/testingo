package com.example.rav.testingo.DataFlow;

import com.loopj.android.http.RequestParams;

/**
 * Created by Shtutman on 14.01.2015.
 */
public abstract class DataClient {
    protected String baseUrl;

    abstract public void get(String url, int id);
    abstract public void post(String url, RequestParams rp, int id);

    public DataClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
