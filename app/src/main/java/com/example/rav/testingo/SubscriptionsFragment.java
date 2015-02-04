package com.example.rav.testingo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rav.testingo.DataFlow.DataClient;
import com.example.rav.testingo.DataFlow.ErrorResponseEvent;
import com.example.rav.testingo.DataFlow.HttpDataClient;
import com.example.rav.testingo.DataFlow.JsonResponseEvent;
import com.example.rav.testingo.DataStructures.ResultCard;
import com.example.rav.testingo.DataStructures.TestCard;
import com.example.rav.testingo.DataStructures.UserBasic;
import com.yelp.android.webimageview.WebImageView;

/**
 * Created by Shtutman on 04.02.2015.
 */

public class SubscriptionsFragment extends LoadingFragment {
    private DataClient client;
    private View rootView;
    private Context context;
    private ListAdapter adapter;
    private ListView list;
    private String baseUrl;
    UserBasic[] cards;

    public static final int USERS_LIST_REQUEST = 97;

    public static SubscriptionsFragment newInstance() {
        SubscriptionsFragment fragment = new SubscriptionsFragment();
        return fragment;
    }

    public SubscriptionsFragment() {
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
        list = (ListView)rootView.findViewById(R.id.list);
        baseUrl = getResources().getString(R.string.base_url);

        client = new HttpDataClient(getResources().getString(R.string.base_url), rootView.getContext());
        client.get("mobile/tests/subscribtions", USERS_LIST_REQUEST);

        return rootView;
    }


    @Override
    public void onEvent(JsonResponseEvent response) {
        if(response.getId() == USERS_LIST_REQUEST) {
            cards = UserBasic.arrayFromJson(response.getData());
            adapter = new SubsListAdapter(context, cards);
            list.setAdapter(adapter);

            loadingComplete(rootView);
        }
    }

    @Override
    public void onEvent(ErrorResponseEvent response) {
        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
    }


    //ADAPTER CLASS
    class SubsListAdapter extends ArrayAdapter<UserBasic> {
        LayoutInflater inflater;

        SubsListAdapter(Context context, UserBasic[] objects) {
            super(context, 0, objects);
            inflater = LayoutInflater.from(this.getContext());
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = inflater.inflate(R.layout.subscribtion_list_card, parent, false);

            }

            UserBasic card = getItem(position);

            ((WebImageView)view.findViewById(R.id.image1)).setImageUrl(baseUrl + "img/avatar/" + card.getAvatar());
            ((TextView)view.findViewById(R.id.text1)).setText(card.getName());
            return view;
        }
    }
}
