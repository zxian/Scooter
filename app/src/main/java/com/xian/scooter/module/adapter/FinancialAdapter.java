package com.xian.scooter.module.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.xian.scooter.R;
import com.xian.scooter.bean.FinancialBean;

import java.util.List;

public class FinancialAdapter extends CommonRvAdapter<FinancialBean> {


    public FinancialAdapter(Context context, int layoutId, List<FinancialBean> datas) {
        super(context, layoutId, datas);
    }


    public void updataItem(List<FinancialBean> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, FinancialBean FinancialBean, int position) {
//        holder.setText(R.id.tv_title, FinancialBean.getCompetition_name());

    }
}
        