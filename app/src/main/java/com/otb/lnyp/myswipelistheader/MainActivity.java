package com.otb.lnyp.myswipelistheader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import bean.DataItem;
import bean.FastJsonUtil;
import bean.HeaderItem;
import swipeactionadapter.SwipeActionAdapter;
import swipeactionadapter.SwipeDirection;
import za.co.immedia.pinnedheaderlistview.PinnedHeaderListView;
import za.co.immedia.pinnedheaderlistview.SectionedBaseAdapter;

public class MainActivity extends Activity implements SwipeActionAdapter.SwipeActionListener {

    protected SwipeActionAdapter mAdapter;

    List<HeaderItem> headers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initView();
    }


    private void initView() {

        PinnedHeaderListView listView = (PinnedHeaderListView) findViewById(R.id.pinnedListView);

        SectionedBaseAdapter sectionedAdapter = new TestSectionedAdapter(headers);

        mAdapter = new SwipeActionAdapter(sectionedAdapter, sectionedAdapter);

        mAdapter.setSwipeActionListener(this)
                .setDimBackgrounds(true)
                .setListView(listView);

        listView.setAdapter(mAdapter);

        mAdapter.addBackground(SwipeDirection.DIRECTION_FAR_LEFT, R.layout.row_bg_left_far)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.row_bg_left)
                .addBackground(SwipeDirection.DIRECTION_FAR_RIGHT, R.layout.row_bg_right_far)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT, R.layout.row_bg_right);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int rawPosition, long id) {
                PinnedHeaderListView.PinnedSectionedHeaderAdapter adapter;

                if (adapterView.getAdapter().getClass().equals(HeaderViewListAdapter.class)) {
                    HeaderViewListAdapter wrapperAdapter = (HeaderViewListAdapter) adapterView.getAdapter();
                    adapter = (PinnedHeaderListView.PinnedSectionedHeaderAdapter) wrapperAdapter.getWrappedAdapter();
                } else {
                    adapter = (PinnedHeaderListView.PinnedSectionedHeaderAdapter) adapterView.getAdapter();
                }
                int section = adapter.getSectionForPosition(rawPosition);

                int position = adapter.getPositionInSectionForPosition(rawPosition);

//                LogUtils.e("section : " + section + "  position : " + position);

                if (position != -1) {
                    String sectionName = headers.get(section).name;
                    DataItem dataItem = headers.get(section).dataItem.get(position);
                    LogUtils.e(sectionName + " --> " + dataItem.name);
                }
            }
        });
    }

    @Override
    public boolean hasActions(int position, SwipeDirection direction) {

//        if (direction.isLeft()) return true;
//        if (direction.isRight()) return true;

//        LogUtils.e("pos :" + position);

//        if (position == 1) {
//            return false;
//        }

        return true;
    }

    @Override
    public boolean shouldDismiss(int position, SwipeDirection direction) {
        boolean isDismiss = false;
        switch (direction) {

            case DIRECTION_FAR_RIGHT:
            case DIRECTION_NORMAL_RIGHT:
                isDismiss = true;
                break;
        }
        return isDismiss;
    }

    @Override
    public void onSwipe(int[] positionList, SwipeDirection[] directionList) {

        for (int i = 0; i < positionList.length; i++) {

            SwipeDirection direction = directionList[i];
            int position = positionList[i];

            String dir = "";

            switch (direction) {
                case DIRECTION_FAR_LEFT:
                    dir = "删除";
                    break;
                case DIRECTION_NORMAL_LEFT:
                    dir = "编辑";
                    break;
                case DIRECTION_FAR_RIGHT:
                    dir = "标为完成";
                    break;
                case DIRECTION_NORMAL_RIGHT:
                    dir = "标为完成";
                    break;
            }

            Toast.makeText(this, dir, Toast.LENGTH_SHORT).show();

            if (mAdapter != null) {
                try {
                    int section = mAdapter.getSectionForPosition(position);
                    int itemPos = mAdapter.getPositionInSectionForPosition(position);

                    if (position != -1) {
//                    String sectionName = headers.get(section).name;
//                    DataItem dataItem = headers.get(section).dataItem.get(itemPos);

                        headers.get(section).dataItem.remove(itemPos);
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initData() {

        for (int i = 0; i < 5; i++) {

            HeaderItem headerItem = new HeaderItem();
            List<DataItem> datas = new ArrayList<>();

            for (int j = 0; j < 5; j++) {
                DataItem dataItem = new DataItem();
                dataItem.name = " 列表数据 " + j;
                datas.add(dataItem);
            }

            headerItem.name = "标题 " + i;
            headerItem.dataItem = datas;

            headers.add(headerItem);
        }

        LogUtils.e(FastJsonUtil.t2Json2(headers));

        for (int i = 0; i < headers.size(); i++) {

            HeaderItem headerItem = headers.get(i);

            List<DataItem> dataItems = headerItem.dataItem;

            for (int j = 0; j < dataItems.size(); j++) {

//                LogUtils.e(headerItem.name + "   " + dataItems.get(j).name);
            }
        }
    }
}
