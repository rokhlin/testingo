package com.example.rav.testingo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class RegistrationActivity extends ActionBarActivity implements View.OnClickListener {
    private String password="", email="", confirmPassword="", fName="", lName="";
    private EditText etPass, etEmail, etConfPass, etFName, etLName;
    private Button confirm;
    private String APP_PREFERENCES = String.valueOf(R.string.APP_PREFERENCES);
    private String APP_PREFERENCES_LOGIN = String.valueOf(R.string.APP_PREFERENCES_LOGIN);
    private String APP_PREFERENCES_PASSWORD = String.valueOf(R.string.APP_PREFERENCES_PASSWORD);
    private String APP_PREFERENCES_FNAME = String.valueOf(R.string.APP_PREFERENCES_FNAME);
    private String APP_PREFERENCES_LNAME = String.valueOf(R.string.APP_PREFERENCES_LNAME);
    private String APP_PREFERENCES_FOTO = String.valueOf(R.string.APP_PREFERENCES_LNAME);
    private String FOTO_PATH ="" ;
    private static final int RESULT_CODE=1;
    private Toast toast;
    private Intent intent;
    private ImageView foto;
    private TextView editFoto_label;
    private Context context;
    private AlertDialog.Builder ad;
    private SharedPreferences mySharedPreferences;
    private static final String LOG_TAG = String.valueOf(R.string.LOG_TAG_REGISTRATION);
    private File file;
    private SharedPreferences.Editor editor;
    private EmailValidator validator=new EmailValidator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = RegistrationActivity.this;

        mySharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        etPass = (EditText) findViewById(R.id.etPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etConfPass = (EditText) findViewById(R.id.etConfPassword);
        etFName = (EditText) findViewById(R.id.etFName);
        etLName = (EditText) findViewById(R.id.etLName);

        foto=(ImageView)findViewById(R.id.imgFoto);
        foto.setOnClickListener(this);

        editFoto_label=(TextView)findViewById(R.id.tvEditFoto);
        editFoto_label.setOnClickListener(this);


        confirm = (Button) findViewById(R.id.btnConfirm);
        confirm.setOnClickListener(this);

        ad = new AlertDialog.Builder(context);
        ad.setTitle(R.string.DIALOG_CHANGE_FOTO_TITLE);  // заголовок
        ad.setMessage(R.string.DIALOG_CHANGE_FOTO_MESSAGE); // сообщение
        ad.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/* ");
                //startActivityForResult(intent,RESULT_CODE);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_CODE);
            }
        });
        ad.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(context, R.string.Cancel, Toast.LENGTH_LONG)
                        .show();
            }
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(context, R.string.Cancel,
                        Toast.LENGTH_LONG).show();
            }
        });

        preLoad();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case RESULT_CODE:
                if(resultCode == RESULT_OK){
                    FOTO_PATH = data.getData().getPath();
                    Uri selectedImageUri = data.getData();

                    String FILE_NAME = data.getData().getLastPathSegment();
                    Log.i(LOG_TAG, "Change Foto_______________________________");
                    Log.i(LOG_TAG, "Full Path: " + FOTO_PATH);
                    try {
                        foto.setImageURI(selectedImageUri);


                        //Сохраняем картинку во временную папку
                        if (mySharedPreferences.contains(APP_PREFERENCES_FOTO)) {
                            file = new File(mySharedPreferences.getString(APP_PREFERENCES_FOTO, ""));

                            Log.i(LOG_TAG, "File delete_______________________________"+file.getPath().toString());
                                try {
                                    file.delete();
                                }
                                catch (Exception e){
                                   Log.i(LOG_TAG, "File delete error:"+e.toString());
                                }
                            Log.i(LOG_TAG, "File deleted!");
                            try {
                                file=getTempFile(context, FOTO_PATH);
                                Log.i(LOG_TAG, "File create_______________________________"+file.getPath().toString());
                            }
                            catch (Exception e){
                                Log.i(LOG_TAG, "File create error:"+e.toString());
                            }

                            editor = mySharedPreferences.edit();
                            editor.putString(APP_PREFERENCES_FOTO, file.getPath());
                            editor.apply();

                            Log.i(LOG_TAG, "File path saved: "+file.getPath().toString());
                        }


                    }
                    catch (Exception e){
                        Log.e(LOG_TAG, "Exception " + e.toString());
                        Toast.makeText(context, "Exception "+e.toString(),
                                Toast.LENGTH_LONG).show();
                    }

                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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
        switch (v.getId()) {
            case R.id.btnConfirm:
                Log.i(LOG_TAG, "Confirm Pressed");
                password=etPass.getText().toString();
                confirmPassword=etConfPass.getText().toString();
                fName=etFName.getText().toString();
                lName=etLName.getText().toString();
                email=etEmail.getText().toString();
               //Проверка на заполненное имя пользователя или фамилию
                 if (fName.equals("")||lName.equals("")){
                    toast= Toast.makeText(getApplicationContext(),
                            R.string.ERR_NULL_F_L_NANE, Toast.LENGTH_LONG);
                    toast.show();

                    Log.i(LOG_TAG, "Input Error_________________________");
                    Log.i(LOG_TAG, "Input fName="+fName+", lName="+lName);
                }

               //  Проверка на заполненное поле Email and Password
                else if (password.equals("")||email.equals("")){
                    toast= Toast.makeText(getApplicationContext(),
                            R.string.ERR_NULL_EDITTEXT, Toast.LENGTH_LONG);
                    toast.show();

                    Log.i(LOG_TAG, "Input Error_________________________");
                    Log.i(LOG_TAG, "Input email="+email+", password="+password);
                }

                 //Проверка поле на то что введен корректный email
                 else if(!validator.validate(email)){
                     toast= Toast.makeText(getApplicationContext(),
                             R.string.ERR_INCORRECT_EMAIL, Toast.LENGTH_LONG);
                     Log.i(LOG_TAG, String.valueOf(R.string.ERR_INCORRECT_EMAIL));
                     toast.show();
                 }
                //Проверка на совпадения поля пароль и его подтверждения
                else if(!password.equals(confirmPassword)){
                    toast= Toast.makeText(getApplicationContext(),
                            R.string.ERR_PASS_NOT_VALID, Toast.LENGTH_LONG);
                    Log.i(LOG_TAG, String.valueOf(R.string.ERR_PASS_NOT_VALID));
                    toast.show();
                    break;
                }
                else {
                    if (registration()) {
                        Log.i(LOG_TAG, String.valueOf(R.string.INFO_REGISTRATION_SUCCESS));
                        intent = new Intent(this, FeedActivity.class);
                        startActivity(intent);
                        toast = Toast.makeText(getApplicationContext(),
                                R.string.INFO_REGISTRATION_SUCCESS, Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        Log.i(LOG_TAG, String.valueOf(R.string.INFO_REGISTRATION_FAILURE));
                        toast = Toast.makeText(getApplicationContext(),
                                R.string.INFO_REGISTRATION_FAILURE, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                break;
            case R.id.tvEditFoto:
            case R.id.imgFoto:
                ad.show();
                break;
        }

    }

    public void preLoad() {
        Log.i(LOG_TAG, "Preload _________________________");
        if (mySharedPreferences.contains(APP_PREFERENCES_LOGIN)) {
            etEmail.setText(mySharedPreferences.getString(APP_PREFERENCES_LOGIN, ""));
            etPass.setText(mySharedPreferences.getString(APP_PREFERENCES_PASSWORD, ""));
            etFName.setText(mySharedPreferences.getString(APP_PREFERENCES_FNAME, ""));
            etLName.setText(mySharedPreferences.getString(APP_PREFERENCES_LNAME, ""));

            Log.i(LOG_TAG, "login preloaded");
            Log.i(LOG_TAG, "password preloaded");
            Log.i(LOG_TAG, "FNAME preloaded");
            Log.i(LOG_TAG, "LNAME preloaded");

                if(mySharedPreferences.contains(APP_PREFERENCES_FOTO)){

                // Тут добавить загрузку фотографии из временной папки
                }
        }
    }

    public boolean inputCheck(String fName, String lName, String email, String password) {
        Boolean res = false;

        //Тут нужно будет написать код для проверки
        res = true;
        return res;
    }

    public boolean registration() {
        Boolean res = false;
        fName=etFName.getText().toString();
        lName=etLName.getText().toString();
        email=etEmail.getText().toString();
        Log.i(LOG_TAG, "Registration _________________________");
        if(inputCheck(fName, lName, email, password)){
            editor = mySharedPreferences.edit();
            editor.putString(APP_PREFERENCES_FNAME, fName);
            editor.putString(APP_PREFERENCES_LNAME, lName);
            editor.putString(APP_PREFERENCES_LOGIN, email);
            editor.putString(APP_PREFERENCES_PASSWORD, password);
            editor.apply();

            //Тут нужно будет написать код для добавления Пользователя
            res=true;

        }


        return res;
    }


    public File getTempFile(Context context, String url) {

        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());}
        catch(IOException eo){
            // Error while creating file

            }

        return file;
    }

}

