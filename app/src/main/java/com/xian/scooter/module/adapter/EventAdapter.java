package com.xian.scooter.module.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bit.adapter.rvadapter.CommonRvAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.xian.scooter.R;
import com.xian.scooter.bean.EventBean;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class EventAdapter extends CommonRvAdapter<EventBean> {

    public EventAdapter(Context context, int layoutId, List<EventBean> datas) {
        super(context, layoutId, datas);
    }

    public void updataItem(List<EventBean> datas) {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, EventBean eventBean, int position) {
        ImageView ivPicture = holder.getView(R.id.iv_picture);
        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        options.bitmapTransform(new RoundedCornersTransformation(30,0, RoundedCornersTransformation.CornerType.ALL));
        Glide.with(mContext)
                .load(eventBean.getPosters_url())
                .apply(options)
                .into(ivPicture);

        holder.setText(R.id.tv_title, eventBean.getCompetition_name());
        holder.setText(R.id.tv_time, "比赛时间："+eventBean.getOfficial_time());
        holder.setText(R.id.tv_address, eventBean.getAddress());

        TextView tvStatus = holder.getView(R.id.tv_status);
        String competition_state = eventBean.getCompetition_state();// competition_state 赛事状态：1、待审核，2、审核未通过，3、已通过，4、取消中，5、取消失败，6、已取消
        switch (competition_state){
            case "1":
                tvStatus.setText("待审核");
                break;
            case "2":
                tvStatus.setText("审核未通过");
                break;
            case "3":
                tvStatus.setText("已通过");
                break;
            case "4":
                tvStatus.setText("取消中");
                break;
            case "5":
                tvStatus.setText("取消失败");
                break;
            case "6":
                tvStatus.setText("已取消");
                break;
        }
    }
}