 package com.example.rav.testingo;

 import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rav.testingo.DataFlow.ErrorResponseEvent;
import com.example.rav.testingo.DataFlow.HttpDataClient;
import com.example.rav.testingo.DataFlow.JsonResponseEvent;
import com.example.rav.testingo.DataStructures.Question;
import com.example.rav.testingo.DataStructures.SimpleJsonResponse;
import com.yelp.android.webimageview.ImageLoader;
import com.yelp.android.webimageview.WebImageView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


 public class TestActivity extends ActionBarActivity {
     private int QUESTION_VIEW=0;
     private int SHOW_RESULT=13;
//     public String base_url = getResources().getString(R.string.base_url);
     private Question q;
     private List<String> answers = new ArrayList<>();
     private List<String> options;
     private View[] controls;
     private int count=0;

     private int questionsCount;
     private String startToken;
     private GridLayout gridLayout;
     private Button send;
     private LinearLayout llContainer;
     private TextView tvQuestion, tvQuestionNumber;
     private HttpDataClient client;
     private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageLoader.initialize(this, new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.e("EROROROROR", ex.getMessage());
                ex.printStackTrace();
            }
        });

        setContentView(R.layout.activity_test);
        context = this;

        Intent intent = getIntent();
        questionsCount = intent.getIntExtra("questionsCount", 0);
        startToken = intent.getStringExtra("startToken");

        gridLayout = (GridLayout)findViewById(R.id.gvItemcontanier);
        llContainer=(LinearLayout)findViewById(R.id.llItemcontanier);

