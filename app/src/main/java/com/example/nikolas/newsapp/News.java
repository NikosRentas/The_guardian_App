package com.example.nikolas.newsapp;

public class News {
    private String mSectionName;
    private String mPublishDate;
    private String mWebTitle;
    private String mWebURL;

    public News(String sectionName, String publishDate, String webTitle, String webURL) {
        mSectionName = sectionName;
        mPublishDate = publishDate;
        mWebTitle = webTitle;
        mWebURL = webURL;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmPublishDate() {
        return mPublishDate;
    }

    public String getmWebTitle() {
        return mWebTitle;
    }

    public String getmWebURL() {
        return mWebURL;
    }
}
