package com.example.rav.testingo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Антон on 17.01.2015.
 */
public class ImageAdapter extends BaseAdapter {

    private String type;
    private List<String> answers;
    Context context;

    public ImageAdapter(String type, List<String> answers, Context context) {
        this.type = type;
        this.answers = answers;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
