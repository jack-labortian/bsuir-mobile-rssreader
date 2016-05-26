package com.bsuir.rssreader.activity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rss.com.example.bsuir.rssreader.R;

public class ItemsAdapter extends ArrayAdapter<Item> {

    public static final String TAG = "ItemsAdapter";

    private Activity activity;


    public class ViewHolder {
        public TextView title;
        public ImageView image;
    }

    public ItemsAdapter(Activity act, int resource, List<Item> items) {
        super(act, resource, items);
        this.activity = act;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Item item = getItem(position);

        View v = convertView;
        final ViewHolder holder;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.singleitem, null);
            holder = new ViewHolder();
            holder.title = (TextView) v.findViewById(R.id.title);
            holder.image = (ImageView) v.findViewById(R.id.image);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        if (item != null) {
            holder.title.setText(item.title);
        } else {
            Log.e(TAG, "item is null");
        }

        return v;
    }
}