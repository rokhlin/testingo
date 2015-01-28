package com.example.rav.testingo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rav.testingo.DataFlow.DataClient;
import com.example.rav.testingo.DataFlow.HttpDataClient;
import com.example.rav.testingo.DataFlow.JsonResponseEvent;
import com.example.rav.testingo.DataStructures.SimpleJsonResponse;
import com.example.rav.testingo.DataStructures.TestDetailCard;
import com.yelp.android.webimageview.WebImageView;

import de.greenrobot.event.EventBus;

public class TestInfoActivity extends ActionBarActivity {
    TextView testDescription, testName, channelName, tags, tested;
    Button btnStartTest, btnBack;

    DataClient dataClient;
    TestDetailCard testCard;
    SimpleJsonResponse startToken;
    private static final int TEST_INFO_CARD_JSON = 0;
    private static final int TEST_START_TOKEN = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        channelName = (TextView) findViewById(R.id.textView);
        testName = (TextView) findViewById(R.id.textView2);
        testDescription = (TextView) findViewById(R.id.textView3);
        tags = (TextView) findViewById(R.id.textView4);
        tested = (TextView) findViewById(R.id.textView5);

        String id = getIntent().getStringExtra("id");

        //Обрабосчик нажатия на начало теста
        Button startTest = (Button) findViewById(R.id.btnStartTest);
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataClient.get("mobile/tests/start/" + testCard.getTest().getId(), TEST_START_TOKEN);
            }
        });

        dataClient = new HttpDataClient(getResources().getString(R.string.base_url), this);
        dataClient.get("mobile/tests/" + id, TEST_INFO_CARD_JSON);
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

        if(id == android.R.id.home) this.finish();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onEvent(JsonResponseEvent event) {
//        Log.d("tag", "test1");
        if(event.getId() == TEST_INFO_CARD_JSON) {
            TestDetailCard t = TestDetailCard.fromJson(event.getData());
            testCard = t;

            channelName.setText(t.getUser().getName());
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

        if(event.getId() == TEST_START_TOKEN) {
            SimpleJsonResponse resp = SimpleJsonResponse.fromJson(event.getData());
            String token = resp.getData();
            showTest(token);
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

    //Добавление обработчика нажатич кнопки на пункт.
    public void showTest(String token){
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("startToken", token);
        intent.putExtra("questionsCount", testCard.getTest().getQuestionsCount());
        startActivity(intent);
    }
}
