package com.example.shuaijia.xzcalendarview.calendar;

import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

/**
 * Created by JiaShuai on 2017/9/21.
 */

public class OutSlideAnimation extends Animation {
    private CalendarScrollListener listener;
    private int mDistanceY;
    private ScheduleState state;

    public OutSlideAnimation(CalendarScrollListener listener, int aoutSlideY, ScheduleState state) {
        this.listener = listener;
        mDistanceY = aoutSlideY;
        this.state = state;
        setDuration(200);
        setInterpolator(new DecelerateInterpolator(1.5f));
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        if (state == ScheduleState.OPEN) {
            listener.onCalendarScroll(mDistanceY);
        } else {
            listener.onCalendarScroll(-mDistanceY);
        }
    }
}
