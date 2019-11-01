package com.xian.scooter.module.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.xian.scooter.R;
import com.xian.scooter.bean.EventRecordBean;

import java.util.List;

public class EventArrangePersonAdapter extends CommonRvAdapter<EventRecordBean> {

    private int selectPosition = -1;

    public EventArrangePersonAdapter(Context context, int layoutId, List<EventRecordBean> datas) {
        super(context, layoutId, datas);
    }


    public void updataItem(List<EventRecordBean> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, EventRecordBean eventBean, int position) {
        holder.setText(R.id.tv_name, eventBean.getChild_name());
        ImageView ivSex = holder.getView(R.id.iv_sex);
        int sex = eventBean.getChild_sex();
        if (sex==1){//性别：1、男，2、女
            ivSex.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_male));
        }else if (sex==2){
            ivSex.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_male));
        }
        int age = eventBean.getChild_age();
        String s = age + "岁";
        holder.setText(R.id.tv_age,s);
    }

    public void updataItem(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

}