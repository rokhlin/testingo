package com.example.rav.testingo;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.rav.testingo.DataStructures.ResultDetail;
import com.yelp.android.webimageview.WebImageView;

import de.greenrobot.event.EventBus;

public class ResultFragment extends LoadingFragment {
    private MainActivityInteractions interactions;
    private static final int RESULT_RESPONSE = 131;
    private static final String RESULT_ID_ARG = "id";
    private Context context;
    private String id;

    private LayoutInflater inflater;
    private ViewGroup rootView;

    public static ResultFragment newInstance(String id) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(RESULT_ID_ARG, id);
        fragment.setArguments(args);
        return fragment;
    }

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(RESULT_ID_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_result, container, false);
        context = getActivity();

        DataClient client = new HttpDataClient(getResources().getString(R.string.base_url), context);
        client.get("mobile/results/"+id, RESULT_RESPONSE);

        Button feedButton = (Button)rootView.findViewById(R.id.toFeedButton);
        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interactions.showFeed();
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
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactions = null;
    }


    @Override
    public void onEvent(JsonResponseEvent response) {
        if(response.getId() == RESULT_RESPONSE) {
            ResultDetail res = ResultDetail.fromJson(response.getData());

//            done=res.getResult().getDone().toString();
            TextView tvRes=(TextView)rootView.findViewById(R.id.tvResult);
            TextView tvAuthor=(TextView)rootView.findViewById(R.id.tvAuthor);
            TextView tvTest=(TextView)rootView.findViewById(R.id.tvTestName);
            TextView tvResDetail=(TextView)rootView.findViewById(R.id.tvResultDetail);
            WebImageView wiv = (WebImageView)rootView.findViewById(R.id.ivResult);
            WebImageView wivAuthorLogo = (WebImageView)rootView.findViewById(R.id.wivAvatar);

            tvRes.setText(res.getResult().getText());
            tvResDetail.setText(res.getResult().getDescription());
            tvAuthor.setText(res.getUser().getName());
            tvTest.setText(res.getTest().getName());

            String base_url = getResources().getString(R.string.base_url);

            wivAuthorLogo.setImageUrl(base_url + "img/avatar/" + res.getUser().getAvatar());

            String image = res.getResult().getDescription();
            if(!image.isEmpty()) {
                wiv.setImageUrl(base_url + "img/result/" + image, R.drawable.image_placeholder);
                wiv.setVisibility(View.VISIBLE);
            }
            else {
                wiv.setVisibility(View.GONE);
            }
            loadingComplete(rootView);
        }
    }

    @Override
    public void onEvent(ErrorResponseEvent response) {
        Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
