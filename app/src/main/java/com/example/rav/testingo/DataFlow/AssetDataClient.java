package com.example.rav.testingo.DataFlow;

import android.content.Context;
import android.os.AsyncTask;

import com.loopj.android.http.RequestParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.greenrobot.event.EventBus;

/**
 * Created by Shtutman on 14.01.2015.
 */
public class AssetDataClient extends DataClient {
    class BackgroundTask extends AsyncTask<String, Void, String> {
        Context c;
        int event_id;

        BackgroundTask(Context c, int id) {
            super();
            this.c = c;
            this.event_id = event_id;
        }

        @Override
        protected String doInBackground(String... params) {
            String res = "";
            if(params.length > 0) {
                StringBuilder buf=new StringBuilder();

                try {
                    BufferedReader in=
                            new BufferedReader(new InputStreamReader(c.getAssets().open(params[0]), "UTF-8"));
                    String str;

                    while ((str=in.readLine()) != null) {
                        buf.append(str);
                    }
                    res = buf.toString();

                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            EventBus.getDefault().post(new JsonResponseEvent(result, event_id));
        }
    }
    private Context c;

    @Override
    public void get(String url, int id) {
        BackgroundTask bt = new BackgroundTask(c, id);
        bt.execute(baseUrl+url);
    }

    @Override
    public void post(String url, RequestParams rp, int id) {
        BackgroundTask bt = new BackgroundTask(c, id);
        bt.execute(baseUrl+url);
    }



    public AssetDataClient(String baseUrl, Context c) {
        super(baseUrl);
        this.c = c;
    }
}
