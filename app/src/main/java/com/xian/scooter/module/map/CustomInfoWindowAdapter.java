package com.xian.scooter.module.map;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.model.Marker;
import com.xian.scooter.R;
import com.xian.scooter.bean.MarkerBean;


public class CustomInfoWindowAdapter implements InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
        setViewContent(marker, view);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void setViewContent(Marker marker, View view) {
        ImageView ivIcon = view.findViewById(R.id.badge);
        TextView tvTitle = view.findViewById(R.id.title);
        TextView tvSnippet = view.findViewById(R.id.snippet);

        MarkerBean bean = (MarkerBean) marker.getObject();
        if (bean != null) {
            if (bean.getIconId() != null) {
                ivIcon.setImageResource(bean.getIconId());
                ivIcon.setVisibility(View.VISIBLE);
            } else {
                ivIcon.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(bean.getTitle())) {
                tvTitle.setText(bean.getTitle());
                tvTitle.setVisibility(View.VISIBLE);
            } else {
                tvTitle.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(bean.getSnippet())) {
                tvSnippet.setText(bean.getSnippet());
                tvSnippet.setVisibility(View.VISIBLE);
            } else {
                tvSnippet.setVisibility(View.GONE);
            }
        }
    }
}
