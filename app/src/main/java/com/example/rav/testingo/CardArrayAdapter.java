package com.example.rav.testingo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rav.testingo.DataStructures.TestCard;
import com.yelp.android.webimageview.WebImageView;

import java.util.ArrayList;
import java.util.List;

/**
* Created by Max on 14.01.2015.
*/
public class CardArrayAdapter extends ArrayAdapter<TestCard> {
    private static final String TAG = "CardArrayAdapter";
    private List<TestCard> cardList = new ArrayList<TestCard>();
    private String baseUrl;

    static class CardViewHolder {
        TextView channelName;
        TextView testName;
        TextView testDescription;
        TextView tested;
        TextView tags;
        WebImageView img1;
        WebImageView img2;
    }

    public CardArrayAdapter(Context context, int resource, ArrayList<TestCard> testCards, String baseUrl) {
        super(context, resource);
        cardList = testCards;
        this.baseUrl=baseUrl;
    }

    @Override
    public void add(TestCard object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public TestCard getItem(int index) {
        return this.cardList.get(index);
    }

    public List<TestCard> getCardList() {
        return cardList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.img1=(WebImageView) row.findViewById(R.id.avatar);
            viewHolder.img2=(WebImageView) row.findViewById(R.id.test_image);
            viewHolder.channelName = (TextView) row.findViewById(R.id.textView);
            viewHolder.testName = (TextView) row.findViewById(R.id.textView2);
            viewHolder.testDescription = (TextView) row.findViewById(R.id.textView3);
            viewHolder.tags = (TextView) row.findViewById(R.id.textView4);
            viewHolder.tested = (TextView) row.findViewById(R.id.textView5);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        TestCard card = getItem(position);
        viewHolder.channelName.setText(card.getUser().getName());
        viewHolder.testName.setText(card.getTest().getName());
        viewHolder.testDescription.setText(card.getTest().getDescription());
        viewHolder.tags.setText(card.getTest().getTags().toString());
        viewHolder.tested.setText("Tested "+card.getTest().getCount()+" times.");

        viewHolder.img1.setImageUrl(baseUrl + "img/avatar/" + card.getUser().getAvatar());
        viewHolder.img2.setImageUrl(baseUrl + "img/test/" + card.getTest().getImage(), R.drawable.image_placeholder);
        Log.d("TAG", baseUrl + "img/avatar/" + card.getUser().getAvatar());

        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}