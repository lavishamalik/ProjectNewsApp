package com.codingblocks.newsappforpitching;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class BookmarkNews {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String newstitle;

    private String newsurl;


    public BookmarkNews(String newstitle, String newsurl) {
        this.newstitle = newstitle;
        this.newsurl = newsurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getNewsurl() {
        return newsurl;
    }

    public void setNewsurl(String newsurl) {
        this.newsurl = newsurl;
    }
}
