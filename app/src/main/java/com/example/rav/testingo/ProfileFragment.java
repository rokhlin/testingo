package com.example.rav.testingo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rav.testingo.DataFlow.ErrorResponseEvent;
import com.example.rav.testingo.DataFlow.HttpDataClient;
import com.example.rav.testingo.DataFlow.JsonResponseEvent;
import com.example.rav.testingo.DataStructures.UserSelfAccount;
import com.yelp.android.webimageview.WebImageView;

/**
 * Created by Max on 17.02.2015.
 */
public class ProfileFragment extends LoadingFragment {
    private MainActivityInteractions interactions;
    private HttpDataClient client; 
    private View rootView;
    LayoutInflater inflater;
    private Context context;
    private EditText editText, editText1, editText2, editText3, editText4;
    private WebImageView webImageView;
    private Button button;
    private TextView textView;
    UserSelfAccount userSelfAccount;
    private static final int PROFILE_JSON = 40;
    
    public static ProfileFragment newInstance() {
       return new ProfileFragment();
    }
    public ProfileFragment(){
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.profile,container,false);
        this.inflater = inflater;
        editText = (EditText)rootView.findViewById(R.id.editText);
        editText1 = (EditText)rootView.findViewById(R.id.editText1);
        editText2 = (EditText)rootView.findViewById(R.id.editText2);
        editText3 = (EditText)rootView.findViewById(R.id.editText3);
        editText4 = (EditText)rootView.findViewById(R.id.editText4);
        webImageView = (WebImageView)rootView.findViewById(R.id.avatar);
        button = (Button)rootView.findViewById(R.id.btnConfirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT);
                interactions.showFeed();
            }
        });
        textView = (TextView)rootView.findViewById(R.id.textView);

        client = new HttpDataClient(getResources().getString(R.string.base_url), rootView.getContext());
        client.get("mobile/profile", PROFILE_JSON);
        return rootView;
    }

    @Override
    public void onEvent(JsonResponseEvent response) {
        if(response.getId() == PROFILE_JSON) {
            UserSelfAccount u = UserSelfAccount.fromJson(response.getData());
            editText.setText(u.getFirstName());
            editText1.setText(u.getLastName());
            editText2.setText(u.getEmail());

            String baseUrl = getResources().getString(R.string.base_url);
            webImageView.setImageUrl(baseUrl + "img/avatar/" + u.getAvatar());

            loadingComplete(rootView);
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

    @Override
    public void onStart() {
        super.onStart();
        interactions.setTitle("Profile");
    }
}
