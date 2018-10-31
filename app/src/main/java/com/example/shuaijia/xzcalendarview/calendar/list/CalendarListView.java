package com.example.shuaijia.xzcalendarview.calendar.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.example.shuaijia.xzcalendarview.adapter.CollectionDayAdapter;

/**
 * Created by JiaShuai on 2017/9/22.
 */

public class CalendarListView extends ListView {
    private CollectionDayAdapter listAdapter;

    public CalendarListView(Context context) {
        this(context, null);
    }

    public CalendarListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDivider(null);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setVerticalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        listAdapter = new CollectionDayAdapter(context);
        setAdapter(listAdapter);
    }

    public CollectionDayAdapter getListAdapter() {
        return listAdapter;
    }

    public boolean isScrollTop() {
        return computeVerticalScrollOffset() == 0;
    }

    public boolean countIsNull() {
        return getChildCount() == 0;
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        super.requestChildFocus(child, focused);
        if (getOnFocusChangeListener() != null) {
            getOnFocusChangeListener().onFocusChange(child, false);
            getOnFocusChangeListener().onFocusChange(focused, true);
        }
    }
}
