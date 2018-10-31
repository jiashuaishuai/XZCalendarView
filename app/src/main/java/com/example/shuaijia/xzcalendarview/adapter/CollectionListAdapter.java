package com.example.shuaijia.xzcalendarview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shuaijia.xzcalendarview.R;
import com.example.shuaijia.xzcalendarview.bean.CollectionBean;

import java.util.List;

/**
 * Created by JiaShuai on 2017/4/26.
 */

public class CollectionListAdapter extends ListBaseAdapter<CollectionBean.Data.PhaseList> {

    public CollectionListAdapter(Context context, boolean isphase) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = getInflater().inflate(R.layout.collection_list_list_item, parent, false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    class ViewHolder {
        View collection_list_title_spot;
        LinearLayout collection_list_content_lly;
        TextView collection_list_title_name;
    }
}
