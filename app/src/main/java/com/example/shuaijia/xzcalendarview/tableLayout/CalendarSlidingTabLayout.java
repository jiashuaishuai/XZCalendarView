package com.example.shuaijia.xzcalendarview.tableLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * Created by JiaShuai on 2017/4/25.
 */

public class CalendarSlidingTabLayout extends SlidingTabLayout {
    private int mDpi;
    private int mHeight;

    public CalendarSlidingTabLayout(Context context) {
        this(context, null);
    }

    public CalendarSlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarSlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        DisplayMetrics mDisplayMetrics = getResources().getDisplayMetrics();
        mDpi = (int) mDisplayMetrics.scaledDensity;
//        mHeight = 48 * mDpi;
//        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,48 * mDpi ));
        setBackgroundColor(Color.parseColor("#ffffff"));
        setTextsize(17);
        setTabPadding(1);
        setDividerPadding(1);
        setTabWidthEqualSize(7);//tabwidth屏幕7分之一
        setDividerColor(Color.parseColor("#ffffff"));
        setIndicatorColor(Color.parseColor("#ffffff"));
        setTextSelectColor(Color.parseColor("#ff8c15"));
        setTextUnselectColor(Color.parseColor("#333333"));
        setIndicatorWidthEqualTitle(true);
        setTabSpaceEqual(false);
        Typeface fromAsset = Typeface.createFromAsset(getContext().getAssets(), "fonts/DINAlternateBold.ttf");
        setTextTypeface(fromAsset);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight  = MeasureSpec.getSize(heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
    }

    public int getmHeight() {
        return mHeight;
    }
}
