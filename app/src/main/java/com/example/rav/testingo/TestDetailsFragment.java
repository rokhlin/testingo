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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rav.testingo.DataFlow.DataClient;
import com.example.rav.testingo.DataFlow.ErrorResponseEvent;
import com.example.rav.testingo.DataFlow.HttpDataClient;
import com.example.rav.testingo.DataFlow.JsonResponseEvent;
import com.example.rav.testingo.DataStructures.Comment;
import com.example.rav.testingo.DataStructures.SimpleJsonResponse;
import com.example.rav.testingo.DataStructures.TestDetailCard;
import com.yelp.android.webimageview.WebImageView;

/**
 * Created by Max on 04.02.2015.
 */
public class TestDetailsFragment extends LoadingFragment {
    private MainActivityInteractions interactions;
    TextView testDescription, testName, channelName, tags, tested;
    Button btnStartTest, buttonComment, addComment;
    private HttpDataClient client;
    LayoutInflater inflater;
    private View rootView;
    private Context context;
    private EditText editText;
    private LinearLayout onChannel, commentBlock, comments;
    TestDetailCard testCard;
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
        rootView = inflater.inflate(R.layout.activity_test_info,container,false);
        this.inflater = inflater;
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
        commentBlock = (LinearLayout)rootView.findViewById(R.id.commentBlock);
        commentBlock.setVisibility(View.GONE);
        comments = (LinearLayout)rootView.findViewById(R.id.comments);
        comments.setVisibility(View.GONE);
        buttonComment = (Button)rootView.findViewById(R.id.buttonComment);
        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (commentBlock.)
                commentBlock.setVisibility(View.VISIBLE);
                buttonComment.setVisibility(View.GONE);
                comments.setVisibility(View.VISIBLE);
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
            onChannel = (LinearLayout)rootView.findViewById(R.id.onChannel);
            onChannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivityInteractions act = (MainActivityInteractions)getActivity();
                    String rId =testCard.getUser().getId();
                    act.showChannel(rId);
                }
            });
            editText = (EditText)rootView.findViewById(R.id.editText);
            addComment = (Button)rootView.findViewById(R.id.addComment);
            addComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String comment=String.valueOf(editText.getText());
                    editText.setText("");
                    String id = getArguments().getString("id");
                    client.put("mobile/tests/comment/"+id, Comment.toJson(comment), 937);
                    client.get("mobile/tests/" + getArguments().getString("id"), TEST_INFO_CARD_JSON);
                    loadingStart(rootView);
                    ((ViewGroup)rootView.findViewById(R.id.comments)).removeAllViews();
                }
            });
            int i = 3;
            ViewGroup container = (ViewGroup)rootView.findViewById(R.id.comments);
            for (Comment comment : t.getComments()) {
                LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.comment_item, container, false);
                ((TextView)layout.findViewById(R.id.textView)).setText(comment.getUser().getName());
                ((TextView)layout.findViewById(R.id.textView2)).setText(comment.getText());
                i++;
                ((TextView)layout.findViewById(R.id.textView3)).setText("17.02/12:1"+i);
                ((WebImageView)layout.findViewById(R.id.avatar)).setImageUrl(baseUrl + "img/avatar/" + comment.getUser().getAvatar());
                container.addView(layout);
            }
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
        interactions.startTest(token, testCard.getTest().getName(),
                testCard.getTest().getQuestionsCount());
    }
}
