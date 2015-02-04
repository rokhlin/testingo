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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignActivity extends ActionBarActivity implements View.OnClickListener {
    private String password, login;
    private EditText etPass, etLogin;
    private Button confirm, registration;
    private String APP_PREFERENCES=String.valueOf(R.string.APP_PREFERENCES);
    private String APP_PREFERENCES_LOGIN=String.valueOf(R.string.APP_PREFERENCES_LOGIN);
    private String APP_PREFERENCES_PASSWORD=String.valueOf(R.string.APP_PREFERENCES_PASSWORD);
    private static final int TEXT_DETAIL_CARD_JSON = 0;
    private Toast toast;
    private Intent intent;
    private SharedPreferences mySharedPreferences;
    private static final  String LOG_TAG= String.valueOf(R.string.LOG_TAG_SIGNIN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mySharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        preLoad();//Авторизация если пользователь ранее вводил свои данные

        etPass=(EditText)findViewById(R.id.etPassword);
        etLogin=(EditText)findViewById(R.id.etEmail);

        confirm=(Button) findViewById(R.id.btnConfirm);
        confirm.setOnClickListener(this);
        registration=(Button)findViewById(R.id.btnRegistr);
        registration.setOnClickListener(this);

    }


//    public void loadData() {
//        DataClient client = new HttpDataClient(getResources().getString(R.string.base_url), this);
//        client.get("test_details.json", TEXT_DETAIL_CARD_JSON);
//    }

//    public void onEvent(JsonResponseEvent event) {
//        if(event.getId() == TEXT_DETAIL_CARD_JSON) {
//            TestDetailCard t = TestDetailCard.fromJson(event.getData());
//            Log.d("NET_TEST", t.getUser().getName());
//            Log.d("NET_TEST", t.getTest().getName());
//        }
//    }
//
//    public void onEvent(ErrorResponseEvent event) {
//        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnConfirm:
                Log.i(LOG_TAG, "Confirm Pressed");
                getData();
                break;
            case R.id.btnRegistr:
                Log.i(LOG_TAG, "Registr Pressed");
                intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                break;
        }
    }


    public void getData(){



        password=etPass.getText().toString();
        login=etLogin.getText().toString();

        if (password.equals("")||login.equals("")){
            toast= Toast.makeText(getApplicationContext(),
                    R.string.ERR_NULL_EDITTEXT, Toast.LENGTH_LONG);
            toast.show();

            Log.i(LOG_TAG, "Input Error_________________________");
            Log.i(LOG_TAG, "Input login="+login+", password="+password);
        }
        else if(password.equals("11")){
            //Действие если все введено правильно
            if(autorise()){
                showFeed();
            }
        }
        else {
            //Действие если все введено не правильно

            toast= Toast.makeText(getApplicationContext(),
                    R.string.ERR_NULL_EDITTEXT, Toast.LENGTH_LONG);
            toast.show();

            Log.i(LOG_TAG, "Input Error_________________________");
            Log.i(LOG_TAG, "Input login="+login+", password="+password);
        }

    }

    public void preLoad(){
        Log.i(LOG_TAG, "Preload _________________________");
        if(mySharedPreferences.contains(APP_PREFERENCES_LOGIN)) {
            login=mySharedPreferences.getString(APP_PREFERENCES_LOGIN, "");
            try{
            etLogin.setText(mySharedPreferences.getString(APP_PREFERENCES_LOGIN, ""));
            Log.i(LOG_TAG, "login preloaded");
            }
            catch (Exception e){
                Log.i(LOG_TAG, "login preloaded Error");
            }
        }
        if(mySharedPreferences.contains(String.valueOf(R.string.APP_PREFERENCES_PASSWORD))) {
            password=mySharedPreferences.getString(APP_PREFERENCES_PASSWORD, "");
            try {
                etPass.setText(mySharedPreferences.getString(APP_PREFERENCES_PASSWORD, ""));
                Log.i(LOG_TAG, "password preloaded");
            }
            catch (Exception e){
                Log.i(LOG_TAG, "password preloaded Error");
            }
        }

       if(autorise()){
           showFeed();
       }

    }

    public boolean autorise(){
        Log.i(LOG_TAG, "Autorise _________________________");
        Boolean res=false;

       //Здесь нужно прописать код для авторизации
        res=true;


        Log.i(LOG_TAG, "Autorise "+res.toString());
        return res;
    }

    public void showFeed(){
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
