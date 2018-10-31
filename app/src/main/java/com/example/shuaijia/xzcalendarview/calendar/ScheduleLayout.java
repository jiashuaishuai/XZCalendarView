package com.example.shuaijia.xzcalendarview.calendar;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.shuaijia.xzcalendarview.R;
import com.example.shuaijia.xzcalendarview.bean.CollectionDateBean;
import com.example.shuaijia.xzcalendarview.calendar.list.CalendarListViewPager;
import com.example.shuaijia.xzcalendarview.calendar.weekView.WeekViewPager;
import com.example.shuaijia.xzcalendarview.tableLayout.CalendarSlidingTabLayout;
import com.example.shuaijia.xzcalendarview.utils.ObjectNullUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiaShuai on 2017/4/20.
 * 需要注意一点，monthViewPager高度是变化的，
 */

public class ScheduleLayout extends FrameLayout implements CalendarScrollListener {
    private CalendarSlidingTabLayout slidingTabLayout;
    private int slidingTabLayoutHeight;
    private WeekViewPager weekViewPager;
    private int weekViewHeight;
    private WeekBarView week_bar_view;
    private int weekBarHeight;
    private MonthViewpager monthViewPager;
    private CalendarListViewPager listViewPager;
    private int firstY, firstMonth, firstDay;//默认为第一天
    private int clickY, clickM, clickD;//当前点击的日期
    private List<String> monthList;
    private OnClickMonthViewDayListener mListener;

    private Point mDownPoint = new Point();
    private boolean mIsScrolling = false;//是否滚动
    GestureDetector mGestureDetector;

    private ScheduleState state = ScheduleState.OPEN;
    private int autoSlideY;


    public ScheduleLayout(Context context) {
        this(context, null);
    }

