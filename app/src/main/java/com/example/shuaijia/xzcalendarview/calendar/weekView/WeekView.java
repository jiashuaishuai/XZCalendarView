package com.example.shuaijia.xzcalendarview.calendar.weekView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.shuaijia.xzcalendarview.calendar.CalendarConfig;
import com.example.shuaijia.xzcalendarview.calendar.OnClickMonthViewDayListener;
import com.example.shuaijia.xzcalendarview.utils.ObjectNullUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiaShuai on 2017/8/7.
 */

public class WeekView extends View {
    private static final int NUM_COLUMNS = 7;
    public static final int NUM_ROWS = 6;//默认六行，需要计算几行
    private int mSelYear, mSelMonth, mSelDay;//当前选中的年月天 month按照java从0月开始，实际+1
    private int mCurrYear, mCurrMonth, mCurrDay;//当年，当月，今天，
    private Paint mPaint;//画笔，
    private int mHintCircleRadius;//提示圆点半径
    private Paint mHintCirclePaint;//回款日画笔
    private int background = CalendarConfig.background;//背景色
    private int mSelectCircleBGColor = CalendarConfig.mSelectCircleBGColor;//选中圆的背景；
    private int mHintCircleColor = CalendarConfig.mHintCircleColor;//提示圆点颜色
    private int mHintCircleWhit = CalendarConfig.mHintCircleWhit;//回款日圆线宽
    private int mDayTextColor = CalendarConfig.mDayTextColor;//文字颜色
    private int mDayTextSize = CalendarConfig.mDayTextSize;//文字大小
    private int mSelectTodayCircleBGColor = CalendarConfig.mSelectTodayCircleBGColor;//选中今天圆的背景；
    private int mSelectTodayTextColor = CalendarConfig.mSelectTodayTextColor;//选中今天的字体颜色
    private int mSelectTextColor = CalendarConfig.mSelectTextColor;//选中的字体颜色；
    private int mTheWeekendTextColor = CalendarConfig.mTheWeekendTextColor;//周六日字体颜色；
    private int mTodayTextColor = CalendarConfig.mTodayTextColor;//今天的字体颜色；
    private DisplayMetrics mDisplayMetrics;
    private int mColumnSize, mRowSize, mSelectCircleSize;//文字宽、高、选中圆圈大小；
    private List<MyDate> dateList;
    private GestureDetector mGestureDetector;
    private int thisHight;
    private int weekNumber, monthDays;//本月一号是星期几，本月有多少天
    private int selectMonthRow;//计算当前月份是5行还是6行
    private OnClickMonthViewDayListener onClickMonthViewDayListener;
    private List<MyDate> mHintList = new ArrayList<>();
    private int position;
    private int count;

    public WeekView(Context context, int mCurrYear, int mCurrMonth, int mCurrDay) {
        this(context, null, mCurrYear, mCurrMonth, mCurrDay);
    }

    public WeekView(Context context, @Nullable AttributeSet attrs, int mCurrYear, int mCurrMonth, int mCurrDay) {
        this(context, attrs, 0, mCurrYear, mCurrMonth, mCurrDay);
    }

