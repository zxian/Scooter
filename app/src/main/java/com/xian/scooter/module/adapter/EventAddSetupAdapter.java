package com.xian.scooter.module.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.xian.scooter.R;
import com.xian.scooter.bean.EventTypeBean;
import com.xian.scooter.beanpar.CompetitionSavePar;

import java.util.List;


public class EventAddSetupAdapter extends CommonRvAdapter<CompetitionSavePar> {

    public EventAddSetupAdapter(Context context, int layoutId, List<CompetitionSavePar> datas) {
        super(context, layoutId, datas);
    }


    public void updataItem(List<CompetitionSavePar> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, CompetitionSavePar eventBean, int position) {
        holder.setText(R.id.tv_name, eventBean.getApply_competition_name());
        holder.setText(R.id.tv_number, eventBean.getLimit()+"人");
        holder.setText(R.id.tv_money, eventBean.getMoney()+"");
        holder.setText(R.id.tv_age, eventBean.getSmall_age()+"-"+eventBean.getBig_age()+"岁");
        ImageView ivAlter = holder.getView(R.id.iv_alter);
        ivAlter.setVisibility(View.VISIBLE);
    }
}