package com.example.shuaijia.xzcalendarview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shuaijia.xzcalendarview.R;
import com.example.shuaijia.xzcalendarview.bean.CollectionBean;

/**
 * Created by JiaShuai on 2017/4/25.
 */

public class CollectionDayAdapter extends ListBaseAdapter<CollectionBean.Data.PhaseList.PhaseData> {

    public CollectionDayAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = getInflater().inflate(R.layout.collection_day_item, parent, false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public class ViewHolder {
        TextView loan_title, phase_num, phase_type, repaidamount;
    }
}
