package com.example.rav.testingo.DataFlow;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

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
        client.get(absoluteURL(url), new CustomJsonHttpResponse(id) {});
    }

    @Override
    public void post(String url, RequestParams rp, int id) {
        client.post(absoluteURL(url), rp, new CustomJsonHttpResponse(id) {});
    }

    public void post(String url, List<String> arr, int id) {
        Gson gson = new Gson();
        String params = gson.toJson(arr);
        StringEntity entity = null;
        try {
            entity = new StringEntity ("{ \"answers\":" + params + " } ");
            entity.setContentType(new BasicHeader("Content-Type", "application/json"));
            client.post(c, absoluteURL(url), entity, "application/json", new CustomJsonHttpResponse(id) {});
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void put(String url, String data, int id) {
        StringEntity entity = null;
        try {
            entity = new StringEntity(data);
            entity.setContentType(new BasicHeader("Content-Type", "application/json"));
            client.put(c, absoluteURL(url), entity, "application/json", new CustomJsonHttpResponse(id) {});
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public HttpDataClient(String baseUrl, Context c) {
        super(baseUrl);
        this.c = c;
    }

    private String absoluteURL(String url) {
        return baseUrl + url;
    }
}
