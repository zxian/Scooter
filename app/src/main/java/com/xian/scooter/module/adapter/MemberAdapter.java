package com.xian.scooter.module.adapter;

import android.content.Context;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.xian.scooter.bean.MemberBean;

import java.util.List;

public class MemberAdapter extends CommonRvAdapter<MemberBean> {
    public MemberAdapter(Context context, int layoutId, List<MemberBean> datas) {
        super(context, layoutId, datas);
    }

    public void  medataItem(List<MemberBean> datas){
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, MemberBean member, int position) {

    }
}
