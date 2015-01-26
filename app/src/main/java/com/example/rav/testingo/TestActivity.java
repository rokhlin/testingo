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
 import android.widget.GridView;
 import android.widget.LinearLayout;
 import android.widget.RadioButton;
 import android.widget.RadioGroup;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.example.rav.testingo.DataFlow.DataClient;
 import com.example.rav.testingo.DataFlow.ErrorResponseEvent;
 import com.example.rav.testingo.DataFlow.HttpDataClient;
 import com.example.rav.testingo.DataFlow.JsonResponseEvent;
 import com.example.rav.testingo.DataStructures.Question;
 import com.yelp.android.webimageview.ImageLoader;
 import com.yelp.android.webimageview.WebImageView;

 import java.util.List;

 import de.greenrobot.event.EventBus;


 public class TestActivity extends ActionBarActivity {
     private int QUESTION_VIEW=0;
     //public String base_url = getResources().getString(R.string.base_url);
     private Question q;
     private List<String> answers;
     private String[] testType=new String[]{"question_text.json",
                                            "question_check.json",
                                            "question_radio.json",
                                            "question_image.json"};
     private int count=0;
     private Button send;
     private LinearLayout llContainer;
     private GridView gwContainer;
     private WebImageView wiv;
     private TextView tvQuestion, tvQuestionNumber;
     private EditText etAnswer;
     private DataClient client;
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
        llContainer=(LinearLayout)findViewById(R.id.llItemcontanier);
        gwContainer=(GridView)findViewById(R.id.gvItemcontanier);
        wiv=(WebImageView)findViewById(R.id.ivQuestion);
        tvQuestion=(TextView)findViewById(R.id.twQuestion);
        tvQuestionNumber=(TextView)findViewById(R.id.tvQuestionNumber);


        client = new HttpDataClient(getResources().getString(R.string.base_url), this);
        client.get("test_json/"+testType[count], QUESTION_VIEW);

        send=(Button)findViewById(R.id.btnSend);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if(count!=testType.length-1){
                    send.setText(R.string.BUTTON_TEST_EVENT);
                    client.get("test_json/"+testType[count], QUESTION_VIEW);
                }
                else {
                    send.setText(R.string.BUTTON_TEST_EVENT_FINISH);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, ResultActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

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
                 answers = q.getData();
                 Log.i("TAG", "Answers loaded.");
             }
             catch (NullPointerException e){
                 Log.i("TAG", "NOT find list");
             }

             String image = q.getImage();
             if(!image.isEmpty()) {
                 String base_url = getResources().getString(R.string.base_url);
                 Log.i("TAG", base_url + "img/question/" + image);
                 WebImageView wiv = (WebImageView) findViewById(R.id.ivQuestion);
                 wiv.setImageUrl(base_url + "img/question/" + image);
             }

             tvQuestion.setText(question);
             tvQuestionNumber.setText((count + 1) + "/" + testType.length);

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

     private void  loadImageAnswer(){
         GridView gridView=(GridView)findViewById(R.id.gvItemcontanier);
         llContainer.removeAllViews();
         LayoutInflater ltInflater = getLayoutInflater();
         for (int i = 0; i <q.getData().size();i++) {
             View item;
             item = ltInflater.inflate(R.layout.question_item_text, gridView, false);

             WebImageView image=(WebImageView)item.findViewById(R.id.ivAnswer);
             String base_url = getResources().getString(R.string.base_url);
             image.setImageUrl(base_url + "img//question/" + answers.get(i));

             image.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                 }
             });

            item.setTag(i);
            gridView.addView(item);
         }
     }
     private void loadTextAnswer(){

         llContainer.removeAllViews();
         LayoutInflater ltInflater = getLayoutInflater();
         View item;

         item = ltInflater.inflate(R.layout.question_item_text, llContainer, false);
         etAnswer=(EditText)item.findViewById(R.id.et_Answer);

         item.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
         llContainer.addView(item);
     }


     private void loadRadioAnswer(){

             llContainer.removeAllViews();
             RadioGroup group = new RadioGroup(this);
             group.setOrientation(RadioGroup.VERTICAL);


             for (int i = 0; i <q.getData().size() ; i++) {
                 RadioButton rbtn = new RadioButton(this);
                 rbtn.setText(q.getData().get(i));

              //   rbtn.setPadding(R.dimen.ButtonPaddingLeft,R.dimen.ButtonPaddingLeft,R.dimen.ButtonPaddingLeft,R.dimen.ButtonPaddingLeft);
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


         for (int i = 0; i < q.getData().size();i++) {

                 item = ltInflater.inflate(R.layout.question_item_check, linLayout, false);
                 final CheckBox checkBox=(CheckBox)item.findViewById(R.id.cb_Answer);
                 checkBox.setText(answers.get(i));
                 checkBox.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

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
