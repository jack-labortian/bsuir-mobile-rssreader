package com.bsuir.rssreader.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.bsuir.rssreader.datasource.model.RSSModel;
import com.bsuir.rssreader.datasource.repository.RepositoryImpl;
import com.bsuir.rssreader.datasource.specification.FindAllNotesSpecification;
import com.bsuir.rssreader.service.DownloadRssTask;

import java.util.ArrayList;
import java.util.List;

import rss.com.example.bsuir.rssreader.R;


public class MainActivity extends AppCompatActivity implements AsyncResponse {
    public static final String TAG = "MainActivity";
    private int currPosition;
    private List<RSSModel> rssModels;
    private EditText newRSSEdit;
    private EditText newNameEdit;
    private ListView listView;
    private RepositoryImpl repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new RepositoryImpl(getApplicationContext());

        rssModels = repository.query(new FindAllNotesSpecification());

        listView = (ListView) findViewById(R.id.rssList);

        RSSAdapter adapter = new RSSAdapter(rssModels, this);

        adapter.notifyDataSetChanged();

        listView = (ListView) findViewById(R.id.rssList);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.w(TAG, "" + position);

                DownloadRssTask downloadRssTask = new DownloadRssTask(MainActivity.this);
                downloadRssTask.execute(rssModels.get(position).getLink());

                currPosition = position;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(MainActivity.this);
                View localView = getLayoutInflater().inflate(R.layout.delete, null);

                localBuilder.setView(localView).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

                        RSSModel model = rssModels.get(position);
                        repository.remove(model);
                        rssModels.remove(position);

                        RSSAdapter adapter = (RSSAdapter) listView.getAdapter();
                        adapter.notifyDataSetChanged();
                    }
                });

                localBuilder.setView(localView).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        RSSAdapter adapter = (RSSAdapter) listView.getAdapter();
                        adapter.notifyDataSetChanged();
                    }
                });

                localBuilder.show();
                return true;
            }
        });

        listView.setAdapter(adapter);
        listView.setLongClickable(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addRss) {

            AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
            View localView = getLayoutInflater().inflate(R.layout.add_rss, null);

            localBuilder.setView(localView).setPositiveButton("Save Rss", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                    String newName = newNameEdit.getText().toString();
                    String newUrl = newRSSEdit.getText().toString();

                    RSSModel model = new RSSModel(newName, newUrl);

                    repository.add(model);
                    rssModels.add(model);

                    RSSAdapter adapter = (RSSAdapter) listView.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            });

            localBuilder.setView(localView).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                    RSSAdapter adapter = (RSSAdapter) listView.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            });

            newNameEdit = (EditText) localView.findViewById(R.id.new_name);
            newRSSEdit = (EditText) localView.findViewById(R.id.new_link);

            localBuilder.show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(ArrayList<Item> items) {

        Intent intent = new Intent(MainActivity.this, RssListActivity.class);
        intent.putParcelableArrayListExtra("items", items);

        intent.putExtra("barTitle", rssModels.get(currPosition).getTitle());
        startActivityForResult(intent, 0);


    }

}
