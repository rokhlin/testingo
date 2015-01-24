package com.example.rav.testingo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class FeedActivity extends ActionBarActivity {
    private String APP_PREFERENCES=String.valueOf(R.string.APP_PREFERENCES);
    SharedPreferences.Editor editor;
    Intent intent;
    Button clear, clear2, clear3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        clear=(Button)findViewById(R.id.button);
        clear2=(Button)findViewById(R.id.button2);
        clear3=(Button)findViewById(R.id.button3);
        clear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTest();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               clear();
            }
        });
        clear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
            }
        });
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
    public void showResult(){
        intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }
}
