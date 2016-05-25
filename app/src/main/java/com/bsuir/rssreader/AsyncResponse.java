package com.bsuir.rssreader;

import java.util.ArrayList;

public interface AsyncResponse {
    void processFinish(ArrayList<Item> items);
}
