package com.xian.scooter.module.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.xian.scooter.base.BaseActivity;
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

    }

}
