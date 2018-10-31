package com.example.shuaijia.xzcalendarview.calendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.example.shuaijia.xzcalendarview.bean.CollectionDateBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by JiaShuai on 2017/4/19.
 */

public class MonthViewpager extends ViewPager {
    private MonthAdapter adapter;
    private MonthView calendarView, nextView, currView;
    private int selectPagerHeight, nextPagerHeigth;
    private int defaultHeight;
    private OnClickMonthViewDayListener listener;
    private int weekSize;
    //    public MonthPagerChangeListener monthPagerChangeListener;
    public int todayPagerIndex;//今天

    private ScheduleState state = ScheduleState.OPEN;

    public MonthViewpager(Context context) {
        this(context, null);
    }

    public MonthViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new MonthAdapter(context);
        setAdapter(adapter);
//        setOffscreenPageLimit(2);
        defaultHeight = getResources().getDisplayMetrics().heightPixels / 3;//跟View一样
        setMonthPagerChangeListener();
    }

    public void setScheduleState(ScheduleState state) {
        this.state = state;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(defaultHeight, MeasureSpec.getMode(heightMeasureSpec));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        nextView = adapter.getSonView(position + 1);
        calendarView = adapter.getSonView(position);
        if (calendarView != null && nextView != null) {
            selectPagerHeight = calendarView.getCalendarViewHight() == 0 ? defaultHeight : calendarView.getCalendarViewHight();
            nextPagerHeigth = nextView.getCalendarViewHight() == 0 ? defaultHeight : nextView.getCalendarViewHight();
            setHeight((int) (selectPagerHeight * (1 - offset) + nextPagerHeigth * offset));
        }
    }

    private void setHeight(int height) {
        if (state == ScheduleState.CLOSE)//关闭状态不改变高度
            return;
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = height;
        setLayoutParams(params);
    }

    public void setClickMonthViewDayListener(OnClickMonthViewDayListener listener) {
        this.listener = listener;
        adapter.setClickMonthViewDayListener(listener);
    }

    public void setAdapterDate(List<CollectionDateBean.Data.YearData> yearDataList, String currdatemilliseconds) {
        List<CollectionDateBean.Data.YearData.MonthData> monthList = new ArrayList<>();
        //格式化当前时间
        Date date = new Date(Long.parseLong(currdatemilliseconds));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
        String currDate = sdf.format(date);
        int pager = 0;
        for (CollectionDateBean.Data.YearData yearData : yearDataList) {
            for (CollectionDateBean.Data.YearData.MonthData monthData : yearData.getMonthList()) {
                monthList.add(monthData);
                if (monthData.getMonth().equals(currDate)) {
                    //记录当前时间为pager第几页
                    todayPagerIndex = pager;
                }
                pager++;
            }
        }

        adapter.setCurrDate(date);
        adapter.setYearDataList(monthList);
    }

    //选中当前月
    public void selectPagerCurrMonth() {
        setCurrentItem(todayPagerIndex, false);
    }

    //回到今天
    public void goToday() {
        selectPagerCurrMonth();
        adapter.selectToday(todayPagerIndex);
    }

    private boolean isFarst = true;

    public void setMonthPagerChangeListener() {
//        this.monthPagerChangeListener = monthPagerChangeListener;
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                currView = adapter.getSonView(position);
                if (listener != null && currView != null) {
//                    weekSize = currView.getSelectMonthRow();
                    listener.clickCallBack(currView.getSelYear(), currView.getSelMonth(), currView.getSelDay(), currView.hintListContainCheckDay());
                    currView.setOnClickMonthViewDayListener(listener);
                }
                if (isFarst & position == todayPagerIndex && currView != null) {//跳转到本业计算高度,上边呢个是为了实现动画
                    currView.post(new Runnable() {
                        @Override
                        public void run() {
                            setCurrentPagerHeight();
                            isFarst = false;
                        }
                    });

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void refreshCurrentView(OnClickMonthViewDayListener listener, int index, int d) {
        currView = adapter.getSonView(index);
        currView.setSelectDay(d);
        currView.setOnClickMonthViewDayListener(listener);
        setCurrentPagerHeight();

    }

    public void setCurrentPagerHeight() {
        if (adapter != null) {
            int height = currView.getCalendarViewHight() == 0 ? defaultHeight : currView.getCalendarViewHight();
            setHeight(height);
        }
    }

    public MonthView getCurrView() {
        return currView;
    }
}
