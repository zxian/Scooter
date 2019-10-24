package com.xian.scooter.module.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.xian.scooter.R;
import com.xian.scooter.bean.EventTypeBean;

import java.util.List;

public class EventArrangeTypeAdapter extends CommonRvAdapter<EventTypeBean> {

    private int selectPosition = -1;

    public EventArrangeTypeAdapter(Context context, int layoutId, List<EventTypeBean> datas) {
        super(context, layoutId, datas);
    }


    public void updataItem(List<EventTypeBean> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, EventTypeBean eventBean, int position) {
        holder.setText(R.id.tv_name, eventBean.getApply_competition_name());
        holder.setText(R.id.tv_number, eventBean.getLimit() + "人");
        holder.setText(R.id.tv_money, eventBean.getMoney() + "");
        holder.setText(R.id.tv_age, eventBean.getSmall_age() + "-" + eventBean.getBig_age() + "岁");

        RelativeLayout rlLayout = holder.getView(R.id.rl_layout);
        ImageView ivSelect = holder.getView(R.id.iv_select);
        if (position == selectPosition) {//判断是否是选择项
            rlLayout.setBackground(mContext.getResources().getDrawable(R.drawable.horn_white_line_theme_20dp));
            ivSelect.setVisibility(View.VISIBLE);
        }else {
            rlLayout.setBackground(mContext.getResources().getDrawable(R.drawable.horn_white_bg_20dp));
            ivSelect.setVisibility(View.GONE);
        }
    }

    public void updataItem(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

}