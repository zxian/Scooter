package com.xian.scooter.module.map;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.xian.scooter.R;
import com.xian.scooter.base.BaseActivity;
import com.xian.scooter.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LocationActivity extends BaseActivity implements AMap.OnMarkerClickListener,
        AMap.OnMapLoadedListener, AMap.OnMapClickListener, LocationSource,
        AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener, AMap.OnCameraChangeListener,
        AMap.OnMarkerDragListener, PoiSearch.OnPoiSearchListener {

    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.lv_address_list)
    ListView lvAddressList;

    private boolean isFirstLoc = true;
    private AMap mAMap;
    private OnLocationChangedListener mListener;
    private MyLocationStyle myLocationStyle;
    private Marker mGPSMarker;//定位位置显示
    private AMapLocation location;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //你编码对象
    private GeocodeSearch geocoderSearch;
    private String custAddr;
    private Double custLon;
    private Double custLat;
    private String actualAddr;
    private Double actualLon;
    private Double actualLat;
    private ImageView img_back;
    private String city;
    private MarkerOptions markOptions;
    private LatLng latLng;
    private String addressName;
    private String deepType = "";// poi搜索类型
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private PoiResult poiResult; // poi返回的结果
    private ArrayList<PoiItem> poiItemList = new ArrayList<>();// poi数据列表
    private AddressAdapter addressAdapter;
    private boolean isFailFisrtGPS = true;//是否首次定位失败

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_location;
    }

    @Override
    protected void init() {
        addressAdapter = new AddressAdapter(mActivity, null,true);
        lvAddressList.setAdapter(addressAdapter);
        lvAddressList.setOnItemClickListener((parent, view, position, id) -> {
            if (poiItemList != null && poiItemList.size() > 0) {
                Intent intent = new Intent();
                intent.putExtra("PoiItem", poiItemList.get(position));
                setResult(2000, intent);
                finish();
            } else {
                ToastUtils.showToast("请稍后重试");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initMap(savedInstanceState);
    }

    private void initMap(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        mAMap = mMapView.getMap();
        // 设置定位监听
        mAMap.setOnMapLoadedListener(this);
        mAMap.setOnMarkerClickListener(this);
        mAMap.setOnMapClickListener(this);
        mAMap.setLocationSource(this);
        //设置地图拖动监听
        mAMap.setOnCameraChangeListener(this);
        // 绑定marker拖拽事件
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        /**
         * 图标
         */
//        myLocationStyle.myLocationIcon(null);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.nothing));// 设置小蓝点的图标
        //myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
//        myLocationStyle.anchor(0.5f, 0.7f);
        mAMap.setMyLocationStyle(myLocationStyle);

        /**
         * 缩放比例
         */
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(15)); //缩放比例

        //添加一个圆
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.radius(20.0f);
        mAMap.addCircle(circleOptions);

        //设置amap的属性
        UiSettings settings = mAMap.getUiSettings();
        settings.setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        location = aMapLocation;
        if (mListener != null && location != null) {
            if (location.getErrorCode() == 0) {
                mListener.onLocationChanged(location);// 显示系统箭头

                LatLng la = new LatLng(location.getLatitude(), location.getLongitude());

//                setMarket(la, location.getCity(), location.getAddress());
                setMarket(la, location.getCity() + location.getDistrict(), location.getAoiName());
                this.actualAddr = location.getAddress();
                this.actualLon = location.getLongitude();
                this.actualLat = location.getLatitude();

                mLocationClient.stopLocation();
                //                this.location = location;
                // 显示导航按钮
                //                btnNav.setVisibility(View.VISIBLE);
            }
        } else if (isFailFisrtGPS){
            ToastUtils.showToast("定位失败");
            isFailFisrtGPS = false;
        }

    }

    private void setMarket(LatLng latLng, String title, String content) {
        if (mGPSMarker != null) {
            mGPSMarker.remove();
        }
        //获取屏幕宽高
//        WindowManager wm = this.getWindowManager();
//        int width = (wm.getDefaultDisplay().getWidth()) / 2;
//        int height = ((wm.getDefaultDisplay().getHeight()) / 2) - 80;
        markOptions = new MarkerOptions();
        markOptions.draggable(true);//设置Marker可拖动
        markOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_location_red))).anchor(0.0f, 0.0f);
        //设置一个角标
        mGPSMarker = mAMap.addMarker(markOptions);
        //设置marker在屏幕的像素坐标
//        mGPSMarker.setPosition(latLng);
//        mGPSMarker.setTitle(title);
//        mGPSMarker.setSnippet(content);
//        mGPSMarker.setSnippet(title);
        //设置像素坐标
        int width = mMapView.getWidth() / 2;
        int height = mMapView.getHeight() / 2;
        mGPSMarker.setPositionByPixels(width, height);
