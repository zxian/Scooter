package com.xian.scooter.module.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.xian.scooter.R;
import com.xian.scooter.bean.EventPlanBean;
import com.xian.scooter.bean.EventTypeBean;

import java.util.List;

public class EventArrangePlanAdapter extends CommonRvAdapter<EventPlanBean> {

    private int selectPosition = -1;

    public EventArrangePlanAdapter(Context context, int layoutId, List<EventPlanBean> datas) {
        super(context, layoutId, datas);
    }


    public void updataItem(List<EventPlanBean> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, EventPlanBean eventBean, int position) {
//        holder.setText(R.id.tv_name, eventBean.getApply_competition_name());
//        holder.setText(R.id.tv_time, eventBean.getLimit());
//
//        ImageView ivSelect = holder.getView(R.id.iv_select);
//        if (position == selectPosition) {//判断是否是选择项
//            ivSelect.setVisibility(View.VISIBLE);
//        }else {
//            ivSelect.setVisibility(View.GONE);
//        }
    }

    public void updataItem(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

}