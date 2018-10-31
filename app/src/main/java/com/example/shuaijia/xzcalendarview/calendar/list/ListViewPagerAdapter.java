package com.example.shuaijia.xzcalendarview.calendar.list;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.shuaijia.xzcalendarview.R;
import com.example.shuaijia.xzcalendarview.adapter.CollectionDayAdapter;

/**
 * Created by JiaShuai on 2017/9/22.
 */

public class ListViewPagerAdapter extends PagerAdapter {
    private SparseArray<View> listArray;
    private Context mContext;


    public ListViewPagerAdapter(Context context) {
        mContext = context;
        listArray = new SparseArray<>(2);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(getView(position));
        return listArray.get(position);
    }

    public View getView(int position) {
        View view = listArray.get(position);
        if (view == null) {
            view = View.inflate(mContext, R.layout.calendar_list_pager_layout, null);
            listArray.put(position, view);
        }
        return view;
    }

    public CollectionDayAdapter getAdapter(int position) {
        return getListView(position).getListAdapter();
    }

    public CalendarListView getListView(int position) {
        return (CalendarListView) getView(position).findViewById(R.id.calendar_list_view);
    }

    public LinearLayout getHintLayout(int position) {

        return (LinearLayout) getView(position).findViewById(R.id.account_balance_no);
    }
}
