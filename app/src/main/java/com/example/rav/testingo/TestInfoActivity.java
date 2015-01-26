package com.example.rav.testingo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rav.testingo.DataFlow.DataClient;
import com.example.rav.testingo.DataFlow.HttpDataClient;
import com.example.rav.testingo.DataFlow.JsonResponseEvent;
import com.example.rav.testingo.DataStructures.TestCard;
import com.example.rav.testingo.DataStructures.TestDetailCard;
import com.example.rav.testingo.R;
import com.yelp.android.webimageview.WebImageView;

import java.util.ArrayList;
import java.util.Arrays;

import de.greenrobot.event.EventBus;

public class TestInfoActivity extends ActionBarActivity {
    TextView testDescription, testName, channelName, tags, tested;
    private static final int TEXT_INFO_CARD_JSON = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_info);

        channelName = (TextView) findViewById(R.id.textView);
        testName = (TextView) findViewById(R.id.textView2);
        testDescription = (TextView) findViewById(R.id.textView3);
        tags = (TextView) findViewById(R.id.textView4);
        tested = (TextView) findViewById(R.id.textView5);

        DataClient client = new HttpDataClient(getResources().getString(R.string.base_url), this);
        client.get("test_json/test_details.json", TEXT_INFO_CARD_JSON);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onEvent(JsonResponseEvent event) {
//        Log.d("tag", "test1");
        if(event.getId() == TEXT_INFO_CARD_JSON) {
            TestDetailCard t = TestDetailCard.fromJson(event.getData());

            channelName.setText(t.getUser().getName());
            Log.d("LAST AAAA", t.getUser().getName());
            testName.setText(t.getTest().getName());
            testDescription.setText(t.getTest().getDescription());
            tags.setText(t.getTest().getTags().toString());
            tested.setText(t.getTest().getCount().toString());

            String baseUrl = getResources().getString(R.string.base_url);
            WebImageView avatar = (WebImageView)findViewById(R.id.avatar);
            WebImageView test_image = (WebImageView)findViewById(R.id.test_image);
            avatar.setImageUrl(baseUrl + "img/avatar/" + t.getUser().getAvatar());
            test_image.setImageUrl(baseUrl + "img/test/" + t.getTest().getImage(), R.drawable.image_placeholder);
            Log.d("TAG", baseUrl + "img/avatar/" + t.getUser().getAvatar());
        }
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
