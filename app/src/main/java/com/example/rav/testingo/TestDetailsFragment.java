package com.example.rav.testingo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rav.testingo.DataFlow.DataClient;
import com.example.rav.testingo.DataFlow.ErrorResponseEvent;
import com.example.rav.testingo.DataFlow.HttpDataClient;
import com.example.rav.testingo.DataFlow.JsonResponseEvent;
import com.example.rav.testingo.DataStructures.SimpleJsonResponse;
import com.example.rav.testingo.DataStructures.TestDetailCard;
import com.yelp.android.webimageview.WebImageView;

/**
 * Created by Max on 04.02.2015.
 */
public class TestDetailsFragment extends LoadingFragment {
    private MainActivityInteractions interactions;
    TextView testDescription, testName, channelName, tags, tested;
    Button btnStartTest;
    private DataClient client;
    private View rootView;
    private Context context;
//    private LayoutInflater inflater;
    TestDetailCard testCard;
    SimpleJsonResponse startToken;
    private static final int TEST_INFO_CARD_JSON = 48;
    private static final int TEST_START_TOKEN = 47;


    public static TestDetailsFragment newInstance(String id) {
        TestDetailsFragment fragment = new TestDetailsFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    public TestDetailsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        this.inflater = inflater;
        rootView = inflater.inflate(R.layout.activity_test_info,container,false);
        channelName = (TextView)rootView.findViewById(R.id.textView);
        testName = (TextView) rootView.findViewById(R.id.textView2);
        testDescription = (TextView) rootView.findViewById(R.id.textView3);
        tags = (TextView) rootView.findViewById(R.id.textView4);
        tested = (TextView) rootView.findViewById(R.id.textView5);
        btnStartTest = (Button)rootView.findViewById(R.id.btnStartTest);
        btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.get("mobile/tests/start/" + testCard.getTest().getId(), TEST_START_TOKEN);
            }
        });
        client = new HttpDataClient(getResources().getString(R.string.base_url), rootView.getContext());
        client.get("mobile/tests/" + getArguments().getString("id"), TEST_INFO_CARD_JSON);
        return rootView;
    }

    @Override
    public void onEvent(JsonResponseEvent response) {
        if(response.getId() == TEST_INFO_CARD_JSON) {
            TestDetailCard t = TestDetailCard.fromJson(response.getData());
            testCard = t;

            channelName.setText(t.getUser().getName());
            testName.setText(t.getTest().getName());
            testDescription.setText(t.getTest().getDescription());
            tags.setText(t.getTest().getTags().toString());
            tested.setText(t.getTest().getCount().toString());

            String baseUrl = getResources().getString(R.string.base_url);
            WebImageView avatar = (WebImageView)rootView.findViewById(R.id.avatar);
            WebImageView test_image = (WebImageView)rootView.findViewById(R.id.test_image);
            avatar.setImageUrl(baseUrl + "img/avatar/" + t.getUser().getAvatar());
            test_image.setImageUrl(baseUrl + "img/test/" + t.getTest().getImage(), R.drawable.image_placeholder);
            Log.d("TAG", baseUrl + "img/avatar/" + t.getUser().getAvatar());

            loadingComplete(rootView);
        }

        if(response.getId() == TEST_START_TOKEN) {
            SimpleJsonResponse resp = SimpleJsonResponse.fromJson(response.getData());
            String token = resp.getData();
            showTest(token);
        }

    }

    @Override
    public void onEvent(ErrorResponseEvent response) {
        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        interactions = (MainActivityInteractions)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactions = null;
    }

    public void showTest(String token){
//        Intent intent = new Intent(rootView.getContext(), TestActivity.class);
//        intent.putExtra("startToken", token);
//        intent.putExtra("questionsCount", testCard.getTest().getQuestionsCount());
//        startActivity(intent);
        interactions.startTest(token, testCard.getTest().getName(),
                testCard.getTest().getQuestionsCount());
    }
}
