package com.example.rav.testingo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        list = (ListView)rootView.findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivityInteractions act = (MainActivityInteractions)getActivity();
                String rId = ((ResultCard)parent.getItemAtPosition(position)).getResult().getId();
//                act.showResultDetails(rId);
                act.showSubscriptions();
            }
        });

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
                view = inflater.inflate(R.layout.result_list_card, parent, false);

            }

            ResultCard card = getItem(position);
            ((TextView)view.findViewById(R.id.text1)).setText(card.getTest().getName());
            ((TextView)view.findViewById(R.id.text2)).setText(card.getResult().getText());
            return view;
        }
    }
}
