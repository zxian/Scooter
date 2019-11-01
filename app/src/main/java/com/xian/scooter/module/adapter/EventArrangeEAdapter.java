package com.xian.scooter.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xian.scooter.R;
import com.xian.scooter.bean.EventArrangeBean;

import java.util.ArrayList;
import java.util.List;

public class EventArrangeEAdapter extends BaseExpandableListAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<EventArrangeBean> data_list = new ArrayList<>();


    public EventArrangeEAdapter(Context context, List<EventArrangeBean> datas) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.data_list = datas;
    }

    public void updataData(List<EventArrangeBean> datas) {
        this.data_list = datas;
        this.notifyDataSetChanged();
    }

    // 获取二级列表的内容
    @Override
    public Object getChild(int arg0, int arg1) {
        return data_list.get(arg0).getList().get(arg1);
    }

    // 获取二级列表的ID
    @Override
    public long getChildId(int arg0, int arg1) {
        return arg1;
    }

    // 定义二级列表中的数据
    @Override
    public View getChildView(int arg0, int arg1, boolean arg2, View arg3, ViewGroup arg4) {
        // 定义一个二级列表的视图类
        HolderView childrenView;
        if (arg3 == null) {
            childrenView = new HolderView();
            // 获取子视图的布局文件
            arg3 = mInflater.inflate(R.layout.item_event_arrange_children, arg4, false);
            childrenView.tvName = (TextView) arg3.findViewById(R.id.tv_name);
            childrenView.tvDate = (TextView) arg3.findViewById(R.id.tv_date);
            // 这个函数是用来将holderview设置标签,相当于缓存在view当中
            arg3.setTag(childrenView);
        } else {
            childrenView = (HolderView) arg3.getTag();
        }

        /**
         * 设置相应控件的内容
         */
        // 设置标题上的文本信息
        childrenView.tvName.setText(data_list.get(arg0).getList().get(arg1).getName());
        childrenView.tvDate.setText(data_list.get(arg0).getList().get(arg1).getOfficial_time());
        return arg3;
    }



    // 保存二级列表的视图类
    private class HolderView {
        TextView tvName;
        TextView tvDate;
    }

    // 获取二级列表的数量
    @Override
    public int getChildrenCount(int arg0) {
        return data_list.get(arg0).getList()!=null?data_list.get(arg0).getList().size():0;
    }

    // 获取一级列表的数据
    @Override
    public Object getGroup(int arg0) {
        return data_list.get(arg0);
    }

    // 获取一级列表的个数
    @Override
    public int getGroupCount() {
        return data_list!=null?data_list.size():0;
    }

    // 获取一级列表的ID
    @Override
    public long getGroupId(int arg0) {
        return arg0;
    }

    // 设置一级列表的view
    @Override
    public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
        HodlerViewFather hodlerViewFather;
        if (arg2 == null) {
            hodlerViewFather = new HodlerViewFather();
            arg2 = mInflater.inflate(R.layout.item_event_arrange_father, arg3, false);
            hodlerViewFather.titlev = (TextView) arg2.findViewById(R.id.tv_title);
            arg2.setTag(hodlerViewFather);
        } else {
            hodlerViewFather = (HodlerViewFather) arg2.getTag();
        }

        /**
         * 设置相应控件的内容
         */
        // 设置标题上的文本信息
        hodlerViewFather.titlev.setText(data_list.get(arg0).getApply_competition_name());

        // 返回一个布局对象
        return arg2;
    }

    // 定义一个 一级列表的view类
    private class HodlerViewFather {
        TextView titlev;
        ImageView group_state;
        ImageView ivSelect;
    }

    /**
     * 指定位置相应的组视图
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 当选择子节点的时候，调用该方法(点击二级列表)
     */
    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }
}
