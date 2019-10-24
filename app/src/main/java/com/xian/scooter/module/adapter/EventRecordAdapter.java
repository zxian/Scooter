package com.xian.scooter.module.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.bumptech.glide.Glide;
import com.xian.scooter.R;
import com.xian.scooter.bean.EventRecordBean;

import java.util.List;

public class EventRecordAdapter extends CommonRvAdapter<EventRecordBean> {

    public EventRecordAdapter(Context context, int layoutId, List<EventRecordBean> datas) {
        super(context, layoutId, datas);
    }

    public void updataItem(List<EventRecordBean> datas){
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, EventRecordBean eventRecordBean, int position) {
        String name = eventRecordBean.getCompetition_set_name();
        holder.setText(R.id.tv_type,name);

        String time = eventRecordBean.getCreate_time();
        holder.setText(R.id.tv_time,time);

        ImageView ivLogo = holder.getView(R.id.iv_logo);
        String logo = eventRecordBean.getChild_face_url();
        Glide.with(mContext).load(logo).load(ivLogo);

        String child_name = eventRecordBean.getChild_name();
        holder.setText(R.id.tv_name,child_name);

        ImageView ivGender = holder.getView(R.id.iv_gender);
        int sex = eventRecordBean.getChild_sex();
        if (sex==1){//性别：1、男，2、女
            ivGender.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_male));
        }else if (sex==2){
            ivGender.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_male));
        }

        int age = eventRecordBean.getChild_age();
        String s = age + "岁";
        holder.setText(R.id.tv_age,s);

        String address = eventRecordBean.getAddress();
        holder.setText(R.id.tv_area,address);

        String phone = eventRecordBean.getCust_user_phone();
        holder.setText(R.id.tv_phone,phone);
    }
}
