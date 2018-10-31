package com.example.shuaijia.xzcalendarview.calendar.weekView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import com.example.shuaijia.xzcalendarview.bean.CollectionDateBean;
import com.example.shuaijia.xzcalendarview.calendar.OnClickMonthViewDayListener;

import java.util.List;

/**
 * Created by JiaShuai on 2017/8/8.
 */

public class WeekViewPager extends ViewPager {
    private DisplayMetrics mDisplayMetrics;
    private WeekViewAdapter weekViewAdapter;
    private OnClickMonthViewDayListener listener;
    private int weekViewHeight;

    public WeekViewPager(Context context) {
        this(context, null);
    }

    public WeekViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        weekViewAdapter = new WeekViewAdapter(context);
        setAdapter(weekViewAdapter);
        mDisplayMetrics = getResources().getDisplayMetrics();//获取屏幕密度，不是分辨率，
        weekViewHeight = (mDisplayMetrics.heightPixels / 3) / 6;
        setMonthPagerChangeListener();
    }


    public void setCurrentDate(List<CollectionDateBean.Data.YearData.MonthData.DayData> dayAll, String currdatemilliseconds, String firstData, String lastData) {
        weekViewAdapter.setCurrentData(dayAll, currdatemilliseconds, firstData, lastData);
    }

    public int getWeekViewHeight() {
        return weekViewHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//计算高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(weekViewHeight, MeasureSpec.getMode(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setListener(OnClickMonthViewDayListener listener) {
        this.listener = listener;
        weekViewAdapter.setListener(listener);

    }

    public void setMonthPagerChangeListener() {//翻页监听
//        this.monthPagerChangeListener = monthPagerChangeListener;
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                WeekView weekView = weekViewAdapter.getSonView(position);
                if (listener != null && weekView != null) {
                    listener.clickCallBack(weekView.getSelYear(), weekView.getSelMonth(), weekView.getSelDay(), weekView.hintListContainCheckDay());
                    weekView.setOnClickMonthViewDayListener(listener);//因为各种原因需要加一下
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void refreshCurrent(OnClickMonthViewDayListener listener, int index, int clickY, int clickM, int clickD) {//刷新当前页
        WeekView weekView = weekViewAdapter.getSonView(index);
        weekView.setSelData(clickY, clickM, clickD);
        //这里需要重新设置一下监听，否则点击监听失效 具体原因是因为在该view还没有生成的时候，CalendarView.monthListener中将监听置空，切换页面生成view，view.listener=null
        weekView.setOnClickMonthViewDayListener(listener);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_MOVE://不能滑动
//                return true;
//            case MotionEvent.ACTION_DOWN://子view不能点击
//            case MotionEvent.ACTION_UP:
//                return true;
//
//        }
//        return super.onInterceptTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_MOVE://不能滑动
//                return true;
//            case MotionEvent.ACTION_DOWN://子view不能点击
//            case MotionEvent.ACTION_UP:
//                return true;
//
//        }
//        return super.onTouchEvent(ev);
//    }
}
