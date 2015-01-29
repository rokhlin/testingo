package com.example.rav.testingo.DataFlow;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * Created by Shtutman on 14.01.2015.
 */
public class HttpDataClient extends DataClient {
    abstract class CustomJsonHttpResponse extends TextHttpResponseHandler {
        private int id;

        protected CustomJsonHttpResponse(int id) {
            this.id = id;
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            EventBus.getDefault().post(new JsonResponseEvent(s, id));
        }

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            EventBus.getDefault().post(new ErrorResponseEvent(throwable.getMessage(), id));
        }
    }

    private Context c;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    public void get(String url, int id) {
        client.get(absuluteURL(url), new CustomJsonHttpResponse(id) {});
    }

    @Override
    public void post(String url, RequestParams rp, int id) {
        client.post(absuluteURL(url), rp, new CustomJsonHttpResponse(id) {});
    }

    public void post(String url, List<String> arr, int id) {
        Gson gson = new Gson();
        String params = gson.toJson(arr);
        Log.d("answers", params);
        StringEntity entity = null;
        try {
            entity = new StringEntity ("{ \"answers\":" + params + " } ");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        Log.d(entity.setContentEncoding("application/json");)
        client.post(c, absuluteURL(url), entity, "application/json", new CustomJsonHttpResponse(id) {});
    }

    public HttpDataClient(String baseUrl, Context c) {
        super(baseUrl);
        this.c = c;
    }

    private String absuluteURL(String url) {
        return baseUrl + url;
    }
}
