package com.bsuir.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import rss.com.example.bsuir.rssreader.R;


public class RssListActivity extends AppCompatActivity {

    public final static String TAG = "RssListActivity";

    private ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_list);
        ListView listView = (ListView) findViewById(R.id.rssListView);

        getSupportActionBar().setTitle(getIntent().getStringExtra("barTitle"));


        items = getIntent().getParcelableArrayListExtra("items");

        ArrayList<String> list = new ArrayList<>(items.size());
        for (Item i : items) {
            list.add(i.title);
        }

        listView.setAdapter(new ItemsAdapter(this, R.layout.singleitem, items));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RssListActivity.this, WebViewActivity.class);
                intent.putExtra("url", items.get(position).link);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rss_list, menu);
        return true;
    }

}
