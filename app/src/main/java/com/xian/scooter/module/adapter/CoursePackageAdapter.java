package com.xian.scooter.module.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.bit.adapter.rvadapter.CommonRvAdapter;

import com.bit.adapter.rvadapter.ViewHolderRv;
import com.xian.scooter.R;
import com.xian.scooter.bean.CoursePackageBean;

import java.util.List;

public class CoursePackageAdapter extends CommonRvAdapter<CoursePackageBean> {
    public CoursePackageAdapter(Context context, int layoutId, List<CoursePackageBean> list) {
        super(context,layoutId,list);

    }

    public void updataItem(List<CoursePackageBean> list){
        this.mDatas.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolderRv holder, CoursePackageBean coursePackageBean, int position) {
        String package_name = coursePackageBean.getPackage_name();
        holder.setText(R.id.tv_course_name,package_name);

        int time = coursePackageBean.getNum();
        String times = "课程次数："+time+"次";
        holder.setText(R.id.tv_time,times);

        int day = coursePackageBean.getValid_day();
        String days = "有效期：购买起"+day+"天";
        holder.setText(R.id.tv_time_limit,days);

        double price = coursePackageBean.getPackage_price();
        String prices = "￥" + price;
        holder.setText(R.id.tv_price,prices);

        Switch aSwitch = holder.getView(R.id.sw_select);
        int state = coursePackageBean.getPackage_state();
        if (state==0){ //是否启用：0、未启用，1、已启用
           aSwitch.setChecked(false);
        }else if (state==1){
            aSwitch.setChecked(true);
        }

    }
}
