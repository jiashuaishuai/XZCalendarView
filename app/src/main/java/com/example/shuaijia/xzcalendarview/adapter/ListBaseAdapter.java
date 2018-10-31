package com.example.shuaijia.xzcalendarview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.example.shuaijia.xzcalendarview.utils.ObjectNullUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/16.
 */

public abstract class ListBaseAdapter<T> extends BaseAdapter {
    public Context mContext;
    private List<T> list;
    private LayoutInflater inflater;

    public ListBaseAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * 更新添加list
     *
     * @param list 数据
     */
    public void upDateList(List<T> list) {
        if (list == null)
            list = new ArrayList<>();
        this.list = list;
        notifyDataSetChanged();
    }

    public void addDateList(List<T> list) {
        if (this.list != null && list != null)
            this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 删除 某条数据
     *
     * @param position index
     */
    public void removeItem(int position) {
        this.list.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (ObjectNullUtils.listIsNull(list))
            return 0;
        return list.size();

    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 根据index获取某条数据
     *
     * @param position index
     * @return T
     */
    @SuppressWarnings("unchecked")
    protected T getBean(int position) {
        return (T) getItem(position);
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public List<T> getList() {
        return list;
    }
}
