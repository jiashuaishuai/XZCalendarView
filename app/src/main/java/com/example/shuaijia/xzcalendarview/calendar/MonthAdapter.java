package com.example.shuaijia.xzcalendarview.calendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuaijia.xzcalendarview.bean.CollectionDateBean;
import com.example.shuaijia.xzcalendarview.utils.ObjectNullUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by JiaShuai on 2017/4/19.
 */

public class MonthAdapter extends PagerAdapter {
    private SparseArray<MonthView> mViews;
    private Context mContext;
    private int currYear, currMonth, currDay;
    private OnClickMonthViewDayListener clickMonthViewDayListener;
    private List<CollectionDateBean.Data.YearData.MonthData> monthList;


    public MonthAdapter(Context context) {
        mContext = context;
        mViews = new SparseArray<>();
    }

    public void setYearDataList(List<CollectionDateBean.Data.YearData.MonthData> monthList) {
        this.monthList = monthList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ObjectNullUtils.listIsNull(monthList) ? 0 : monthList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mViews.get(position) == null) {
            int date[] = getYearAndMonth(position);
            MonthView calendarView = new MonthView(mContext, date[0], date[1]);
            calendarView.initCurrMonth(currYear, currMonth, currDay);
            calendarView.setmHintList(monthList.get(position).getDayList());
            calendarView.setId(position);
            calendarView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            calendarView.invalidate();
            calendarView.setOnClickMonthViewDayListener(clickMonthViewDayListener);
            mViews.put(position, calendarView);
        }
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public int[] getYearAndMonth(int position) {
        int date[] = new int[2];
        String s = monthList.get(position).getMonth();
        date[0] = Integer.parseInt(s.split("-")[0]);
        date[1] = Integer.parseInt(s.split("-")[1]) - 1;
        return date;
    }

    public MonthView getSonView(int position) {
        return mViews.get(position);
    }

    public void setClickMonthViewDayListener(OnClickMonthViewDayListener clickMonthViewDayListener) {
        this.clickMonthViewDayListener = clickMonthViewDayListener;
    }

    //设置当前日期
    public void setCurrDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        currYear = calendar.get(Calendar.YEAR);
        currMonth = calendar.get(Calendar.MONTH) + 1;
        currDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void selectToday(int index) {
        MonthView monthView = mViews.get(index);
        if (monthView != null) {
            monthView.setSelectDay(currDay);
            monthView.clickCurrentListener();
        }
    }

}