    public ScheduleLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        autoSlideY = (int) (15 * getResources().getDisplayMetrics().scaledDensity);
        mGestureDetector = new GestureDetector(context, new OnScheduleScrollListener(this));
    }

    private LinearLayout calendar_total_lly, list_all_lly, today_all_lly;
    private int todayLyHeight;
    private RelativeLayout month_view_lly;


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        slidingTabLayout = (CalendarSlidingTabLayout) findViewById(R.id.calendar_tab_layout);
        list_all_lly = (LinearLayout) findViewById(R.id.list_all_lly);
        month_view_lly = (RelativeLayout) findViewById(R.id.month_view_lly);
        weekViewPager = (WeekViewPager) findViewById(R.id.week_view_pager);
        monthViewPager = (MonthViewpager) findViewById(R.id.month_view_pager);
        calendar_total_lly = (LinearLayout) findViewById(R.id.calendar_total_lly);
        week_bar_view = (WeekBarView) findViewById(R.id.week_bar_view);
        listViewPager = (CalendarListViewPager) findViewById(R.id.collection_view_pager);
        today_all_lly = (LinearLayout) findViewById(R.id.today_all_lly);
        weekBarHeight = week_bar_view.getmWeekHeight();
        setListAllLayoutHeight();
    }


    private void setListAllLayoutHeight() {
        calendar_total_lly.post(new Runnable() {
            @Override
            public void run() {
                slidingTabLayoutHeight = slidingTabLayout.getmHeight();
                todayLyHeight = today_all_lly.getHeight();
                ViewGroup.LayoutParams params = calendar_total_lly.getLayoutParams();
                int paH = calendar_total_lly.getHeight();
                params.height = paH + slidingTabLayoutHeight + weekViewPager.getWeekViewHeight() * 4 + todayLyHeight;
                calendar_total_lly.setLayoutParams(params);

            }
        });
    }


    public void setClickMonthViewDayListener(OnClickMonthViewDayListener listener) {//回调
        mListener = listener;
        monthViewPager.setClickMonthViewDayListener(monthListener);
        weekViewPager.setListener(weekListener);
    }

    OnClickMonthViewDayListener monthListener = new OnClickMonthViewDayListener() {//月历回调
        @Override
        public void clickCallBack(int selYear, int selMonth, int selDay, boolean isCashBackDay) {
            weekViewPager.setListener(null);
            setClickDate(selYear, selMonth, selDay);
            mListener.clickCallBack(selYear, selMonth, selDay, isCashBackDay);
            showWeekView();
            weekViewPager.setListener(weekListener);
        }
    };
    OnClickMonthViewDayListener weekListener = new OnClickMonthViewDayListener() {//周历回调
        @Override
        public void clickCallBack(int selYear, int selMonth, int selDay, boolean isCashBackDay) {
            monthViewPager.setClickMonthViewDayListener(null);
            setClickDate(selYear, selMonth, selDay);
            mListener.clickCallBack(selYear, selMonth, selDay, isCashBackDay);
            showMonthView();
            monthViewPager.setClickMonthViewDayListener(monthListener);
        }
    };


    public void setAdapterDate(List<CollectionDateBean.Data.YearData> yearDataList, String currdatemilliseconds) {//配置数据源
        monthViewPager.setAdapterDate(yearDataList, currdatemilliseconds);
        monthList = new ArrayList<>();
        List<CollectionDateBean.Data.YearData.MonthData.DayData> dayAll = new ArrayList<>();
        for (CollectionDateBean.Data.YearData yearData : yearDataList) {
            for (CollectionDateBean.Data.YearData.MonthData monthData : yearData.getMonthList()) {
                monthList.add(monthData.getMonth());
                if (!ObjectNullUtils.listIsNull(monthData.getDayList()))
                    dayAll.addAll(monthData.getDayList());
            }
        }
        weekViewPager.setCurrentDate(dayAll, currdatemilliseconds, monthList.get(0), monthList.get(monthList.size() - 1));
        String[] defaultFirstDate = monthList.get(0).split("-");
        firstY = Integer.parseInt(defaultFirstDate[0]);
        firstMonth = Integer.parseInt(defaultFirstDate[1]);
        firstDay = 1;
        setMonthTablArray(monthList);
        goToday();

    }

    public void setMonthTablArray(List<String> list) {
        if (ObjectNullUtils.listIsNull(list))
            return;
        String[] tablArray = new String[list.size()];
        for (int i = 0, l = list.size(); i < l; i++) {
            String s = list.get(i);
            tablArray[i] = Integer.parseInt(s.split("-")[1]) + "月";
        }
        if (slidingTabLayout != null && monthViewPager != null && tablArray.length > 0) {
            slidingTabLayout.setViewPager(monthViewPager, tablArray);
        }
    }

    public void goToday() {//回到今天
        monthViewPager.goToday();
    }

    public void jumpToMonth(String month) {//回到某个月
//        state = ScheduleState.OPEN;
        int index = monthList.indexOf(month);
        monthViewPager.setCurrentItem(index, false);
        monthViewPager.post(new Runnable() {//这里需要在viewpager初始化后执行
            @Override
            public void run() {
                if (state == ScheduleState.CLOSE) {
                    resetCalendarPosition();
                }
            }
        });

    }

    private void setLastDate(int year, int month, int day) {
        firstDay = day;
        firstMonth = month;
        firstY = year;
    }

    private void setClickDate(int y, int m, int d) {
        clickY = y;
        clickM = m;
        clickD = d;
    }

    public void showMonthView() {//触发显示month时操作
        int index = monthList.indexOf(clickY + "-" + String.format("%02d", clickM));
        if (index < 0)
            return;
        setLastDate(clickY, clickM, clickD);
        monthViewPager.setScheduleState(state);
        monthViewPager.setCurrentItem(index, false);
        monthViewPager.refreshCurrentView(monthListener, index, clickD);
        resetCalendarPosition();
    }

    public void showWeekView() {//触发显示weekView时操作
        int item = weekViewPager.getCurrentItem();
        item += CalendarUtils.getWeeksAgo(firstY, firstMonth, firstDay, clickY, clickM, clickD);
        setLastDate(clickY, clickM, clickD);
        weekViewPager.setCurrentItem(item, false);
        weekViewPager.refreshCurrent(weekListener, item, clickY, clickM, clickD);

    }

    /**
     * @param distanceY 滑动距离
     *                  根据滑动距离计算各个view的Y
     */
    @Override
    public void onCalendarScroll(float distanceY) {
        MonthView monthView = monthViewPager.getCurrView();
        weekViewHeight = monthView.getmRowSize();//weekView高度和monthView单个高度相等，所以可以这么写
        float calendarDistanceY = distanceY / monthView.getMonthRow();//当前页滑动比例
        int row = monthView.getmSelRow();//当前选择第几周

        /**
         * calendar_total_lly
         *
         */
        float calendar_tab = calendar_total_lly.getY() - distanceY;//月份选择坐标
        calendar_tab = Math.max(calendar_tab, -slidingTabLayoutHeight);
        calendar_tab = Math.min(calendar_tab, 0);
        calendar_total_lly.setY(calendar_tab);


        /**
         * monthViewPager
         * 这里注意 monthViewPager 外层包裹一层布局，移动该布局的坐标，
         * 原因猜测：移动最外层布局calendar_total_lly，导致monthViewPager坐标计算不准确
         */
        float monthTop = -row * weekViewHeight;//顶部高度
        float monthY = month_view_lly.getY() - calendarDistanceY * row;//滑动距离
        monthY = Math.max(monthY, monthTop);
        monthY = Math.min(monthY, 0);
        month_view_lly.setY(monthY);

        /**
         * list_all_lly
         * 在外层布局，top高度=星期Bar高度 + weekViewHeight + slidingTabLayout高度
         * below同理
         */
        float listAllTop = weekBarHeight + weekViewHeight + slidingTabLayoutHeight;
        float listBelow = weekBarHeight + monthView.getCalendarViewHight() + slidingTabLayoutHeight + todayLyHeight;
        float listAllY = list_all_lly.getY() - calendarDistanceY * monthView.getMonthRow();
        listAllY = Math.max(listAllY, listAllTop);
        listAllY = Math.min(listAllY, listBelow);
        list_all_lly.setY(listAllY);

//
//        float toayDayllyTop = -todayLyHeight;
//        float toayDayllyBelow = 0;
//        float toayDayY = today_all_lly.getY() - distanceY;
//        toayDayY = Math.max(toayDayY, toayDayllyTop);
//        toayDayY = Math.min(toayDayY, toayDayllyBelow);
//        today_all_lly.setY(toayDayY);
    }

    /**
     * 重置
     */
    private void resetCalendarPosition() {
        int mathViewHeight = monthViewPager.getCurrView().getCalendarViewHight();
        int mathViewSelRow = monthViewPager.getCurrView().getmSelRow();
        int mathViewRowSize = monthViewPager.getCurrView().getmRowSize();
        if (state == ScheduleState.OPEN) {
            calendar_total_lly.setY(0);
            month_view_lly.setY(0);
            list_all_lly.setY(weekBarHeight + mathViewHeight + slidingTabLayoutHeight + todayLyHeight);
        } else {
            calendar_total_lly.setY(-slidingTabLayoutHeight);
            month_view_lly.setY(-mathViewSelRow * mathViewRowSize);
            list_all_lly.setY(weekBarHeight + slidingTabLayoutHeight + mathViewRowSize);//关闭状态
        }
    }

    private void closeMove() {
        if (state == ScheduleState.CLOSE) {
            monthViewPager.setScheduleState(ScheduleState.OPEN);
            monthViewPager.setCurrentPagerHeight();
            month_view_lly.setVisibility(VISIBLE);
            weekViewPager.setVisibility(INVISIBLE);
        }
    }

    private void resetCalendar() {//更新状态
        if (state == ScheduleState.OPEN) {
            month_view_lly.setVisibility(VISIBLE);
            weekViewPager.setVisibility(INVISIBLE);
        } else {
            month_view_lly.setVisibility(INVISIBLE);
            weekViewPager.setVisibility(VISIBLE);
        }
    }

    private void changeState() {
        if (state == ScheduleState.OPEN) {
            state = ScheduleState.CLOSE;
            month_view_lly.setVisibility(INVISIBLE);
            weekViewPager.setVisibility(VISIBLE);

        } else {
            state = ScheduleState.OPEN;
            month_view_lly.setVisibility(VISIBLE);
            weekViewPager.setVisibility(INVISIBLE);
        }
    }


    private int outScrollTop;
    private int outScrollBelow;

    private void outScroll() {
        outScrollTop = weekBarHeight + weekViewHeight * 2 + slidingTabLayoutHeight;
        outScrollBelow = weekBarHeight + slidingTabLayoutHeight + monthViewPager.getCurrView().getCalendarViewHight() + todayLyHeight - weekViewHeight;
        if (list_all_lly.getY() > outScrollTop && list_all_lly.getY() < outScrollBelow) {
            OutSlideAnimation animation = new OutSlideAnimation(this, autoSlideY, state);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    changeState();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            list_all_lly.startAnimation(animation);
        } else if (list_all_lly.getY() <= outScrollTop) {//顶部 这里需要考虑上滑，和下滑
            OutSlideAnimation animation = new OutSlideAnimation(this, autoSlideY, ScheduleState.OPEN);
            animation.setDuration(50);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (state == ScheduleState.OPEN) {
                        changeState();
                    } else {
                        resetCalendar();
                    }

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            list_all_lly.startAnimation(animation);
        } else {
            OutSlideAnimation animation = new OutSlideAnimation(this, autoSlideY, ScheduleState.CLOSE);
            animation.setDuration(50);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (state == ScheduleState.CLOSE)
                        state = ScheduleState.OPEN;

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            list_all_lly.startAnimation(animation);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownPoint.x = (int) event.getRawX();
                mDownPoint.y = (int) event.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE:
                closeMove();
                mGestureDetector.onTouchEvent(event);
                mIsScrolling = true;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mGestureDetector.onTouchEvent(event);
                resetScrollingState();
                outScroll();
                return true;

        }
        return super.onTouchEvent(event);
    }


    private void resetScrollingState() {
        mDownPoint.x = 0;
        mDownPoint.y = 0;
        mIsScrolling = false;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownPoint.x = (int) ev.getRawX();
                mDownPoint.y = (int) ev.getRawY();
                mGestureDetector.onTouchEvent(ev);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mIsScrolling) {
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float x = ev.getRawX();
                float y = ev.getRawY();
                float distanceX = Math.abs(x - mDownPoint.x);
                float distanceY = Math.abs(y - mDownPoint.y);
                if (distanceY > 1 && distanceY > distanceX * 2.0f) {//y达到最小滑动距离，并且，滑动的y大于两倍的x
                    return (y > mDownPoint.y && isListViewTounch()) || (y < mDownPoint.y && state == ScheduleState.OPEN);
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 检查listview的状态
     * 这里还有点小问题，原因不明，
     *
     * @return
     */
    private boolean isListViewTounch() {
        //关闭状态&&listview子view为0或者滑动到顶部
        return state == ScheduleState.CLOSE && (listViewPager.getCurrentList().countIsNull() || listViewPager.getCurrentList().isScrollTop());
    }


}
