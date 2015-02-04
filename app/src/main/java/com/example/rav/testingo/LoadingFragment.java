package com.example.rav.testingo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.rav.testingo.DataFlow.DataClient;
import com.example.rav.testingo.DataFlow.ErrorResponseEvent;
import com.example.rav.testingo.DataFlow.HttpDataClient;
import com.example.rav.testingo.DataFlow.JsonResponseEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by Shtutman on 02.02.2015.
 */
public abstract class LoadingFragment extends Fragment {
    public abstract void onEvent(JsonResponseEvent response);
    public abstract void onEvent(ErrorResponseEvent response);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void loadingComplete(View rootView) {
        View loadingContainer = rootView.findViewById(R.id.loading_container);
        View contentContainer = rootView.findViewById(R.id.content_container);

        loadingContainer.startAnimation(
                AnimationUtils.loadAnimation(rootView.getContext(), android.R.anim.fade_out));
        contentContainer.startAnimation(
                AnimationUtils.loadAnimation(rootView.getContext(), android.R.anim.fade_in));

        loadingContainer.setVisibility(View.INVISIBLE);
        contentContainer.setVisibility(View.VISIBLE);
    }

    protected void loadingStart(View rootView) {
        View loadingContainer = rootView.findViewById(R.id.loading_container);
        View contentContainer = rootView.findViewById(R.id.content_container);

        loadingContainer.startAnimation(
                AnimationUtils.loadAnimation(rootView.getContext(), android.R.anim.fade_in));
        contentContainer.startAnimation(
                AnimationUtils.loadAnimation(rootView.getContext(), android.R.anim.fade_out));

        loadingContainer.setVisibility(View.VISIBLE);
        contentContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
