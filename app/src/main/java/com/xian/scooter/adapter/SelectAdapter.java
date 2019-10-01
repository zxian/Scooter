package com.xian.scooter.adapter;

import android.content.Context;

import com.bit.adapter.lvadapter.MultiItemTypeAdapter;
import com.bit.adapter.lvadapter.ViewHolderLv;
import com.bit.adapter.lvadapter.base.ItemViewDelegate;
import com.xian.scooter.R;

import java.util.List;

public abstract class SelectAdapter<T> extends MultiItemTypeAdapter<T> {


    public SelectAdapter(Context context, List<T> datas, int selectPosition) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.dialog_select_item;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolderLv holder, T t, int position) {
                converts(holder, t);
                if (position == selectPosition) {//判断是否是选择项
                    holder.setVisible(R.id.iv_select, true);
                    holder.setTextColorRes(R.id.tv_dialog_select_item,R.color.theme);
                } else {
                    holder.setVisible(R.id.iv_select, false);
                    holder.setTextColorRes(R.id.tv_dialog_select_item,R.color.black_33);
                }
            }
        });
    }

    protected abstract void converts(ViewHolderLv viewHolder, T item);

}