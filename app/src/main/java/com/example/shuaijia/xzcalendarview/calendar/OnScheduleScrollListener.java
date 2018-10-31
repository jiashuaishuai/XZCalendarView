package com.example.shuaijia.xzcalendarview.calendar;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by JiaShuai on 2017/9/21.
 */

public class OnScheduleScrollListener extends GestureDetector.SimpleOnGestureListener {
    private CalendarScrollListener listener;

    public OnScheduleScrollListener(CalendarScrollListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (distanceY > 200)
            distanceY = distanceY / 10;
        if (distanceY < -200)
            distanceY = distanceY / 10;
        listener.onCalendarScroll(distanceY);
        return true;
    }
}
