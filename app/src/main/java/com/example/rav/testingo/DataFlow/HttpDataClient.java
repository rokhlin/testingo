package com.example.rav.testingo.DataFlow;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

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
    AsyncHttpClient client = new AsyncHttpClient();

    @Override
    public void get(String url, int id) {
        client.get(absuluteURL(url), new CustomJsonHttpResponse(id) {});
    }

    @Override
    public void post(String url, String data, int id) {
        //nothing
    }

    public HttpDataClient(String baseUrl, Context c) {
        super(baseUrl);
        this.c = c;
    }

    private String absuluteURL(String url) {
        return baseUrl + url;
    }
}
