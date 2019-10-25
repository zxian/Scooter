package com.xian.scooter.module.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.bumptech.glide.Glide;
import com.xian.scooter.R;
import com.xian.scooter.bean.CoursePurchaseRecordsBean;

import java.util.List;

public class CoursePurchaseRecordsAdapter extends CommonRvAdapter<CoursePurchaseRecordsBean> {
    public CoursePurchaseRecordsAdapter(Context context, int layoutId, List<CoursePurchaseRecordsBean> list) {
        super(context,layoutId,list);

    }

    public void updataItem(List<CoursePurchaseRecordsBean> list){
        this.mDatas.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, CoursePurchaseRecordsBean coursePurchaseRecordsBean, int position) {
        ImageView ivLogo = holder.getView(R.id.iv_logo);
        Glide.with(mContext).load(coursePurchaseRecordsBean.getCust_user_head_url()).into(ivLogo);
        holder.setText(R.id.tv_title,coursePurchaseRecordsBean.getPackage_name());
        holder.setText(R.id.tv_time,coursePurchaseRecordsBean.getCreate_time());

    }

}
