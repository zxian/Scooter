package com.xian.scooter.module.adapter;

import android.content.Context;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.xian.scooter.R;
import com.xian.scooter.bean.SetUpCommonBean;

import java.util.List;


public class SetUpCommonAdapter extends CommonRvAdapter<SetUpCommonBean> {

    public SetUpCommonAdapter(Context context, int layoutId, List<SetUpCommonBean> datas) {
        super(context, layoutId, datas);
    }

    public void updataItem(List<SetUpCommonBean> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, SetUpCommonBean setUpCommonBean, int position) {
        holder.setText(R.id.tv_content,setUpCommonBean.getTitle());
    }
}