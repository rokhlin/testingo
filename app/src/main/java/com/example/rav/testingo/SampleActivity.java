package com.example.rav.testingo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rav.testingo.DataFlow.*;
import com.example.rav.testingo.DataStructures.TestDetailCard;
import com.yelp.android.webimageview.ImageLoader;
import com.yelp.android.webimageview.WebImageView;

import de.greenrobot.event.EventBus;


public class SampleActivity extends Activity {

    private static final int TEXT_DETAIL_CARD_JSON = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageLoader.initialize(this, null); // Initialization for WebImageView

        setContentView(R.layout.sample_activity);

        DataClient client = new HttpDataClient(getResources().getString(R.string.base_url), this);
        client.get("test_json/test_details.json", TEXT_DETAIL_CARD_JSON);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEvent(JsonResponseEvent event) {
        if(event.getId() == TEXT_DETAIL_CARD_JSON) {
            TestDetailCard t = TestDetailCard.fromJson(event.getData());
            Log.d("NET_TEST", t.getUser().getName());
            Log.d("NET_TEST", t.getTest().getName());

            String base_url = getResources().getString(R.string.base_url);
            WebImageView wiv = (WebImageView)findViewById(R.id.user_image);
            wiv.setImageUrl(base_url + "img/avatar/" + t.getUser().getAvatar());
            Log.d("TAG", base_url + "img/avatar/" + t.getUser().getAvatar());
        }
    }

    public void onEvent(ErrorResponseEvent event) {
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
