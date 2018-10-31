package com.example.shuaijia.xzcalendarview.calendar;

import com.example.shuaijia.xzcalendarview.calendar.weekView.MyDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by JiaShuai on 2017/4/19.
 */

public class CalendarUtils {
    /**
     * 通过年份和月份 得到当月的日子
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        month++;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 返回当前月份1号位于周几
     *
     * @param year  年份
     * @param month 月份，传入系统获取的，不需要正常的
     * @return 日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static List<MyDate> getCurrentWeekDays(Calendar calendar) {
        List<MyDate> dateList = new ArrayList<>();
        setToFirstDay(calendar);
        for (int i = 0; i < 7; i++) {
            dateList.add(new MyDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)));
            calendar.add(Calendar.DATE, 1);
        }
        return dateList;
    }

    public static List<MyDate> getCurrentWeekDays(int year, int month, int day) {
        return getCurrentWeekDays(year, month, day);
    }

    private static final int FIRST_DAY = Calendar.SUNDAY;//第一个是周日

    public static void setToFirstDay(Calendar calendar) {
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
            calendar.add(Calendar.DATE, -1);
        }
    }


    public static List<MyDate> getLastWeekDays(Calendar calendar, int index) {
        calendar.add(Calendar.DATE, 7 * index);
        return getCurrentWeekDays(calendar);
    }

    public static List<MyDate> getLastWeekDays(int year, int month, int day, int index) {
        return getLastWeekDays(getCalendar(year, month, day), index);
    }

    public static Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar;
    }

    /**
     * 获得两个日期距离几周
     * 不设置时分秒微妙会出现计算误差
     * 如getWeeksAgo(2017,9,30,2017,9,9);
     * 打印一百次看结果
     *
     * @return
     */
    public static int getWeeksAgo(int lastYear, int lastMonth, int lastDay, int year, int month, int day) {
        Calendar lastClickDay = Calendar.getInstance();
        lastClickDay.set(lastYear, lastMonth - 1, lastDay, 0, 0, 0);//0，0，0时分秒
        lastClickDay.set(Calendar.MILLISECOND, 0);//毫秒也要设置否则会出现计算误差
        int week = lastClickDay.get(Calendar.DAY_OF_WEEK) - 1;
        Calendar clickDay = Calendar.getInstance();
        clickDay.set(year, month - 1, day, 0, 0, 0);
        clickDay.set(Calendar.MILLISECOND, 0);
        if (clickDay.getTimeInMillis() > lastClickDay.getTimeInMillis()) {
            return (int) ((clickDay.getTimeInMillis() - lastClickDay.getTimeInMillis() + week * 24 * 3600 * 1000) / (7 * 24 * 3600 * 1000));
        } else {
            return (int) ((clickDay.getTimeInMillis() - lastClickDay.getTimeInMillis() + (week - 6) * 24 * 3600 * 1000) / (7 * 24 * 3600 * 1000));
        }
    }
}
