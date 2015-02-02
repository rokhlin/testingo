package com.example.rav.testingo;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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

import java.util.List;

import de.greenrobot.event.EventBus;

public class ResultListFragment extends LoadingFragment {
    private DataClient client;
    private View rootView;
    private Context context;
    private ListAdapter adapter;
    private ListView list;
    ResultCard[] cards;

    public static final int RESULT_LIST_REQUEST = 98;

    public static ResultListFragment newInstance() {
        ResultListFragment fragment = new ResultListFragment();
        return fragment;
    }

    public ResultListFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_result_list, container, false);
        list = (ListView)rootView.findViewById(R.id.list);

        client = new HttpDataClient(getResources().getString(R.string.base_url), rootView.getContext());
        client.get("mobile/results", RESULT_LIST_REQUEST);

        return rootView;
    }


    @Override
    public void onEvent(JsonResponseEvent response) {
        if(response.getId() == RESULT_LIST_REQUEST) {
            cards = ResultCard.arrayFromJson(response.getData());
            adapter = new ResultListAdapter(context, cards);
            list.setAdapter(adapter);

            loadingComplete(rootView);
        }
    }

    @Override
    public void onEvent(ErrorResponseEvent response) {
        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
    }


    //ADAPTER CLASS
    class ResultListAdapter extends ArrayAdapter<ResultCard> {
        LayoutInflater inflater;

        ResultListAdapter(Context context, ResultCard[] objects) {
            super(context, 0, objects);
            inflater = LayoutInflater.from(this.getContext());
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            }

            ResultCard card = getItem(position);
            ((TextView)view.findViewById(android.R.id.text1)).setText(card.getTest().getName());
            return view;
        }
    }
}
