package com.example.rav.testingo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rav.testingo.DataFlow.DataClient;
import com.example.rav.testingo.DataFlow.ErrorResponseEvent;
import com.example.rav.testingo.DataFlow.HttpDataClient;
import com.example.rav.testingo.DataFlow.JsonResponseEvent;
import com.example.rav.testingo.DataStructures.TestCard;
import com.yelp.android.webimageview.WebImageView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Max on 04.02.2015.
 */
public class FeedActivityFragment extends LoadingFragment {

    public static final int FEED_LIST_REQUEST = 49;
    private TestListAdapter cardArrayAdapter;
    private ListView listView;
    private Context context;
    private View rootView;
    private DataClient client;
    private String baseUrl;
    ArrayList<TestCard> testCards;

    public static FeedActivityFragment newInstance() {
        FeedActivityFragment fragment = new FeedActivityFragment();
        return fragment;
    }

    public FeedActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView)rootView.findViewById(R.id.list);
        baseUrl = getResources().getString(R.string.base_url);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivityInteractions act = (MainActivityInteractions)getActivity();
                String rId = ((TestCard)parent.getItemAtPosition(position)).getTest().getId();
                act.showTestDetails(rId);
            }
        });

        client = new HttpDataClient(getResources().getString(R.string.base_url), rootView.getContext());
        client.get("mobile/tests", FEED_LIST_REQUEST);

        return rootView;
    }


    @Override
    public void onEvent(JsonResponseEvent response) {
        if(response.getId() == FEED_LIST_REQUEST) {

            TestCard[] rawCards = TestCard.arrayFromJson(response.getData());
            testCards = new ArrayList<TestCard>(Arrays.asList(rawCards));
            testCards.add(testCards.get(0));
            testCards.add(testCards.get(0));
            testCards.add(testCards.get(0));
            baseUrl=getResources().getString(R.string.base_url);
            cardArrayAdapter = new TestListAdapter(context, testCards);
            listView.setAdapter(cardArrayAdapter);

            loadingComplete(rootView);
        }
    }

    @Override
    public void onEvent(ErrorResponseEvent response) {
        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
    }


//    ADAPTER CLASS
    class TestListAdapter extends ArrayAdapter<TestCard> {
        LayoutInflater inflater;

        TestListAdapter(Context context, ArrayList<TestCard> objects) {
            super(context, 0, objects);
            inflater = LayoutInflater.from(this.getContext());
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = inflater.inflate(R.layout.list_item_card, parent, false);

            }
            TestCard card = getItem(position);
            ((TextView)view.findViewById(R.id.textView)).setText(card.getUser().getName());
            ((TextView)view.findViewById(R.id.textView2)).setText(card.getTest().getName());
            ((TextView)view.findViewById(R.id.textView3)).setText(card.getTest().getDescription());
            ((TextView)view.findViewById(R.id.textView4)).setText(card.getTest().getTags().toString());
            ((TextView)view.findViewById(R.id.textView5)).setText("Tested "+card.getTest().getCount()+" times.");
            ((WebImageView)view.findViewById(R.id.avatar)).setImageUrl(baseUrl + "img/avatar/" + card.getUser().getAvatar());
            ((WebImageView)view.findViewById(R.id.test_image)).setImageUrl(baseUrl + "img/test/" + card.getTest().getImage(), R.drawable.image_placeholder);
            return view;
        }
    }
}
