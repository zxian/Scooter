package com.xian.scooter.adapter;

import android.content.Context;

import com.bit.adapter.rvadapter.MultiItemTypeAdapter;
import com.bit.adapter.rvadapter.ViewHolderRv;
import com.bit.adapter.rvadapter.base.ItemViewDelegate;
import com.xian.scooter.R;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiSelectRvAdapter<T> extends MultiItemTypeAdapter<T> {

    private List<Integer> selectList=new ArrayList<>();

    public MultiSelectRvAdapter(Context context, int layout, List<T> datas, List<Integer> selectPositionList) {
        super(context, datas);
        if (selectList.size() == 0) {
            selectList = selectPositionList;
        }
        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layout;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolderRv holder, T t, int position) {
                converts(holder, t);
                holder.setTextColorRes(R.id.tv_content,R.color.black_33);
                holder.setBackgroundRes(R.id.tv_content,R.drawable.horn_gray_f2_20dp);
                for (Integer selectPosition : selectList) {
                    if (position == selectPosition) {//判断是否是选择项
                        holder.setTextColorRes(R.id.tv_content,R.color.theme);
                        holder.setBackgroundRes(R.id.tv_content,R.drawable.horn_blue_ec_line_theme_20dp);
                    }else {
                        holder.setTextColorRes(R.id.tv_content,R.color.black_33);
                        holder.setBackgroundRes(R.id.tv_content,R.drawable.horn_gray_f2_20dp);
                    }
                }
            }
        });

    }

    public void updataItem(List<Integer> selectList) {
        this.selectList = selectList;
        notifyDataSetChanged();
    }

    protected abstract void converts(ViewHolderRv viewHolder, T item);

}
