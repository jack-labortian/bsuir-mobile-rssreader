package com.bsuir.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import rss.com.example.bsuir.rssreader.R;


public class MainActivity extends AppCompatActivity implements AsyncResponse {
    public static final String TAG = "MainActivity";
    private List<String> rssList;
    private int currPosition;
    private List<String> category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.rssList);

        rssList = new ArrayList<>();
        rssList.add("https://news.yahoo.com/rss/");
        rssList.add("http://odinpiotrred-001-site1.etempurl.com/files/test.xml");

        category = new ArrayList<>();
        category.add("My Rss");
        category.add("Your Rss");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, category);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.w(TAG, "" + position);

                DownloadRssTask downloadRssTask = new DownloadRssTask(MainActivity.this);
                downloadRssTask.execute(rssList.get(position));

                currPosition = position;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(ArrayList<Item> items) {

        Intent intent = new Intent(MainActivity.this, RssListActivity.class);
        intent.putParcelableArrayListExtra("items", items);

        intent.putExtra("barTitle", category.get(currPosition));
        startActivityForResult(intent, 0);


    }
}