    public WeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mCurrYear, int mCurrMonth, int mCurrDay) {
        super(context, attrs, defStyleAttr);
        initAll(mCurrYear, mCurrMonth, mCurrDay);
    }

    private void initAll(int mCurrYear, int mCurrMonth, int mCurrDay) {
        this.mCurrYear = mCurrYear;
        this.mCurrMonth = mCurrMonth;
        this.mCurrDay = mCurrDay;
        initGestureDetector();
        initPaint();
    }

    public void setDateList(List<MyDate> dateList) {
        this.dateList = dateList;
    }

    public void setPosition(int position, int count) {
        this.position = position;
        this.count = count;
    }

    private void initPaint() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        Typeface fromAsset = Typeface.createFromAsset(getContext().getAssets(), "fonts/DINAlternateBold.ttf");
        mPaint.setTypeface(fromAsset);
        mDisplayMetrics = getResources().getDisplayMetrics();//获取屏幕密度，不是分辨率，
        mPaint.setTextSize(mDayTextSize);

        mHintCirclePaint = new Paint();
        mHintCirclePaint.setAntiAlias(true);
        mHintCirclePaint.setColor(mHintCircleColor);
        mHintCirclePaint.setStyle(Paint.Style.STROKE);
        mHintCirclePaint.setStrokeWidth(mHintCircleWhit);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = (mDisplayMetrics.heightPixels / 3) / NUM_ROWS;

        initSize(widthSize, heightSize);
        setMeasuredDimension(widthSize, heightSize);

    }

    private void initSize(int widthSize, int heightSize) {
        mColumnSize = widthSize / NUM_COLUMNS;
        mRowSize = heightSize;
        mSelectCircleSize = (int) (mColumnSize / 4.5);//圆圈的大小;
//        mHintCircleRadius = (mSelectCircleSize / 4);
        while (mSelectCircleSize > mRowSize / 2) {//如果圆圈大小超出view高度/1.3
            mSelectCircleSize = (int) (mSelectCircleSize / 1.3);
        }
        mHintCircleRadius = mSelectCircleSize;

    }

    private void drawWeek(Canvas canvas) {
        for (int i = 0; i < dateList.size(); i++) {
            mPaint.setColor(mDayTextColor);
            if (i == 0 || i == 6)
                mPaint.setColor(mTheWeekendTextColor);
            if (mCurrDay == dateList.get(i).getDay() && mCurrMonth == dateList.get(i).getMonth() && mCurrYear == dateList.get(i).getYear())//今天
                mPaint.setColor(mTodayTextColor);
            if (mSelDay == dateList.get(i).getDay() && mSelMonth == dateList.get(i).getMonth() && mSelYear == dateList.get(i).getYear()) {
                drawSelect(canvas, i);
                mPaint.setColor(mSelectTextColor);
            }
            drawWeekTest(canvas, i);
            drawHint(canvas, i);

        }
    }

    private void drawHint(Canvas canvas, int column) {
        if (!mHintList.contains(dateList.get(column)))
            return;
//        float hinY = mRowSize / 2 + mSelectCircleSize;
//        float hinX = mColumnSize * column + mColumnSize / 2;
        float hinX = mColumnSize * column + mColumnSize / 2;//圆心x
        float hinY = mRowSize / 2;//圆心y
        canvas.drawCircle(hinX, hinY, mHintCircleRadius, mHintCirclePaint);
    }

    private void drawSelect(Canvas canvas, int column) {
        float recX = mColumnSize * column + mColumnSize / 2;//圆心x
        float recY = mRowSize / 2;//圆心y
        mPaint.setColor(mSelectCircleBGColor);
        canvas.drawCircle(recX, recY, mSelectCircleSize, mPaint);
    }

    private void drawWeekTest(Canvas canvas, int index) {
        String dayString = dateList.get(index).getDay() + "";
        int startX = (int) (mColumnSize * index + (mColumnSize / 2 - mPaint.measureText(dayString) / 2));//文字x起始坐标  （单个长度*横坐标+（单个长度-文字长度）/2）=x起始坐标
        int startY = (int) (mRowSize / 2 - (mPaint.ascent() + mPaint.descent()) / 2);//文字y起始坐标， 根据绘制文字基线计算，
        canvas.drawText(dayString, startX, startY, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(background);
        drawWeek(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private void initGestureDetector() {
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                doOnclickAction((int) e.getX(), (int) e.getY());
                return true;
            }
        });
    }

    private void doOnclickAction(int x, int y) {
        if (y > getHeight())
            return;
        int column = x / mColumnSize;
        column = Math.min(column, 6);//防止越界
        if (dateList != null && dateList.get(column) != null) {
            if (position == count - 1 && dateList.get(column).getDay() < 7 || position == 0 && dateList.get(column).getDay() > 7) {
                return;
            }
            mSelDay = dateList.get(column).getDay();
            mSelMonth = dateList.get(column).getMonth();
            mSelYear = dateList.get(column).getYear();
            if (onClickMonthViewDayListener != null) {
                onClickMonthViewDayListener.clickCallBack(getSelYear(), getSelMonth(), getSelDay(), hintListContainCheckDay());
            } else {
                throw new NullPointerException("WeekView onClickMonthViewDayListener is null");
            }
        } else {
//            throw  new NullPointerException("WeekView dateList == null && dateList.get(column) == null ");
            return;
        }
//        Logger.i("CalendarView", "------------" + "Year:" + mSelYear + "Month:" + (mSelMonth + 1) + "Day:" + mSelDay);
        invalidate();
    }

    public void setOnClickMonthViewDayListener(OnClickMonthViewDayListener listener) {
        this.onClickMonthViewDayListener = listener;
    }

    public int getSelYear() {
        return mSelYear;
    }

    public int getSelMonth() {
        return mSelMonth + 1;
    }

    public int getSelDay() {
        return mSelDay;
    }

    public void setSelData(int y, int m, int d) {//选择莫一天
        mSelYear = y;
        mSelMonth = m - 1;
        mSelDay = d;
        invalidate();
    }

    public void setDefaultSelDate() {//默认选择日期，首选当页第一个回款日，，其次选择当页第一个，最后如果是第一页，则选择当前最大年月的最小值
        if (ObjectNullUtils.listIsNull(mHintList)) {
            if (position == 0) {
                if (mSelYear == 0)
                    mSelYear = dateList.get(6).getYear();
                if (mSelMonth == 0)
                    mSelMonth = dateList.get(6).getMonth();
                if (mSelDay == 0)
                    mSelDay = getSmallDay();
            } else {
                if (mSelYear == 0)
                    mSelYear = dateList.get(0).getYear();
                if (mSelMonth == 0)
                    mSelMonth = dateList.get(0).getMonth();
                if (mSelDay == 0)
                    mSelDay = dateList.get(0).getDay();
            }
        } else {
            mSelYear = mHintList.get(0).getYear();
            mSelMonth = mHintList.get(0).getMonth();
            mSelDay = mHintList.get(0).getDay();
        }
    }

    public void setTagDayList(List<MyDate> mHintList) {//回款日集合
        this.mHintList = mHintList;
    }

    public boolean hintListContainCheckDay() {//是否是回款日
        MyDate myDate = new MyDate(getSelYear(), getSelMonth() - 1, getSelDay());
        return mHintList.contains(myDate);
    }

    public int getSmallDay() {
        int sm = dateList.get(0).getDay();
        for (int i = 1, l = dateList.size(); i < l; i++) {
            MyDate myDate = dateList.get(i);
            if (myDate.getDay() < sm)
                sm = myDate.getDay();
        }
        return sm;
    }
}
