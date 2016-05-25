package com.bsuir.rssreader;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParser {
    private static final String ns = null;
    public static final String TAG = "XmlParser";

    public ArrayList parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private ArrayList readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Item> items = new ArrayList<Item>();
        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            int eventType = parser.getEventType();
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagName.equalsIgnoreCase("item")) {
                        items.add(readEntry(parser));
                    }
                    break;
            }
        }
        return items;
    }

    private Item readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");

        Item item = new Item();
        while (true) {
            if (parser.next() == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                break;
            }
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    item.title = readText(parser);
                    break;
                case "description":
                    String desc = readText(parser);

                    Pattern p = Pattern.compile("img src=\"(.*?)\"");
                    Matcher m = p.matcher(desc);
                    if (m.find()) {
                        item.img = m.group(1);
                    }

                    p = Pattern.compile("</a>(.*?)</p>");
                    m = p.matcher(desc);
                    if (m.find()) {
                        item.description = m.group(1);
                    }
                    break;
                case "link":
                    item.link = readText(parser);
                    break;
            }
        }

        return item;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}
