package com.example.rav.testingo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rav.testingo.DataFlow.DataClient;
import com.example.rav.testingo.DataFlow.ErrorResponseEvent;
import com.example.rav.testingo.DataFlow.HttpDataClient;
import com.example.rav.testingo.DataFlow.JsonResponseEvent;
import com.example.rav.testingo.DataStructures.TestCard;
import com.example.rav.testingo.DataStructures.TestInfo;
import com.example.rav.testingo.DataStructures.UserChannel;
import com.yelp.android.webimageview.WebImageView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Max on 04.02.2015.
 */
public class ChannelFragment extends LoadingFragment {
    public static final String CHANNEL_ID_ARG = "id";
    public static final int CHANNEL_REQUEST = 49;
    private TestListAdapter cardArrayAdapter;
    private ListView listView;
    private Context context;
    private View rootView;
    private DataClient client;
    private String baseUrl;
    private String id;
    LayoutInflater inflater;
    private String lastSearch = "";
    private MainActivityInteractions interactions;
    private MenuItem subscribeButton;

    private boolean subscribed = false;
    private boolean dataReady = false;

    public static ChannelFragment newInstance(String id) {
        ChannelFragment fragment = new ChannelFragment();
        Bundle b = new Bundle();
        b.putString(CHANNEL_ID_ARG, id);
        fragment.setArguments(b);
        return fragment;
    }

    public ChannelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            id = args.getString(CHANNEL_ID_ARG);
        }
        Log.d("TAG",id);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        this.setHasOptionsMenu(true);
        this.inflater = inflater;
        listView = (ListView)rootView.findViewById(R.id.list);
        baseUrl = getResources().getString(R.string.base_url);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String rId = ((TestInfo)parent.getItemAtPosition(position)).getId();
                interactions.showTestDetails(rId);
            }
        });

        client = new HttpDataClient(getResources().getString(R.string.base_url), rootView.getContext());
        client.get("mobile/channels/"+id, CHANNEL_REQUEST);

        return rootView;
    }


    @Override
    public void onEvent(JsonResponseEvent response) {
        if(response.getId() == CHANNEL_REQUEST) {
            UserChannel channel = UserChannel.fromJson(response.getData());
            baseUrl=getResources().getString(R.string.base_url);
            cardArrayAdapter = new TestListAdapter(context, (ArrayList<TestInfo>)channel.getChannel());
            listView.setAdapter(cardArrayAdapter);
            listView.setEmptyView(rootView.findViewById(R.id.list_empty));
            Log.d("LOG", String.valueOf(response.getData()));

            interactions.setTitle(channel.getUser().getName());

            subscribed = channel.isSubscribed();
            if(subscribed) subscribeButton.setIcon(R.drawable.ic_subs);
            dataReady = true;
            loadingComplete(rootView);
        }
    }

    @Override
    public void onEvent(ErrorResponseEvent response) {
        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        interactions.setTitle("Loading...");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        interactions = (MainActivityInteractions)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactions = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        new MenuInflater(getActivity().getApplication()).inflate(R.menu.channel, menu);

        subscribeButton = menu.findItem(R.id.subscribe);
        if(subscribed) subscribeButton.setIcon(R.drawable.ic_subs);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.equals(lastSearch)) return true;
                loadingStart(rootView);
                client.get("mobile/channels/" + id + "?search=" + s, CHANNEL_REQUEST);
                lastSearch = s;
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if(s.equals(lastSearch)) return true;
                if(s.isEmpty()) {
                    client.get("mobile/channels/" + id, CHANNEL_REQUEST);
                    lastSearch = "";
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        switch (id) {
            case R.id.subscribe:
                if(!dataReady) break;

                if(subscribed) {
                    client.get("mobile/tests/unsubscribe/"+this.id, 654);
                    item.setIcon(R.drawable.ic_nosubs);
                    subscribed = false;
                }
                else {
                    client.get("mobile/tests/subscribe/"+this.id, 655);
                    item.setIcon(R.drawable.ic_subs);
                    subscribed = true;
                }
            break;
        }

        return true;
    }

    //    ADAPTER CLASS
    class TestListAdapter extends ArrayAdapter<TestInfo> {

        TestListAdapter(Context context, ArrayList<TestInfo> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = inflater.inflate(R.layout.channel_item_card, parent, false);
            }
            TestInfo card = getItem(position);
            ((TextView)view.findViewById(R.id.textView2)).setText(card.getName());
            ((TextView)view.findViewById(R.id.textView3)).setText(card.getDescription());
            ((TextView)view.findViewById(R.id.textView4)).setText(card.getTags().toString());
            ((TextView)view.findViewById(R.id.textView5)).setText("Tested "+card.getCount()+" times.");
            ((WebImageView)view.findViewById(R.id.test_image)).setImageUrl(baseUrl + "img/test/" + card.getImage(), R.drawable.image_placeholder);
            return view;
        }
    }


}
