package com.xian.scooter.module.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.xian.scooter.R;


import java.util.ArrayList;

/**
 * author : zhangzhao.ke
 * time   : 2018/03/31
 * desc   :地址的列表
 */

public class AddressAdapter extends BaseAdapter {

    private ArrayList<PoiItem> poiItemList;
    private Context mContext;
    private LayoutInflater inflater;
    private PoiItem poiItem;
    private boolean hasPositionImg;

    public AddressAdapter(Context context, ArrayList<PoiItem> list, boolean hasPositionImg) {
        poiItemList = new ArrayList<>();
        this.mContext = context;
        this.hasPositionImg = hasPositionImg;
        poiItemList = new ArrayList<>();
        if (list != null)
            poiItemList.addAll(list);
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return poiItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return poiItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.address_list_item, null);
            holder.ivPosition = convertView.findViewById(R.id.iv_position);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvAddress = convertView.findViewById(R.id.tv_address);
            convertView.setTag(holder); // 加入缓存
        } else {
            holder = (ViewHolder) convertView.getTag(); // 如果ConvertView不为空，则表示在缓存中
        }
        if (position == 0 && hasPositionImg) {
            holder.ivPosition.setVisibility(View.VISIBLE);
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.theme));
        } else {
            holder.ivPosition.setVisibility(View.INVISIBLE);
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.black_33));
        }

        poiItem = poiItemList.get(position);
        holder.tvName.setText(poiItem.getTitle());
        String addressStr = poiItem.getProvinceName() + poiItem.getCityName()
                + poiItem.getAdName() + poiItem.getSnippet();
        holder.tvAddress.setText(addressStr);
        return convertView;
    }

    public void setData(ArrayList<PoiItem> list) {
        if (poiItemList != null) {
            poiItemList.clear();
            poiItemList.addAll(list);
        }
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvAddress;
        ImageView ivPosition;
    }
}
