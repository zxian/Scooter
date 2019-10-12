package com.xian.scooter.module.adapter;

import android.content.Context;
import android.text.TextUtils;
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
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.EventBean;
import com.xian.scooter.utils.TimeUtils;
import com.xian.scooter.utils.ToastUtils;

import java.lang.reflect.Member;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class EventAdapter extends CommonRvAdapter<EventBean> {

    private long time;

    public EventAdapter(Context context, int layoutId, List<EventBean> datas) {
        super(context, layoutId, datas);
    }


    public void updataItem(List<EventBean> datas, long time) {
        this.time = time;
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, EventBean eventBean, int position) {
        ImageView ivPicture = holder.getView(R.id.iv_picture);
        RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
        options.bitmapTransform(new RoundedCornersTransformation(30, 0, RoundedCornersTransformation.CornerType.ALL));
        Glide.with(mContext)
                .load(eventBean.getPosters_url())
                .apply(options)
                .into(ivPicture);

        holder.setText(R.id.tv_title, eventBean.getCompetition_name());
        holder.setText(R.id.tv_time, "比赛时间：" + eventBean.getOfficial_time());
        holder.setText(R.id.tv_address, eventBean.getAddress());

        TextView tvStatus = holder.getView(R.id.tv_status);

        long start_time = TimeUtils.getStringToDate(eventBean.getStart_time(), "yyyy-MM-dd HH:mm:ss");//报名开始时间
        long end_time = TimeUtils.getStringToDate(eventBean.getEnd_time(), "yyyy-MM-dd HH:mm:ss");//报名结束时间

        String competition_state = eventBean.getCompetition_state();// competition_state 赛事状态：1、待审核，2、审核未通过，3、已通过，4、取消中，5、取消失败，6、已取消
        if (!TextUtils.isEmpty(competition_state)) {
            switch (competition_state) {
                case "2":
                    tvStatus.setText("审核未通过");
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
                default:
                    if (time >= start_time && time <= end_time) {
                        tvStatus.setText("报名中");
//                        holder.setText(R.id.tv_money, eventBean.gettp());

                    } else {
                        tvStatus.setText("报名结束");
                    }
                    break;
            }
        } else {
            if (time >= start_time && time <= end_time) {
                tvStatus.setText("报名中");
            } else {
                tvStatus.setText("报名结束");
            }

        }
    }
}