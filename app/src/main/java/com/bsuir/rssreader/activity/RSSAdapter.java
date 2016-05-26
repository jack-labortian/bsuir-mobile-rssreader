package com.bsuir.rssreader.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bsuir.rssreader.datasource.model.RSSModel;

import java.util.List;

import rss.com.example.bsuir.rssreader.R;

/**
 * @author Eugene Novik
 */
public class RSSAdapter extends BaseAdapter {
    private List<RSSModel> items = null;
    private AppCompatActivity actionBar;

    public RSSAdapter(List<RSSModel> items, AppCompatActivity actionBar) {
        this.items = items;
        this.actionBar = actionBar;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int paramInt) {
        return items.get(paramInt);
    }

    @Override
    public long getItemId(int paramInt) {
        return paramInt;
    }

    @Override
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null) {
            LayoutInflater localLayoutInflater = (LayoutInflater) actionBar.getSystemService("layout_inflater");
            paramView = localLayoutInflater.inflate(R.layout.activity_main, paramViewGroup, false);
            localLayoutInflater.inflate(R.layout.activity_main, paramViewGroup, false);
        }
        TextView view = (TextView) paramView.findViewById(R.id.title);
        RSSModel rssModel = this.items.get(paramInt);
        view.setText(rssModel.getTitle());

        return paramView;
    }
}
