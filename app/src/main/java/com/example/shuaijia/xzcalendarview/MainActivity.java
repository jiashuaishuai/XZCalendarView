package com.example.shuaijia.xzcalendarview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shuaijia.xzcalendarview.bean.CollectionDateBean;
import com.example.shuaijia.xzcalendarview.calendar.OnClickMonthViewDayListener;
import com.example.shuaijia.xzcalendarview.calendar.ScheduleLayout;
import com.example.shuaijia.xzcalendarview.calendar.list.CalendarListViewPager;
import com.example.shuaijia.xzcalendarview.data.Data;
import com.example.shuaijia.xzcalendarview.tableLayout.SlidingTabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        startActivity(new Intent(this, CalendActivity.class));
    }

}
