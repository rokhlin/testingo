package com.example.rav.testingo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rav.testingo.DataFlow.DataClient;
import com.example.rav.testingo.DataFlow.ErrorResponseEvent;
import com.example.rav.testingo.DataFlow.HttpDataClient;
import com.example.rav.testingo.DataFlow.JsonResponseEvent;
import com.example.rav.testingo.DataStructures.ResultCard;
import com.example.rav.testingo.DataStructures.ResultDetail;
import com.yelp.android.webimageview.WebImageView;

import de.greenrobot.event.EventBus;


public class ResultActivity extends ActionBarActivity {
    private int TEXT_DETAIL_CARD_JSON=0;
    private ResultCard[] res;
    private Button btnClose;
    private TextView tvRes, tvResDetail;
    private String result, textDetail,image,done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tvRes=(TextView)findViewById(R.id.tvResult);
        tvResDetail=(TextView)findViewById(R.id.tvResultDetail);
        btnClose=(Button)findViewById(R.id.btnResult);

        DataClient client = new HttpDataClient(getResources().getString(R.string.base_url), this);
        client.get("test_json/result_detail.json", TEXT_DETAIL_CARD_JSON);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onEvent(JsonResponseEvent event) {
        if(event.getId() == TEXT_DETAIL_CARD_JSON) {

            ResultDetail res = ResultDetail.fromJson(event.getData());
            result = res.getResult().getText();
            textDetail=res.getResult().getDescription();
            image=res.getResult().getDescription();
            done=res.getResult().getDone().toString();

            Log.d("RESULT", "Load Result");
            Log.d("RESULT", "text " + result);

            Log.d("RESULT", "textDetail) "+textDetail);
            Log.d("RESULT", "image "+image);
            Log.d("RESULT", "done "+done);

            tvRes.setText(result);
            tvResDetail.setText(textDetail);

            if(!image.isEmpty()) {
                String base_url = getResources().getString(R.string.base_url);
                WebImageView wiv = (WebImageView)findViewById(R.id.ivResult);
                wiv.setImageUrl(base_url + "img/result/" + image);
            }


        }
    }

    public void onEvent(ErrorResponseEvent event) {
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
