package com.example.shuaijia.xzcalendarview.calendar;

import android.graphics.Color;

import com.example.shuaijia.xzcalendarview.utils.UIUtils;

/**
 * Created by JiaShuai on 2018/6/25.
 * 回款日历配置信息
 */

public class CalendarConfig {

    public static int background = Color.parseColor("#ffffff");//背景色
    public static int mSelectCircleBGColor = Color.parseColor("#FFC832");//选中圆的背景；
    public static int mHintCircleColor = Color.parseColor("#ff8c15");//提示圆点颜色
    //    public static int mHintCircleRadius;//提示圆点半径=选中圆半径相同=测量
    public static int mHintCircleWhit = UIUtils.dp2px(1);//回款日圆线宽
    public static int mSelectTodayCircleBGColor = mSelectCircleBGColor;//选中今天圆的背景；
    public static int mDayTextColor = Color.parseColor("#333333");//文字颜色
    public static int mDayTextSize = UIUtils.sp2px(17);//文字大小
    public static int mSelectTodayTextColor = Color.parseColor("#ffffff");//选中今天的字体颜色
    public static int mSelectTextColor = Color.parseColor("#ffffff");//选中的字体颜色；
    public static int mTheWeekendTextColor = Color.parseColor("#999999");//周六日字体颜色；
    public static int mTodayTextColor = Color.parseColor("#2F64EB");//今天的字体颜色；
}
