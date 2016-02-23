package com.otb.lnyp.myswipelistheader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bean.HeaderItem;
import za.co.immedia.pinnedheaderlistview.SectionedBaseAdapter;

public class TestSectionedAdapter extends SectionedBaseAdapter {

    List<HeaderItem> headers;

    public TestSectionedAdapter(List<HeaderItem> headers) {
        this.headers = headers;
    }

    @Override
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    @Override
    public int getSectionCount() {
        return headers.size();
    }

    @Override
    public int getCountForSection(int section) {
        return headers.get(section).dataItem.size();
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {

        LinearLayout layout = null;

        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.list_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }

//        ((TextView) layout.findViewById(R.id.textItem)).setText("Section " + section + " Item " + position);
        ((TextView) layout.findViewById(R.id.textItem)).setText(headers.get(section).dataItem.get(position).name);

        return layout;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
//        ((TextView) layout.findViewById(R.id.textItem)).setText("Header for section " + section);
        ((TextView) layout.findViewById(R.id.textItem)).setText(headers.get(section).name);
        return layout;
    }
}
