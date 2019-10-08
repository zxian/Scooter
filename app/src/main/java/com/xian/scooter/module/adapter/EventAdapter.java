package com.xian.scooter.module.adapter;

import android.content.Context;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventBean;

import java.lang.reflect.Member;
import java.util.List;

public class EventAdapter extends CommonRvAdapter<EventBean> {

    public EventAdapter(Context context, int layoutId, List<EventBean> datas) {
        super(context, layoutId, datas);
    }


    public void updataItem(List<EventBean> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, EventBean eventBean, int position) {
//        holder.setText(R.id.tv_unit, eventBean.getUnitName());
    }
}