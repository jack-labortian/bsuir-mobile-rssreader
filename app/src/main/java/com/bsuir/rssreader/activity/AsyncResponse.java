package com.bsuir.rssreader.activity;

import java.util.ArrayList;

public interface AsyncResponse {
    void processFinish(ArrayList<Item> items);
}
