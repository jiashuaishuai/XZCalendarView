package com.example.shuaijia.xzcalendarview.calendar.list;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.shuaijia.xzcalendarview.R;
import com.example.shuaijia.xzcalendarview.adapter.CollectionDayAdapter;
import com.example.shuaijia.xzcalendarview.bean.CollectionBean;
import com.example.shuaijia.xzcalendarview.utils.ObjectNullUtils;

/**
 * Created by JiaShuai on 2017/9/22.
 */

public class CalendarListViewPager extends ViewPager {
    private ListViewPagerAdapter adapter;
    private View footView1, footView2;

    public CalendarListViewPager(Context context) {
        this(context, null);
    }

    public CalendarListViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new ListViewPagerAdapter(context);
        setAdapter(adapter);
        footView1 = LayoutInflater.from(context).inflate(R.layout.collection_listview_end_view, null);
        footView2 = LayoutInflater.from(context).inflate(R.layout.collection_listview_end_view, null);
        adapter.getListView(0).addFooterView(footView1);
        adapter.getListView(1).addFooterView(footView2);
    }

    public CollectionDayAdapter getListAdapter(int position) {
        return adapter.getAdapter(position);
    }

    public CalendarListView getCurrentList() {
        return adapter.getListView(getCurrentItem());
    }

    public LinearLayout getHintLayout(int position) {
        return adapter.getHintLayout(position);
    }

    public void setListData(CollectionBean bean) {
        getListAdapter(0).upDateList(null);
        getListAdapter(1).upDateList(null);
        if (bean != null && !ObjectNullUtils.listIsNull(bean.getData().getPhaseList())) {
            getHintLayout(0).setVisibility(GONE);
            getListAdapter(0).upDateList(bean.getData().getPhaseList().get(0).getPhaseData());
            footView1.setVisibility(VISIBLE);
        } else {
            getHintLayout(0).setVisibility(VISIBLE);
            footView1.setVisibility(GONE);
        }
        if (bean != null && !ObjectNullUtils.listIsNull(bean.getData().getRepaidList())) {
            getHintLayout(1).setVisibility(GONE);
            getListAdapter(1).upDateList(bean.getData().getRepaidList().get(0).getPhaseData());
            footView2.setVisibility(VISIBLE);
        } else {
            getHintLayout(1).setVisibility(VISIBLE);
            footView2.setVisibility(GONE);
        }
    }

}
