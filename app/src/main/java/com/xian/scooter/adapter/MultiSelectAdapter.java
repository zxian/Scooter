package com.xian.scooter.adapter;

import android.content.Context;

import com.bit.adapter.lvadapter.MultiItemTypeAdapter;
import com.bit.adapter.lvadapter.ViewHolderLv;
import com.bit.adapter.lvadapter.base.ItemViewDelegate;
import com.xian.scooter.R;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiSelectAdapter<T> extends MultiItemTypeAdapter<T> {

    private List<Integer> selectList=new ArrayList<>();

    public MultiSelectAdapter(Context context, List<T> datas, List<Integer> selectPositionList) {
        super(context, datas);
        if (selectList.size() == 0) {
            selectList = selectPositionList;
        }
        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.dialog_multiselect_item;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolderLv holder, T t, int position) {
                converts(holder, t);
                holder.setVisible(R.id.iv_select, false);
                holder.setVisible(R.id.v_line, false);
                holder.setTextColorRes(R.id.tv_dialog_select_item,R.color.black_33);
                for (Integer selectPosition : selectList) {
                    if (position == selectPosition) {//判断是否是选择项
                        holder.setTextColorRes(R.id.tv_dialog_select_item,R.color.theme);
                        holder.setVisible(R.id.iv_select, true);
                        holder.setVisible(R.id.v_line, true);
                    }
                }
            }
        });

    }

    public void updataItem(List<Integer> selectList) {
        this.selectList = selectList;
        notifyDataSetChanged();
    }

    protected abstract void converts(ViewHolderLv viewHolder, T item);

}
