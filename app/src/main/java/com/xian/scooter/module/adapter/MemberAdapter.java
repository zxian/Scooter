package com.xian.scooter.module.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.bumptech.glide.Glide;
import com.xian.scooter.R;
import com.xian.scooter.bean.MembersBean;

import java.util.List;

public class MemberAdapter extends CommonRvAdapter<MembersBean> {
    public MemberAdapter(Context context, int layoutId, List<MembersBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolderRv holder, MembersBean member, int position) {
        ImageView ivLogo = holder.getView(R.id.iv_logo);
        Glide.with(mContext).load(member.getHead_mage_url()).into(ivLogo);
        holder.setText(R.id.tv_name,member.getName());
        holder.setText(R.id.tv_time,member.getCreate_time());
    }
}
