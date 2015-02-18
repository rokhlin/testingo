package com.example.rav.testingo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
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
    public static final String IS_NOTIFICATIONS_ARG = "isNotifications";
    private TestListAdapter cardArrayAdapter;
    private ListView listView;
    private Context context;
    private View rootView;
    private DataClient client;
    private String baseUrl;
    private LayoutInflater inflater;
    private LinearLayout linear;
    private String REST_URL;
    private String lastSearch = "";
    ArrayList<TestCard> testCards;
    private boolean isNotifications;

    public static FeedActivityFragment newInstance(boolean isNotifications) {
        FeedActivityFragment fragment = new FeedActivityFragment();
        Bundle b = new Bundle();
        b.putBoolean(IS_NOTIFICATIONS_ARG, isNotifications);
        fragment.setArguments(b);
        return fragment;
    }

    public FeedActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        if(getArguments() != null) {
            isNotifications = getArguments().getBoolean(IS_NOTIFICATIONS_ARG);
            if(!isNotifications) REST_URL = "mobile/tests";
            else REST_URL = "mobile/notifications/all";
        }
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        this.inflater = inflater;
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
        client.get(REST_URL, FEED_LIST_REQUEST);

        return rootView;
    }


    @Override
    public void onEvent(JsonResponseEvent response) {
        if(response.getId() == FEED_LIST_REQUEST) {
            TestCard[] rawCards = TestCard.arrayFromJson(response.getData());
            testCards = new ArrayList<TestCard>(Arrays.asList(rawCards));
            baseUrl=getResources().getString(R.string.base_url);
            cardArrayAdapter = new TestListAdapter(context, testCards);
            listView.setAdapter(cardArrayAdapter);
            listView.setEmptyView(rootView.findViewById(R.id.list_empty));

            loadingComplete(rootView);
        }
    }

    @Override
    public void onEvent(ErrorResponseEvent response) {
        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
    }


//    ADAPTER CLASS
    class TestListAdapter extends ArrayAdapter<TestCard> {
        TestListAdapter(Context context, ArrayList<TestCard> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = inflater.inflate(R.layout.list_item_card, parent, false);

            }

            final TestCard card = getItem(position);
            String description = card.getTest().getDescription();
            if(description.length() > 180) description = description.substring(0, 180) + "...";
            ((TextView)view.findViewById(R.id.textView)).setText(card.getUser().getName());
            ((TextView)view.findViewById(R.id.textView2)).setText(card.getTest().getName());
            ((TextView)view.findViewById(R.id.textView3)).setText(description);
            ((TextView)view.findViewById(R.id.textView4)).setText(card.getTest().getTags().toString());
            ((TextView)view.findViewById(R.id.textView5)).setText("Tested " + card.getTest().getCount() + " times.");
            ((WebImageView)view.findViewById(R.id.avatar)).setImageUrl(baseUrl + "img/avatar/" + card.getUser().getAvatar());
            ((WebImageView)view.findViewById(R.id.test_image)).setImageUrl(baseUrl + "img/test/" + card.getTest().getImage(), R.drawable.image_placeholder);

            linear = (LinearLayout)view.findViewById(R.id.onChannel);
            linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivityInteractions act = (MainActivityInteractions)getActivity();
                    String rId = card.getUser().getId();
                    act.showChannel(rId);
                }
            });

            return view;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isNotifications)
            ((MainActivityInteractions)getActivity()).setTitle("Notifications");
        else
            ((MainActivityInteractions)getActivity()).setTitle("Feed");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        new MenuInflater(getActivity().getApplication()).inflate(R.menu.search, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.equals(lastSearch)) return true;
                loadingStart(rootView);
                client.get("mobile/tests?search=" + s, FEED_LIST_REQUEST);
                lastSearch = s;
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if(s.equals(lastSearch)) return true;
                if(s.isEmpty()) {
                    client.get("mobile/tests", FEED_LIST_REQUEST);
                    lastSearch = "";
                }
                return true;
            }
        });
    }
}
