package com.codingblocks.newsappforpitching;


public class News {

    private String mTitle;
    private String mSection;
    private String mAuthor;
    private String mDate;
    private String mUrl;
    private String mThumbnail;
    private String mTrailTextHtml;

    public News(String title, String section, String author, String date, String url, String thumbnail, String trailText) {
        mTitle = title;
        mSection = section;
        mAuthor = author;
        mDate = date;
        mUrl = url;
        mThumbnail = thumbnail;
        mTrailTextHtml = trailText;
    }

    public News(String webTitle, String author, String webPublicationDate, String webUrl, String thumbnail, String trailText) {
        mTitle = webTitle;
     //   mSection = section;
        mAuthor = author;
        mDate = webPublicationDate;
        mUrl = webUrl;
        mThumbnail = thumbnail;
        mTrailTextHtml = trailText;
    }

    public News(String webTitle, String thumbnail) {
        mTitle = webTitle;
        mThumbnail=thumbnail;

    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
    public String getThumbnail() {
        return mThumbnail;
    }

    public String getTrailTextHtml() {
        return mTrailTextHtml;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmSection() {
        return mSection;
    }

    public void setmSection(String mSection) {
        this.mSection = mSection;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public String getmTrailTextHtml() {
        return mTrailTextHtml;
    }

    public void setmTrailTextHtml(String mTrailTextHtml) {
        this.mTrailTextHtml = mTrailTextHtml;
    }
}
