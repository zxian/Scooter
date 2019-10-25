package com.xian.scooter.module.map;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.bean.MarkerBean;
import com.xian.scooter.bean.NavBean;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MapLocationPositionActivity extends BaseActivity {

    @BindView(R.id.iv_sign_map_back)
    ImageView ivSignMapBack;
    @BindView(R.id.map_view)
    MapView mapView;
    private NavBean navInfo;//地址信息

    private AMap mAMap;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_map_location_position;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void handleIntent(Intent intent) {
        navInfo = intent.getParcelableExtra("nav_info");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);
        mAMap = mapView.getMap();
        mAMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(mActivity));
        if (navInfo != null) {
            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(navInfo.getLatitude(), navInfo.getLongitude())));
            mAMap.moveCamera(CameraUpdateFactory.zoomTo(15)); //缩放比例
            LatLng latLng = new LatLng(navInfo.getLatitude(), navInfo.getLongitude());
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(latLng);

//        markerOption.title(navInfo.getAddress());
            markerOption.draggable(false);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.mipmap.ic_location)));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//        markerOption.setFlat(true);//设置marker平贴地图效果
            Marker marker = mAMap.addMarker(markerOption);
            MarkerBean bean = new MarkerBean();
            bean.setTitle(navInfo.getAddress());
            marker.setObject(bean);
            marker.showInfoWindow();//显示信息窗口
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }


    @OnClick(R.id.iv_sign_map_back)
    public void onViewClicked() {
        finish();
    }
}