//        if (!TextUtils.isEmpty(content)) {
//            mGPSMarker.showInfoWindow();
//        }
        mMapView.invalidate();
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(LatLng latLonPoint) {
//        aMap.setOnMapClickListener(null);// 进行poi搜索时清除掉地图点击事件
        int currentPage = 0;
        query = new PoiSearch.Query("", "生活", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        LatLonPoint lp = new LatLonPoint(latLonPoint.latitude, latLonPoint.longitude);

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true));
        // 设置搜索区域为以lp点为圆心，其周围2000米范围
        poiSearch.searchPOIAsyn();// 异步搜索

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000 * 10);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        latLng = cameraPosition.target;
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        Log.e("latitude", latitude + "");
        Log.e("longitude", longitude + "");
        getAddress(latLng);
        //add ke
        doSearchQuery(latLng);
        //add ke
    }

    /**
     * 根据经纬度得到地址
     */
    public void getAddress(final LatLng latLonPoint) {
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
//        RegeocodeQuery query = new RegeocodeQuery(convertToLatLonPoint(latLonPoint), 500, GeocodeSearch.AMAP);
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLonPoint.latitude, latLonPoint.longitude), 500, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }


    @Override
    public void onMapClick(LatLng latLng) {
//        mAMap.clear();
        //        this.custLat = latLng.latitude;
        //        this.custLon = latLng.longitude;
        //
        ////        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        ////        if (!TextUtils.isEmpty(latLonPoint.toString())) {
        ////            getAddress(latLonPoint);
        ////        } else {
        ////            Util.showToast(AttendanceViewMap.this, "拜访地址获取失败");
        ////        }
        //        MarkerOptions otMarkerOptions = new MarkerOptions();
        //        otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bd_location_icon01));
        //        otMarkerOptions.position(latLng);
        //        mAMap.addMarker(otMarkerOptions);
        //        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));

    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        // aMapEx.onRegister();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁定位
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        //        if (aMapEx != null) {
        //            aMapEx.onUnregister();
        //        }
        mMapView.onDestroy();

    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
//                addressName = result.getRegeocodeAddress().getFormatAddress(); // 逆转地里编码不是每次都可以得到对应地图上的opi
//                Logger.e("逆地理编码回调  得到的地址：" + addressName);
// mAddressEntityFirst = new AddressSearchTextEntity(addressName, addressName, true, convertToLatLonPoint(mFinalChoosePosition));
//                setMarket(latLng, location.getCity(), addressName);
//                 String addTitle = result.getRegeocodeAddress().getPois().get(0).getTitle();
                setMarket(latLng, location.getCity() + location.getDistrict(), addressName);
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.e("marker", "marker正在拖拽");
    }

    /**
     * 在marker拖动完成后回调此方法, 这个marker的位置可以通过getPosition()方法返回。
     * 这个位置可能与拖动的之前的marker位置不一样。
     * marker 被拖动的marker对象。
     *
     * @param marker Marker
     */
    @Override
    public void onMarkerDrag(Marker marker) {
        latLng = marker.getPosition();
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        Log.e("latitude", latitude + "");
        Log.e("longitude", longitude + "");
        getAddress(latLng);

    }

    /**
     * 在marker拖动过程中回调此方法, 这个marker的位置可以通过getPosition()方法返回。
     * 这个位置可能与拖动的之前的marker位置不一样。
     * marker 被拖动的marker对象。
     *
     * @param marker Marker
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.e("marker", "marker拖拽完成");
//        setMarket(latLng, location.getCity()+location.getDistrict(), addressName);
        setMarket(latLng, location.getCity() + location.getDistrict(), location.getAoiName());
        // 销毁定位
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }

    }

    /**
     * 查询附件地址列表
     *
     * @param result PoiResult
     * @param i i
     */
    @Override
    public void onPoiSearched(PoiResult result, int i) {
        poiItemList = result.getPois();
        addressAdapter.setData(poiItemList);
    }

    /**
     * 查询附件地址列表
     *
     * @param poiItem PoiItem
     * @param i i
     */
    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            setResult(2000, data);
//            if (requestCode == 1000 && resultCode == 2000) {
//                PoiItem poiItem = data.getParcelableExtra("PoiItem");
//                if (poiItem != null) {
//                    mEtDistrict.setText(poiItem.getProvinceName() + poiItem.getCityName()
//                            + poiItem.getAdName());
//                    mEtAddressDetails.setText(poiItem.getSnippet());
//                }
//            }
            finish();
        }
    }

    @OnClick({R.id.tv_search, R.id.tv_right_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                startActivityForResult(new Intent(mActivity, POISearchActivity.class), 1000);
                break;
            case R.id.tv_right_text:
                finish();
                break;
        }
    }
}
