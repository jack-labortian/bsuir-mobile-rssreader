package com.bsuir.rssreader.datasource.model;

/**
 * @author Eugene Novik
 */
public class RSSModel {
    private long id;
    private String title;
    private String link;

    public RSSModel() {

    }

    public RSSModel(long id, String title, String link) {
        this.id = id;
        this.title = title;
        this.link = link;
    }

    public RSSModel(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String url) {
        this.link = url;
    }
}
