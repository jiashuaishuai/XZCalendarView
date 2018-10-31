package com.example.shuaijia.xzcalendarview.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.shuaijia.xzcalendarview.R;
import com.example.shuaijia.xzcalendarview.utils.UIUtils;


/**
 * Created by Jimmy on 2016/10/6 0006.
 */
public class WeekBarView extends View {

    private int mWeekTextColor;
    private int theWeekendTextColor;
    private int linColor;
    private int linHeight;
    private int backgroundColor;
    private int mWeekSize;
    private Paint mPaint;
    private Paint mLinPaint;
    private Paint mLinPaint1;
    private DisplayMetrics mDisplayMetrics;
    private String[] mWeekString;
    private int mWeekHeight;

    public WeekBarView(Context context) {
        this(context, null);
    }

    public WeekBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WeekBarView);
        mWeekTextColor = array.getColor(R.styleable.WeekBarView_week_text_color, Color.parseColor("#999999"));
        theWeekendTextColor = array.getColor(R.styleable.WeekBarView_the_weekend_color, Color.parseColor("#dcdcdc"));
        mWeekSize = array.getInteger(R.styleable.WeekBarView_week_text_size, 14);
        linColor = array.getInteger(R.styleable.WeekBarView_linColor, Color.parseColor("#eeeeee"));
        linHeight = array.getInteger(R.styleable.WeekBarView_linHeight, 1);
        backgroundColor = array.getInteger(R.styleable.WeekBarView_backgroundColor, Color.parseColor("#ffffff"));
        array.recycle();
        mWeekString = context.getResources().getStringArray(R.array.calendar_week);
    }

    private void initPaint() {
        mDisplayMetrics = getResources().getDisplayMetrics();
        mWeekHeight = (int) (mDisplayMetrics.scaledDensity * 48);
        mLinPaint = new Paint();
        mLinPaint.setColor(linColor);
        mLinPaint.setStrokeWidth((float) linHeight);
        mLinPaint1 = new Paint();
        mLinPaint1.setColor(Color.parseColor("#f4f5fa"));
        mLinPaint1.setStrokeWidth((float) 8 * mDisplayMetrics.scaledDensity);

        //实例化文字画笔
        mPaint = new Paint();
        mPaint.setColor(mWeekTextColor);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mWeekSize * mDisplayMetrics.scaledDensity);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = mWeekHeight;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        canvas.drawColor(backgroundColor);
        //绘制线条
        canvas.drawLine(0, UIUtils.dp2px(8) / 2, width, UIUtils.dp2px(8) / 2, mLinPaint1);//分割快
        canvas.drawLine(0, 1, width, 1, mLinPaint);//第一条线
        canvas.drawLine(0, UIUtils.dp2px(8), width, UIUtils.dp2px(8), mLinPaint);//第二条线
        canvas.drawLine(0, height - 1, width, height - 1, mLinPaint);//最底部线

        //绘制文字
        int columnWidth = width / 7;
        for (int i = 0; i < mWeekString.length; i++) {
            String text = mWeekString[i];
            int fontWidth = (int) mPaint.measureText(text);
            int startX = columnWidth * i + (columnWidth - fontWidth) / 2;
            int startY = (int) (height / 2 - (mPaint.ascent() + mPaint.descent()) / 2) + UIUtils.dp2px(4);
            if (i == 0 || i == mWeekString.length - 1) {
                mPaint.setColor(theWeekendTextColor);
            } else {
                mPaint.setColor(mWeekTextColor);
            }
            canvas.drawText(text, startX, startY, mPaint);
        }
    }

    public int getmWeekHeight() {
        return mWeekHeight;
    }
}
