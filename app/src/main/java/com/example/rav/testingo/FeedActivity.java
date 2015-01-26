package com.example.rav.testingo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rav.testingo.DataFlow.DataClient;
import com.example.rav.testingo.DataFlow.HttpDataClient;
import com.example.rav.testingo.DataFlow.JsonResponseEvent;
import com.example.rav.testingo.DataStructures.TestCard;
import com.yelp.android.webimageview.ImageLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import de.greenrobot.event.EventBus;


public class FeedActivity extends ActionBarActivity {
    private String APP_PREFERENCES=String.valueOf(R.string.APP_PREFERENCES);
    SharedPreferences.Editor editor;
    Intent intent;
    private static final String TAG = "CardListActivity";
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;
    private static final int TEXT_CARD_JSON = 0;
    ArrayList<TestCard> testCards;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageLoader.initialize(this, null);
        setContentView(R.layout.activity_feed);

        listView = (ListView) findViewById(R.id.card_listView);

//        listView.addHeaderView(new View(this));
//        listView.addFooterView(new View(this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  Log.d("TAG", testCards.get(position).toString());
                Intent intent;
                intent = new Intent(FeedActivity.this, TestInfoActivity.class);
//                intent.putExtra("position", testCards);
                startActivity(intent);

            }
        });
        DataClient client = new HttpDataClient(getResources().getString(R.string.base_url), this);
        client.get("test_json/feed.json", TEXT_CARD_JSON);

        SharedPreferences mySharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed, menu);
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

   public void clear(){
       editor.clear();
   }

    public void showTest(){
        intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void onEvent(JsonResponseEvent event) {
        Log.d("tag", "test1");
        if(event.getId() == TEXT_CARD_JSON) {
            TestCard[] rawCards = TestCard.arrayFromJson(event.getData());
            testCards = new ArrayList<TestCard>(Arrays.asList(rawCards));
            testCards.add(testCards.get(0));
            testCards.add(testCards.get(0));
            testCards.add(testCards.get(0));
            String baseUrl=getResources().getString(R.string.base_url);


            cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card, testCards, baseUrl);

//            for (int i = 0; i < 2; i++) {
//                userName = t[i].getUser().getName();
//                testName =t[i].getTest().getName();
//                testDescription = t[i].getTest().getDescription();
//                tested = t[i].getTest().getCount().toString();
//                tags = t[i].getTest().getTags().toString();
//                Card card = new Card(userName, testName, testDescription, tested, tags);
//                cardArrayAdapter.add(testCards[i]);
//            }
            listView.setAdapter(cardArrayAdapter);
//            String base_url = getResources().getString(R.string.base_url);
//            WebImageView wiv = (WebImageView)findViewById(R.id.user_image);
//            wiv.setImageUrl(base_url + "img/avatar/" + t.getUser().getAvatar());
//            Log.d("TAG", base_url + "img/avatar/" + t.getUser().getAvatar());
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
