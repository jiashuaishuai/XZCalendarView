package com.example.shuaijia.xzcalendarview.calendar.weekView;


import com.example.shuaijia.xzcalendarview.bean.CollectionDateBean;

/**
 * Created by JiaShuai on 2017/8/7.
 */

public class MyDate {
    private int day;
    private int month;
    private int year;

    public MyDate() {
    }

    public MyDate(int year, int month, int day) {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CollectionDateBean.Data.YearData.MonthData.DayData) {
            CollectionDateBean.Data.YearData.MonthData.DayData data = (CollectionDateBean.Data.YearData.MonthData.DayData) o;//为了方便就这么写了，
            String[] dates = data.getDueDate().split("-");
            return Integer.parseInt(dates[0]) == year && (Integer.parseInt(dates[1]) - 1) == month && Integer.parseInt(dates[2]) == day;//这里月需要-1
        }
        if (o instanceof MyDate) {
            MyDate myDate = (MyDate) o;
            return year == myDate.getYear() && month == myDate.getMonth() && day == myDate.getDay();
        }
        return super.equals(o);
    }
}
