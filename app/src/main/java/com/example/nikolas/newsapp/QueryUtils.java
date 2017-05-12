package com.example.nikolas.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getName();
    private static final String RESPONSE = "response";
    private static final String RESULTS = "results";
    private static final String SECTION_NAME = "sectionName";
    private static final String PUBLISH_DATE = "webPublicationDate";
    private static final String WEB_TITLE = "webTitle";
    private static final String WEB_URL = "webUrl";

    public static List<News> fetchData(String requestURL) {
        URL url = create(requestURL);

        String valueOfJSON = null;
        try {
            valueOfJSON = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "HTTP request problem", e);
        }

        return extractFromJSON(valueOfJSON);
    }

    private static List<News> extractFromJSON(String valueOfJSON) {
        if (TextUtils.isEmpty(valueOfJSON)) return null;

        List<News> listNews = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(valueOfJSON);
            JSONObject response = root.getJSONObject(RESPONSE);
            JSONArray results = response.getJSONArray(RESULTS);

            for (int i = 0; i < results.length(); i++) {
                JSONObject currentObj = results.getJSONObject(i);

                // Get section name
                String sectionName = currentObj.getString(SECTION_NAME);

                // Get publish date
                String publishDate = currentObj.getString(PUBLISH_DATE);

                // Get web title
                String webTitle = currentObj.getString(WEB_TITLE);

                // Get web url
                String webURL = currentObj.getString(WEB_URL);

                listNews.add(new News(sectionName, publishDate, webTitle, webURL));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSON extract problem", e);
        }
        return listNews;
    }

    private static String makeHTTPRequest(URL url) throws IOException {
        String dataJSON = "";

        if (url == null) return dataJSON;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                dataJSON = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return dataJSON;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL create(String requestURL) {
        URL url = null;
        try {
            url = new URL(requestURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL", e);
        }
        return url;
    }
}
