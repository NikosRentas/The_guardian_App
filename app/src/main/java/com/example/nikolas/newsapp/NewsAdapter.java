package com.example.nikolas.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> newsArrayList) {
        super(context, 0, newsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view_item, parent, false);
        }
        News currentObj = getItem(position);

        // Title text view
        TextView title = (TextView) convertView.findViewById(R.id.title_text);
        title.setText(currentObj.getsWebTitle());

        // Section name text view
        TextView section = (TextView) convertView.findViewById(R.id.section_view);
        section.setText(currentObj.getsSectionName());

        // Date text view
        TextView date = (TextView) convertView.findViewById(R.id.date_view);
        date.setText(dateForm(currentObj.getsPublishDate()));

        return convertView;
    }

    private String dateForm(String dateTime) {
        String date[] = dateTime.split("T");
        String time[] = date[1].split("Z");

        return date[0] + ", " + time[0];
    }
}