//        WebImageView wiv = (WebImageView) findViewById(R.id.ivQuestion);
        tvQuestion=(TextView)findViewById(R.id.twQuestion);
        tvQuestionNumber=(TextView)findViewById(R.id.tvQuestionNumber);


        client = new HttpDataClient(getResources().getString(R.string.base_url), this);
        send=(Button)findViewById(R.id.btnSend);

        nextQuestion();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

    }

    public void nextQuestion() {
        int responce_id = QUESTION_VIEW;
        if(count > questionsCount - 1) {
            responce_id = SHOW_RESULT;
            send.setText(R.string.BUTTON_TEST_EVENT_FINISH);
        }
        if(count > 0) getAnswers();
        client.post("mobile/next-question/"+startToken, answers, responce_id);
    }

     public void getAnswers() {
        answers.clear();

        String qType = q.getType();

         if(qType.equals("text")) {
             answers.add(((EditText)controls[0]).getText().toString());
         }

         if(qType.equals("check")) {
             for(int i = 0; i < controls.length; i++) {
                 if(((CheckBox)controls[i]).isChecked()) answers.add(options.get(i));
             }
         }

         if(qType.equals("radio")) {
             for(int i = 0; i < controls.length; i++) {
                 if(((RadioButton)controls[i]).isChecked()) answers.add(options.get(i));
             }
         }

         if(qType.equals("image")) {
             for(int i = 0; i < controls.length; i++) {
                 int tag = (Integer)controls[i].getTag();
                 if(tag > 0) answers.add(options.get(i));
             }
         }
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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
//        Log.d("NET_TEST", event.getId());
//         Log.d("json",event.getData());
//         Log.d("json", String.valueOf(event.getId()));

         if(event.getId() == QUESTION_VIEW ) {
             q = Question.fromJson(event.getData());

             Log.i("TAG", "Question load");
             String question=q.getText();
             Log.i("TAG", "question: "+question);

             String hint=q.getHint();
             Log.i("TAG", "hint: "+hint);

             String type=q.getType();
             Log.i("TAG", "TestType: "+type);

             try {
                 options = q.getData();
                 Log.i("TAG", "options loaded.");
             }
             catch (NullPointerException e){
                 Log.i("TAG", "NOT found list");
             }

             gridLayout.removeAllViews();
             llContainer.removeAllViews();

             String image = q.getImage();
             WebImageView wiv = (WebImageView) findViewById(R.id.ivQuestion);
             if(!image.isEmpty()) {
                 String base_url = getResources().getString(R.string.base_url);
                 Log.i("TAG", base_url + "img/test/" + image);
                 wiv.setImageUrl(base_url + "img/test/" + image, R.drawable.image_placeholder);
             }
             else {
                 wiv.setImageDrawable(null);
             }

             tvQuestion.setText(question);
             tvQuestionNumber.setText((count + 1) + "/" + questionsCount);

             switch (type) {
                 case "text":
                     loadTextAnswer();
                     Log.d("TAG", "Load a Text type question");
                     break;
                 case "radio":
                     loadRadioAnswer();
                     Log.d("TAG", "Load a RadioButton type question");
                     break;
                 case "check":
                     loadSelectAnswer();
                 Log.d("TAG", "Load a CheckBox type question");
                     break;
                 case "image":
                     loadImageAnswer();
                 Log.d("TAG", "Load an Image type question");
                     break;
                 default:
                     Log.e("EROROROR", "Question type is not correct.");
                     break;
             }
             count++;
         }

        if(event.getId() == SHOW_RESULT) {
            SimpleJsonResponse resp = SimpleJsonResponse.fromJson(event.getData());
            Intent intent = new Intent(context, ResultActivity.class);
            intent.putExtra("id", resp.getData());
            startActivity(intent);
        }
     }

     public void onEvent(ErrorResponseEvent event) {
         Log.d("json",event.getMessage());
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

     private void  loadImageAnswer(){
         LayoutInflater ltInflater = getLayoutInflater();

         controls = new View[q.getData().size()];

         for (int i = 0; i < q.getData().size();i++) {
             View item;
             item = ltInflater.inflate(R.layout.question_item_image, gridLayout, false);

             final WebImageView image=(WebImageView)item.findViewById(R.id.ivAnswer);
             controls[i] = image;

            String base_url = getResources().getString(R.string.base_url);
             image.setImageUrl(base_url + "img/question/" + q.getData().get(i));
             image.setTag(0);
             //Прописываем действие при выборе элемента
             image.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     int parameter=(Integer)image.getTag();

                     if(parameter==0) {
                         image.setBackgroundResource(R.color.editText_color);
                         image.setHovered(true);
                         image.setPadding(2, 6, 2, 6);
                         image.setTag(1);

                     }
                     else {
                         image.setBackgroundResource(0);
                         image.setPadding(0, 0, 0, 0);
                         image.setTag(0);
                         image.setHovered(false);

                     }
                 }
             });

            item.setTag(i);
            gridLayout.addView(item);
         }
     }
     private void loadTextAnswer(){
         LayoutInflater ltInflater = getLayoutInflater();
         View item;

         item = ltInflater.inflate(R.layout.question_item_text, llContainer, false);
         EditText etAnswer = (EditText) item.findViewById(R.id.et_Answer);
         controls = new View[] { etAnswer };

         item.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
         llContainer.addView(item);
     }


     private void loadRadioAnswer(){
             RadioGroup group = new RadioGroup(this);
             group.setOrientation(RadioGroup.VERTICAL);

             controls = new View[q.getData().size()];

            for (int i = 0; i <q.getData().size() ; i++) {
                 RadioButton rbtn = new RadioButton(this);
                 controls[i] = rbtn;

                 rbtn.setText(q.getData().get(i));

   rbtn.setPadding(40,40,40,40);//R.dimen.ButtonPaddingLeft,R.dimen.ButtonPaddingLeft,R.dimen.ButtonPaddingLeft,R.dimen.ButtonPaddingLeft);
//                 RadioGroup.LayoutParams params
//                         = new RadioGroup.LayoutParams(context, null);
//                 params.setMargins(0, R.dimen.ButtonPaddingLeft, 0, R.dimen.ButtonPaddingLeft);
//                 rbtn.setLayoutParams(params);
                 group.addView(rbtn);

             }
             llContainer.addView(group);

 //        }
     }

     private void loadSelectAnswer(){
         LinearLayout linLayout = (LinearLayout) findViewById(R.id.llItemcontanier);
         linLayout.removeAllViews();
         LayoutInflater ltInflater = getLayoutInflater();
         View item;

         controls = new View[q.getData().size()];

         for (int i = 0; i < q.getData().size();i++) {

                 item = ltInflater.inflate(R.layout.question_item_check, linLayout, false);
                 final CheckBox checkBox=(CheckBox)item.findViewById(R.id.cb_Answer);

                 controls[i] = checkBox;

                 checkBox.setText(options.get(i));
                 checkBox.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
//                             answers
                             Toast.makeText(context, "Item checked", Toast.LENGTH_SHORT).show();

                     }
                 });

  //           }
             item.setTag(i);
             item.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
             linLayout.addView(item);
         }
     }

}
