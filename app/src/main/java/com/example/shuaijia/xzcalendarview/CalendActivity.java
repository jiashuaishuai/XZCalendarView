package com.example.shuaijia.xzcalendarview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.shuaijia.xzcalendarview.bean.CollectionDateBean;
import com.example.shuaijia.xzcalendarview.calendar.OnClickMonthViewDayListener;
import com.example.shuaijia.xzcalendarview.calendar.ScheduleLayout;
import com.example.shuaijia.xzcalendarview.data.Data;

import java.util.List;

public class CalendActivity extends AppCompatActivity {
    private int mSelYear, mSelMonth, mSelDay;
    private ScheduleLayout calendarView;
    private List<CollectionDateBean.Data.YearData> loopViewItemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_calendar);
        initView();
    }

    private void initView() {
        calendarView = (ScheduleLayout) findViewById(R.id.calendar_view);


        calendarView.setClickMonthViewDayListener(new OnClickMonthViewDayListener() {
            @Override
            public void clickCallBack(int selYear, int selMonth, int selDay, boolean isCashBackDay) {
                mSelYear = selYear;
                mSelMonth = selMonth;
                mSelDay = selDay;

                Toast.makeText(CalendActivity.this, selYear + "-" + selMonth + "-" + selDay, Toast.LENGTH_LONG).show();
            }
        });

        new Handler().postAtTime(new Runnable() {
            @Override
            public void run() {
                loopViewItemList = Data.getBean().getData().getData();
                calendarView.setAdapterDate(loopViewItemList, Data.getBean().getData().getGmt());
            }
        }, 5000);

    }
}
