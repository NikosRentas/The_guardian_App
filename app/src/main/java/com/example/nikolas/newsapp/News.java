package com.example.nikolas.newsapp;

public class News {
    private static String sSectionName;
    private static String sPublishDate;
    private static String sWebTitle;
    private static String sWebURL;

    public News(String sectionName, String publishDate, String webTitle, String webURL) {
        sSectionName = sectionName;
        sPublishDate = publishDate;
        sWebTitle = webTitle;
        sWebURL = webURL;
    }

    public String getsSectionName() {
        return sSectionName;
    }

    public String getsPublishDate() {
        return sPublishDate;
    }

    public String getsWebTitle() {
        return sWebTitle;
    }

    public String getsWebURL() {
        return sWebURL;
    }
}
