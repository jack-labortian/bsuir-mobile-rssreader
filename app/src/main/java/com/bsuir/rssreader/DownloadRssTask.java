package com.bsuir.rssreader;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class DownloadRssTask extends AsyncTask<String, Integer, ArrayList<Item>> {

    public static final String TAG = "DownloadRssTask";

    private ArrayList<Item> items;
    private AsyncResponse delgate;

    public DownloadRssTask(AsyncResponse asyncResponse) {
        delgate = asyncResponse;
    }

    @Override
    protected void onPreExecute() {
        items = new ArrayList<>();
    }


    @Override
    protected ArrayList<Item> doInBackground(String... urls) {
        for (String u : urls) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(u);
                conn = (HttpURLConnection) url.openConnection();

                XmlParser parser = new XmlParser();

                items = parser.parse(conn.getInputStream());
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

        }
        return items;
    }

    @Override
    protected void onPostExecute(ArrayList<Item> result) {
        delgate.processFinish(result);
    }
}
