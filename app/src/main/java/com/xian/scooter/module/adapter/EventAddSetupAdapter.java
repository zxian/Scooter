package com.xian.scooter.module.adapter;

import android.content.Context;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.xian.scooter.bean.EventAddSetupBean;

import java.util.List;


public class EventAddSetupAdapter extends CommonRvAdapter<EventAddSetupBean> {

    public EventAddSetupAdapter(Context context, int layoutId, List<EventAddSetupBean> datas) {
        super(context, layoutId, datas);
    }


    public void updataItem(List<EventAddSetupBean> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, EventAddSetupBean eventBean, int position) {
//        holder.setText(R.id.tv_title, eventBean.getCompetition_name());

    }
}