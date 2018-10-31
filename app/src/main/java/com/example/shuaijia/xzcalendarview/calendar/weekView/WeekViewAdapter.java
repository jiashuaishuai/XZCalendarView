package com.example.shuaijia.xzcalendarview.calendar.weekView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuaijia.xzcalendarview.bean.CollectionDateBean;
import com.example.shuaijia.xzcalendarview.calendar.CalendarUtils;
import com.example.shuaijia.xzcalendarview.calendar.OnClickMonthViewDayListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by JiaShuai on 2017/8/8.
 */

public class WeekViewAdapter extends PagerAdapter {
    private SparseArray<WeekView> weekViews;
    private Context mContext;
    private int currentYear, currentMonth, currentDay;//今天
    private int lastY, lastM, lastD;//结束时间
    private int firstY, firstM, firstD;//开始时间
    private OnClickMonthViewDayListener listener;
    private int mCount = 0;//根据第一天和最后一天计算共有多少周
    private List<CollectionDateBean.Data.YearData.MonthData.DayData> dayAll;

    public WeekViewAdapter(Context context) {
        mContext = context;
        weekViews = new SparseArray<>();
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(getSonView(position));
        return weekViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * @param dayAll               所有回款日集合
     * @param currdatemilliseconds 今天的时间戳
     * @param firstData            第一天
     * @param lastData             最后一天
     */
    public void setCurrentData(List<CollectionDateBean.Data.YearData.MonthData.DayData> dayAll, String currdatemilliseconds, String firstData, String lastData) {
        this.dayAll = dayAll;
        //今天
        Date date = new Date(Long.parseLong(currdatemilliseconds));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.currentYear = calendar.get(Calendar.YEAR);
        this.currentMonth = calendar.get(Calendar.MONTH);
        this.currentDay = calendar.get(Calendar.DATE);


        String[] first = firstData.split("-");
        String[] last = lastData.split("-");

        firstY = Integer.parseInt(first[0]);
        firstM = Integer.parseInt(first[1]);
        firstD = 1;

        lastY = Integer.parseInt(last[0]);
        lastM = Integer.parseInt(last[1]);
        lastD = CalendarUtils.getMonthDays(lastY, lastM - 1);

        mCount = CalendarUtils.getWeeksAgo(firstY, firstM, firstD, lastY, lastM, lastD) + 1;//从开始到结束相隔多少周，+1加上本周
        notifyDataSetChanged();
    }

    public WeekView getSonView(int position) {
        WeekView view = weekViews.get(position);
        if (view == null) {
            view = new WeekView(mContext, currentYear, currentMonth, currentDay);
            List<MyDate> myDates = CalendarUtils.getLastWeekDays(firstY, firstM, firstD, position);//获取当前页的日期集合
            view.setDateList(myDates);
            view.setTagDayList(selectTag(myDates));//获取当前页回款日集合
            view.setPosition(position, mCount);
            view.setOnClickMonthViewDayListener(listener);//回调
            view.setDefaultSelDate();//设置默认选择的日期
            view.setId(position);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.invalidate();
            weekViews.put(position, view);
        }
        return view;
    }

    public void setListener(OnClickMonthViewDayListener listener) {
        this.listener = listener;
    }

    public List<MyDate> selectTag(List<MyDate> dateList) {
        List<MyDate> tagList = new ArrayList<>();
        for (MyDate date : dateList) {
            if (dayAll.contains(date)) {
                tagList.add(date);
            }
        }
        return tagList;
    }

}
