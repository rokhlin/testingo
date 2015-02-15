package com.example.rav.testingo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
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

public class TestFragment extends Fragment {
    private static final String START_TOKEN_ARG = "token";
    private static final String Q_COUNT_ARG = "count";
    private static final String TEST_NAME_ARG = "test_name";
    private MainActivityInteractions interactions;
    private Context context;
    private ViewGroup rootView;
    LayoutInflater inflater;

    private int QUESTION_VIEW=0;
    private int SHOW_RESULT=13;
    private Question q;
    private List<String> answers = new ArrayList<>();
    private List<String> options;
    private View[] controls;
    private int count=0;

    private String testName;
    private int questionsCount;
    private String startToken;
    private GridLayout gridLayout;
    private Button send;
    private LinearLayout llContainer;
    private TextView tvQuestion, tvQuestionNumber;
    private HttpDataClient client;
    private InputMethodManager imm;

    public static TestFragment newInstance(String token, String testName, int qCount) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString(START_TOKEN_ARG, token);
        args.putString(TEST_NAME_ARG, testName);
        args.putInt(Q_COUNT_ARG, qCount);
        fragment.setArguments(args);
        return fragment;
    }

    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            startToken = getArguments().getString(START_TOKEN_ARG);
            questionsCount = getArguments().getInt(Q_COUNT_ARG);
            testName = getArguments().getString(TEST_NAME_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_test, container, false);
        context = getActivity();
        this.inflater = inflater;
        ImageLoader.initialize(context, null);

        gridLayout = (GridLayout)rootView.findViewById(R.id.gvItemcontanier);
        llContainer=(LinearLayout)rootView.findViewById(R.id.llItemcontanier);
        tvQuestion=(TextView)rootView.findViewById(R.id.twQuestion);
        tvQuestionNumber=(TextView)rootView.findViewById(R.id.tvQuestionNumber);


        client = new HttpDataClient(getResources().getString(R.string.base_url), context);
        send=(Button)rootView.findViewById(R.id.btnSend);
        imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        nextQuestion();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            interactions = (MainActivityInteractions) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MainActivityInteractions");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactions = null;
    }

    public void showLoading() {
        View loading = rootView.findViewById(R.id.loading_container);
        View container = rootView.findViewById(R.id.container);

        loading.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
        container.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));

        loading.setVisibility(View.VISIBLE);
        container.setVisibility(View.INVISIBLE);
    }

    public void hideLoading() {
        View loading = rootView.findViewById(R.id.loading_container);
        View container = rootView.findViewById(R.id.container);

        loading.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
        container.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));

        loading.setVisibility(View.INVISIBLE);
        container.setVisibility(View.VISIBLE);
    }

    public void nextQuestion() {
        int responce_id = QUESTION_VIEW;
        if(count > questionsCount - 1) {
            responce_id = SHOW_RESULT;
        }

        boolean valid = true;
        if(count > 0) {
            getAnswers();
            if(answers.size() == 0) {
                Toast.makeText(context, "you're trying to pass empty answer!", Toast.LENGTH_SHORT).show();
                return;
            }
            showLoading();
        }
        client.post("mobile/next-question/"+startToken, answers, responce_id);
    }

    public void getAnswers() {
        answers.clear();

        String qType = q.getType();

        if(qType.equals("text")) {
            String txt = ((EditText)controls[0]).getText().toString();
            if(txt.length() > 0) {
                answers.add(txt);
                imm.hideSoftInputFromWindow(controls[0].getWindowToken(), 0);
            }
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

    public void onEvent(JsonResponseEvent event) {
        if(event.getId() == QUESTION_VIEW ) {
            q = Question.fromJson(event.getData());

            String question=q.getText();
            String hint=q.getHint();
            String type=q.getType();
            options = q.getData();

            gridLayout.removeAllViews();
            llContainer.removeAllViews();

            String image = q.getImage();
            WebImageView wiv = (WebImageView)rootView.findViewById(R.id.ivQuestion);
            if(!image.isEmpty()) {
                String base_url = getResources().getString(R.string.base_url);
                wiv.setImageUrl(base_url + "img/test/" + image, R.drawable.image_placeholder);
                wiv.setVisibility(View.VISIBLE);
            }
            else {
                wiv.setImageDrawable(null);
                wiv.setVisibility(View.INVISIBLE);
            }

            tvQuestion.setText(question);
            tvQuestionNumber.setText((count + 1) + "/" + questionsCount);

            switch (type) {
                case "text":
                    loadTextAnswer();
                    break;
                case "radio":
                    loadRadioAnswer();
                    break;
                case "check":
                    loadSelectAnswer();
                    break;
                case "image":
                    loadImageAnswer();
                    break;
            }
            count++;

            hideLoading();
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
        Toast.makeText(context, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void  loadImageAnswer(){
        controls = new View[q.getData().size()];

        for (int i = 0; i < q.getData().size();i++) {
            WebImageView image = (WebImageView)inflater.inflate(
                    R.layout.question_item_image, gridLayout, false);

            controls[i] = image;

            String base_url = getResources().getString(R.string.base_url);
            image.setImageUrl(base_url + "img/question/" + q.getData().get(i));
            image.setTag(0);

            //Прописываем действие при выборе элемента
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < gridLayout.getChildCount(); j++) {
                        View img = gridLayout.getChildAt(j);
                        if(v == img) continue;
                        img.setBackgroundResource(R.drawable.bachground_image);
                        img.setTag(0);
                    }
                    v.setTag(1);
                    v.setBackgroundResource(R.drawable.background_image_active);
                }
            });

            gridLayout.addView(image);
        }
    }
    private void loadTextAnswer(){
        EditText etAnswer = (EditText)inflater.inflate(R.layout.question_item_text, llContainer, false);
        controls = new View[] { etAnswer };
        imm.showSoftInput(etAnswer, 0);
        llContainer.addView(etAnswer);
    }


    private void loadRadioAnswer() {
        final RadioGroup group = new RadioGroup(context);
        group.setOrientation(RadioGroup.VERTICAL);

        controls = new View[q.getData().size()];

        for (int i = 0; i < q.getData().size() ; i++) {
            RadioButton rb = (RadioButton)inflater.inflate(R.layout.question_item_radio, group, false);
            controls[i] = rb;
            rb.setText(q.getData().get(i));
            group.addView(rb);
        }
        llContainer.addView(group);
    }

    private void loadSelectAnswer(){
        controls = new View[q.getData().size()];

        for (int i = 0; i < q.getData().size();i++) {
            CheckBox checkBox = (CheckBox)inflater.inflate(R.layout.question_item_check,
                    llContainer, false);
            controls[i] = checkBox;
            checkBox.setText(options.get(i));
            llContainer.addView(checkBox);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        interactions.setTitle(testName);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
